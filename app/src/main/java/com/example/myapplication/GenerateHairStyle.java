package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.api.ApiPreviewImgService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.hairfast.GenerateHairResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateHairStyle extends AppCompatActivity {
    public static final String TAG=GenerateHairStyle.class.getName();

    private static final int MY_REQUEST_CODE = 10;
    private static int SELECT_PICTURE = 200;

    private static final String SelfImg = "SelfImg";
    private static final String ShapeImg = "ShapeImg";
    private static final String ColorImg = "ColorImg";
    private String TargetImg = null;

    private Button btSelfImg,btShapeImg,btColorImg,btUpload;
    private ImageView imgSelf,imgShape,imgColor,imgResult;
    private ImageView targetImageView;
    private TextView tvResult;

    private Uri selfUri,shapeUri,colorUri;

    private ProgressDialog progressDialog;
    private TokenManager tokenManager ;

    private ActivityResultLauncher<Intent> mActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult" );
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            return;
                        }
                        Uri uri = data.getData();
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imgSelf.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_hair_style);
        init();
        tokenManager = new TokenManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading....");
        btSelfImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser(imgSelf,SelfImg);
            }
        });

        btShapeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser(imgShape,ShapeImg);
            }
        });

        btColorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser(imgColor,ColorImg);
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selfUri!=null && shapeUri!=null && colorUri!=null){
                    uploadImage();
                }
            }
        });
    }
    private void init(){
        btSelfImg = findViewById(R.id.btSelfImg);
        btShapeImg = findViewById(R.id.btShapeImg);
        btColorImg = findViewById(R.id.btColorImg);
        btUpload = findViewById(R.id.btUpload);
        imgSelf = findViewById(R.id.selfImg);
        imgShape = findViewById(R.id.shapeImg);
        imgColor = findViewById(R.id.colorImg);
        imgResult = findViewById(R.id.resultImg);
        tvResult = findViewById(R.id.tvResult);
    }

    private String getRealPathFromURI(Context context, Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex( OpenableColumns.DISPLAY_NAME);

        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));

        File file = new File(context.getFilesDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();


            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }

            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getAbsolutePath());

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getAbsolutePath();
    }

    private void uploadImage() {
        progressDialog.show();
        String accessToken = tokenManager.getAccessToken();

        String selfImgRealPath = getRealPathFromURI(this,selfUri);
        Log.d(TAG,"selfImgRealPath: "+selfImgRealPath);
        File fileSelfImg = new File(selfImgRealPath);
        RequestBody selfImgBody = RequestBody.create(MultipartBody.FORM,fileSelfImg);
        MultipartBody.Part selfImgPart = MultipartBody.Part.createFormData("self_img",fileSelfImg.getName(),selfImgBody);

        String shapeImgRealPath = getRealPathFromURI(this,shapeUri);
        Log.d(TAG,"shapeImgRealPath: "+shapeImgRealPath);
        File fileShapeImg = new File(shapeImgRealPath);
        RequestBody shapeImgBody = RequestBody.create(MultipartBody.FORM,fileShapeImg);
        MultipartBody.Part shapeImgPart = MultipartBody.Part.createFormData("shape_img",fileShapeImg.getName(),shapeImgBody);

        String colorImgRealPath = getRealPathFromURI(this,colorUri);
        Log.d(TAG,"colorImgRealPath: "+colorImgRealPath);
        File fileColorImg = new File(colorImgRealPath);
        RequestBody colorImgBody = RequestBody.create(MultipartBody.FORM,fileColorImg);
        MultipartBody.Part colorImgPart = MultipartBody.Part.createFormData("color_img",fileColorImg.getName(),colorImgBody);

        ApiPreviewImgService.apiService.generateHair(accessToken,selfImgPart,shapeImgPart,colorImgPart).enqueue(new Callback<GenerateHairResponse>() {
            @Override
            public void onResponse(Call<GenerateHairResponse> call, Response<GenerateHairResponse> response) {
                Toast.makeText(GenerateHairStyle.this,"Upload success",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                GenerateHairResponse resp =  response.body();
                if (resp != null){
                    tvResult.setText(resp.getHairResponse().toString());
                }
                Toast.makeText(GenerateHairStyle.this,"Please wait notification for the result\n sometimes it takes one minute ",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<GenerateHairResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GenerateHairStyle.this,"Upload failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void imageChooser(ImageView targetImageView,String typeImage) {
        this.targetImageView = targetImageView;
        this.TargetImg = typeImage;
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    private ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        targetImageView.setImageBitmap(
                                selectedImageBitmap);
                        switch (TargetImg){
                            case SelfImg:
                                selfUri = selectedImageUri;
                                break;
                            case ShapeImg:
                                shapeUri = selectedImageUri;
                                break;
                            case ColorImg:
                                colorUri = selectedImageUri;
                                break;
                        }
                    }
                }
            });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode!=MY_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openGallary();
            }
        }
    }

    private void openGallary() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }
}
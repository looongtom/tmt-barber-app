package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.adapter.ResultRecycleViewAdapter;
import com.example.myapplication.api.ApiResultService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.account.Account;
import com.example.myapplication.model.booking.response.BookingResponse;
import com.example.myapplication.model.result.Result;
import com.example.myapplication.model.result.response.CreateResultResponseData;
import com.example.myapplication.model.result.response.ImageResult;
import com.example.myapplication.model.result.response.ResultDetailResponse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageV2 extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private Button btUploadImg,btBack;
    private ImageView btAddMoreImg;
    private RecyclerView recyclerView;
    private TokenManager tokenManager ;
    private ResultRecycleViewAdapter adapter;
    private List<Result> listResult = new ArrayList<>();
    private List<File> listResultFile = new ArrayList<>();
    private List<Uri> listUri = new ArrayList<>();
    private BookingResponse booking;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        if (!hasPermissions()) {
            requestPermissions();
        }
        if (Build.VERSION.SDK_INT >= 30){
            if (!Environment.isExternalStorageManager()){
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image_v2);
        tokenManager = new TokenManager(this);
        adapter=new ResultRecycleViewAdapter(this);
        btUploadImg = findViewById(R.id.btChoosePhoto);
        btBack = findViewById(R.id.btBack);
        btAddMoreImg = findViewById(R.id.btAddMore);

        recyclerView = findViewById(R.id.rvImageResult);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        Intent intent = getIntent();
        booking = (BookingResponse) intent.getSerializableExtra("booking");
        sendApiGetDetailResult(booking.getId());

        recyclerView.setAdapter(adapter);

        if (roleId == Account.RoleUser){
            btAddMoreImg.setVisibility(View.GONE);
            btUploadImg.setVisibility(View.GONE);
        }

        btAddMoreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendApiCreateResult();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath() };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            System.out.println("Uri:"+ uri.toString());
                            listResult.add(new Result( uri.toString() ));
                            listResultFile.add(getPathFromUri(this, uri));
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.toString());
                        adapter.setList(listResult);
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                // Proceed with file access
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private String getRealPathFromURI(Context mContext, Uri contentURI) {
        String result = null;
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Video.Media.DATA};
            ContentResolver mContentResolver = mContext.getContentResolver();
            String mime = mContentResolver.getType(contentURI);
            cursor = mContentResolver.query(contentURI, proj, null, null, null);
            if (cursor == null) {
                return null;
            } else {
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (column_index > -1)
                    result = cursor.getString(column_index);
                cursor.close();
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return result;
    }

    public static File getFileFromUri(Context context, Uri uri) {
        String filePath = null;

        // Check if the URI is a document URI
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            String[] split = docId.split(":");
            String type = split[0];

            if ("raw".equalsIgnoreCase(type)) {
                // For raw file paths
                filePath = split[1];
            } else if ("primary".equalsIgnoreCase(type)) {
                // Handle files in primary storage
                filePath = "/storage/emulated/0/" + split[1];
            }
        } else if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            // Handle content URIs
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else if (uri.getScheme() != null && uri.getScheme().equals("file")) {
            // Handle file URIs
            filePath = uri.getPath();
        }

        // Return the file or null if the file path could not be resolved
        System.out.println("File path: " + filePath);
        return filePath != null ? new File(filePath) : null;
    }

    public static File getPathFromUri(Context context, Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            String[] split = documentId.split(":");
            String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                path = "/sdcard/" + split[1];
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    path = cursor.getString(columnIndex);
                }
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        System.out.println("URI path: " + path);
        return path != null ? new File(path) : null;
    }



    private void sendApiCreateResult(){
        String accessToken = tokenManager.getAccessToken();
        List<MultipartBody.Part> listImages = convertUrlsToMultipart(listResultFile);
        RequestBody requestBarberId = RequestBody.create(booking.getBarberId()+"",MediaType.parse("text/plain"));
        RequestBody requestUserId = RequestBody.create(booking.getCustomerId()+"",MediaType.parse("text/plain"));
        RequestBody requestBookingId = RequestBody.create(booking.getId()+"",MediaType.parse("text/plain"));
        RequestBody requestDescription = RequestBody.create("description",MediaType.parse("text/plain"));

        ApiResultService.API_RESULT_SERVICE.createResultV2(accessToken,listImages,requestBarberId,requestUserId,requestBookingId,requestDescription).enqueue(new Callback<CreateResultResponseData>() {

            @Override
            public void onResponse(Call<CreateResultResponseData> call, Response<CreateResultResponseData> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UploadImageV2.this, "Upload image success", Toast.LENGTH_SHORT).show();
                    CreateResultResponseData createResultResponse = response.body();
                    //log the response
                    Log.d("Response", createResultResponse.getData().toString());
                    }
                finish();
                }
            @Override
            public void onFailure(Call<CreateResultResponseData> call, Throwable t) {
                Toast.makeText(UploadImageV2.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static List<MultipartBody.Part> convertUrlsToMultipart( List<File> listResult) {
        List<MultipartBody.Part> imageParts = new ArrayList<>();

        for (File file : listResult) {
            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(file,MediaType.parse("image/*"));
                MultipartBody.Part part = MultipartBody.Part.createFormData("list_img", file.getName(), requestBody);
                imageParts.add(part);
            } else {
                System.out.println("File not found: " + file.getName());
            }
        }

        // Convert list to array
        return imageParts;
    }

    public static Map<String, RequestBody> prepareFileParameters(List<Result> listResult) {
        Map<String, RequestBody> parameters = new HashMap<>();

        int index = 0;
        for (Result result : listResult) {
            File file = new File(result.getUrl()); // Assuming the URL points to a local file path
            if (file.exists()) {
                RequestBody fileBody = RequestBody.create(file,MediaType.parse("application/octet-stream"));
                parameters.put("list_img[" + index + "]", fileBody); // Unique key for each file
                index++;
            }
        }
        return parameters;
    }

    private void sendApiGetDetailResult(Integer id){
        String accessToken = tokenManager.getAccessToken();
        ApiResultService.API_RESULT_SERVICE.getResultByBookingId(accessToken,id).enqueue(new Callback<ResultDetailResponse>(){

            @Override
            public void onResponse(Call<ResultDetailResponse> call, Response<ResultDetailResponse> response) {
                if(response.isSuccessful()){
                    ResultDetailResponse resultDetailResponse = response.body();

                    if(resultDetailResponse != null){
                        for (ImageResult imageResult:
                                resultDetailResponse.getData().getList()) {
                            listResult.add(new Result(imageResult.getId(), imageResult.getUrl()));
                        };
                        adapter.setList(listResult);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDetailResponse> call, Throwable t) {
                Toast.makeText(UploadImageV2.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
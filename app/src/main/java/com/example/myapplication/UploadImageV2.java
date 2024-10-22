package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.adapter.ResultRecycleViewAdapter;
import com.example.myapplication.api.ApiResultService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.result.Result;
import com.example.myapplication.model.result.response.ImageResult;
import com.example.myapplication.model.result.response.ResultDetailResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageV2 extends AppCompatActivity {
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private Button btChooseImage,btBack;
    private RecyclerView recyclerView;
    private TokenManager tokenManager ;
    private ResultRecycleViewAdapter adapter;
    private List<Result> listResult = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image_v2);
        tokenManager = new TokenManager(this);
        adapter=new ResultRecycleViewAdapter(this);
        btChooseImage = findViewById(R.id.btChoosePhoto);
        btBack = findViewById(R.id.btBack);

        recyclerView = findViewById(R.id.rvImageResult);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        Intent intent = getIntent();
        int bookingId = intent.getIntExtra("bookingId", 0);
        sendApiGetDetailResult(bookingId);

        recyclerView.setAdapter(adapter);


        btChooseImage.setOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
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
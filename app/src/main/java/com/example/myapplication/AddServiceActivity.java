package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.myapplication.dal.ServiceDataSource;
import com.example.myapplication.model.Service;

import java.util.HashMap;
import java.util.Map;

public class AddServiceActivity extends AppCompatActivity {
    private EditText eName, ePrice, eDescription;
    private Button btnAdd, btnChooseImage,btnCancel;
    private ImageView img;
    private Uri imagePath=null;
    private Service service;

    private int SELECT_PICTURE = 200;

    private static boolean isMediaManagerInitialized = false;

    ServiceDataSource serviceDataSource = new ServiceDataSource(AddServiceActivity.this);
    private void cloudinaryConfig(){
        if (!isMediaManagerInitialized) {
            Map config = new HashMap();
            config.put("cloud_name", "dsjuckdxu");
            config.put("api_key", "973371356842627");
            config.put("api_secret", "zJ5bMJgfkw3XBdyBocwO8Kgs1us");
            config.put("secure", true);
            MediaManager.init(this, config);
            isMediaManagerInitialized = true;
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cloudinaryConfig();
        setContentView(R.layout.activity_add_service);
        service = new Service();

        eName = findViewById(R.id.eName);
        ePrice = findViewById(R.id.ePrice);
        eDescription = findViewById(R.id.eDescription);
        btnAdd = findViewById(R.id.btAdd);
        img = findViewById(R.id.imgView);
        btnCancel = findViewById(R.id.btCancel);
        btnChooseImage = findViewById(R.id.BSelectImage);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagePath!=null){
                    MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            String imageUrl = resultData.get("url").toString();
                            String name = eName.getText().toString();
                            String price = ePrice.getText().toString();
                            String description = eDescription.getText().toString();
                            if(name.isEmpty() || price.isEmpty() || description.isEmpty() || imagePath == null){
                                Toast.makeText(AddServiceActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                            }
                            try{
                                Service service=new Service(name, Double.parseDouble(price), description,imageUrl);
                                serviceDataSource.addService(service);
                                finish();
                            }catch (NumberFormatException numberFormatException){
                                Toast.makeText(AddServiceActivity.this, "Price must be filled with number", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    }).dispatch();
                }else{
                    Toast.makeText(AddServiceActivity.this, "Please choose image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imagePath= selectedImageUri;
                    img.setImageURI(selectedImageUri);
                }
            }
        }
    }

}
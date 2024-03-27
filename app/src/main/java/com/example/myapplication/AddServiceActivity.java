package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddServiceActivity extends AppCompatActivity {
    private EditText edtName, edtPrice, edtInfo;
    private ImageView img;
    private Button btAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        initView();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();

            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
                String info = edtInfo.getText().toString();
                String imgPath=img.getContext().toString();

                Toast.makeText(AddServiceActivity.this, name+"-"+price+"-"+info+"-"+imgPath, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void requestPermissions() {
        if(ContextCompat.checkSelfPermission(AddServiceActivity.this, android.Manifest.permission.READ_MEDIA_IMAGES)
                == PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else
        {
            ActivityCompat.requestPermissions(AddServiceActivity.this, new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES
            },1);
        }
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    private void initView(){
        edtName = findViewById(R.id.eName);
        edtPrice = findViewById(R.id.ePrice);
        edtInfo = findViewById(R.id.eDescription);
        img = findViewById(R.id.imgView);
        btAdd=findViewById(R.id.btAdd);
    }
}
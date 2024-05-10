package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class UploadImage extends AppCompatActivity {
    private ImageView imgView1, imgView2, imgView3, imgView4;
    private Button btChoose;
    private static final int pic_id1 = 1, pic_id2 = 2, pic_id3 = 3, pic_id4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imgView1 = findViewById(R.id.click_image1);
        imgView2 = findViewById(R.id.click_image2);
        imgView3 = findViewById(R.id.click_image3);
        imgView4 = findViewById(R.id.click_image4);
        btChoose = findViewById(R.id.btChoosePhoto);

        imgView1.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id1);
        });

        imgView2.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id2);
        });

        imgView3.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id3);
        });

        imgView4.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id4);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id1) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            imgView1.setImageBitmap(photo);
        }
        if (requestCode == pic_id2) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            imgView2.setImageBitmap(photo);
        }

        if (requestCode == pic_id3) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            imgView3.setImageBitmap(photo);
        }

        if (requestCode == pic_id4) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            imgView4.setImageBitmap(photo);
        }
    }
}
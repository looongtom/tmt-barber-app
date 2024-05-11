package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;
import com.example.myapplication.dal.BookingDataSource;
import com.example.myapplication.dal.ResultDataSource;
import com.example.myapplication.model.Booking;
import com.example.myapplication.model.Result;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadImage extends AppCompatActivity {
    private ImageView imgView1, imgView2, imgView3, imgView4;
    private Button btChoose,btBack;
    private static final int pic_id1 = 1, pic_id2 = 2, pic_id3 = 3, pic_id4 = 4;
    private Booking booking;
    private Result result;
    private static boolean isMediaManagerInitialized = false;

    private String imgFront, imgBack, imgLeft, imgRight;

    private void cloudinaryConfig() {
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
        setContentView(R.layout.activity_upload_image);
        cloudinaryConfig();
        result = new Result();

        Intent intent = getIntent();
        Integer bookingId = intent.getIntExtra("bookingId", 0);
        int roleId = intent.getIntExtra("roleId", -1);
        BookingDataSource bookingDataSource = new BookingDataSource(this);
        booking = bookingDataSource.getById(bookingId);
        System.out.println("Log from UploadImage: "+booking.toString());


        imgView1 = findViewById(R.id.click_image1);
        imgView2 = findViewById(R.id.click_image2);
        imgView3 = findViewById(R.id.click_image3);
        imgView4 = findViewById(R.id.click_image4);
        btChoose = findViewById(R.id.btChoosePhoto);
        btChoose.setVisibility(roleId != 3 ? View.VISIBLE : View.GONE);
        btBack = findViewById(R.id.btBack);

        if (booking.getResultId() != null && booking.getResultId() != 0) {
            ResultDataSource resultDataSource = new ResultDataSource(this);
            result = resultDataSource.getById(booking.getResultId());
            if (result.getImageFront() != null)
                Picasso.get().load(result.getImageFront()).into(imgView1);
            if (result.getImageBack() != null)
                Picasso.get().load(result.getImageBack()).into(imgView2);
            if (result.getImageLeft() != null)
                Picasso.get().load(result.getImageLeft()).into(imgView3);
            if (result.getImageRight() != null)
                Picasso.get().load(result.getImageRight()).into(imgView4);
        }

        if (roleId != 3) {

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
            btChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imgView1.getDrawable() == null || imgView2.getDrawable() == null || imgView3.getDrawable() == null || imgView4.getDrawable() == null) {
                        Toast.makeText(UploadImage.this, "Chọn đầy đủ 4 ảnh", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Convert the Bitmap to a byte array
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    Bitmap bitmap1 = ((BitmapDrawable) imgView1.getDrawable()).getBitmap();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
                    byte[] imageData1 = byteArrayOutputStream1.toByteArray();

                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    Bitmap bitmap2 = ((BitmapDrawable) imgView2.getDrawable()).getBitmap();
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                    byte[] imageData2 = byteArrayOutputStream2.toByteArray();

                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    Bitmap bitmap3 = ((BitmapDrawable) imgView3.getDrawable()).getBitmap();
                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream3);
                    byte[] imageData3 = byteArrayOutputStream3.toByteArray();

                    ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                    Bitmap bitmap4 = ((BitmapDrawable) imgView4.getDrawable()).getBitmap();
                    bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream4);
                    byte[] imageData4 = byteArrayOutputStream4.toByteArray();

                    ResultDataSource resultDataSource = new ResultDataSource(UploadImage.this);
                    Result uploadResult = resultDataSource.insertResult(UploadImage.this, result);
                    result = uploadResult;

                    booking.setResultId(uploadResult.getId());
                    bookingDataSource.updateBooking(booking);
                    booking = bookingDataSource.getById(bookingId);

                    // Upload the image to Cloudinary
                    MediaManager.get().upload(imageData1).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            // Called when the upload request starts
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            // Called periodically during the upload process to provide updates on the upload progress
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            // Called when the upload successfully completes
                            String image = resultData.get("secure_url").toString();
                            result.setImageFront(image);
                            resultDataSource.updateResult(UploadImage.this, result);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            // Called when an error occurs during the upload
                            Toast.makeText(UploadImage.this, "Upload error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // Called when the upload is rescheduled to be tried again later
                        }
                    }).dispatch();
                    MediaManager.get().upload(imageData2).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            // Called when the upload request starts
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            // Called periodically during the upload process to provide updates on the upload progress
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            // Called when the upload successfully completes
                            String image = resultData.get("secure_url").toString();
                            result.setImageBack(image);
                            resultDataSource.updateResult(UploadImage.this, result);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            // Called when an error occurs during the upload
                            Toast.makeText(UploadImage.this, "Upload error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // Called when the upload is rescheduled to be tried again later
                        }
                    }).dispatch();
                    MediaManager.get().upload(imageData3).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            // Called when the upload request starts
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            // Called periodically during the upload process to provide updates on the upload progress
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            // Called when the upload successfully completes
                            String image = resultData.get("secure_url").toString();
                            result.setImageLeft(image);
                            resultDataSource.updateResult(UploadImage.this, result);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            // Called when an error occurs during the upload
                            Toast.makeText(UploadImage.this, "Upload error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // Called when the upload is rescheduled to be tried again later
                        }
                    }).dispatch();
                    MediaManager.get().upload(imageData4).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            // Called when the upload request starts
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            // Called periodically during the upload process to provide updates on the upload progress
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            // Called when the upload successfully completes
                            Toast.makeText(UploadImage.this, "Upload successful", Toast.LENGTH_SHORT).show();

                            String image = resultData.get("secure_url").toString();
                            result.setImageRight(image);
                            resultDataSource.updateResult(UploadImage.this, result);
                            finish();
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            // Called when an error occurs during the upload
                            Toast.makeText(UploadImage.this, "Upload error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // Called when the upload is rescheduled to be tried again later
                        }
                    }).dispatch();

                }
            });
        }

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
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
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudinary.android.MediaManager;
import com.example.myapplication.model.hairfast.HairFastWS;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailHairFastActivity extends AppCompatActivity {

    private ImageView resultImg,selfImg,shapeImg,colorImg;
    private Button btnBack;
    private HairFastWS hairFastWS;
    private static boolean isMediaManagerInitialized = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hair_fast);
        init();
        cloudinaryConfig();
        hairFastWS = (HairFastWS) getIntent().getSerializableExtra("hairFastWS");
        System.out.println("hairFastWS:"+hairFastWS.toString());

        Glide.with(this)
                .load(hairFastWS.getGeneratedImgCloud())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading) // Placeholder image
                        .error(R.drawable.error_loading) // Error image in case of loading failure
                )
                .into(resultImg);
        Glide.with(this)
                .load(hairFastWS.getSelfImgCloud())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading) // Placeholder image
                        .error(R.drawable.error_loading) // Error image in case of loading failure
                )
                .into(selfImg);
        Glide.with(this)
                .load(hairFastWS.getShapeImgCloud())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading) // Placeholder image
                        .error(R.drawable.error_loading) // Error image in case of loading failure
                )
                .into(shapeImg);
        Glide.with(this)
                .load(hairFastWS.getColorImgCloud())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading) // Placeholder image
                        .error(R.drawable.error_loading) // Error image in case of loading failure
                )
                .into(colorImg);

//        Picasso.get().load(hairFastWS.getGeneratedImgCloud()).into(resultImg);
//        Picasso.get().load(hairFastWS.getSelfImgCloud()).into(selfImg);
//        Picasso.get().load(hairFastWS.getShapeImgCloud()).into(shapeImg);
//        Picasso.get().load(hairFastWS.getColorImgCloud()).into(colorImg);

        btnBack.setOnClickListener(v -> {
            finish();
        });

    }

    private void init(){
        resultImg = findViewById(R.id.resultImg);
        selfImg = findViewById(R.id.selfImg);
        shapeImg = findViewById(R.id.shapeImg);
        colorImg = findViewById(R.id.colorImg);
        btnBack = findViewById(R.id.btBack);
    }
}
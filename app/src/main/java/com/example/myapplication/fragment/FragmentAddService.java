package com.example.myapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragmentAddService extends Fragment {
    private EditText edtName, edtPrice, edtInfo;
    private ImageView img;
    private Button btAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                requestPermissions();

            }
        });

    }
    private void initView(View view){
        edtName = view.findViewById(R.id.eName);
        edtPrice = view.findViewById(R.id.ePrice);
        edtInfo = view.findViewById(R.id.eDescription);
        img = view.findViewById(R.id.imgView);
        btAdd=view.findViewById(R.id.btAdd);
    }
}

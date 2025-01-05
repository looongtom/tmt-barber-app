package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.api.ApiAccountService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.account.request.ChangePasswordRequest;
import com.example.myapplication.model.account.response.ChangePasswordResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText txtOldPassword, txtNewPassword, txtConfirmPassword;
    private Button btnChangePassword;
    private TokenManager tokenManager ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        tokenManager = new TokenManager(this);

        txtOldPassword = findViewById(R.id.txtCurrentPass);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtRepeatPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtConfirmPassword.getText().toString().equals(txtNewPassword.getText().toString())) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }

                else if (txtOldPassword.getText().toString().equals(txtNewPassword.getText().toString())) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới phải khác mật khẩu cũ", Toast.LENGTH_SHORT).show();
                } else {
                    // Change password
                    changePassword();
                }
            }
        });
    }

    private void changePassword() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        // Change password
        String username = sharedPreferences.getString("username", "");
        String oldPassword = txtOldPassword.getText().toString();
        String newPassword = txtNewPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(username, oldPassword, newPassword);
        String accessToken = tokenManager.getAccessToken();

        ApiAccountService.API_ACCOUNT_SERVICE.changePassword("Bearer " + accessToken, changePasswordRequest).enqueue(new Callback<ChangePasswordResponse>() {

            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful()) {
                    // Change password success
                    Toast.makeText(ChangePasswordActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this, "Error change password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Error change password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
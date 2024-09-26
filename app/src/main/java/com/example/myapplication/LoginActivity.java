package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.api.ApiAccountService;
import com.example.myapplication.auth.TokenManager;
import com.example.myapplication.model.account.response.GetProfileResponse;
import com.example.myapplication.model.account.request.LoginRequest;
import com.example.myapplication.model.account.response.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputEditText txtUsername;
    TextInputEditText txtPassword;
    private ProgressBar progressBar;
    private TextView text1;
    private TextView textSignUp;
    boolean isProgressVisible = false;
    private TokenManager tokenManager ;


    public void init(){
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        text1 = findViewById(R.id.text1);
        textSignUp = findViewById(R.id.textSignUp);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        tokenManager = new TokenManager(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = findViewById(R.id.progressBar);
                sendApiLogin();
            }
        });

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendApiLogin(){
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        LoginRequest loginRequest = new LoginRequest(username, password);
        ApiAccountService.API_ACCOUNT_SERVICE.login(loginRequest).enqueue(new Callback<LoginResponse>(){

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();

                    if (isProgressVisible) {
                        progressBar.setVisibility(View.GONE);
                        isProgressVisible = false;
                    }else {
                        isProgressVisible = true;
                        progressBar.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.INVISIBLE);
                        textSignUp.setVisibility(View.INVISIBLE);
                    }

                    tokenManager.saveAccessToken(loginResponse.getAccessToken());
                    sendApiGetProfile();
                }else{
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendApiGetProfile() {
        String username = txtUsername.getText().toString()+"@gmail.com";
        String accessToken = tokenManager.getAccessToken();
        ApiAccountService.API_ACCOUNT_SERVICE.getProfile(accessToken,username).enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if(response.isSuccessful()){
                    GetProfileResponse account = response.body();

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", txtUsername.getText().toString());
                    editor.putInt("userId", account.getAccount().getId());
                    editor.putInt("roleId", account.getAccount().getRole());
                    editor.putString("fullName", account.getAccount().getFullName());
                    editor.putString("email", account.getAccount().getEmail());
                    editor.putString("phone", account.getAccount().getPhoneNumber());
                    editor.putString("avatar", account.getAccount().getAvatar());

                    editor.commit();
                    if (account.getAccount().getRole() == 0) {
                        Toast.makeText(LoginActivity.this, "Bạn đang đăng nhập với quyền Admin", Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    } else if (account.getAccount().getRole() == 1) {
                        Toast.makeText(LoginActivity.this, "Bạn đang đăng nhập với quyền User", Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    } else if (account.getAccount().getRole() == 2) {
                        Toast.makeText(LoginActivity.this, "Bạn đang đăng nhập với quyền Barber", Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    }
                    loginSuccess();
                }else{
                    Toast.makeText(LoginActivity.this, "Get profile fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Get profile error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
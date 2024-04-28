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

import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.model.Account;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputEditText txtUsername;
    TextInputEditText txtPassword;
    private ProgressBar progressBar;
    private TextView text1;
    private TextView textSignUp;
    boolean isProgressVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        text1 = findViewById(R.id.text1);
        textSignUp = findViewById(R.id.textSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        AccountDataSource accountDataSource = new AccountDataSource(LoginActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = findViewById(R.id.progressBar);

                boolean isCheckAccount = accountDataSource.checkAccount(txtUsername.getText().toString(), txtPassword.getText().toString());

                if (isProgressVisible || !isCheckAccount) {
                    progressBar.setVisibility(View.GONE);
                    isProgressVisible = false;
                } else {
                    isProgressVisible = true;
                    progressBar.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.INVISIBLE);
                    textSignUp.setVisibility(View.INVISIBLE);
                }

                if (!isCheckAccount) {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                } else {
                    //Lưu thông tin của người dùng vừa nhập vào SharedPreferences để hiển thị lên giao diện
                    int userId = accountDataSource.getUserIdByUsername(txtUsername.getText().toString());
                    int roleId = accountDataSource.getRoleIdByAccountId(userId);
                    Account account = accountDataSource.getAccountById(userId);

//                    sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserData",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", txtUsername.getText().toString());
                    editor.putInt("userId", userId);
                    editor.putInt("roleId", roleId);
                    editor.putString("fullName", account.getName());
                    editor.putString("email", account.getEmail());
                    editor.putString("phone", account.getPhone());
                    editor.putString("gender", account.getGender());
                    editor.putString("dob", account.getDateOfBirth());
                    editor.putString("avatar", account.getAvatar());

                    editor.commit();

                    if (roleId == 1) {
                        Toast.makeText(LoginActivity.this, "Bạn đang đăng nhập với quyền Admin", Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    } else if (roleId == 2) {
                        Toast.makeText(LoginActivity.this, "Bạn đang đăng nhập với quyền Staff", Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    }
                    loginSuccess();
                }
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

    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.model.Account;
import com.example.myapplication.verification.VerifyOTP;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    EditText txtUsername;
    EditText txtPassword;
    EditText txtEmail;
    EditText txtDateOfBirth;
    EditText txtName;
    EditText txtPhone;
    ImageView calendarImage;
    Button btnSignUp;
    ProgressBar progressBar;
    TextView text1;
    TextView textSignIn;
    Account acc = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        txtName = findViewById(R.id.txtName);
        btnSignUp = findViewById(R.id.btnSignUp);
        calendarImage = findViewById(R.id.calendarImageView);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);

        progressBar = findViewById(R.id.progressBar);
        text1 = findViewById(R.id.text1);
        textSignIn = findViewById(R.id.textSignIn);
        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
        txtDateOfBirth.setShowSoftInputOnFocus(false);
        txtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        handleGender();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acc.setName(txtName.getText().toString());
                acc.setUsername(txtUsername.getText().toString());
                acc.setPassword(txtPassword.getText().toString());
                acc.setEmail(txtEmail.getText().toString());
                acc.setDateOfBirth(txtDateOfBirth.getText().toString());
                acc.setPhone(txtPhone.getText().toString());
                AccountDataSource accountDataSource = new AccountDataSource(RegisterActivity.this);

                if (txtUsername.getText().toString().isEmpty() ||
                        txtName.getText().toString().isEmpty() ||
                        txtPassword.getText().toString().isEmpty() ||
                        txtEmail.getText().toString().isEmpty() ||
                        txtDateOfBirth.getText().toString().isEmpty()||
                        txtPhone.getText().toString().isEmpty()) {

                    progressBar.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                    textSignIn.setVisibility(View.VISIBLE);

                    Toast.makeText(RegisterActivity.this, "Please enter complete information !", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(txtEmail.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                    textSignIn.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "Please enter correct email format !", Toast.LENGTH_SHORT).show();
                } else {
                    int findAcount= accountDataSource.getUserIdByUsername(txtUsername.getText().toString());
                    if(findAcount!=-1){
                        progressBar.setVisibility(View.GONE);
                        text1.setVisibility(View.VISIBLE);
                        textSignIn.setVisibility(View.VISIBLE);
                        Toast.makeText(RegisterActivity.this, "Username already exists !", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.INVISIBLE);
                    textSignIn.setVisibility(View.INVISIBLE);
                    btnSignUp.setVisibility(View.INVISIBLE);

//                    accountDataSource.addAccount(acc);
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    //Clear các hoạt động trước đó và chuyển sang MainActivity
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+84"+txtPhone.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            RegisterActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    btnSignUp.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    btnSignUp.setVisibility(View.VISIBLE);
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verification, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    btnSignUp.setVisibility(View.VISIBLE);
                                    Intent intent=new Intent(RegisterActivity.this, VerifyOTP.class);
                                    intent.putExtra("mobile", txtPhone.getText().toString().substring(1));
                                    intent.putExtra("verificationId", verification);
                                    intent.putExtra("account", acc);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Toast.makeText(RegisterActivity.this, "Mã OTP đã được gửi đến thiết bị", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(intent);
                                }
                            }
                    );
                }
            }
        });

        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void showDatePicker(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "";
                if (mMonth > 8) {
                    date = dayOfMonth+ "-" + (month + 1)+ "-"+year;
                } else {
                    date = dayOfMonth + "-0" + (month + 1)+ "-"+year;
                }
                txtDateOfBirth.setText(date);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void handleGender() {
        Spinner genderSpinner = findViewById(R.id.spinner);
//        TextView selectedGenderTextView = findViewById(R.id.selectedGenderTextView);

        // Tạo một danh sách các lựa chọn giới tính
        ArrayList<String> genderOptions = new ArrayList<>();
        genderOptions.add("Nam");
        genderOptions.add("Nữ");

        // Tạo một ArrayAdapter để liên kết dữ liệu giữa danh sách và Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán ArrayAdapter vào Spinner
        genderSpinner.setAdapter(adapter);

        // Thiết lập sự kiện lắng nghe khi người dùng chọn một lựa chọn giới tính
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị giới tính được chọn
                String selectedGender = genderOptions.get(position);
//                selectedGenderTextView.setText(selectedGender);

                // Hiển thị giá trị giới tính trong TextView
                acc.setGender(selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có lựa chọn nào được chọn
            }
        });
    }

    // Kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}


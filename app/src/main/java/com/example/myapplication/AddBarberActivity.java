package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.time.LocalDate;
import java.util.ArrayList;

public class AddBarberActivity extends AppCompatActivity {
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtEmail;
    private EditText txtDateOfBirth;
    private EditText txtName;
    private ImageView calendarImage;
    private Button btAdd;
    private Account acc = new Account();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barber);
        initView();

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        handleGender();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acc.setName(txtName.getText().toString());
                acc.setUsername(txtUsername.getText().toString());
                acc.setPassword(txtPassword.getText().toString());
                acc.setEmail(txtEmail.getText().toString());
                acc.setDateOfBirth(txtDateOfBirth.getText().toString());

                AccountDataSource accountDataSource=new AccountDataSource(AddBarberActivity.this);

                if(txtUsername.getText().toString().isEmpty() ||
                        txtName.getText().toString().isEmpty() ||
                        txtPassword.getText().toString().isEmpty() ||
                        txtEmail.getText().toString().isEmpty() ||
                        txtDateOfBirth.getText().toString().isEmpty()){

                    Toast.makeText(AddBarberActivity.this, "Please enter complete information !", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidEmail(txtEmail.getText().toString())){
                    Toast.makeText(AddBarberActivity.this, "Please enter correct email format !", Toast.LENGTH_SHORT).show();
                }
                else{
                    acc.setRoleId(2);
                    accountDataSource.addAccount(acc);
                    Toast.makeText(AddBarberActivity.this, "Add successfully !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    private void initView() {
        txtName = findViewById(R.id.txtName);
        btAdd = findViewById(R.id.btAdd);
        calendarImage = findViewById(R.id.calendarImageView);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtEmail = findViewById(R.id.txtEmail);
    }

    public void showDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // Xử lý giá trị năm sinh được chọn ở đây
                LocalDate date_of_birth = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date_of_birth = LocalDate.of(year, month +1, dayOfMonth);
                }
                txtDateOfBirth.setText(date_of_birth.toString());
            }
        }, 2000, 2, 2);
        datePickerDialog.show();
    }

    public void handleGender(){
        Spinner genderSpinner = findViewById(R.id.spinner);

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
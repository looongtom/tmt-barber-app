package com.example.myapplication.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ChooseTimeSlotActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.model.Account;

import java.time.LocalDate;
import java.util.Calendar;

public class FragmentAccount extends Fragment {
    private Button btLogout;
    private EditText txtName, txtUsername, txtEmail, txtDateOfBirth,txtPhone;
    private Spinner spinnerGender;
    private Button btnUpdate;
    private String[] genders ;
    private ArrayAdapter<String> adapter;
    private ImageView calendarImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AccountDataSource accountDataSource = new AccountDataSource(getContext());
        genders = getResources().getStringArray(R.array.gender_options);

        calendarImage = view.findViewById(R.id.calendarImageView);
        txtName = view.findViewById(R.id.txtName);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtDateOfBirth = view.findViewById(R.id.txtDateOfBirth);
        spinnerGender = view.findViewById(R.id.spinner);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btLogout = view.findViewById(R.id.btnLogOut);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        Integer userId = sharedPreferences.getInt("userId", 0);
        String name = sharedPreferences.getString("fullName", "");
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String dateOfBirth = sharedPreferences.getString("dob", "");
        String avatar = sharedPreferences.getString("avatar", "");
        String gender = sharedPreferences.getString("gender", "");
        String phone= sharedPreferences.getString("phone", "");
        txtName.setText(name);
        txtUsername.setText(username);
        txtEmail.setText(email);
        txtDateOfBirth.setText(dateOfBirth);
        txtPhone.setText(phone);

        //set value for spinner
        if (gender.equals("Nam"))
            spinnerGender.setSelection(0);
        else
            spinnerGender.setSelection(1);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update user info
                boolean validEmail = isValidEmail(txtEmail.getText().toString());
                if (validEmail) {
                    String name = txtName.getText().toString();
                    String username = txtUsername.getText().toString();
                    String email = txtEmail.getText().toString();
                    String dateOfBirth = txtDateOfBirth.getText().toString();
                    String phone= txtPhone.getText().toString();
                    String gender= spinnerGender.getSelectedItem().toString();
                    Account account = new Account();

                    account.setId(userId);
                    account.setName(name);
                    account.setUsername(username);
                    account.setEmail(email);
                    account.setDateOfBirth(dateOfBirth);
                    account.setPhone(phone);
                    account.setGender(gender);

                    accountDataSource.updateAccount(account);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("username", username);
                    editor.putString("email", email);
                    editor.putString("dob", dateOfBirth);
                    editor.putString("phone", phone);
                    editor.putString("gender", spinnerGender.getSelectedItem().toString());
                    editor.apply();

                    Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtDateOfBirth.setShowSoftInputOnFocus(false);
        txtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

    }
    public void showDatePicker(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}

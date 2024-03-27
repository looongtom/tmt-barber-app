package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.myapplication.dal.AccountDataSource;
import com.example.myapplication.model.Account;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateDeleteBarberActivity extends AppCompatActivity {
    private ImageView calendarImage, avatar;
    private TextView profileName;
    private EditText txtName, txtUsername, txtAboutMe, txtEmail, txtDateOfBirth;
    private Button btUpdate, btCancel;
    private Account acc = new Account();
    private int SELECT_PICTURE = 200;
    private Uri imagePath = null;
    private static boolean isMediaManagerInitialized = false;
    private AccountDataSource accountDataSource = new AccountDataSource(UpdateDeleteBarberActivity.this);

    private static boolean isChooseImage = false;

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
        setContentView(R.layout.activity_update_delete_barber);

        initView();

        Intent intent = getIntent();
        acc = (Account) intent.getSerializableExtra("account");

        txtName.setText(acc.getName());
        txtUsername.setText(acc.getUsername());
        txtAboutMe.setText(acc.getAbout());
        txtEmail.setText(acc.getEmail());
        txtDateOfBirth.setText(acc.getDateOfBirth());
        profileName.setText(acc.getName());

        String fileImage = acc.getAvatar();
        if(fileImage!=null)imagePath = Uri.parse(fileImage);
        if (imagePath != null && !imagePath.toString().isEmpty())
            isChooseImage = true;

        if (fileImage != null) Picasso.get().load(fileImage).resize(129, 110).into(avatar);
        else avatar.setImageResource(R.drawable.barber_man);

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        handleGender();
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePath != null) {
                    if (isChooseImage) {
                        String name = txtName.getText().toString();
                        String username = txtUsername.getText().toString();
                        String about = txtAboutMe.getText().toString();
                        String email = txtEmail.getText().toString();
                        String dateOfBirth = txtDateOfBirth.getText().toString();
                        String image = imagePath.toString();
                        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || dateOfBirth.isEmpty()) {
                            Toast.makeText(UpdateDeleteBarberActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        }
                        if (isValidEmail(email) == false) {
                            Toast.makeText(UpdateDeleteBarberActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                        } else {
                            acc.setName(name);
                            acc.setUsername(username);
                            acc.setAbout(about);
                            acc.setEmail(email);
                            acc.setDateOfBirth(dateOfBirth);
                            acc.setAvatar(image);
                            System.out.println("UPDATE ACCOUNT");
                            System.out.println(acc.getAccount());
                            accountDataSource.updateAccount(acc);
                            finish();
                        }
                    }
                    else {
                        MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                String name = txtName.getText().toString();
                                String username = txtUsername.getText().toString();
                                String about = txtAboutMe.getText().toString();
                                String email = txtEmail.getText().toString();
                                String dateOfBirth = txtDateOfBirth.getText().toString();
                                String image = resultData.get("url").toString();
                                if (name.isEmpty() || username.isEmpty() || email.isEmpty() || dateOfBirth.isEmpty()) {
                                    Toast.makeText(UpdateDeleteBarberActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                                }
                                if (isValidEmail(email) == false) {
                                    Toast.makeText(UpdateDeleteBarberActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                                } else {
                                    acc.setName(name);
                                    acc.setUsername(username);
                                    acc.setAbout(about);
                                    acc.setEmail(email);
                                    acc.setDateOfBirth(dateOfBirth);
                                    acc.setAvatar(image);
                                    System.out.println("UPDATE ACCOUNT");
                                    System.out.println(acc.getAccount());
                                    accountDataSource.updateAccount(acc);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {

                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {

                            }
                        }).dispatch();
                    }
                } else {
                    Toast.makeText(UpdateDeleteBarberActivity.this, "Please choose image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imagePath = selectedImageUri;
                    avatar.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public void handleGender() {
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

    public void showDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // Xử lý giá trị năm sinh được chọn ở đây
                LocalDate date_of_birth = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    date_of_birth = LocalDate.of(year, month + 1, dayOfMonth);
                }
                //format LocalDate to String dd/MM/yyyy
                String formatDate= date_of_birth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                txtDateOfBirth.setText(formatDate);
            }
        }, 2000, 2, 2);
        datePickerDialog.show();
    }

    private void initView() {
        avatar = findViewById(R.id.avatar);
        profileName = findViewById(R.id.barberProfileName);
        txtName = findViewById(R.id.txtName);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
        calendarImage = findViewById(R.id.calendarImageView);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        txtUsername = findViewById(R.id.txtUsername);
        txtAboutMe = findViewById(R.id.txtAboutMe);
        txtEmail = findViewById(R.id.txtEmail);
    }
}
package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.adapter.ViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //receive general notification
        receiveGeneralNotification();

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("UserData",Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName=sharedPreferences.getString("username","");

        navigationView=findViewById(R.id.bottom_nav);
        viewPager=findViewById(R.id.viewPager);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display activity choose barber
                Intent intent=new Intent(MainActivity.this,ChooseBarberActivity.class);
                startActivity(intent);
            }
        });
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.action_history).setChecked(true);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.action_service).setChecked(true);
                        break;
                    case 3: navigationView.getMenu().findItem(R.id.action_account).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_history:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_service:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_account:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    private void receiveGeneralNotification() {
        FirebaseMessaging.getInstance().subscribeToTopic("go-chat-2")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
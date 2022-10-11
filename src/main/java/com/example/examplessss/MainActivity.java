package com.example.examplessss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button login_btn= findViewById(R.id.loginbtn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent open_login_page = new Intent(MainActivity.this, login_page.class);
                startActivity(open_login_page);
            }
        });
    }
}
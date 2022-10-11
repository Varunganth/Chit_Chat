package com.example.examplessss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class home_page extends AppCompatActivity {
    Button btLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btLogout=findViewById(R.id.logout);
        final Button don_btn = findViewById(R.id.donate_btn);
        don_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent open_sign_in_page = new Intent(home_page.this, donation_pg.class);
                startActivity(open_sign_in_page);
            }
        });


        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_page.this, login_page.class);
                startActivity(intent);
                finish();
                Toast.makeText(home_page.this, "Successfully logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
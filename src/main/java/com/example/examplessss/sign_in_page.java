package com.example.examplessss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class sign_in_page extends AppCompatActivity {
    private EditText name, email, password , confirm_password;
    private Button sign_in, login;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        sign_in = findViewById(R.id.btnregister);
        progressbar = findViewById(R.id.progressbar);
        login = findViewById(R.id.button6);

        // Set on Click Listener on Registration button
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_login_page = new Intent(sign_in_page.this, login_page.class);
                startActivity(open_login_page);
            }
        });
    }

        private void registerNewUser() {

            // show the visibility of progress bar to show loading
            // Take the value of two edit texts in Strings
            String username, mail, pwd , cfm_pass;
            username = name.getText().toString();
            mail = email.getText().toString();
            pwd = password.getText().toString();
            cfm_pass = confirm_password.getText().toString();

            // Validations for input email and password
            if(username.isEmpty()){
                name.setError("Name is required");
                name.requestFocus();
                return;
            }
            if(mail.isEmpty()){
                email.setError("Mail_id is required");
                email.requestFocus();
                return;
            }
            if(pwd.isEmpty()){
                password.setError("password is required");
                password.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                email.setError("please provide valid email");
                email.requestFocus();
                return;
            }
            if(cfm_pass.isEmpty()){
                confirm_password.setError("conform password is required");
                confirm_password.requestFocus();
                return;
            }
            if(!pwd.equals(cfm_pass)){
                confirm_password.setError("password not matched");
                return;
            }
            if(pwd.length()<6){
                password.setError("minimum length should be 6 characters");
                password.requestFocus();
                return;

            }
            progressbar.setVisibility(View.VISIBLE);

            // create new user or register new user
            mAuth
                    .createUserWithEmailAndPassword(mail, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {
                                User user = new User(username, mail, pwd, cfm_pass);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(sign_in_page.this, "user has been registered Successfully", Toast.LENGTH_LONG).show();
                                                    progressbar.setVisibility(View.GONE);
                                                    Intent intent = new Intent(sign_in_page.this, login_page.class);
                                                    startActivity(intent);

                                                } else {
                                                    Toast.makeText(sign_in_page.this, "failed to register", Toast.LENGTH_LONG).show();
                                                    progressbar.setVisibility(View.GONE);
                                                }
                                            }
                                        });

                            }
                            else {

                                // Registration failed
                                Toast.makeText(
                                                getApplicationContext(),
                                                "Registration failed!!"
                                                        + " Please try again later",
                                                Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar
                                progressbar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
}
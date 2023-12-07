package com.example.minor_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Password extends AppCompatActivity {

    String email;
    FirebaseAuth auth;
    TextInputEditText Email_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Button Submit = findViewById(R.id.submit_btn);
        Email_Address = findViewById(R.id.email_address_txt);
        auth = FirebaseAuth.getInstance();
        TextView Back_btn = findViewById(R.id.back_button);

        Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ProgressBar progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                validate_password();
            }
        });

        Back_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ProgressBar progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Forget_Password.this, Login_Page.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validate_password()
    {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        email = Email_Address.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Email_Address.setError("Required!!");
            progressBar.setVisibility(View.GONE);
        }

        else
        {
            forget_password();
        }

    }

    private void forget_password()
    {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    ProgressBar progressBar = findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Forget_Password.this, "CHECK YOUR EMAIL", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Forget_Password.this, Login_Page.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    ProgressBar progressBar = findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Forget_Password.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
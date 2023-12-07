package com.example.minor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Page extends AppCompatActivity
{

    public String email, password;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent intent = new Intent(Login_Page.this, MainActivity.class);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button Forget_Password_btn = findViewById(R.id.forget_password_btn);
        ProgressBar progressBar = findViewById(R.id.Log_in_progressBar);
        Button Login_btn;
        Login_btn = findViewById(R.id.Log_in_btn);
        Button forget_Password_btn;
        forget_Password_btn = findViewById(R.id.forget_password_btn);
        TextInputEditText login_Email_Address_Text;
        login_Email_Address_Text = findViewById(R.id.email_address_txt);
        TextInputEditText login_Password_Text;
        login_Password_Text = findViewById(R.id.password_txt);
        TextView dont_Have_Account;
        dont_Have_Account = findViewById(R.id.register_account_txtbtn);

        dont_Have_Account.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Login_Page.this, "Create an Account!!", Toast.LENGTH_SHORT).show();
                Intent Register_page_intent = new Intent(Login_Page.this, Register_Page.class);
                startActivity(Register_page_intent);
            }
        });

        Forget_Password_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Login_Page.this, Forget_Password.class);
                startActivity(intent);
            }
        });

        Login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                email = login_Email_Address_Text.getText().toString();
                password = login_Password_Text.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Login_Page.this, "ENTER YOUR EMAIL ADDRESS!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login_Page.this, "ENTER YOUR PASSWORD!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful())
                                {
                                    Intent intent = new Intent(Login_Page.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login_Page.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}
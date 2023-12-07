package com.example.minor_project;

import static android.view.View.VISIBLE;
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

public class Register_Page extends AppCompatActivity {

    String email, password;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        TextInputEditText register_email_txt = findViewById(R.id.Register_email_address_txt);
        TextInputEditText register_password_txt = findViewById(R.id.Register_password_txt);
        Button register_btn = findViewById(R.id.register_btn);
        ProgressBar progressBar = findViewById(R.id.Register_progressBar);

        register_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(VISIBLE);
                email = String.valueOf(register_email_txt.getText());
                password = String.valueOf(register_password_txt.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Register_Page.this, "ENTER YOUR EMAIL ADDRESS!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Register_Page.this, "ENTER YOUR PASSWORD!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                else if(password.length() < 6)
                {
                    Toast.makeText(Register_Page.this, "YOUR PASSWORD MUST CONTAIN 6 LETTERS!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Register_Page.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register_Page.this, Login_Page.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register_Page.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Register_Page.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
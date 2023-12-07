package com.example.minor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Raise_A_Query extends AppCompatActivity {

    EditText to_txt;
    EditText subject_txt;
    EditText message_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_aquery);

        to_txt = (EditText) findViewById(R.id.to_edittxt);
        subject_txt = (EditText) findViewById(R.id.subject_edittxt);
        message_txt = (EditText) findViewById(R.id.message_edittxt);
        TextView Back_btn = findViewById(R.id.Go_Back_btn);

        Back_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Raise_A_Query.this, MainActivity.class);
                Toast.makeText(Raise_A_Query.this, "'BACK'", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });


        Button Send_btn = (Button) findViewById(R.id.send_btn);
        Send_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String receptent = to_txt.getText().toString();
                String subject = subject_txt.getText().toString();
                String message = message_txt.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{receptent});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email Client : "));
            }
        });
    }
}
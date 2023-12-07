package com.example.minor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Mark_Atten extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_atten);

        ImageView Face_Recognisation_Atten = findViewById(R.id.Face_Recognisation_Atten_btn);
        ImageView QR_Scanner_Atten = findViewById(R.id.QR_Scanner_Atten_btn);
        TextView Back_btn = findViewById(R.id.Go_Back_btn);

        Back_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Mark_Atten.this, MainActivity.class);
                Toast.makeText(Mark_Atten.this, "'BACK'", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

        Face_Recognisation_Atten.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Mark_Atten.this, Face_Dection_Mark_Atten.class);
                startActivity(intent);
                finish();
            }
        });

        QR_Scanner_Atten.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Mark_Atten.this, Barcode_Read_Mark_Atten.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
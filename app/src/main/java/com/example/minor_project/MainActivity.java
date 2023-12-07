package com.example.minor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        TextView logout_button = findViewById(R.id.Logout_button);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        ImageView mark_Attendence = findViewById(R.id.Mark_Atten_btn);
        ImageView review_Attendence = findViewById(R.id.Review_Atten_btn);
        ImageView raise_A_Query = findViewById(R.id.Raise_A_Query_btn);

        if( user == null)
        {
            Intent intent = new Intent(MainActivity.this, Login_Page.class);
            startActivity(intent);
            finish();
        }

        else
        {
            textView.setText(user.getEmail());
        }

        logout_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login_Page.class);
                startActivity(intent);
                finish();
            }
        });

        raise_A_Query.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent query_intent = new Intent(MainActivity.this, Raise_A_Query.class);
                startActivity(query_intent);
            }
        });

        mark_Attendence.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Mark_Atten.class);
                startActivity(intent);
            }
        });

        review_Attendence.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Review_Atten_Page.class);
                startActivity(intent);
            }
        });
    }
}
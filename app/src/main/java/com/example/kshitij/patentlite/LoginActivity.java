package com.example.kshitij.patentlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private int role = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button admin = findViewById(R.id.admin);
        Button appraiser = findViewById(R.id.appraiser);
        Button applicant = findViewById(R.id.applicant);
        Button inspector = findViewById(R.id.inspector);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 4;
                redirect();
            }
        });
        appraiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 2;
                redirect();
            }
        });
        applicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 1;
                redirect();
            }
        });
        inspector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 3;
                redirect();
            }
        });
    }
    public void redirect() {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("role", role);
        startActivity(intent);
    }
}

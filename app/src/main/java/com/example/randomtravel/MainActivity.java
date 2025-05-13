package com.example.randomtravel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.example.randomtravel.draw.DrawActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button drawBtn = findViewById(R.id.drawBtn);
        drawBtn.setOnClickListener(view ->{
            Intent i = new Intent(MainActivity.this, DrawActivity.class);
            startActivity(i);
        });
    }
}
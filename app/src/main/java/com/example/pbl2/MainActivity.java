package com.example.pbl2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.pbl2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.course.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
            startActivity(intent);
        });

        binding.eligibility.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EligibilityActivity.class);
            startActivity(intent);
        });

        binding.aboutus.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutContactActivity.class);
            startActivity(intent);
        });
    }
}
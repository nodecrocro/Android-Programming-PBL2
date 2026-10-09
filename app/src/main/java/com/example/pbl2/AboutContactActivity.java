package com.example.pbl2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.pbl2.databinding.ActivityAboutContactBinding;

public class AboutContactActivity extends AppCompatActivity {

    private ActivityAboutContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_contact);

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gmi.edu.my"));
            startActivity(intent);
        });
    }
}
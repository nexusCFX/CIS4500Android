package com.cis4500.music;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}

package com.example.marketapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.marketapp.dbHelper.ConnectionSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.marketapp.ui.main.SectionsPagerAdapter;
import com.example.marketapp.databinding.ActivityMainBinding;
import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FloatingActionButton fab;
    ConnectionSQLite connectionSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        fab = binding.fab;

        connectionSQLite = ConnectionSQLite.getInstance(getApplication());
    }

}
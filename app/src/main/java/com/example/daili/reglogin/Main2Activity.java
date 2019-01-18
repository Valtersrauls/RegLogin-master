package com.example.daili.reglogin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Session ses;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.widget.Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_open, R.string.nav_close);
        setSupportActionBar(mToolbar);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //pārbauda, ja sesija nav aktīva, iziet no aktivitātes
        ses = new Session(this);
        if (!ses.HasLoggedIn()) {
            logout();
        }
    }

    //aktivitāšu nodošana pēc menu izvēles nospiešanas
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_rehearsals:
                Intent openCalendarActivity = new Intent(this, Activity_Calendar.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openCalendarActivity);
                finish();
                break;
            case R.id.nav_concerts:
                Intent openConcertActivity = new Intent(this, ConcertActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openConcertActivity);
                finish();
                break;
        }
        return true;
    }

    //navigācijas joslas atvēršana pēc ikonas/pogas nospiešanas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        ses.setLoggedIn(false);
        Intent openLoginActivity = new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(openLoginActivity);
        finish();
    }


}

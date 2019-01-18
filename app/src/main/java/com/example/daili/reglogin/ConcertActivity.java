package com.example.daili.reglogin;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConcertActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Session ses;
    private TextView megLaiks;
    private TextView megGraph;
    private TextView notes;
    private TextView megLaiksText;
    private TextView megGraphText;
    private TextView notesText;
    CompactCalendarView compactCalendar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.widget.Toolbar mToolbar;

    private SimpleDateFormat dateFormantMonth = new SimpleDateFormat("MMMM - YYYY", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concert);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar1);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout2);
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

        //final ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(false);
        mToolbar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        megLaiks = (TextView) findViewById(R.id.tvMegLaiks);
        megGraph = (TextView) findViewById(R.id.tvMegGraph);
        notes = (TextView) findViewById(R.id.tvNotes);
        megLaiksText = (TextView) findViewById(R.id.megLaiksText);
        megGraphText = (TextView) findViewById(R.id.megGraphText);
        notesText = (TextView) findViewById(R.id.notesText);

        Event ev1 = new Event(Color.BLUE, 1547935200000L);
        compactCalendar.addEvent(ev1);

        setInvisible();
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if (dateClicked.toString().compareTo("Sun Jan 20 00:00:00 GMT+02:00 2019") == 0) {
                    setVisible();
                    megLaiks.setText("14.30 - 19.30");
                    megGraph.setText("Dziesmu un deju svētku atklāšanas dienas programma: \n" +
                            "\n" +
                            "14.30     “Imanta Dimanta”\n" +
                            "15.30     Elektrum programma “Izdziedam 100” - Vidzeme\n" +
                            "                Ģirts Ķesteris, Gints Andžāns, Olga Dreģe\n" +
                            "16.00     „Laimas muzykanti”\n" +
                            "17.00     Elektrum programma “Izdziedam 100” – Kurzeme\n" +
                            "               Gints Grāvelis, Ērika Eglija, Edgars Pujāts\n" +
                            "17.30     “Rīgas danču klubs”\n" +
                            "18.00     Elektrum programma “Izdziedam 100” – Zemgale\n" +
                            "               Artūrs Skrastiņš, Gundars Grasbergs, Maija Doveika\n" +
                            "18.30     Elektrum programma “Izdziedam 100” –Latgale un Sēlija\n" +
                            "                Sarmīte Rubule, Kristaps Rasims, Ilona Bagele\n" +
                            "19.00     “Tautumeitas”\n" +
                            "19.30     Elektrum programma ");
                    notes.setText("Būs bomba!");
                    Toast.makeText(getApplicationContext(), dateClicked.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    setInvisible();
                    Toast.makeText(getApplicationContext(), dateClicked.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                mToolbar.setTitle(dateFormantMonth.format(firstDayOfNewMonth));
            }
        });
    }

    private void setInvisible() {
        megLaiks.setVisibility(View.INVISIBLE);
        megGraph.setVisibility(View.INVISIBLE);
        notes.setVisibility(View.INVISIBLE);
        megLaiksText.setVisibility(View.INVISIBLE);
        megGraphText.setVisibility(View.INVISIBLE);
        notesText.setVisibility(View.INVISIBLE);
    }

    private void setVisible() {
        megLaiks.setVisibility(View.VISIBLE);
        megGraph.setVisibility(View.VISIBLE);
        notes.setVisibility(View.VISIBLE);
        megLaiksText.setVisibility(View.VISIBLE);
        megGraphText.setVisibility(View.VISIBLE);
        notesText.setVisibility(View.VISIBLE);
    }

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
            case R.id.nav_chat:
                Intent openChatActivity = new Intent(this, Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openChatActivity);
                finish();
            default:
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

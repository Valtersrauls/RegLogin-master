package com.example.daili.reglogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_Calendar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_calendar);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toobar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
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

        Event ev1 = new Event(Color.BLUE, 1548453600000L);
        compactCalendar.addEvent(ev1);

        Event ev2 = new Event(Color.BLUE, 1547848800000L);
        compactCalendar.addEvent(ev2);

        setInvisible();
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if (dateClicked.toString().compareTo("Sat Jan 26 00:00:00 GMT+02:00 2019") == 0) {
                    setVisible();
                    megLaiks.setText("10:00 - 13:00");
                    megGraph.setText("10:00 - 10:30 iesildīšanās\n10:30 - 11:30 Visi ciema suņi rēja\n11:30 - 12:30 Atkārtosim jaunu deju\n12:30 - 13:00 atsildīšanās");
                    notes.setText("Lorem ipsum dolor sit amet, libero velit ut lacinia, \nenim integer, donec \npraesent penatibus tenetur et nec lacus. Amet commodo ullamcorper commodo in, pharetra justo quis. Justo ut\n elit purus, ac purus consectetuer\n\n nulla sollicitudin, at metus, ante ut suscipit, \n\njusto augue tempus. Libero odio nunc, \ntristique consequat mollis dolor scelerisque proin, nibh nunc, consectetuer odio adipiscing. Sit sit phasellus, ipsum in wisi dolor nunc integer eget, pulvinar integer fermentum, odio \n\nanim justo proin accumsan mauris nunc. Est integer nulla lacinia luctus suscipit, blandit ligula.");
                    Toast.makeText(getApplicationContext(),dateClicked.toString(), Toast.LENGTH_SHORT).show();
                }
                else if (dateClicked.toString().compareTo("Sat Jan 19 00:00:00 GMT+02:00 2019") == 0) {
                    setVisible();
                    megLaiks.setText("10:00 - 13:00");
                    megGraph.setText("10:00 - 10:30 iesildīšanās\n10:30 - 11:30 Kam ir gaiss\n11:30 - 12:30 Mācīsimies jaunu deju\n12:30 - 13:00 atsildīšanās");
                    notes.setText("Ņemiet līdzi daudz spēka, jaunā deja nav viegla :)");
                    Toast.makeText(getApplicationContext(),"Rāda 19. Janvāri", Toast.LENGTH_SHORT).show();
                } else {
                    setInvisible();
                    Toast.makeText(getApplicationContext(),dateClicked.toString(), Toast.LENGTH_SHORT).show();
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
        switch (menuItem.getItemId()){
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_chat:
                Intent openChatActivity = new Intent(this, Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openChatActivity);
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

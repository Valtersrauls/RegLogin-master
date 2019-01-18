package com.example.daili.reglogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean checkEmail, checkUsername;
    private CheckBox chbDanceGroupTeacher;
    private EditText mDanceGroupName;
    private EditText mDanceGroupCategory;
    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordRepeat;
    private Integer mDanceGroupTeacher;
    private Button btnRegister;
    private TextView btnBack;
    private String RegValueEmail, RegValueUsername, RegValuePassword, RegValuePasswordRepeat, RegValueDanceGroupName, RegValueDanceGroupCategory;
    private establishDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        chbDanceGroupTeacher = (CheckBox) findViewById(R.id.chbDanceTeacher);
        mDanceGroupName = (EditText) findViewById(R.id.etxtDanceGroupName);
        mDanceGroupCategory = (EditText) findViewById(R.id.etxtDanceGroupCategory);
        mEmail = (EditText) findViewById(R.id.etxtEmailReg);
        mUsername = (EditText) findViewById(R.id.etxtUsername);
        mPassword = (EditText) findViewById(R.id.etxtPassReg);
        mPasswordRepeat = (EditText) findViewById(R.id.etxtPassRepeat);
        btnRegister = (Button) findViewById(R.id.btnRegToLogin);
        btnBack = (TextView) findViewById(R.id.tvButton);
        db = new establishDatabase(this);
        btnRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        chbDanceGroupTeacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            //Ja tiek iezīmēts (checkbox) tiek atvērti papildus EditText lauki, priekš deju kolektīva vadītāja
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDanceGroupName.setVisibility(View.VISIBLE);
                    mDanceGroupCategory.setVisibility(View.VISIBLE);
                    mDanceGroupTeacher = 1;
                    RegValueDanceGroupName = mDanceGroupName.getText().toString();
                    RegValueDanceGroupCategory = mDanceGroupCategory.getText().toString();
                } else {
                    mDanceGroupName.setVisibility(View.INVISIBLE);
                    mDanceGroupCategory.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegToLogin:
                goToLogin();
                break;
            case R.id.tvButton:
                Intent openLoginActivity = new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openLoginActivity);
                finish();
                break;
        }
    }

    public void goToLogin() {
        mDanceGroupTeacher = 0;
        RegValueDanceGroupName = "";
        RegValueDanceGroupCategory = "";
        RegValueEmail = mEmail.getText().toString();
        RegValueUsername = mUsername.getText().toString();
        RegValuePassword = mPassword.getText().toString();
        RegValuePasswordRepeat = mPasswordRepeat.getText().toString();
        //tiek veiktas visas datu pārbaudes
        if (RegValueEmail.equals("") || RegValueUsername.equals("") || RegValuePassword.equals("") || RegValuePasswordRepeat.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.toast_EmptyFields, Toast.LENGTH_SHORT).show();
        } else {
            if (RegValueEmail.contains("@")) {
                try {
                    if (RegValuePassword.length() < 6) {
                        Toast.makeText(getApplicationContext(), R.string.toast_PasswordShort, Toast.LENGTH_SHORT).show();
                    } else {
                        if (RegValuePassword.equals(RegValuePasswordRepeat)) {

                            checkEmail = db.checkEmail(RegValueEmail);
                            checkUsername = db.checkUsername(RegValueUsername);
                            if (checkEmail == true && checkUsername == true) {
                                Boolean insert = db.addData(RegValueEmail, RegValueUsername, RegValuePassword, RegValueDanceGroupName, RegValueDanceGroupCategory, mDanceGroupTeacher);
                                if (insert == true) {
                                    Toast.makeText(getApplicationContext(), R.string.toast_SuccessMessage, Toast.LENGTH_SHORT).show();
                                    Intent openMainActivity = new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(openMainActivity);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.toast_Error, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.toast_EmailUsernameExists, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.toast_PasswordMismatch, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_Emailwrong, Toast.LENGTH_SHORT).show();
            }
        }
    }
}


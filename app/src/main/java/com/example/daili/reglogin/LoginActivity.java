package com.example.daili.reglogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail, mPassword;
    private establishDatabase db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnReg = (Button) findViewById(R.id.btnRegister);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        CheckBox chbRemember = (CheckBox) findViewById(R.id.chbRememberMe);
        mEmail = (EditText) findViewById(R.id.etxtEmail);
        mPassword = (EditText) findViewById(R.id.etxtPassword);
        db = new establishDatabase(this);
        session = new Session(this);
        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        //pārbauda vai sessija ir aktīva
        if (session.HasLoggedIn()) {
            startActivity(new Intent(this, Main2Activity.class));
            finish();
        }
    }

    //pēc nospiestās pogas izpilda darbību
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegister:
                Intent openRegisterActivity = new Intent(this, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openRegisterActivity);
                finish();
                break;
            default:
        }
    }

    //metode, kas piekļūst klasei establishDatabase, lai pārbaudītu ievadītos lietotāja datus
    public void login() {

        String enteredEmail = mEmail.getText().toString();
        String enteredPassword = mPassword.getText().toString();
        Boolean checkData = db.checkData(enteredEmail, enteredPassword);
        if (enteredEmail.equals("") || enteredPassword.equals("")) {
            Toast.makeText(getApplicationContext(), R.string.toast_EmptyFields, Toast.LENGTH_SHORT).show();
        } else {
            if (checkData) {
                Toast.makeText(getApplicationContext(), R.string.toast_LoggedIn, Toast.LENGTH_SHORT).show();
                //sessija kļūst aktīva
                session.setLoggedIn(true);
                Intent openJustActivity = new Intent(this, Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openJustActivity);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_FailedToLogIn, Toast.LENGTH_SHORT).show();
            }
        }
    }
}


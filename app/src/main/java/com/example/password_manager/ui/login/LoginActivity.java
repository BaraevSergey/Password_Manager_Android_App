package com.example.password_manager.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.password_manager.List_of_passwords;
import com.example.password_manager.R;
import com.example.password_manager.RegisterPage;

public class LoginActivity extends AppCompatActivity {

    Button LogIn;
    Button Rgstr;
    EditText LogEnt;
    EditText RegEnt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            LogIn = (Button) findViewById(R.id.login);
            Rgstr = (Button) findViewById(R.id.Registration);
            LogEnt = (EditText) findViewById(R.id.username);
            RegEnt = (EditText) findViewById(R.id.password);

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "PreferencesFilename",
                    masterKeyAlias,
                    LoginActivity.this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            String lgn = sharedPreferences.getString("Login", "");
            String psw = sharedPreferences.getString("Password", "");

            if (!lgn.isEmpty()) {
                Rgstr.setVisibility(Button.INVISIBLE);
            }



            View.OnClickListener ClickButton = new View.OnClickListener() {
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.login: {
                            try {

                                String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                                SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                                        "PreferencesFilename",
                                        masterKeyAlias,
                                        LoginActivity.this,
                                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                                String lgn = sharedPreferences.getString("Login", "");
                                String psw = sharedPreferences.getString("Password", "");

                                if (LogEnt.getText().toString().equals(lgn) && RegEnt.getText().toString().equals(psw)) {
                                    Intent EntryActivity = new Intent(LoginActivity.this, List_of_passwords.class);
                                    startActivity(EntryActivity);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Неверные логин и пароль", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            } catch (Exception exc) {
                                Toast.makeText(LoginActivity.this, exc.toString(), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }

                        case R.id.Registration: {
                            Intent Registr = new Intent(LoginActivity.this, RegisterPage.class);
                            startActivity(Registr);
                            break;
                        }
                    }
                }
            };
            LogIn.setOnClickListener(ClickButton);
            Rgstr.setOnClickListener(ClickButton);
        }
        catch (Exception exc) { }

    }
}

package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RegisterPage extends AppCompatActivity {
    Button Zareg;
    EditText Login;
    EditText Password;
    EditText ConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Zareg = (Button) findViewById(R.id.Register);
        Login = (EditText) findViewById(R.id.LoginInfo);
        Password = (EditText) findViewById(R.id.PasswordEdit);
        ConfirmPassword = (EditText) findViewById(R.id.ConfirmPassword);
        TextWatcher check = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Login.getText().toString().length() != 0 && Password.getText().toString().length() != 0 && ConfirmPassword.getText().toString().length() != 0) {
                    Zareg.setBackgroundColor(getResources().getColor(R.color.RegistrActive));
                } else {
                    Zareg.setBackgroundColor(getResources().getColor(R.color.RegistrNonActive));
                }
            }
        };
        Login.addTextChangedListener(check);
        Password.addTextChangedListener(check);
        ConfirmPassword.addTextChangedListener(check);


        View.OnClickListener ButtonClick = new View.OnClickListener() {
            public void onClick(View v) {
                //Проверки:
                //1) Не пустой ли логин
                //2) Не пустой ли пароль
                //3) Не пустой ли повтор пароля
                // Если выше перечисленные факты нормальные, то активировать кнопку регистрации
                //4) Логин >=6 символов
                //5) Пароль >= 8 символов
                //6) Пароль и подтверждение совпадают
                //7) Содержат только разрешенные символы
                String LoginEnrty = Login.getText().toString();
                String PasswordEntry = Password.getText().toString();
                String ConfirmPasswordEntry = ConfirmPassword.getText().toString();
                if  (checking(LoginEnrty, PasswordEntry, ConfirmPasswordEntry) == true) {
                    try {
                        String  masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                        SharedPreferences  sharedPreferences = EncryptedSharedPreferences.create(
                                "PreferencesFilename",
                                masterKeyAlias,
                                RegisterPage.this,
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("Login",LoginEnrty).putString("Password",PasswordEntry).apply();
                        //editor.clear().commit();
                        finish();
                    }
                    catch (Exception exc) {
                        //требовал обработку exceptionov

                    }

                }
                else {
                    Toast.makeText(RegisterPage.this, "Не норм", Toast.LENGTH_SHORT).show();
                }
            }
        };


        Zareg.setOnClickListener(ButtonClick);

        };

    public boolean checking(String Lgn, String Psw, String CnfPsw) {
        if (Lgn.length() != 0 && Psw.length() != 0 && CnfPsw.length() != 0) { //проверка пустоты
                if (Lgn.length() >= 6) { //проверка длины логина
                    if (Psw.length() >= 8) { //проверка длины пароля
                        if (Psw.equals(CnfPsw)) { //проверка совпадают ли пароли
                            String RegEx = "[0-9A-Za-zА-Яа-я]{0,}";
                            if (Lgn.matches(RegEx) || Psw.matches(RegEx)) { //проверка на недопустимые символы
                                return true;
                            }
                            else {
                                String MessageError = "Логин или пароль содержат недопустимые символы, используйте только [0-9],[a-z],[А-Я]";
                                Toast.makeText(RegisterPage.this, MessageError, Toast.LENGTH_SHORT).show();
                                return false;
                            }

                        }
                        else {
                            Toast.makeText(RegisterPage.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    else {
                        Toast.makeText(RegisterPage.this, "Длина пароля должна быть больше 7 символов", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                else {
                    Toast.makeText(RegisterPage.this, "Длина логина должна быть больше 5 символов", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else
            {
                Toast.makeText(RegisterPage.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    }

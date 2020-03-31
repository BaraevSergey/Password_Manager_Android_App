package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

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
        Login = (EditText) findViewById(R.id.LoginInfo) ;
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
                    Toast.makeText(RegisterPage.this, "Succesful", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Login.addTextChangedListener(check) ;
        Password.addTextChangedListener(check) ;
        ConfirmPassword.addTextChangedListener(check) ;


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
                //7)

            }
        };
        Zareg.setOnClickListener(ButtonClick);

    }
}

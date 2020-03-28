package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EntryInfo extends AppCompatActivity {
    Button canc; // кнопка "Отмена"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_info);
        canc = (Button) findViewById(R.id.cancel);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //по кнопке отмена закрыть эту штуку
                //надо будет перевызвать активити тут
            }
        };
        canc.setOnClickListener(oclBtnOk);
        //необходимо прописать логику кнопки добавить
    }
    private static long back_pressed;
    @Override
    public void onBackPressed() { //событик двойного даблклика назад
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            Intent mainintentbck = new Intent(this, List_of_passwords.class);
            finish();
            startActivity(mainintentbck);
        } else {
        }
        back_pressed = System.currentTimeMillis();
    }
}

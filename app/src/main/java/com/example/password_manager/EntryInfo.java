package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EntryInfo extends AppCompatActivity {
    Button canc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_info);
        canc = (Button) findViewById(R.id.cancel);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                finish();
            }
        };

        // присвоим обработчик кнопке OK (btnOk)
        canc.setOnClickListener(oclBtnOk);
    }
}

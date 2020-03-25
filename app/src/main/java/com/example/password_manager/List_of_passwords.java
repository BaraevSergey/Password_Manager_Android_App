package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class List_of_passwords extends AppCompatActivity {
    Button add;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_passwords);
        //находим view элементы
        add = (Button) findViewById(R.id.add);
        //создание обрабочтка нажатия
        View.OnClickListener clickadd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Test Click", Toast.LENGTH_SHORT).show();
            }
        };
        add.setOnClickListener(clickadd);
    }
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            Toast.makeText(getBaseContext(), "Fuck You", Toast.LENGTH_SHORT).show();
            finish();
        } else {
           // Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

}

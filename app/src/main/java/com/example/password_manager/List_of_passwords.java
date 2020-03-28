package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class List_of_passwords extends AppCompatActivity {
    Button add;
    LinearLayout llMain;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_passwords);
        //находим view элементы
        add = (Button) findViewById(R.id.add);
        //создание обрабочтка нажатия
        llMain = (LinearLayout) findViewById(R.id.llMain);
        View.OnClickListener clickadd = new View.OnClickListener() {
            @Override
            public void onClick(View v) { //событие нажатия на кпопку добавить
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                        wrapContent, wrapContent);
                Display display = getWindowManager().getDefaultDisplay(); //для получения параметров дисплея
                Point size = new Point(); //точки края
                display.getSize(size); //получение размера дисплея
                int width = size.x - 30; //ширина
                //int height = size.y;
                int btnGravity = Gravity.CENTER;
                lParams.gravity = btnGravity;
                lParams.width = width;
                lParams.bottomMargin = 12;

                // создаем Button, пишем текст и добавляем в LinearLayout
                Button btnNew = new Button(List_of_passwords.this);
                btnNew.setBackgroundColor(getResources().getColor(R.color.colorForButton)); // установили цвет кнопки
                btnNew.setText("Test_Button");
                llMain.addView(btnNew, lParams);
            }
        };
        add.setOnClickListener(clickadd);
    }
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            Toast.makeText(getBaseContext(), "its false", Toast.LENGTH_SHORT).show();
            finish();
        } else {
           // Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

}

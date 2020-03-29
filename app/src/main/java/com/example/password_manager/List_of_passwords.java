package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    Button canc;
    LinearLayout llMain;
    int idactivbut; // хранит значение id выбранной кнпопки
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    Button tmpBtn; //прошлая нажатая кнопка
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_passwords);
        //находим view элементы
        add = (Button) findViewById(R.id.add);
        canc = (Button) findViewById(R.id.delete);
        idactivbut = -1;
        //создание обработчика нажатия
        llMain = (LinearLayout) findViewById(R.id.llMain);
        View.OnClickListener clickadd = new View.OnClickListener() {
            @Override
            public void onClick(View v) { //событие нажатия на кпопку добавить
                //будет логика:
                //вызов нового активити
                //закрытие этого активити естественно


                //открытие окошка добавления
                //Intent EntryIntent = new Intent(List_of_passwords.this, EntryInfo.class);
                //startActivity(EntryIntent);

                switch(v.getId()) {
                    //добавление кнопки на лойаут
                    case R.id.add: {
                        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                                wrapContent, wrapContent);

                        Display display = getWindowManager().getDefaultDisplay(); //для получения параметров дисплея
                        Point size = new Point(); //точки края
                        display.getSize(size); //получение размера дисплея
                        int width = size.x - 30; //ширина
                        int btnGravity = Gravity.CENTER;
                        lParams.gravity = btnGravity;
                        lParams.width = width;
                        lParams.bottomMargin = 12;

                        // создаем Button, пишем текст и добавляем в LinearLayout
                        Button btnNew = new Button(List_of_passwords.this);
                        btnNew.setBackgroundColor(getResources().getColor(R.color.colorForButton)); // установили цвет кнопки
                        int a = (int) (Math.random() * 1000000);
                        btnNew.setText(String.valueOf(a));
                        btnNew.setId(a);
                        btnNew.setOnClickListener(this);
                        llMain.addView(btnNew, lParams);
                        llMain.removeView(add);
                        break;
                    }
                    case R.id.delete: {
                        tmpBtn = (Button) findViewById(idactivbut);
                        llMain.removeView(tmpBtn);
                        idactivbut = -1;
                        break;
                    }
                    default:
                    {
                        if (idactivbut!=-1) {
                            tmpBtn = (Button) findViewById(idactivbut);
                            tmpBtn.setBackgroundColor(getResources().getColor(R.color.colorForButton));
                        }
                        idactivbut = v.getId();
                        v.setBackgroundColor(getResources().getColor(R.color.colorForButtonact));
                        if (idactivbut!=-1)
                        {
                            canc.setBackgroundColor(getResources().getColor(R.color.deletebutactive));
                        }
                        break;
                    }
                }
            }
        };
        add.setOnClickListener(clickadd);
        canc.setOnClickListener(clickadd);
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

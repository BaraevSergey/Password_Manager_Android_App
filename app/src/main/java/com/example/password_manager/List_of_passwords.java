package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class List_of_passwords extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    Button add;
    Button canc;
    LinearLayout llMain;
    DBHelper dbHelper;
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
        llMain = (LinearLayout) findViewById(R.id.llMain);

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("url_site");
            int emailColIndex = c.getColumnIndex("password");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", email = " + c.getString(emailColIndex));
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
                btnNew.setText(c.getString(nameColIndex));
                btnNew.setId(a);
                //btnNew.setOnClickListener((View.OnClickListener) List_of_passwords.this);
                llMain.addView(btnNew, lParams);
            } while (c.moveToNext());
        }
        c.close();

        //создание обработчика нажатия
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
                        Intent mainintentbck = new Intent(List_of_passwords.this, EntryInfo.class);
                        finish();
                        startActivity(mainintentbck);
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

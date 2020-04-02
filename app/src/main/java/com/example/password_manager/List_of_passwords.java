package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
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

        View.OnLongClickListener longclickadd = new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) { //событие нажатия на кпопку добавить
                //Toast.makeText(List_of_passwords.this, "test",Toast.LENGTH_LONG);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String k = ((Button)v).getText().toString();
                String selection = "url_site = ?";
                String selectionArgs[] = new String[] {k};
                Cursor c = db.query("mytable", null, selection, selectionArgs, null, null, null);
                if (c.moveToFirst()) {
                    int urlIndex = c.getColumnIndex("url_site");
                    int pasindex = c.getColumnIndex("password");
                    int logindex = c.getColumnIndex("login");
                    do {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", c.getString(pasindex));
                        clipboard.setPrimaryClip(clip);
                        String text_password = String.format("Ваш логин - %s , Ваш пароль - %s",new String[]{c.getString(logindex),c.getString(pasindex)});
                        Toast.makeText(List_of_passwords.this, text_password,Toast.LENGTH_LONG).show();
                    } while (c.moveToNext());
                }
                c.close();
                return true;
            }
        };



        View.OnClickListener clickadd = new View.OnClickListener() {
            @Override
            public void onClick(View v) { //событие нажатия на кпопку добавить
                SQLiteDatabase db = dbHelper.getWritableDatabase();
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
                        //String test = String.format("url_site = ?",((Button)tmpBtn).getText().toString());
                        String k = ((Button)tmpBtn).getText().toString();
                        db.delete("mytable", "url_site = ?",new String[]{k});
                        //Toast.makeText(List_of_passwords.this, test, Toast.LENGTH_SHORT);
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
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            int nameColIndex = c.getColumnIndex("url_site");

            do {
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
                btnNew.setOnClickListener(clickadd);
                btnNew.setOnLongClickListener(longclickadd);
                llMain.addView(btnNew, lParams);
            } while (c.moveToNext());
        }
        c.close();

        //создание обработчика нажатия

        add.setOnClickListener(clickadd);
        canc.setOnClickListener(clickadd);
    }
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {

            Toast.makeText(getBaseContext(), "By By", Toast.LENGTH_SHORT).show();
            finish();
        } else {
           // Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}

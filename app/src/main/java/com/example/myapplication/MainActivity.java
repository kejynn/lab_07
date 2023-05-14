package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txt_key;
    EditText txt_value;
    DB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txt_key = findViewById(R.id.txt_key);
        txt_value = findViewById(R.id.txt_value);

        mydb = new DB(this, "mybase.db", null, 1);
    }

    public void on_insert_click(View v)
    {
        String key = txt_key.getText().toString();
        String value = txt_value.getText().toString();
        try
        {
            mydb.do_insert(key, value);
        }
        catch (SQLException e)
        {
            AlertDialog.Builder key_exist = new AlertDialog.Builder(MainActivity.this);
            key_exist.setMessage("Ключ уже существует")
                    .setCancelable(true)
                    .setPositiveButton("Ок", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = key_exist.create();
            alert.setTitle("Ошибка!");
            alert.show();
        }
    }

    public void on_update_click(View v)
    {
        String key = txt_key.getText().toString();
        String value = txt_value.getText().toString();

        mydb.do_update(key, value);
    }

    public void on_select_click(View v)
    {
        String key = txt_key.getText().toString();
        String value = mydb.do_select(key);

        txt_value.setText(value);
    }

    public void on_delete_click(View v)
    {
        String key = txt_key.getText().toString();

        AlertDialog.Builder delete = new AlertDialog.Builder(MainActivity.this);
        delete.setMessage("Вы действительно хотите удалить запись?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mydb.do_delete(key);
                            }
                        })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog del = delete.create();
        del.setTitle("Удаление записи");
        del.show();
    }
}
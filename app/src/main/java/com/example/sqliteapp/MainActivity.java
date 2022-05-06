package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, phone, dateOfBirth;
    Button insert, update, delete, select;
    DatabaseHelper databaseHelper;
    String editingName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtNumber);
        dateOfBirth = findViewById(R.id.txtDate);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnEdit);
        delete = findViewById(R.id.btnDelete);
        select = findViewById(R.id.btnSelect);
        databaseHelper = new DatabaseHelper(this);

        insert.setOnClickListener(view -> {
            Boolean checkInsertData = databaseHelper.insert(name.getText().toString(), phone.getText().toString(),
                    dateOfBirth.getText().toString());
            if (checkInsertData) {
                Toast.makeText(getApplicationContext(), "Данные успешно добавлены", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        });

        select.setOnClickListener(view -> {
            Cursor res = databaseHelper.getData();
            if (res.getCount()==0) {
                Toast.makeText(MainActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder builder = new StringBuilder();
            while (res.moveToNext()) {
                builder.append("Имя: ").append(res.getString(1)).append("\n");
                builder.append("Тел. номер: ").append(res.getString(2)).append("\n");
                builder.append("Дата рождения: ").append(res.getString(3)).append("\n\n");
            }

            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setCancelable(true);
            builder1.setTitle("Данные пользователей");
            builder1.setMessage(builder.toString());
            builder1.show();
        });

        delete.setOnClickListener(view ->{
            Boolean checkInsertData = databaseHelper.delete(name.getText().toString());
            if(checkInsertData)
            {
                Toast.makeText(getApplicationContext(),"Данные успешно удалены",Toast.LENGTH_LONG).show();
                name.setText("");
                phone.setText("");
                dateOfBirth.setText("");
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Произошла ошибка",Toast.LENGTH_LONG).show();
            }
        });

        update.setOnClickListener(view ->{
            if(name.getText().toString() == null || name.getText().toString() == "")
            {
                Toast.makeText(getApplicationContext(),"Введите сначала имя, которую хотите изменить, а после новые данные",Toast.LENGTH_LONG).show();
            }
            else
            {
                Cursor res = databaseHelper.getData();
                if(res.getCount() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Нет данных чтобы изменить",Toast.LENGTH_LONG).show();
                    return;
                }
                while (res.moveToNext()){

                    if(editingName.equals(""))
                    {
                        editingName = name.getText().toString();
                        Toast.makeText(getApplicationContext(),"Теперь введите новые данные для изменение пользователя",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Boolean checkInsertData =  databaseHelper.edit(editingName,name.getText().toString(),phone.getText().toString(),dateOfBirth.getText().toString());
                        if(checkInsertData)
                        {
                            Toast.makeText(getApplicationContext(),"Данные успешно изменены",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Произошла ошибка",Toast.LENGTH_LONG).show();
                        }
                        editingName = "";
                    }
                }
            }
        });
    }
}
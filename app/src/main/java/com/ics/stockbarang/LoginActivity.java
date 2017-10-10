package com.ics.stockbarang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ics.stockbarang.Action.Action_Barang;
import com.ics.stockbarang.Connection.DB_helper;

public class LoginActivity extends AppCompatActivity {

    DB_helper dbcenter;
    Cursor cursor;

    EditText username,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Username sama dengan EditText yang idnya
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        //Koneksi Ke database
        dbcenter = new DB_helper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbcenter.getReadableDatabase();
                cursor = db.rawQuery("SELECT * FROM tbl_user WHERE username = '"+ username.getText().toString() +"' AND password = '"+ password.getText().toString() +"'",null);
                //Baca Dari Atas
                cursor.moveToFirst();
                //Jika datanya ada
                if(cursor.getCount() > 0){
                    cursor.moveToPosition(0);
                    if(cursor.getString(0).toString().equals("manager")){
                        Intent i = new Intent(LoginActivity.this,ManagerAdmin.class);
                        startActivity(i);
                        finish();

                    }else {
                        Intent i = new Intent(LoginActivity.this,HomeAdmin.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Snackbar.make(v,"Username Atau Password Salah",Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }
}
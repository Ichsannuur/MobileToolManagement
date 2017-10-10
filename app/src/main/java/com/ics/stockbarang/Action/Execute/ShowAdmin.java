package com.ics.stockbarang.Action.Execute;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class ShowAdmin extends AppCompatActivity {

    EditText username,password;
    DB_helper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_admin);
        getSupportActionBar().setTitle("Manager");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Show admin");

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        dbcenter = new DB_helper(this);

        SQLiteDatabase db = dbcenter.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_user WHERE username = '" + getIntent().getStringExtra("username") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            username.setText("Username : " + cursor.getString(0).toString());
            password.setText("Password : " + cursor.getString(1).toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id != R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

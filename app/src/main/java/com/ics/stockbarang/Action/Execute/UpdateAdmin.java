package com.ics.stockbarang.Action.Execute;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ics.stockbarang.Action.Action_Distributor;
import com.ics.stockbarang.Action.RegistrasiAdmin;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class UpdateAdmin extends AppCompatActivity {

    EditText username,password;
    DB_helper dbcenter;
    Button update;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);
        getSupportActionBar().setTitle("Manager");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Update admin");

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        update = (Button)findViewById(R.id.update);

        dbcenter = new DB_helper(this);

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_user WHERE username = '" + getIntent().getStringExtra("username") + "'",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            cursor.moveToPosition(0);
            username.setText(cursor.getString(0).toString());
            password.setText(cursor.getString(1).toString());
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (username.getText().toString().equals(null)) {
                        username.setError("Data tidak boleh kosong");
                    } else if (password.getText().toString().equals(null)) {
                        password.setError("Data tidak boleh kosong");
                    } else {
                        SQLiteDatabase db = dbcenter.getWritableDatabase();
                        db.execSQL("UPDATE tbl_user SET username = '" +
                                username.getText().toString() + "',password = '" +
                                password.getText().toString() + "' WHERE username = '" + getIntent().getStringExtra("username") + "'");
                        Toast.makeText(UpdateAdmin.this, "Data Terupdate", Toast.LENGTH_SHORT).show();
                        RegistrasiAdmin.ra.showList();
                        finish();
                    }
                }catch (SQLiteException e){
                    Toast.makeText(UpdateAdmin.this, "Data Sudah Ada", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

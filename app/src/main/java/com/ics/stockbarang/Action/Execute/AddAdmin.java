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

import com.ics.stockbarang.Action.RegistrasiAdmin;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class AddAdmin extends AppCompatActivity {
    Button simpan;
    Cursor cursor;
    DB_helper db_center;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        getSupportActionBar().setTitle("Manager");
        getSupportActionBar().setSubtitle("Add Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db_center = new DB_helper(this);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        simpan = (Button)findViewById(R.id.simpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    username.setError("Data tidak boleh kosong");
                } else if (password.getText().toString().equals("")) {
                    password.setError("Data tidak boleh kosong");
                } else {
                    try {


                        SQLiteDatabase db = db_center.getWritableDatabase();
                        db.execSQL("INSERT INTO tbl_user VALUES ('"+username.getText().toString().trim()+"','"+password.getText().toString().trim()+"')");
                        Toast.makeText(AddAdmin.this, "Data Ditambah", Toast.LENGTH_SHORT).show();
                        RegistrasiAdmin.ra.showList();
                        finish();
                    } catch (SQLiteException e) {
                        Toast.makeText(AddAdmin.this, "Data Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
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

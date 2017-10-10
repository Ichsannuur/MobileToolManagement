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
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class Add_distributor extends AppCompatActivity {
    Button simpan;
    Cursor cursor;
    DB_helper db_center;
    EditText nama_distributor,alamat_distributor,no_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_distributor);
        getSupportActionBar().setTitle("Stock Barang");
        getSupportActionBar().setSubtitle("Add Distributor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db_center = new DB_helper(this);

        nama_distributor = (EditText)findViewById(R.id.nama_distributor);
        alamat_distributor = (EditText)findViewById(R.id.alamat_distributor);
        no_hp = (EditText)findViewById(R.id.no_hp);
        simpan = (Button)findViewById(R.id.simpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama_distributor.getText().toString().equals("")) {
                    nama_distributor.setError("Data tidak boleh kosong");
                } else if (alamat_distributor.getText().toString().equals("")) {
                    alamat_distributor.setError("Data tidak boleh kosong");
                } else if (no_hp.getText().toString().equals("")) {
                    no_hp.setError("Data tidak boleh kosong");
                } else{
                    try {

                        SQLiteDatabase sh = db_center.getReadableDatabase();
                        cursor = sh.rawQuery("SELECT COUNT(*) FROM tbl_distributor", null);
                        cursor.moveToFirst();
                        int count = cursor.getInt(0);
                        int id = count + 1;

                        SQLiteDatabase db = db_center.getWritableDatabase();
                        db.execSQL("INSERT INTO tbl_distributor VALUES ('" + id + "','" + nama_distributor.getText().toString()
                                + "','" + alamat_distributor.getText().toString() + "','" + no_hp.getText().toString() + "')");
                        Toast.makeText(Add_distributor.this, "Data Ditambah", Toast.LENGTH_SHORT).show();
                        Action_Distributor.ad.showList();
                        finish();
                    } catch (SQLiteException e) {
                        Toast.makeText(Add_distributor.this, "Data Sudah Ada", Toast.LENGTH_SHORT).show();
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

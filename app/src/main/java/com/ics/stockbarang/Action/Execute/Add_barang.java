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

import com.ics.stockbarang.Action.Action_Barang;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_barang extends AppCompatActivity {

    DB_helper dbcenter;
    EditText kode_barang,nama_barang;
    Button simpan;
    Cursor cursor;
    int id_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barang);
        getSupportActionBar().setTitle("Stock Barang");
        getSupportActionBar().setSubtitle("Add Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbcenter = new DB_helper(this);

        kode_barang = (EditText)findViewById(R.id.kode_barang);
        nama_barang = (EditText)findViewById(R.id.nama_barang);
        simpan = (Button)findViewById(R.id.simpan);

        //Auto_increment
        SQLiteDatabase id = dbcenter.getReadableDatabase();
        cursor = id.rawQuery("SELECT COUNT(*) FROM tbl_barang",null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        id_barang = count + 1;
        kode_barang.setText("BRNG" + id_barang);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validasi Jika text masih kosong
                if(nama_barang.getText().toString().equals("")){
                    nama_barang.setError("Data tidak boleh kosong");
                }else {
                    try {
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                        SQLiteDatabase sql = dbcenter.getWritableDatabase();
                        sql.execSQL("INSERT INTO tbl_barang VALUES ('" + id_barang + "','" + kode_barang.getText().toString() + "','"
                                + nama_barang.getText().toString() + "','"
                                + 0 + "','"
                                + date + "')");

                        Toast.makeText(Add_barang.this, "Data Ditambah", Toast.LENGTH_SHORT).show();
                        Action_Barang.ab.showList();
                        finish();
                    } catch (SQLiteException e) {
                        Toast.makeText(Add_barang.this, "Data Sudah Ada", Toast.LENGTH_SHORT).show();
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

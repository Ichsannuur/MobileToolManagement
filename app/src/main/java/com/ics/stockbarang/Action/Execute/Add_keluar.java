package com.ics.stockbarang.Action.Execute;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ics.stockbarang.Action.Action_keluar;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_keluar extends AppCompatActivity {

    Button butBarang,simpanData;
    EditText barang_keluar,qty,tgl_masuk;
    Cursor cursor;
    String tanggal;
    SQLiteDatabase db;
    DB_helper dbcenter;
    int stok_old;
    int harpok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keluar);

        tgl_masuk = (EditText)findViewById(R.id.tgl_masuk);
        simpanData = (Button)findViewById(R.id.simpanData);
        butBarang = (Button)findViewById(R.id.butBarang);
        qty = (EditText)findViewById(R.id.qty);
        barang_keluar = (EditText)findViewById(R.id.barang_keluar);

        dbcenter = new DB_helper(this);

        //Tanggal Hari ini
        tanggal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tgl_masuk.setText(tanggal + "");

        db = dbcenter.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_barang WHERE id_barang = '"+ getIntent().getIntExtra("barang_keluar",0) +"'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            barang_keluar.setText(cursor.getString(2).toString());
            //OLD Stok
            stok_old = cursor.getInt(3);
        }

        butBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_keluar.this,TransaksiBarangKeluar.class);
                startActivity(i);
                finish();
            }
        });

        simpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (barang_keluar.getText().toString().equals("")) {
                    barang_keluar.setError("Data Tidak Boleh Kosong");
                } else if (qty.getText().toString().equals("")) {
                    qty.setError("Data Tidak Boleh Kosong");
                } else if (qty.getText().toString().equals("0")){
                    qty.setError("Data Tidak Boleh Diisi 0");
                } else{

                    try {
                        int stok_keluar = Integer.parseInt(qty.getText().toString());
                        int jum = stok_old - stok_keluar;

                        if (stok_keluar > stok_old) {
                            qty.setError("Quantity tidak boleh lebih dari " + stok_old);
                        } else {

                            SQLiteDatabase sh = dbcenter.getReadableDatabase();
                            cursor = sh.rawQuery("SELECT COUNT(*) FROM tbl_keluar", null);
                            cursor.moveToFirst();
                            int count = cursor.getInt(0);
                            int id = count + 1;

                            SQLiteDatabase sql = dbcenter.getWritableDatabase();
                            sql.execSQL("UPDATE tbl_barang SET stok = '" + jum + "' WHERE id_barang = '" + getIntent().getIntExtra("barang_keluar", 0) + "'");
                            sql.execSQL("INSERT INTO tbl_keluar VALUES ('" + id + "','" + getIntent().getIntExtra("barang_keluar", 0) + "','" + barang_keluar.getText().toString() + "','" + qty.getText().toString() + "','" + tanggal + "')");
                            Toast.makeText(Add_keluar.this, "Data Disimpan", Toast.LENGTH_SHORT).show();
                            Action_keluar.ak.showList();
                            finish();
                        }
                    } catch (SQLiteException e) {

                    }
                }
            }
        });
    }
}

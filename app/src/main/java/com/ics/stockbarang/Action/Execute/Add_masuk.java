package com.ics.stockbarang.Action.Execute;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.ics.stockbarang.Action.Action_masuk;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_masuk extends AppCompatActivity {

    Button cancel,butDistri,butBarang,simpanData;
    EditText distributor_masuk,barang_masuk,qty,tgl_masuk;
    Cursor cursor;
    String tanggal;
    SQLiteDatabase db,ab;
    SharedPreferences preferences;
    DB_helper dbcenter;
    int id_distributor;
    int stok_old;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_masuk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tgl_masuk = (EditText)findViewById(R.id.tgl_masuk);
        distributor_masuk = (EditText)findViewById(R.id.distributor_masuk);
        butDistri = (Button)findViewById(R.id.butDistri);
        cancel = (Button)findViewById(R.id.cancel);
        simpanData = (Button)findViewById(R.id.simpanData);
        butBarang = (Button)findViewById(R.id.butBarang);
        qty = (EditText)findViewById(R.id.qty);
        barang_masuk = (EditText)findViewById(R.id.barang_masuk);

        //Get Data Session Distributor
        preferences = getSharedPreferences("distri_masuk",MODE_PRIVATE);
        id_distributor = preferences.getInt("id_distributor",0);

        //Cetak Tanggal
        tanggal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tgl_masuk.setText(tanggal + "");

        //Null Distributor Masuk
        distributor_masuk.setText("");

        dbcenter = new DB_helper(this);

        ab = dbcenter.getWritableDatabase();
        cursor = ab.rawQuery("SELECT * FROM tbl_distributor WHERE id_distributor = '"+ id_distributor +"'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            distributor_masuk.setText(cursor.getString(1).toString());
        }

        db = dbcenter.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_barang WHERE id_barang = '"+ getIntent().getIntExtra("barang_distributor",0) +"'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            barang_masuk.setText(cursor.getString(2).toString());
            //OLD Stok
            stok_old = cursor.getInt(3);
        }

        butDistri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_masuk.this,TransaksiDistributorMasuk.class);
                startActivity(i);
                finish();
            }
        });

        butBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_masuk.this,TransaksiBarangMasuk.class);
                startActivity(i);
                finish();
            }
        });

        simpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (qty.getText().toString().equals("")){
                    qty.setError("Data Tidak Boleh kosong");
                }else if(distributor_masuk.getText().toString().equals("")) {
                    barang_masuk.setError("Data Tidak Boleh Kosong");
                }else if(barang_masuk.getText().toString().equals("")){
                    barang_masuk.setError("Data Tidak Boleh Kosong");
                }else if(qty.getText().toString().equals("0")){
                    qty.setError("QTY tidak boleh diisi 0");
                }else {

                    try {
                        int stok_new = Integer.parseInt(qty.getText().toString());
                        int jum = stok_old + stok_new;
                        SQLiteDatabase sh = dbcenter.getReadableDatabase();
                        cursor = sh.rawQuery("SELECT COUNT(*) FROM tbl_masuk", null);
                        cursor.moveToFirst();
                        int count = cursor.getInt(0);
                        int id = count + 1;

                        SQLiteDatabase sql = dbcenter.getWritableDatabase();
                        sql.execSQL("UPDATE tbl_barang SET stok = '" + jum + "' WHERE id_barang = '" + getIntent().getIntExtra("barang_distributor", 0) + "'");
                        sql.execSQL("INSERT INTO tbl_masuk VALUES ('" + id + "','" + id_distributor + "','" + distributor_masuk.getText().toString() + "','" + getIntent().getIntExtra("barang_distributor", 0) + "','" + barang_masuk.getText().toString() + "','" + qty.getText().toString() + "','" + tanggal + "')");
                        Toast.makeText(Add_masuk.this, "Data Disimpan", Toast.LENGTH_SHORT).show();
                        Action_masuk.am.showList();
                        finish();
                    } catch (SQLiteException e) {

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

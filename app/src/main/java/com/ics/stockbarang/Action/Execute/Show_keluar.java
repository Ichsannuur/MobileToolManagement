package com.ics.stockbarang.Action.Execute;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class Show_keluar extends AppCompatActivity {

    EditText nama_barang,stok,tanggal;
    DB_helper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_keluar);
        getSupportActionBar().setTitle("Stock Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Detail Barang Keluar");

        nama_barang = (EditText)findViewById(R.id.nama_barang);
        stok = (EditText)findViewById(R.id.stok);
        tanggal = (EditText)findViewById(R.id.tanggal);

        dbcenter = new DB_helper(this);

        SQLiteDatabase db = dbcenter.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_keluar WHERE id_transaksi_keluar = '" + getIntent().getIntExtra("id_transaksi_keluar",0) + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            nama_barang.setText("Nama Barang : " + cursor.getString(2).toString());
            stok.setText("Barang di keluarkan : " + cursor.getString(3).toString());
            tanggal.setText("Tanggal Keluar : " + cursor.getString(4).toString());
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

package com.ics.stockbarang.Action.Execute;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class Show_masuk extends AppCompatActivity {

    EditText nama_distributor,nama_barang,stok,tanggal;
    DB_helper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_masuk);
        getSupportActionBar().setTitle("Stock Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Detail Barang Masuk");

        nama_distributor = (EditText)findViewById(R.id.nama_distributor);
        nama_barang = (EditText)findViewById(R.id.nama_barang);
        stok = (EditText)findViewById(R.id.stok);
        tanggal = (EditText)findViewById(R.id.tanggal);

        dbcenter = new DB_helper(this);

        SQLiteDatabase db = dbcenter.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_masuk WHERE id_transaksi = '" + getIntent().getIntExtra("id_transaksi",0) + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            nama_distributor.setText("Nama Distributor: " + cursor.getString(2).toString());
            nama_barang.setText("Nama Barang : " + cursor.getString(4).toString());
            stok.setText("Barang di Stok : " + cursor.getString(5).toString());
            tanggal.setText("Tanggal Masuk : " + cursor.getString(6).toString());
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

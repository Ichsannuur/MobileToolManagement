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

public class Show_distributor extends AppCompatActivity {

    EditText nama_distributor,alamat_distributor,no_hp;
    DB_helper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_distributor);
        getSupportActionBar().setTitle("Stock Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Detail Distributor");

        nama_distributor = (EditText)findViewById(R.id.nama_distributor);
        alamat_distributor = (EditText)findViewById(R.id.alamat_distributor);
        no_hp = (EditText)findViewById(R.id.no_hp);

        dbcenter = new DB_helper(this);

        SQLiteDatabase db = dbcenter.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_distributor WHERE id_distributor = '" + getIntent().getIntExtra("id_distributor",0) + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            nama_distributor.setText("Nama Distributor : " + cursor.getString(1).toString());
            alamat_distributor.setText("Alamat Distributor : " + cursor.getString(2).toString());
            no_hp.setText("No Handphone : " + cursor.getString(3).toString());
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

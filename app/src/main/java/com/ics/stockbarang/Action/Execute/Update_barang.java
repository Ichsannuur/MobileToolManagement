package com.ics.stockbarang.Action.Execute;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ics.stockbarang.Action.Action_Barang;
import com.ics.stockbarang.Action.Action_Distributor;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Update_barang extends AppCompatActivity {

    EditText kode_barang,nama_barang;
    DB_helper dbcenter;
    Button update;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);
        getSupportActionBar().setTitle("Stock Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Detail Barang");

        kode_barang = (EditText)findViewById(R.id.kode_barang);
        nama_barang = (EditText)findViewById(R.id.nama_barang);
        update = (Button)findViewById(R.id.update);

        dbcenter = new DB_helper(this);

        SQLiteDatabase db = dbcenter.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_barang WHERE id_barang = '" + getIntent().getIntExtra("id_barang",0) + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            kode_barang.setText(cursor.getString(1).toString());
            nama_barang.setText(cursor.getString(2).toString());
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_barang.getText().toString().equals(null)){
                    nama_barang.setError("Data tidak boleh kosong");
                }else {
                    SQLiteDatabase db = dbcenter.getWritableDatabase();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    db.execSQL("UPDATE tbl_barang SET nama_barang = '" +
                            nama_barang.getText().toString() + "',tgl_masuk = '" + date + "' WHERE id_barang = '" + getIntent().getIntExtra("id_barang", 0) + "'");
                    Toast.makeText(Update_barang.this, "Data Terupdate", Toast.LENGTH_SHORT).show();
                    Action_Barang.ab.showList();
                    finish();
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

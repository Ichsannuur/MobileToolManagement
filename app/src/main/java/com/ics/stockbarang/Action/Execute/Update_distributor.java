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

import com.ics.stockbarang.Action.Action_Distributor;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class Update_distributor extends AppCompatActivity {

    EditText nama_distributor,alamat_distributor,no_hp;
    DB_helper dbcenter;
    Button update;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_distributor);
        getSupportActionBar().setTitle("Stock Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Detail Distributor");

        nama_distributor = (EditText)findViewById(R.id.nama_distributor);
        alamat_distributor = (EditText)findViewById(R.id.alamat_distributor);
        no_hp = (EditText)findViewById(R.id.no_hp);
        update = (Button)findViewById(R.id.update);

        dbcenter = new DB_helper(this);

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_distributor WHERE id_distributor = '" + getIntent().getIntExtra("id_distributor",0) + "'",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            cursor.moveToPosition(0);
            nama_distributor.setText(cursor.getString(1).toString());
            alamat_distributor.setText(cursor.getString(2).toString());
            no_hp.setText(cursor.getString(3).toString());
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama_distributor.getText().toString().equals(null)) {
                    nama_distributor.setError("Data tidak boleh kosong");
                } else if (alamat_distributor.getText().toString().equals(null)) {
                    alamat_distributor.setError("Data tidak boleh kosong");
                } else if (no_hp.getText().toString().equals(null)) {
                    no_hp.setError("Data tidak boleh kosong");
                } else {
                    SQLiteDatabase db = dbcenter.getWritableDatabase();
                    db.execSQL("UPDATE tbl_distributor SET nama_distributor = '" +
                            nama_distributor.getText().toString() + "',alamat_distributor = '" +
                            alamat_distributor.getText().toString() + "',no_hp = '" +
                            no_hp.getText().toString() + "' WHERE id_distributor = '" + getIntent().getIntExtra("id_distributor", 0) + "'");
                    Toast.makeText(Update_distributor.this, "Data Terupdate", Toast.LENGTH_SHORT).show();
                    Action_Distributor.ad.showList();
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

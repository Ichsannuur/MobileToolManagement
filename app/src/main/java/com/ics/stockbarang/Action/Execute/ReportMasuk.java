package com.ics.stockbarang.Action.Execute;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

public class ReportMasuk extends AppCompatActivity {

    protected Cursor cursor;
    DB_helper dbcenter;
    public static ReportMasuk rm;
    SharedPreferences sharedPreferences;
    String awal,akhir;
    ListView listView;
    String [] listMsk;
    int [] listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_masuk);
        //SET SESSION AWAL DAN AKHIR
        sharedPreferences = getSharedPreferences("report_masuk",MODE_PRIVATE);
        awal = sharedPreferences.getString("tanggal_awal","");
        akhir = sharedPreferences.getString("tanggal_akhir","");

        getSupportActionBar().setTitle("Manager Report");
        getSupportActionBar().setSubtitle(awal + " To " + akhir);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rm = this;
        dbcenter = new DB_helper(this);
        showList();

    }

    public void showList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_masuk WHERE tgl_transaksi BETWEEN '"+ awal +"' AND '"+ akhir +"' ORDER BY id_transaksi DESC",null);
        listMsk = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0;cc < cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            listMsk[cc] = cursor.getString(2).toString();
            listId[cc] = cursor.getInt(0);
        }

        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listMsk));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selection = listId[position]; //.getItemAtPosition(position).toString();
                final CharSequence[] dialogitem = {"Detail"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ReportMasuk.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), Show_masuk.class);
                                i.putExtra("id_transaksi", selection);
                                startActivity(i);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
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

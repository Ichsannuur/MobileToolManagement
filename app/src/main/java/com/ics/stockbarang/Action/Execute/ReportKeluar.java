package com.ics.stockbarang.Action.Execute;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ics.stockbarang.Action.Action_keluar;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class ReportKeluar extends AppCompatActivity {

    protected Cursor cursor;
    DB_helper dbcenter;
    public static ReportKeluar rk;
    SharedPreferences sharedPreferences;
    String awal,akhir;
    ListView listView;
    String [] listKel;
    int [] listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_keluar);

        //SET SESSION AWAL DAN AKHIR
        sharedPreferences = getSharedPreferences("report_keluar",MODE_PRIVATE);
        awal = sharedPreferences.getString("tgawal","");
        akhir = sharedPreferences.getString("tgakhir","");

        getSupportActionBar().setTitle("Manager Report");
        getSupportActionBar().setSubtitle(awal + " To " + akhir);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rk = this;
        dbcenter = new DB_helper(this);
        showKeluar();
    }

    public void showKeluar(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_keluar WHERE tgl_transaksi BETWEEN '"+ awal +"' AND '"+ akhir +"' ORDER BY id_transaksi_keluar DESC",null);
        listKel = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0;cc < cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            listKel[cc] = cursor.getString(2).toString();
            listId[cc] = cursor.getInt(0);
        }

        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listKel));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selection = listId[position]; //.getItemAtPosition(position).toString();
                final CharSequence[] dialogitem = {"Detail"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ReportKeluar.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), Show_keluar.class);
                                i.putExtra("id_transaksi_keluar", selection);
                                startActivity(i);
                                break;
                            case 1 :
//                                Intent i = new Intent(getApplicationContext(), Update_masuk.class);
//                                i.putExtra("id_transaksi", selection);
//                                startActivity(i);
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

package com.ics.stockbarang.Action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ics.stockbarang.Action.Execute.Add_keluar;
import com.ics.stockbarang.Action.Execute.Show_keluar;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class Action_keluar extends AppCompatActivity {

    protected Cursor cursor;
    Button fab;
    DB_helper dbcenter;
    public static Action_keluar ak;
    ListView listView;
    String [] listBrng;
    int [] listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_keluar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Barang Keluar");

        ak = this;
        dbcenter = new DB_helper(this);
        showList();

        fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Add_keluar.class);
                startActivity(i);
            }
        });
    }

    public void showList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_keluar ORDER BY id_transaksi_keluar DESC",null);
        listBrng = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0;cc < cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            listBrng[cc] = cursor.getString(2).toString();
            listId[cc] = cursor.getInt(0);
        }

        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listBrng));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selection = listId[position]; //.getItemAtPosition(position).toString();
                final CharSequence[] dialogitem = {"Lihat Barang"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Action_keluar.this);
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

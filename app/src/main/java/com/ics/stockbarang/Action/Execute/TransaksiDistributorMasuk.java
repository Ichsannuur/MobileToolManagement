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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ics.stockbarang.Action.Action_masuk;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class TransaksiDistributorMasuk extends AppCompatActivity {

    protected Cursor cursor;
    DB_helper dbcenter;
    public static TransaksiDistributorMasuk ad;
    ListView listView;
    EditText search;
    String [] listDist;
    int [] listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_distributor_masuk);
        search = (EditText)findViewById(R.id.search);

        ad = this;
        dbcenter = new DB_helper(this);
        showList();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                showList();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showListSearch(search.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void showList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_distributor",null);
        listDist = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0;cc < cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            listDist[cc] = cursor.getString(1).toString();
            listId[cc] = cursor.getInt(0);
        }

        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDist));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selection = listId[position]; //.getItemAtPosition(position).toString();
                final CharSequence[] dialogitem = {"Pilih"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiDistributorMasuk.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 :
                                SharedPreferences preferences = getSharedPreferences("distri_masuk",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("id_distributor",selection);
                                editor.apply();
                                Intent i = new Intent(getApplicationContext(),Add_masuk.class);
                                startActivity(i);
                                finish();
                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
    }

    public void showListSearch(String cari){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_distributor WHERE nama_distributor LIKE '%"+cari+"%'",null);
        listDist = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0;cc < cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            listDist[cc] = cursor.getString(1).toString();
            listId[cc] = cursor.getInt(0);
        }

        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDist));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selection = listId[position]; //.getItemAtPosition(position).toString();
                final CharSequence[] dialogitem = {"Pilih"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiDistributorMasuk.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 :
                                SharedPreferences preferences = getSharedPreferences("distri_masuk",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("id_distributor",selection);
                                editor.apply();
                                Intent i = new Intent(getApplicationContext(),Add_masuk.class);
                                startActivity(i);
                                finish();
                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id != R.id.home) {
//            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

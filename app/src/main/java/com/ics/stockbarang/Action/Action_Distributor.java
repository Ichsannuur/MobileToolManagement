package com.ics.stockbarang.Action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ics.stockbarang.Action.Execute.Add_distributor;
import com.ics.stockbarang.Action.Execute.Show_distributor;
import com.ics.stockbarang.Action.Execute.Update_distributor;
import com.ics.stockbarang.Connection.DB_helper;
import com.ics.stockbarang.R;

public class Action_Distributor extends AppCompatActivity {

    protected Cursor cursor;
    DB_helper dbcenter;
    public static Action_Distributor ad;
    ListView listView;
    EditText search;
    String [] listDist;
    int [] listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Action_Distributor.this, Add_distributor.class);
                startActivity(i);
            }
        });

    }

    public void showList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tbl_distributor ORDER BY id_distributor DESC",null);
        listDist = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0;cc < cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            listDist[cc] = cursor.getString(1).toString();
            listId[cc] = cursor.getInt(0);
        }

        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDist));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selection = listId[position]; //.getItemAtPosition(position).toString();
                final CharSequence[] dialogitem = {"Lihat Distributor","Update Distributor","Hapus Distributor"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Action_Distributor.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), Show_distributor.class);
                                i.putExtra("id_distributor", selection);
                                startActivity(i);
                                break;

                            case 1 :
                                Intent i2 = new Intent(getApplicationContext(), Update_distributor.class);
                                i2.putExtra("id_distributor",selection);
                                startActivity(i2);
                                break;

                            case 2 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM tbl_distributor WHERE id_distributor = '" + selection  +"'");
                                showList();
                                Toast.makeText(Action_Distributor.this, "Data Dihapus ", Toast.LENGTH_SHORT).show();
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
        cursor = db.rawQuery("SELECT * FROM tbl_distributor WHERE nama_distributor LIKE '%"+ cari +"%'",null);
        listDist = new String[cursor.getCount()];
        listId = new int[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0;cc < cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            listDist[cc] = cursor.getString(1).toString();
            listId[cc] = cursor.getInt(0);
        }

        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDist));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selection = listId[position]; //.getItemAtPosition(position).toString();
                final CharSequence[] dialogitem = {"Lihat Distributor","Update Distributor","Hapus Distributor"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Action_Distributor.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), Show_distributor.class);
                                i.putExtra("id_distributor", selection);
                                startActivity(i);
                                break;

                            case 1 :
                                Intent i2 = new Intent(getApplicationContext(), Update_distributor.class);
                                i2.putExtra("id_distributor",selection);
                                startActivity(i2);
                                break;

                            case 2 :
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM tbl_distributor WHERE id_distributor = '" + selection  +"'");
                                showList();
                                Toast.makeText(Action_Distributor.this, "Data Dihapus ", Toast.LENGTH_SHORT).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id != R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

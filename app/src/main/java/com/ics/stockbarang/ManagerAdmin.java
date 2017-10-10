package com.ics.stockbarang;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.stockbarang.Action.Execute.SortKeluar;
import com.ics.stockbarang.Action.Execute.SortMasuk;
import com.ics.stockbarang.Action.RegistrasiAdmin;
import com.ics.stockbarang.Connection.DB_helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManagerAdmin extends AppCompatActivity {

    Button cancel,butdialog;
    DB_helper dbcenter;
    Cursor cursor;
    CardView card1,card2,card3;
    TextView jumbar,jumdis,jumsuk,jumkel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_admin);
        getSupportActionBar().setTitle("Manager Admin");
        String date = new SimpleDateFormat("yyy-MM-dd").format(new Date());
        getSupportActionBar().setSubtitle(date);
        jumbar = (TextView)findViewById(R.id.jumBar);
        jumdis = (TextView)findViewById(R.id.jumDis);
        jumsuk = (TextView)findViewById(R.id.jumSuk);

        card1 = (CardView)findViewById(R.id.cardMasuk);
        card2 = (CardView)findViewById(R.id.cardKeluar);
        card3 = (CardView)findViewById(R.id.cardTambah);
        jumkel = (TextView)findViewById(R.id.jumKel);
        dbcenter = new DB_helper(this);

        //Jumlah Data Barang
        SQLiteDatabase sqlBarang = dbcenter.getReadableDatabase();
        cursor = sqlBarang.rawQuery("SELECT COUNT(*) FROM tbl_barang",null);
        cursor.moveToFirst();
        int jumBar = cursor.getInt(0);
        jumbar.setText(jumBar + "");

        //Jumlah Data Distributor
        SQLiteDatabase sqlDistri = dbcenter.getReadableDatabase();
        cursor = sqlBarang.rawQuery("SELECT COUNT(*) FROM tbl_distributor",null);
        cursor.moveToFirst();
        int jumDis = cursor.getInt(0);
        jumdis.setText(jumDis + "");

        //Jumlah Data Barang Masuk
        SQLiteDatabase sqlMasuk = dbcenter.getReadableDatabase();
        cursor = sqlBarang.rawQuery("SELECT COUNT(*) FROM tbl_masuk",null);
        cursor.moveToFirst();
        int jumSek = cursor.getInt(0);
        jumsuk.setText(jumSek + "");

        //Jumlah Data Barang Keluar
        SQLiteDatabase sqlKeluar = dbcenter.getReadableDatabase();
        cursor = sqlBarang.rawQuery("SELECT COUNT(*) FROM tbl_keluar",null);
        cursor.moveToFirst();
        int jumKel = cursor.getInt(0);
        jumkel.setText(jumKel + "");


        //Button Sort By Date Barang Masuk
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SortMasuk.class);
                startActivity(i);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SortKeluar.class);
                startActivity(i);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrasiAdmin.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_file){

            final Dialog dialog = new Dialog(ManagerAdmin.this);
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Apakah Anda Yakin ?");

            butdialog = (Button)dialog.findViewById(R.id.butdialog);
            cancel = (Button)dialog.findViewById(R.id.cancel);
            dialog.show();

            butdialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(ManagerAdmin.this, "Suskes Logout", Toast.LENGTH_SHORT).show();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.ics.stockbarang.Connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Ichsan.Fatiha on 7/29/2017.
 */

public class DB_helper extends SQLiteOpenHelper {
    public DB_helper(Context context) {
        super(context, "db_barang.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String distributor = "CREATE TABLE tbl_distributor(id_distributor INTEGER PRIMARY KEY,nama_distributor text null,alamat_distributor text null,no_hp distributor text null)";
        db.execSQL(distributor);
        String barang = "CREATE TABLE tbl_barang(id_barang INTEGER PRIMARY KEY,kode_barang text null,nama_barang text null,stok INTEGER,tgl_masuk text null)";
        db.execSQL(barang);
        String transaksi = "CREATE TABLE tbl_masuk(id_transaksi INTEGER PRIMARY KEY,id_distributor INTEGER,nama_distributor text null,id_barang INTEGER,nama_barang text null,qty INTEGER,tgl_transaksi text null)";
        db.execSQL(transaksi);
        String keluar = "CREATE TABLE tbl_keluar(id_transaksi_keluar INTEGER PRIMARY KEY,id_barang INTEGER,nama_barang text null,qty_keluar INTEGER,tgl_transaksi text null)";
        db.execSQL(keluar);
        String user = "CREATE TABLE tbl_user(username text null PRIMARY KEY,password text null)";
        db.execSQL(user);


        String insert = "INSERT INTO tbl_user VALUES('admin','admin')";
        db.execSQL(insert);

        String insert2 = "INSERT INTO tbl_user VALUES('manager','manager')";
        db.execSQL(insert2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST tbl_distributor");
        db.execSQL("DROP TABLE IF EXIST tbl_barang");
        db.execSQL("DROP TABLE IF EXIST tbl_user");
        db.execSQL("DROP TABLE IF EXIST tbl_masuk");
        db.execSQL("DROP TABLE IF EXIST tbl_keluar");
    }

}

package com.ics.stockbarang.Action.Execute;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ics.stockbarang.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SortKeluar extends AppCompatActivity {

    Button cek,cancel;
    EditText tanggalAwal,tanggalAkhir;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_keluar);
        getSupportActionBar().setTitle("Manager Report");
        getSupportActionBar().setSubtitle("Laporan Barang Keluar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myCalendar = Calendar.getInstance();

        cek = (Button)findViewById(R.id.cek);
        cancel = (Button)findViewById(R.id.cancel);
        tanggalAwal = (EditText)findViewById(R.id.tanggalAwal);
        tanggalAkhir = (EditText)findViewById(R.id.tanggalAkhir);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tanggalAwal.setText(sdf.format(myCalendar.getTime()));
            }

        };

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tanggalAkhir.setText(sdf.format(myCalendar.getTime()));
            }

        };

        tanggalAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SortKeluar.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tanggalAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SortKeluar.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tanggalAwal.getText().toString().equals("")){
                    Snackbar.make(v, "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                }else if(tanggalAkhir.getText().toString().equals("")){
                    Snackbar.make(v, "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                }else {
                    int tangwal = Integer.parseInt(tanggalAwal.getText().toString().replace("-", ""));
                    int tanghir = Integer.parseInt(tanggalAkhir.getText().toString().replace("-", ""));

                    if (tangwal > tanghir) {
                        Snackbar.make(v, "Tanggal Awal Melebihi Tanggal Akhir", Snackbar.LENGTH_LONG).show();
                    } else {
                        //Put awal dan akhir di SESSION

                        SharedPreferences preferences = getSharedPreferences("report_keluar",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("tgawal",tanggalAwal.getText().toString());
                        editor.putString("tgakhir",tanggalAkhir.getText().toString());
                        editor.apply();

                        Intent i = new Intent(SortKeluar.this, ReportKeluar.class);
                        startActivity(i);
                        tanggalAwal.setText("");
                        tanggalAkhir.setText("");
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

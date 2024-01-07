package com.example.perpus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {

    TextView nama, tglKembali;
    Button home;
    String name, tanggal_kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        nama = findViewById(R.id.namaPeminjam);
        tglKembali = findViewById(R.id.tglKembali);

        name = getIntent().getStringExtra("nama");
        tanggal_kembali = getIntent().getStringExtra("tglKembali");

        nama.setText("Thank you for borrowing book(s) from our place, " + name + "!");
        tglKembali.setText("Please return borrowed book(s) by " + tanggal_kembali);

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
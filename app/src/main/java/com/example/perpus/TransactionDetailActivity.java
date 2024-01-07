package com.example.perpus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.perpus.adapter.TransactionDetailRecyclerViewAdapter;
import com.example.perpus.db.DBHelper;
import com.example.perpus.model.CartItem;
import com.example.perpus.model.Transaction;

import java.util.ArrayList;

public class TransactionDetailActivity extends AppCompatActivity {

    Integer transaction;
    TextView back, nama, tglPinjam, tglKembali, total;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TransactionDetailRecyclerViewAdapter recyclerViewAdapter;

    ArrayList<CartItem> arr;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        recyclerView = findViewById(R.id.buku);

        arr = new ArrayList<CartItem>();

        db = new DBHelper(this);

        transaction = Integer.valueOf(getIntent().getStringExtra("id"));

        Cursor test = db.getTransactionItems(transaction);
        while (test.moveToNext()) {
            CartItem cartItem = new CartItem();
            cartItem.setId(Integer.valueOf(test.getString(0)));
            cartItem.setTransaction_id(Integer.valueOf(test.getString(1)));
            cartItem.setBook_id(Integer.valueOf(test.getString(2)));
            cartItem.setJumlah_item(Integer.valueOf(test.getString(3)));
            arr.add(cartItem);
        }
        recyclerViewAdapter = new TransactionDetailRecyclerViewAdapter(arr, db);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        Cursor test2 = db.getTransaction(transaction);
        Transaction item = new Transaction();
        while (test2.moveToNext()) {
            item.setNama(test2.getString(4));
            item.setTglPinjam(test2.getString(2));
            item.setTglKembali(test2.getString(3));
            item.setTotal_item(Integer.valueOf(test2.getString(1)));
        }
        nama = findViewById(R.id.namaPeminjam);
        nama.setText("Nama Peminjam: " + item.getNama());
        tglPinjam = findViewById(R.id.tglPinjam);
        tglPinjam.setText("Tanggal Peminjaman: " + item.getTglPinjam());
        tglKembali = findViewById(R.id.tglKembali);
        tglKembali.setText("Tanggal Pengembalian: " + item.getTglKembali());
        total = findViewById(R.id.total);
        total.setText("Total Buku: " + item.getTotal_item().toString());

        back = findViewById(R.id.cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
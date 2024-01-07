package com.example.perpus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perpus.adapter.CartRecyclerViewAdapter;
import com.example.perpus.db.DBHelper;
import com.example.perpus.model.CartItem;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CartActivity extends AppCompatActivity {

    TextView back, name, tglPinjam, tglKembali, total, noItem;
    TextInputEditText nama;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CartRecyclerViewAdapter recyclerViewAdapter;

    ArrayList<CartItem> arr;

    DBHelper db;

    Button checkout;

    Integer total_item = 0;
    String tanggal_peminjaman, tanggal_pengembalian, tempName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart);

        arr = new ArrayList<CartItem>();
        db = new DBHelper(this);
        Cursor test = db.getCart();

        while (test.moveToNext()) {
            CartItem cartItem = new CartItem();
            cartItem.setId(Integer.valueOf(test.getString(0)));
            cartItem.setBook_id(Integer.valueOf(test.getString(1)));
            cartItem.setJumlah_item(Integer.valueOf(test.getString(2)));
            arr.add(cartItem);
            total_item += cartItem.getJumlah_item();
        }
        recyclerViewAdapter = new CartRecyclerViewAdapter(arr, db);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        checkout = findViewById(R.id.checkout);
        nama = findViewById(R.id.namaPeminjam);
        noItem = findViewById(R.id.noItem);
        if (arr.size() == 0) {
            checkout.setVisibility(View.GONE);
            nama.setVisibility(View.GONE);
        } else {
            noItem.setVisibility(View.GONE);
        }

        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date date = Calendar.getInstance().getTime();
        tanggal_peminjaman = dateFormat.format(date);


        date = Date.from(Calendar.getInstance().getTime().toInstant().plus(7, ChronoUnit.DAYS));
        tanggal_pengembalian = dateFormat.format(date);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama.getText().length() == 0) {
                    Toast.makeText(CartActivity.this, "Please fill in your name", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Checkout Confirmation");
                    LayoutInflater inflater = LayoutInflater.from(v.getContext());
                    View view = inflater.inflate(R.layout.dialog_checkout, null);

                    builder.setView(view);

                    name = view.findViewById(R.id.namaPeminjam);
                    tempName = String.valueOf(nama.getText());
                    tempName = tempName.substring(0, 1).toUpperCase() + tempName.substring(1);
                    name.setText("Nama Peminjam: " + tempName);
                    total = view.findViewById(R.id.total);
                    total.setText("Total Buku: " + total_item.toString());
                    tglPinjam = view.findViewById(R.id.tglPinjam);
                    tglPinjam.setText("Tanggal Peminjaman: " + tanggal_peminjaman);
                    tglKembali = view.findViewById(R.id.tglKembali);
                    tglKembali.setText("Tanggal Pengembalian: " + tanggal_pengembalian);

                    builder.setPositiveButton("Yes, my data is correct", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            long checkInsertData = db.makeTransaction(total_item, tempName, tanggal_peminjaman, tanggal_pengembalian);
                            Boolean checkDeleteData = db.emptyCart();
                            Boolean checkInsertItem = false;
                            for (int i = 0; i < arr.size(); i++) {
                                checkInsertItem = db.makeTransactionItem(Integer.valueOf(String.valueOf(checkInsertData)), arr.get(i).getBook_id(), arr.get(i).getJumlah_item());
                            }
                            if (checkInsertData != -1 && checkDeleteData == true && checkInsertItem == true) {
                                Toast.makeText(v.getContext(), "Successfully checkout your cart", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ReceiptActivity.class);
                                intent.putExtra("nama", tempName);
                                intent.putExtra("tglKembali", tanggal_pengembalian);
                                startActivity(intent);
                            } else
                                Toast.makeText(CartActivity.this, "Failed to checkout your cart", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Something is wrong", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create();
                    builder.show();
                }
            }
        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
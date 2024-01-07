package com.example.perpus.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpus.R;
import com.example.perpus.db.DBHelper;
import com.example.perpus.model.Book;
import com.example.perpus.model.CartItem;
import com.example.perpus.model.Transaction;

import java.util.ArrayList;

public class TransactionDetailRecyclerViewAdapter extends RecyclerView.Adapter<TransactionDetailRecyclerViewAdapter.MyViewHolder>{

    ArrayList<CartItem> arr;

    DBHelper db;

    public TransactionDetailRecyclerViewAdapter(ArrayList<CartItem> arr, DBHelper db) {
        this.arr = arr;
        this.db = db;
    }

    @NonNull
    @Override
    public TransactionDetailRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_list_layout, parent, false);
        TransactionDetailRecyclerViewAdapter.MyViewHolder myViewHolder = new TransactionDetailRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionDetailRecyclerViewAdapter.MyViewHolder holder, int position) {
        Cursor test = db.getBook(arr.get(position).getBook_id());
        Book book = new Book();
        while (test.moveToNext()) {
            book.setJudul(test.getString(1));
            book.setPenulis(test.getString(2));
            book.setKategori_id(Integer.valueOf(test.getString(3)));
        }
        holder.judul.setText(book.getJudul());
        holder.penulis.setText(book.getPenulis());
        holder.kategori.setText(db.getCategory(book.getKategori_id()));
        holder.stok.setText(arr.get(position).getJumlah_item().toString());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView judul, penulis, kategori, stok;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            penulis = itemView.findViewById(R.id.penulis);
            kategori = itemView.findViewById(R.id.kategori);
            stok = itemView.findViewById(R.id.stok);
        }
    }
}

package com.example.perpus.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpus.R;
import com.example.perpus.TransactionDetailActivity;
import com.example.perpus.db.DBHelper;
import com.example.perpus.model.Book;
import com.example.perpus.model.CartItem;
import com.example.perpus.model.Transaction;

import java.util.ArrayList;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.MyViewHolder>{

    ArrayList<Transaction> arr;

    DBHelper db;

    public TransactionRecyclerViewAdapter(ArrayList<Transaction> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public TransactionRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_layout, parent, false);
        TransactionRecyclerViewAdapter.MyViewHolder myViewHolder = new TransactionRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.transaction.setText("Transaction " + String.valueOf(position + 1));
        holder.tglPinjam.setText("Tanggal Peminjaman: " + arr.get(position).getTglPinjam());
        holder.tglKembali.setText("Tanggal Pengembalian: " + arr.get(position).getTglKembali());
        holder.total.setText(arr.get(position).getTotal_item() + " Book(s)");
        holder.nama.setText(arr.get(position).getNama());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), TransactionDetailActivity.class);
                intent.putExtra("id", arr.get(position).getId().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transaction, tglPinjam, tglKembali, total, nama;
        GridLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            transaction = itemView.findViewById(R.id.transaction);
            tglPinjam = itemView.findViewById(R.id.tglPinjam);
            tglKembali = itemView.findViewById(R.id.tglKembali);
            total = itemView.findViewById(R.id.total);
            nama = itemView.findViewById(R.id.namaPeminjam);

            linearLayout = itemView.findViewById(R.id.buku);
        }
    }
}

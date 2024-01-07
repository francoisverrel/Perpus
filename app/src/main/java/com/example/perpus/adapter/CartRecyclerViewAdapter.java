package com.example.perpus.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpus.R;
import com.example.perpus.db.DBHelper;
import com.example.perpus.model.Book;
import com.example.perpus.model.CartItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder> {
    ArrayList<CartItem> arr;

    DBHelper db;

    public CartRecyclerViewAdapter(ArrayList<CartItem> arr, DBHelper db) {
        this.arr = arr;
        this.db = db;
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_layout, parent, false);
        CartRecyclerViewAdapter.MyViewHolder myViewHolder = new CartRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.MyViewHolder holder, int position) {
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkDeleteData = db.deleteCartItem(arr.get(position).getId());
                if (checkDeleteData == true) {
                    Toast.makeText(v.getContext(), "Item deleted from cart", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(v.getContext(), "Failed to delete item from cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView judul, penulis, kategori, stok;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            penulis = itemView.findViewById(R.id.penulis);
            kategori = itemView.findViewById(R.id.kategori);
            stok = itemView.findViewById(R.id.stok);

            delete = itemView.findViewById(R.id.delete);
        }
    }
}

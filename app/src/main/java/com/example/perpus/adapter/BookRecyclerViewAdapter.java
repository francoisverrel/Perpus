package com.example.perpus.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perpus.R;
import com.example.perpus.db.DBHelper;
import com.example.perpus.menu.HomeFragment;
import com.example.perpus.model.Book;
import com.example.perpus.model.CartItem;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.MyViewHolder> {

    ArrayList<Book> arr;

    DBHelper db;

    public BookRecyclerViewAdapter(ArrayList<Book> arr, DBHelper db) {
        this.arr = arr;
        this.db = db;
    }

    @NonNull
    @Override
    public BookRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_layout, parent, false);
        BookRecyclerViewAdapter.MyViewHolder myViewHolder = new BookRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.judul.setText(arr.get(position).getJudul());
        holder.penulis.setText("By " + arr.get(position).getPenulis());
        holder.kategori.setText("Genre: " + db.getCategory(arr.get(position).getKategori_id()));
        holder.stok.setText("Stok: " + String.valueOf(arr.get(position).getStok()));

        holder.buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add to Cart");
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View view = inflater.inflate(R.layout.dialog_add_to_cart, null);

                builder.setView(view);

                holder.judul = view.findViewById(R.id.judul);
                holder.penulis = view.findViewById(R.id.penulis);
                holder.kategori = view.findViewById(R.id.kategori);
                holder.stok = view.findViewById(R.id.stok);
                holder.jumlah = view.findViewById(R.id.jumlah);

                holder.judul.setText(arr.get(position).getJudul());
                holder.penulis.setText("By " + arr.get(position).getPenulis());
                holder.kategori.setText("Genre: " + db.getCategory(arr.get(position).getKategori_id()));
                holder.stok.setText("Stok: " + String.valueOf(arr.get(position).getStok()));

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Integer.valueOf(holder.jumlah.getText().toString()) > Integer.valueOf(String.valueOf(arr.get(position).getStok()))) {
                            Toast.makeText(view.getContext(), "Stok tidak mencukupi", Toast.LENGTH_SHORT).show();
                        } else {
                            CartItem cartItem = new CartItem();
                            cartItem.setBook_id(arr.get(position).getId());
                            cartItem.setJumlah_item(Integer.valueOf(holder.jumlah.getText().toString()));
                            db = new DBHelper(view.getContext());
                            Boolean checkInsertData = db.addToCart(cartItem);
                            if (checkInsertData == true) {
                                Toast.makeText(view.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(view.getContext(), "Failed adding to cart", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judul, penulis, kategori, stok;
        TextInputEditText jumlah;
        LinearLayout buku;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            penulis = itemView.findViewById(R.id.penulis);
            kategori = itemView.findViewById(R.id.kategori);
            stok = itemView.findViewById(R.id.stok);

            buku = itemView.findViewById(R.id.buku);
        }
    }
}

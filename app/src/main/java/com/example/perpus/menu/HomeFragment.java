package com.example.perpus.menu;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.perpus.R;
import com.example.perpus.adapter.BookRecyclerViewAdapter;
import com.example.perpus.db.DBHelper;
import com.example.perpus.model.Book;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BookRecyclerViewAdapter recyclerViewAdapter;

    ArrayList<Book> arr;

    DBHelper db;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.buku);

        db = new DBHelper(getActivity());

        arr = new ArrayList<Book>();

        Cursor test = db.getBooks();

        while (test.moveToNext()) {
            Book book = new Book();
            book.setId(Integer.valueOf(test.getString(0)));
            book.setJudul(test.getString(1));
            book.setPenulis(test.getString(2));
            book.setKategori_id(Integer.valueOf(test.getString(3)));
            book.setStok(Integer.valueOf(test.getString(4)));
            arr.add(book);
        }
        recyclerViewAdapter = new BookRecyclerViewAdapter(arr, db);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}
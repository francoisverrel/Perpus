package com.example.perpus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.perpus.model.CartItem;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {super(context, "Perpustakaan.db", null, 1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table buku(id Integer PRIMARY KEY AUTOINCREMENT, judul TEXT, penulis TEXT, kategori_id Integer, stok Integer)");
        db.execSQL("create Table kategori(id Integer PRIMARY KEY AUTOINCREMENT, kategori Text)");
        db.execSQL("create Table cart(id Integer PRIMARY KEY AUTOINCREMENT, book_id Integer, jumlah_item INTEGER, FOREIGN KEY (book_id) REFERENCES buku (id))");
        db.execSQL("create Table transactions(id Integer PRIMARY KEY AUTOINCREMENT, jumlah_item INTEGER, tanggal_pinjam TEXT, tanggal_pengembalian TEXT, peminjam TEXT)");
        db.execSQL("create Table transaction_item(id Integer PRIMARY KEY AUTOINCREMENT, transaction_id INTEGER, book_id Integer, jumlah_item INTEGER, FOREIGN KEY (book_id) REFERENCES buku (id), FOREIGN KEY (transaction_id) REFERENCES transactions (id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists buku");
        db.execSQL("drop Table if exists kategori");
        db.execSQL("drop Table if exists cart");
        db.execSQL("drop Table if exists transactions");
        db.execSQL("drop Table if exists transaction_item");
    }

    public Cursor getBooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from buku", null);
        return cursor;
    }

    public Cursor getBook(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from buku where id=" + id.toString(), null);
        return cursor;
    }

    public String getCategory(Integer kategori_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select kategori from kategori where id=" + kategori_id.toString(), null);
        String result = "";
        while (cursor.moveToNext()){
            result = cursor.getString(0);
        }
        return result;
    }

    public Boolean editBookStock(Integer book_id, Integer stok) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select stok from buku where id=" + book_id.toString(), null);
        String coba = "";
        while (cursor.moveToNext()) {
            coba = cursor.getString(0);
        }
        Integer temp = Integer.valueOf(coba) - stok;
        ContentValues contentValues = new ContentValues();
        contentValues.put("stok", temp);
        long result = db.update("buku", contentValues, "id=" + book_id, null);
        if (result == -1) {
            return false;
        } else return true;
    }

    public Boolean addToCart(CartItem cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("book_id", cartItem.getBook_id());
        contentValues.put("jumlah_item", cartItem.getJumlah_item());
        long result = db.insert("cart", null, contentValues);
        if (result == -1) {
            return false;
        } else return editBookStock(cartItem.getBook_id(), cartItem.getJumlah_item());
    }

    public Cursor getCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from cart", null);
        return cursor;
    }

    public Boolean deleteCartItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("cart", "id=?", new String[]{id.toString()});
        if (result == -1) {
            return false;
        } else return true;
    }

    public Boolean emptyCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("cart", null, null);
        if (result == -1) {
            return false;
        } else return true;
    }

    public long makeTransaction(Integer total_item, String nama, String tglPinjam, String tglKembali) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("jumlah_item", total_item);
        contentValues.put("tanggal_pinjam", tglPinjam);
        contentValues.put("tanggal_pengembalian", tglKembali);
        contentValues.put("peminjam", nama);
        long result = db.insert("transactions", null, contentValues);
        if (result == -1) {
            return -1;
        } else return result;
    }

    public Boolean makeTransactionItem(Integer id, Integer book_id, Integer total_item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("transaction_id", id);
        contentValues.put("book_id", book_id);
        contentValues.put("jumlah_item", total_item);
        long result = db.insert("transaction_item", null, contentValues);
        if (result == -1) {
            return false;
        } else return true;
    }

    public Cursor getTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from transactions", null);
        return cursor;
    }

    public Cursor getTransaction(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from transactions where id=" + String.valueOf(id), null);
        return cursor;
    }

    public Cursor getTransactionItems(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from transaction_item where transaction_id=" + String.valueOf(id), null);
        return cursor;
    }

}

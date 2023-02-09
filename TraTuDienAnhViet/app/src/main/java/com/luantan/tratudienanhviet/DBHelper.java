package com.luantan.tratudienanhviet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luantan.tratudienanhviet.Models.Irregular;
import com.luantan.tratudienanhviet.Models.Utils;
import com.luantan.tratudienanhviet.Models.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper {
    Context context;
    Utils utils;
    String DB_NAME = "DB_TUDIENAV.db";
    private String DB_PATH = "/databases/";
    SQLiteDatabase database = null;

    public DBHelper(Context context) {
        this.context = context;
        utils = new Utils(context);
    }


    public void SaoChepDuLieuTuAssets() {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            copyDatabase();
        } else {
//            dbFile.delete();
//            copyDatabase();
        }
    }

    private SQLiteDatabase openDB() {
        return context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    private void closeDB(SQLiteDatabase db) {
        db.close();
    }

    private void copyDatabase() {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outFileName = context.getApplicationInfo().dataDir + DB_PATH + DB_NAME;
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH);
            if (!f.exists()) {
                f.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, len);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "Loiiiii");
        }
    }

    public ArrayList<Word> getALLWordHistory() {
        database = openDB();
        ArrayList<Word> arr = new ArrayList<>();
        String sql = "select * from TUVUNG where LICHSU = 1";
        Cursor csr = database.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int maTu = csr.getInt(0);
                    String tu = csr.getString(1);
                    String nghiaTu = csr.getString(2);
                    int luuY = csr.getInt(3);
                    int lichSu = csr.getInt(4);
                    arr.add(new Word(maTu, tu, nghiaTu, luuY, lichSu));
                } while (csr.moveToNext());
            }
        }
        closeDB(database);
        return arr;
    }

    public ArrayList<Word> getALLWordNote() {
        database = openDB();
        ArrayList<Word> arr = new ArrayList<>();
        String sql = "select * from TUVUNG where LUUY = 1";
        Cursor csr = database.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int maTu = csr.getInt(0);
                    String tu = csr.getString(1);
                    String nghiaTu = csr.getString(2);
                    int luuY = csr.getInt(3);
                    int lichSu = csr.getInt(4);
                    arr.add(new Word(maTu, tu, nghiaTu, luuY, lichSu));
                } while (csr.moveToNext());
            }
        }
        closeDB(database);
        return arr;
    }

    public ArrayList<Word> getALLWord() {
        database = openDB();
        ArrayList<Word> arr = new ArrayList<>();
        String sql = "select * from TUVUNG";
        Cursor csr = database.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int maTu = csr.getInt(0);
                    String tu = csr.getString(1);
                    String nghiaTu = csr.getString(2);
                    int luuY = csr.getInt(3);
                    int lichSu = csr.getInt(4);
                    arr.add(new Word(maTu, tu, nghiaTu, luuY, lichSu));
                } while (csr.moveToNext());
            }
        }
        closeDB(database);
        return arr;
    }

    public ArrayList<Irregular> getIrregulars() {
        database = openDB();
        ArrayList<Irregular> arr = new ArrayList<>();
        String sql = "select * from DONGTUBATQUYTAC";
        Cursor csr = database.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    int maTu = csr.getInt(0);
                    String verb1 = csr.getString(1);
                    String verb2 = csr.getString(2);
                    String verb3 = csr.getString(3);
                    String means = csr.getString(4);
                    arr.add(new Irregular(maTu, verb1, verb2, verb3, means));
                } while (csr.moveToNext());
            }
        }
        closeDB(database);
        return arr;
    }

//    public boolean check_primary_key_history(int ma) {
//        boolean flag = true; // insert ok
//        database = openDB();
//        String sql = "select * from  LICHSUTRATU where MATU = " + ma;
//        Cursor csr = database.rawQuery(sql, null);
//        if (csr != null) {
//            if (csr.moveToFirst()) {
//                flag = false;
//            }
//        }
//        closeDB(database);
//        return flag;
//    }

    //    public boolean InsertWord_History(Word word) {
//        try {
//            database = openDB();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("MATU", word.getMaTu());
//            contentValues.put("TU", word.getTenTu());
//            contentValues.put("NGHIA", word.getNghia());
//            long r = database.insert("LICHSUTRATU", null, contentValues);
//            closeDB(database);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
    public void UpdateHistory_Click(int ma) {
        database = openDB();
        String sql = "update TUVUNG set LICHSU = 1 WHERE MATU =" + ma;
        database.execSQL(sql);
        closeDB(database);
    }

    public void ClearWordHistory(int maTu) {
        database = openDB();
        String sql = "update TUVUNG set LICHSU = 0 WHERE MATU = " + maTu;
        database.execSQL(sql);
        closeDB(database);
    }

    public void ClearAllHistory() {
        database = openDB();
        String sql = "update TUVUNG set LICHSU = 0 WHERE LICHSU = 1";
        database.execSQL(sql);
        closeDB(database);
    }

    // them vao ds luu y
    public void UpdateNote_Click(int ma) {
        database = openDB();
        String sql = "update TUVUNG set LUUY = 1 WHERE MATU =" + ma;
        database.execSQL(sql);
        closeDB(database);
    }

    // xoa khoi danh sach luu y
    public void ClearWordNote(int maTu) {
        database = openDB();
        String sql = "update TUVUNG set LUUY = 0 WHERE MATU = " + maTu;
        database.execSQL(sql);
        closeDB(database);
    }

    public void ClearAllLuuY() {
        database = openDB();
        String sql = "update TUVUNG set LUUY = 0 WHERE LUUY = 1";
        database.execSQL(sql);
        closeDB(database);
    }
    public Word getWord(int ma) {
        database = openDB();
        Word word = null;
        String sql = "select * from TUVUNG where MA = ma";
        Cursor csr = database.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                int maTu = csr.getInt(0);
                String tu = csr.getString(1);
                String nghiaTu = csr.getString(2);
                int luuY = csr.getInt(3);
                int lichSu = csr.getInt(4);
                word = new Word(maTu, tu, nghiaTu, luuY, lichSu);
            }
        }
        closeDB(database);
        return word;
    }

    public Boolean timTuLuuYTheoMa(int ma) {
        boolean check = false;
        SQLiteDatabase db = openDB();
        ArrayList<Word> arr = new ArrayList<>();
        String sql = "select * from TUVUNG where LUUY = 1 AND MATU =" + ma;
        Cursor csr = db.rawQuery(sql, null);
        if (csr.getCount() > 0) {
            check = true;
        }
        closeDB(db);
//        if (check)
//        {
//            Toast.makeText(context, "Tim thay", Toast.LENGTH_SHORT).show();
//        }
//        Toast.makeText(context, "" + check, Toast.LENGTH_SHORT).show();
        return check;
    }

//    public void DeleteWord_History(int ma) {
//        database = openDB();
//        // database.execSQL("delete from LICHSUTRATU where MATU = 3");
//        database.delete("LICHSUTRATU", "MATU=?", new String[]{String.valueOf(ma)});
//        closeDB(database);
//    }

    public Word timTu(String newKey) {
        database = openDB();
        Word word = null;
        String sql = "select * from TUVUNG where TU = '" + newKey + "'";
        Cursor csr = database.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                    int maTu = csr.getInt(0);
                    String tu = csr.getString(1);
                    String nghiaTu = csr.getString(2);
                    int luuY = csr.getInt(3);
                    int lichSu = csr.getInt(4);
                    word = new Word(maTu, tu, nghiaTu, luuY, lichSu);
            }
        }
        closeDB(database);
        return word;
    }

}

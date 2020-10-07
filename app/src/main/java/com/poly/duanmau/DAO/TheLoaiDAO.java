package com.poly.duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {
    DatabaseBookManager databaseBookManager;
    public static final String TABLE_THELOAI = "theloai";
    public static final String COLUMN_MA_TL = "matheloai";
    public static final String COLUMN_TEN_TL = "tentheloai";
    public static final String COLUMN_MO_TA = "mota";
    public static final String COLUMN_VI_TRI = "vitri";
    public static final String SQL_THE_LOAI = "CREATE TABLE " + TABLE_THELOAI +
            " (" + COLUMN_MA_TL + " text primary key, " + COLUMN_TEN_TL + " text, "
            + COLUMN_MO_TA + " text," + COLUMN_VI_TRI + " integer);";

    public TheLoaiDAO(DatabaseBookManager databaseBookManager) {
        this.databaseBookManager = databaseBookManager;
    }

    public long insertTheLoai(TheLoai theLoai) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_TL, theLoai.getMaTheLoai());
        contentValues.put(COLUMN_TEN_TL, theLoai.getTenTheLoai());
        contentValues.put(COLUMN_MO_TA, theLoai.getMoTa());
        contentValues.put(COLUMN_VI_TRI, theLoai.getViTri());
        return sqLiteDatabase.insert(TABLE_THELOAI, null, contentValues);
    }

    public long updateTheLoai(TheLoai theLoai) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_TL, theLoai.getMaTheLoai());
        contentValues.put(COLUMN_TEN_TL, theLoai.getTenTheLoai());
        contentValues.put(COLUMN_MO_TA, theLoai.getMoTa());
        contentValues.put(COLUMN_VI_TRI, theLoai.getViTri());
        return sqLiteDatabase.update(TABLE_THELOAI, contentValues, COLUMN_MA_TL + "=?", new String[]{theLoai.getMaTheLoai()});
    }

    public long deleteTheLoai(String matheloai) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_THELOAI, COLUMN_MA_TL + "=?", new String[]{matheloai});
    }

    public List<TheLoai> getAllTheLoai() {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getReadableDatabase();
        List<TheLoai> theLoaiList = new ArrayList<>();
        String Select = "SELECT * FROM " + TABLE_THELOAI;
        Cursor cursor = sqLiteDatabase.rawQuery(Select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TheLoai theLoai = new TheLoai();
            theLoai.setMaTheLoai(cursor.getString(cursor.getColumnIndex(COLUMN_MA_TL)));
            theLoai.setTenTheLoai(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TL)));
            theLoai.setMoTa(cursor.getString(cursor.getColumnIndex(COLUMN_MO_TA)));
            theLoai.setViTri(cursor.getInt(cursor.getColumnIndex(COLUMN_VI_TRI)));
            theLoaiList.add(theLoai);
            cursor.moveToNext();
        }
        cursor.close();
        return theLoaiList;
    }
}

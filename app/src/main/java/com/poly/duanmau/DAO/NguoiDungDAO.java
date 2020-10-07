package com.poly.duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    DatabaseBookManager databaseBookManager;
    public static final String TABLE_NGUOIDUNG = "nguoidung";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_PASS_WORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_HO_TEN = "hoten";
    public static final String SQL_NGUOI_DUNG = "CREATE TABLE " + TABLE_NGUOIDUNG +
            " (" + COLUMN_USER_NAME + " text PRIMARY KEY, " + COLUMN_PASS_WORD + " text, " +
            COLUMN_PHONE + " text, " + COLUMN_HO_TEN + " text);";

    public NguoiDungDAO(DatabaseBookManager databaseBookManager) {
        this.databaseBookManager = databaseBookManager;
    }

    public long insertNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, nguoiDung.getUserName());
        contentValues.put(COLUMN_PASS_WORD, nguoiDung.getPassWord());
        contentValues.put(COLUMN_PHONE, nguoiDung.getPhone());
        contentValues.put(COLUMN_HO_TEN, nguoiDung.getHoTen());
        return sqLiteDatabase.insert(TABLE_NGUOIDUNG, null, contentValues);
    }

    public long updateNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, nguoiDung.getUserName());
        contentValues.put(COLUMN_PASS_WORD, nguoiDung.getPassWord());
        contentValues.put(COLUMN_PHONE, nguoiDung.getPhone());
        contentValues.put(COLUMN_HO_TEN, nguoiDung.getHoTen());
        return sqLiteDatabase.update(TABLE_NGUOIDUNG, contentValues, COLUMN_USER_NAME + "=?", new String[]{nguoiDung.getUserName()});
    }

    public long deleteNguoiDung(String username) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NGUOIDUNG, COLUMN_USER_NAME + "=?", new String[]{username});
    }

    public List<NguoiDung> getAllNguoiDung() {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getReadableDatabase();
        List<NguoiDung> nguoiDungList = new ArrayList<>();
        String Select = "SELECT * FROM " + TABLE_NGUOIDUNG;
        Cursor cursor = sqLiteDatabase.rawQuery(Select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            nguoiDung.setPassWord(cursor.getString(cursor.getColumnIndex(COLUMN_PASS_WORD)));
            nguoiDung.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            nguoiDung.setHoTen(cursor.getString(cursor.getColumnIndex(COLUMN_HO_TEN)));
            nguoiDungList.add(nguoiDung);
            cursor.moveToNext();
        }
        cursor.close();
        return nguoiDungList;
    }

    public int changePasswordNguoiDung(NguoiDung nd) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", nd.getUserName());
        values.put("password", nd.getPassWord());
        int result = sqLiteDatabase.update(TABLE_NGUOIDUNG, values, COLUMN_USER_NAME + "=?", new String[]{nd.getUserName()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }
   /* public void NguoiDung(String username ){
        String compare = "SELECT * from user where= '"+ username + "'";
        Log.e("Cau lenh",compare);
        Cursor cursor =
    }*/
}

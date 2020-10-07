package com.poly.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.poly.duanmau.DAO.HoaDonChiTietDAO;
import com.poly.duanmau.DAO.HoaDonDAO;
import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.DAO.SachDAO;
import com.poly.duanmau.DAO.TheLoaiDAO;

public class DatabaseBookManager extends SQLiteOpenHelper {
    public DatabaseBookManager(Context context) {
        super(context, "dbBookManager", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NguoiDungDAO.SQL_NGUOI_DUNG);
        db.execSQL(TheLoaiDAO.SQL_THE_LOAI);
        db.execSQL(SachDAO.SQL_SACH);
        db.execSQL(HoaDonDAO.SQL_HOA_DON);
        db.execSQL(HoaDonChiTietDAO.SQL_HDCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // xóa bảng cũ và tạo bảng mới
        db.execSQL("Drop table if exists " + NguoiDungDAO.TABLE_NGUOIDUNG);
        db.execSQL("Drop table if exists " + TheLoaiDAO.TABLE_THELOAI);
        db.execSQL("Drop table if exists " + SachDAO.TABLE_SACH);
        db.execSQL("Drop table if exists " + HoaDonDAO.TABLE_HOADON);
        db.execSQL("Drop table if exists " + HoaDonChiTietDAO.TABLE_HODONCHITIET);
    }
}

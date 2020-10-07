package com.poly.duanmau.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.Model.HoaDonChiTiet;
import com.poly.duanmau.Model.Sach;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO {
    DatabaseBookManager databaseBookManager;
    public static final String TABLE_HODONCHITIET = "hoadonchitiet";
    public static final String COLUMN_MA_HDCT = "mahoadonchitiet";
    public static final String COLUMN_MA_HD = "mahoadon";
    public static final String COLUMN_MA_SACH = "masach";
    public static final String COLUMN_SO_LUONG_MUA = "soluongmua";
    public static final String SQL_HDCT = "CREATE TABLE " + TABLE_HODONCHITIET +
            " ( " + COLUMN_MA_HDCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MA_HD +
            " text NOT NULL, " + COLUMN_MA_SACH + " text NOT NULL, " + COLUMN_SO_LUONG_MUA + " INTEGER);";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonChiTietDAO(DatabaseBookManager databaseBookManager) {
        this.databaseBookManager = databaseBookManager;
    }

    public long insertHDCT(HoaDonChiTiet hoaDonChiTiet) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_HD, hoaDonChiTiet.getHoaDon().getMaHoaDon());
        contentValues.put(COLUMN_MA_SACH, hoaDonChiTiet.getSach().getMaSach());
        contentValues.put(COLUMN_SO_LUONG_MUA, hoaDonChiTiet.getSoLuongMua());
        return sqLiteDatabase.insert(TABLE_HODONCHITIET, null, contentValues);
    }

    public long updateHDCT(HoaDonChiTiet hoaDonChiTiet) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_HD, hoaDonChiTiet.getHoaDon().getMaHoaDon());
        contentValues.put(COLUMN_MA_SACH, hoaDonChiTiet.getSach().getMaSach());
        contentValues.put(COLUMN_SO_LUONG_MUA, hoaDonChiTiet.getSoLuongMua());
        return sqLiteDatabase.update(TABLE_HODONCHITIET, contentValues, COLUMN_MA_HDCT + "=?", new String[]{String.valueOf(hoaDonChiTiet.getMaHDCT())});
    }

    public long deleteHDCT(String maHDCT) {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_HODONCHITIET, COLUMN_MA_HDCT + "=?", new String[]{maHDCT});
    }

    public List<HoaDonChiTiet> getAllHoaDonChiTiet() {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getReadableDatabase();
        List<HoaDonChiTiet> dsHoaDonChiTiet = new ArrayList<>();
        String sSQL = "SELECT mahoadonchitiet, hoadon.mahoadon,hoadon.ngaymua, "
                + "sach.masach, sach.matheloai, sach.tensach, sach.tacgia, sach.nxb, sach.giaban, "
                + "sach.soluong, hoadonchitiet.soluongmua FROM hoadonchitiet INNER JOIN hoadon " +
                "on hoadonchitiet.mahoadon = hoadon.mahoadon INNER JOIN sach on sach.masach = hoadonchitiet.masach";
        Cursor cursor = sqLiteDatabase.rawQuery(sSQL, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setMaHDCT(cursor.getInt(cursor.getColumnIndex(COLUMN_MA_HDCT)));
            try {
                hoaDonChiTiet.setHoaDon(new HoaDon(cursor.getString(cursor.getColumnIndex(COLUMN_MA_HD)),
                        (Date) sdf.parse(cursor.getString(2))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hoaDonChiTiet.setSach(new Sach(cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7), cursor.getInt(9),
                    cursor.getDouble(8)));
            hoaDonChiTiet.setSoLuongMua(cursor.getInt(cursor.getColumnIndex(COLUMN_SO_LUONG_MUA)));
            dsHoaDonChiTiet.add(hoaDonChiTiet);
            cursor.moveToNext();
        }
        cursor.close();
        return dsHoaDonChiTiet;
    }

    public double getDoanhThuTheoNgay() {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(sach.giaban * hoadonchitiet.soluongmua) as 'tongtien' " +
                "FROM hoadon INNER JOIN hoadonchitiet on hoadon.mahoadon = hoadonchitiet.mahoadon " +
                "INNER JOIN sach on hoadonchitiet.masach = sach.masach where hoadon.ngaymua = date('now') " +
                "GROUP BY hoadonchitiet.masach)tmp";
        Cursor c = sqLiteDatabase.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoThang() {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(sach.giaban * hoadonchitiet.soluongmua) as" +
                " 'tongtien' " + "FROM hoadon INNER JOIN hoadonchitiet on hoadon.mahoadon " +
                "= hoadonchitiet.mahoadon " + "INNER JOIN sach on " +
                "hoadonchitiet.masach = sach.masach where strftime('%m',hoadon.ngaymua) " +
                "= strftime('%m','now') GROUP BY hoadonchitiet.masach)tmp";
        Cursor c = sqLiteDatabase.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoNam() {
        SQLiteDatabase sqLiteDatabase = databaseBookManager.getWritableDatabase();
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) from (SELECT SUM(sach.giaban * hoadonchitiet.soluongmua) as" +
                " 'tongtien' " + "FROM hoadon INNER JOIN hoadonchitiet on hoadon.mahoadon = hoadonchitiet.mahoadon "
                + "INNER JOIN sach on hoadonchitiet.masach = sach.masach where strftime('%Y',hoadon.ngaymua) = strftime('%Y','now') " +
                "GROUP BY hoadonchitiet.masach)tmp";
        Cursor c = sqLiteDatabase.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
}

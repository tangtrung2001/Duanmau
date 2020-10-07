package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.poly.duanmau.Adapter.AdapterHDCT;
import com.poly.duanmau.DAO.HoaDonChiTietDAO;
import com.poly.duanmau.DAO.HoaDonDAO;
import com.poly.duanmau.DAO.SachDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.Model.HoaDonChiTiet;
import com.poly.duanmau.Model.Sach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HDCTActivity extends AppCompatActivity {
    AdapterHDCT adapterHDCT;
    ListView lv_hdct;
    DatabaseBookManager databaseBookManager;
    HoaDonChiTiet hoaDonChiTiet = null;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    List<HoaDonChiTiet> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdct);
        setTitle("Hóa đơn chi tiết");
        lv_hdct = findViewById(R.id.lv_hdct);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("datagui");
        String maHDnhan = bundle.getString("maHDgui");
        Log.e("mã hóa đơn đã nhận: ", " " + maHDnhan);
        databaseBookManager = new DatabaseBookManager(HDCTActivity.this);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(databaseBookManager);
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietDAO.getAllHoaDonChiTiet();
        for (int i = 0; i < hoaDonChiTietList.size(); i++) {
            if (hoaDonChiTietList.get(i).getHoaDon().getMaHoaDon().equals(maHDnhan)) {
                hoaDonChiTiet = hoaDonChiTietList.get(i);
                myList.add(hoaDonChiTiet);
            }
        }
        Log.e("số phần tử tìm được ", "" + myList);
        adapterHDCT = new AdapterHDCT(HDCTActivity.this, myList);
        lv_hdct.setAdapter(adapterHDCT);
        registerForContextMenu(lv_hdct);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.sua_hdct, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // lấy postion phần tử sửa
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int pos = menuInfo.position;
        int id = item.getItemId();
        if (id == R.id.menu_sua_hdct) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HDCTActivity.this);
            View view = getLayoutInflater().inflate(R.layout.sua_hdct, null);
            builder.setView(view);
            builder.setTitle("Sửa hóa đơn chi tiết");
            final EditText sua_hdct_masach, sua_hdct_soluong;
            final TextView sua_hdct_ngaymua;
            Button sua_hdct_pickdate, btn_update_hdct;
            sua_hdct_ngaymua = view.findViewById(R.id.sua_hdct_ngaymua);
            sua_hdct_masach = view.findViewById(R.id.sua_hdct_masach);
            sua_hdct_soluong = view.findViewById(R.id.sua_hdct_soluong);
            sua_hdct_pickdate = view.findViewById(R.id.sua_hdct_pickdate);
            btn_update_hdct = view.findViewById(R.id.btn_update_hdct);
            sua_hdct_pickdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(HDCTActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            sua_hdct_ngaymua.setText(simpleDateFormat.format(calendar.getTime()));
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }

            });
            btn_update_hdct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HoaDonChiTiet hdct = myList.get(pos);
                    Sach sach = hdct.getSach();
                    // so sánh mã sách, trùng thì thay thế sách vào đây
                    // nếu không thay sẽ k cập nhật giá sách
                    SachDAO sachDAO = new SachDAO(databaseBookManager);
                    for (int i = 0; i < sachDAO.getAllSach().size(); i++) {
                        if (sua_hdct_masach.getText().toString().equals(sachDAO.getAllSach().get(i).getMaSach())) {
                            sach = sachDAO.getAllSach().get(i);
                        }
                    }
                    HoaDon hoaDon = hdct.getHoaDon();
                    Date date = null;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date = simpleDateFormat.parse(sua_hdct_ngaymua.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    hoaDon.setNgayMua(date);
                    HoaDonDAO hoaDonDAO = new HoaDonDAO(databaseBookManager);
                    hoaDonDAO.updateHoaDon(hoaDon);
                    hdct.setSach(sach);
                    hdct.setHoaDon(hoaDon);
                    hdct.setSoLuongMua(Integer.valueOf(sua_hdct_soluong.getText().toString()));
                    hoaDonChiTietDAO.updateHDCT(hdct);
                    myList.set(pos, hdct);
                    Log.e("pos", "" + myList.get(pos));
                    adapterHDCT = new AdapterHDCT(HDCTActivity.this, myList);
                    lv_hdct.setAdapter(adapterHDCT);
                    adapterHDCT.changeDataSet(myList);
                }
            });
            builder.create().show();
        }
        return super.onContextItemSelected(item);
    }
}

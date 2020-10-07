package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.poly.duanmau.Adapter.AdapterSach;
import com.poly.duanmau.DAO.SachDAO;
import com.poly.duanmau.DAO.TheLoaiDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.Model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class Them_Sach_Activity extends AppCompatActivity {
    EditText edt_maSach, edt_tenSach, edt_giaBan, edt_nxb, edt_soLuong, edt_tacGia;
    Button btn_them, btn_huy, btn_show;
    Spinner spinner_theloai;
    DatabaseBookManager databaseBookManager;
    SachDAO sachDAO;
    TheLoaiDAO theLoaiDAO;
    AdapterSach adapterSach;
    String matheloai = "";
    List<TheLoai> theLoaiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_sach);
        setTitle("Thêm sách");
        edt_tenSach = findViewById(R.id.edt_tenSach);
        edt_maSach = findViewById(R.id.edt_maSach);
        edt_tacGia = findViewById(R.id.edt_tacGia);
        edt_nxb = findViewById(R.id.edt_nxb);
        edt_soLuong = findViewById(R.id.edt_soLuong);
        edt_giaBan = findViewById(R.id.edt_soLuong);
        btn_them = findViewById(R.id.btn_them_sach);
        btn_huy = findViewById(R.id.btn_huy_them_sach);
        btn_show = findViewById(R.id.btn_show_book);
        spinner_theloai = findViewById(R.id.spTheLoai);
        databaseBookManager = new DatabaseBookManager(Them_Sach_Activity.this);
        sachDAO = new SachDAO(databaseBookManager);
        theLoaiDAO = new TheLoaiDAO(databaseBookManager);
        theLoaiList = theLoaiDAO.getAllTheLoai();
        adapterSach = new AdapterSach(Them_Sach_Activity.this, sachDAO.getAllSach());
        ArrayAdapter<TheLoai> adapterSP = new ArrayAdapter<TheLoai>(Them_Sach_Activity.this, android.R.layout.simple_spinner_item, theLoaiDAO.getAllTheLoai());
        adapterSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_theloai.setAdapter(adapterSP);
        spinner_theloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matheloai = theLoaiList.get(spinner_theloai.getSelectedItemPosition()).getMaTheLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sach sach = new Sach(edt_maSach.getText().toString(), matheloai, edt_tenSach.getText().toString(), edt_tacGia.getText().toString(),
                        edt_nxb.getText().toString(), Integer.valueOf(edt_soLuong.getText().toString()), Double.valueOf(edt_giaBan.getText().toString()));
                if (!edt_maSach.getText().toString().isEmpty() && !edt_tenSach.getText().toString().isEmpty() && !edt_tacGia.getText().toString().isEmpty() &&
                        !edt_nxb.getText().toString().isEmpty() && !edt_soLuong.getText().toString().isEmpty() && !edt_giaBan.getText().toString().isEmpty()) {
                    long value = sachDAO.insertSach(sach);
                    adapterSach.changeDataset(sachDAO.getAllSach());
                    if (value > 0) {
                        Toast.makeText(Them_Sach_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Them_Sach_Activity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Them_Sach_Activity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Them_Sach_Activity.this, BookActivity.class);
                startActivity(intent);
            }
        });
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Them_Sach_Activity.this, BookActivity.class);
                startActivity(intent);
            }
        });
    }
}

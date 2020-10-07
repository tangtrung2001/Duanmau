package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.poly.duanmau.Adapter.AdapterTheLoai;
import com.poly.duanmau.DAO.TheLoaiDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.TheLoai;

public class Them_Tloai_Activity extends AppCompatActivity {
    EditText edt_matheloai, edt_tentheloai, edt_vitri, edt_mota;
    Button btn_them, btn_huy, btn_show;
    DatabaseBookManager databaseBookManager;
    TheLoaiDAO theLoaiDAO;
    AdapterTheLoai adapterTheLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_the_loai);
        setTitle("Thêm thể loại");
        edt_matheloai = findViewById(R.id.edt_maTheLoai);
        edt_tentheloai = findViewById(R.id.edt_tenTheLoai);
        edt_mota = findViewById(R.id.edt_moTa);
        edt_vitri = findViewById(R.id.edt_viTri);
        btn_them = findViewById(R.id.btn_them_tl);
        btn_huy = findViewById(R.id.btn_huy_tl);
        btn_show = findViewById(R.id.btn_show_tl);
        databaseBookManager = new DatabaseBookManager(Them_Tloai_Activity.this);
        theLoaiDAO = new TheLoaiDAO(databaseBookManager);
        adapterTheLoai = new AdapterTheLoai(Them_Tloai_Activity.this, theLoaiDAO.getAllTheLoai());
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseBookManager = new DatabaseBookManager(Them_Tloai_Activity.this);
                theLoaiDAO = new TheLoaiDAO(databaseBookManager);
                TheLoai theLoai = new TheLoai(edt_matheloai.getText().toString(), edt_tentheloai.getText().toString(), edt_mota.getText().toString(), Integer.valueOf(edt_vitri.getText().toString()));
                if (!edt_matheloai.getText().toString().isEmpty() && !edt_tentheloai.getText().toString().isEmpty() && !edt_mota.getText().toString().isEmpty() && !edt_vitri.getText().toString().isEmpty()
                ) {
                    long value = theLoaiDAO.insertTheLoai(theLoai);
                    adapterTheLoai.changeDataset(theLoaiDAO.getAllTheLoai());
                    if (value > 0) {
                        Toast.makeText(Them_Tloai_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Them_Tloai_Activity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Them_Tloai_Activity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterTheLoai.changeDataset(theLoaiDAO.getAllTheLoai());
                Intent intent = new Intent(Them_Tloai_Activity.this, TheLoaiActivity.class);
                startActivity(intent);
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Them_Tloai_Activity.this, TheLoaiActivity.class);
                startActivity(intent);
            }
        });
    }
}

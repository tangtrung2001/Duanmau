package com.poly.duanmau;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.poly.duanmau.Adapter.AdapterNguoiDung;
import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;

public class Them_Nd_Activity extends AppCompatActivity {
    DatabaseBookManager databaseBookManager;
    NguoiDungDAO nguoiDungDAO;
    AdapterNguoiDung adapterNguoiDung;
    EditText edUser, edPassWord, edRePass, edPhone, edfullName;
    Button btn_them, btn_danh_sach, btn_huy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_nguoi_dung);
        setTitle("Thêm người dùng");
        edUser = findViewById(R.id.edUser);
        edPassWord = findViewById(R.id.edPassWord);
        edRePass = findViewById(R.id.edRePassWord);
        edPhone = findViewById(R.id.edPhone);
        edfullName = findViewById(R.id.edfullName);
        btn_them = findViewById(R.id.btn_themND);
        btn_danh_sach = findViewById(R.id.btn_ds_ND);
        btn_huy = findViewById(R.id.btn_huyND);
        databaseBookManager = new DatabaseBookManager(Them_Nd_Activity.this);
        nguoiDungDAO = new NguoiDungDAO(databaseBookManager);
        adapterNguoiDung = new AdapterNguoiDung(nguoiDungDAO.getAllNguoiDung(), Them_Nd_Activity.this);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseBookManager = new DatabaseBookManager(Them_Nd_Activity.this);
                nguoiDungDAO = new NguoiDungDAO(databaseBookManager);
                NguoiDung nguoiDung = new NguoiDung(edUser.getText().toString(), edPassWord.getText().toString(), edPhone.getText().toString(), edfullName.getText().toString());
                if (!edUser.getText().toString().isEmpty() && !edPassWord.getText().toString().isEmpty() && !edRePass.getText().toString().isEmpty()) {
                    if (edPassWord.getText().toString().equals(edRePass.getText().toString())) {
                        long result = nguoiDungDAO.insertNguoiDung(nguoiDung);
                        adapterNguoiDung.changeDataset(nguoiDungDAO.getAllNguoiDung());
                        if (result > 0) {
                            Toast.makeText(Them_Nd_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Them_Nd_Activity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Them_Nd_Activity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_danh_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterNguoiDung.changeDataset(nguoiDungDAO.getAllNguoiDung());
                Intent intent = new Intent(Them_Nd_Activity.this, NguoiDungActivity.class);
                startActivity(intent);
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Them_Nd_Activity.this, NguoiDungActivity.class);
                startActivity(intent);
            }
        });
    }
}

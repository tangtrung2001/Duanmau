package com.poly.duanmau;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.poly.duanmau.Adapter.AdapterNguoiDung;
import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;

import java.util.List;

public class NguoiDungActivity extends AppCompatActivity {
    DatabaseBookManager databaseBookManager;
    NguoiDungDAO nguoiDungDAO;
    ListView lvNguoiDung;
    AdapterNguoiDung adapterNguoiDung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_nguoi_dung);
        setTitle("Người dùng");
        lvNguoiDung = findViewById(R.id.lvNguoiDung);
        databaseBookManager = new DatabaseBookManager(NguoiDungActivity.this);
        nguoiDungDAO = new NguoiDungDAO(databaseBookManager);
        final List<NguoiDung> nguoiDungList = nguoiDungDAO.getAllNguoiDung();
        adapterNguoiDung = new AdapterNguoiDung(nguoiDungList, NguoiDungActivity.this);
        lvNguoiDung.setAdapter(adapterNguoiDung);
        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NguoiDung nguoiDung = nguoiDungList.get(position);
                Intent intent = new Intent(NguoiDungActivity.this, NguoiDungDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("duser", nguoiDung.getUserName());
                bundle.putString("dphone", nguoiDung.getPhone());
                bundle.putString("dfullname", nguoiDung.getHoTen());
                bundle.putString("dpass", nguoiDung.getPassWord());
                intent.putExtra("datauser", bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nguoi_dung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.themND:
                Intent intent = new Intent(NguoiDungActivity.this, Them_Nd_Activity.class);
                startActivity(intent);
                break;
            case R.id.doi_mk_ND:
                Intent intent1 = new Intent(NguoiDungActivity.this, ChangePassActivity.class);
                startActivity(intent1);
                break;
            case R.id.dang_xuat_ND:
                Intent intent2 = new Intent(NguoiDungActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

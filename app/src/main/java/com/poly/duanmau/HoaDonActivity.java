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

import com.poly.duanmau.Adapter.AdapterHoaDon;
import com.poly.duanmau.DAO.HoaDonDAO;
import com.poly.duanmau.Database.DatabaseBookManager;

public class HoaDonActivity extends AppCompatActivity {
    ListView lv_hoadon;
    DatabaseBookManager databaseBookManager;
    HoaDonDAO hoaDonDAO;
    AdapterHoaDon adapterHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_hoa_don);
        setTitle("Hóa đơn");
        lv_hoadon = findViewById(R.id.lv_hoaDon);
        databaseBookManager = new DatabaseBookManager(HoaDonActivity.this);
        hoaDonDAO = new HoaDonDAO(databaseBookManager);
        adapterHoaDon = new AdapterHoaDon(HoaDonActivity.this, hoaDonDAO.getAllHoaDon());
        lv_hoadon.setAdapter(adapterHoaDon);
        lv_hoadon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HoaDonActivity.this, HDCTActivity.class);
                Bundle bundle = new Bundle();
                String maHDgui = hoaDonDAO.getAllHoaDon().get(position).getMaHoaDon();
                bundle.putString("maHDgui", maHDgui);
                intent.putExtra("datagui", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hoa_don, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.them_hoa_don) {
            Intent intent = new Intent(HoaDonActivity.this, Them_Hoa_Don_Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

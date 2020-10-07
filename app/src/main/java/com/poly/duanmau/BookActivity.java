package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.poly.duanmau.Adapter.AdapterSach;
import com.poly.duanmau.DAO.SachDAO;
import com.poly.duanmau.Database.DatabaseBookManager;



public class BookActivity extends AppCompatActivity {

    DatabaseBookManager databaseBookManager;
    SachDAO sachDAO;
    AdapterSach adapterSach;
    ListView lv_sach;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.danh_sach_sach);
        setTitle("Sách");
        lv_sach = findViewById(R.id.lv_sach);
        databaseBookManager = new DatabaseBookManager(BookActivity.this);
        sachDAO = new SachDAO(databaseBookManager);
        adapterSach = new AdapterSach(BookActivity.this, sachDAO.getAllSach());
        lv_sach.setAdapter(adapterSach);
        lv_sach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookActivity.this, SachDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("masach", sachDAO.getAllSach().get(position).getMaSach());
                bundle.putString("matlsach", sachDAO.getAllSach().get(position).getMaTheLoai());
                bundle.putString("tensach", sachDAO.getAllSach().get(position).getTenSach());
                bundle.putString("tacgia", sachDAO.getAllSach().get(position).getTacGia());
                bundle.putString("nxb", sachDAO.getAllSach().get(position).getNXB());
                bundle.putInt("soluong", sachDAO.getAllSach().get(position).getSoLuong());
                bundle.putDouble("giaban", sachDAO.getAllSach().get(position).getGiaBan());
                intent.putExtra("sach", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sach, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.them_sach) {
            Intent intent = new Intent(BookActivity.this, Them_Sach_Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

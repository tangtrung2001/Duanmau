package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.poly.duanmau.Adapter.AdapterTheLoai;
import com.poly.duanmau.DAO.TheLoaiDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.TheLoai;

public class TheLoaiActivity extends AppCompatActivity {
    DatabaseBookManager databaseBookManager;
    TheLoaiDAO theLoaiDAO;
    ListView lv_theloai;
    AdapterTheLoai adapterTheLoai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_the_loai);
        setTitle("Thể loại");
        lv_theloai = findViewById(R.id.lvTheLoai);
        databaseBookManager = new DatabaseBookManager(TheLoaiActivity.this);
        theLoaiDAO = new TheLoaiDAO(databaseBookManager);
        adapterTheLoai = new AdapterTheLoai(TheLoaiActivity.this, theLoaiDAO.getAllTheLoai());
        lv_theloai.setAdapter(adapterTheLoai);
        lv_theloai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TheLoaiActivity.this, TheLoaiDetail.class);
                Bundle bundle = new Bundle();
                String maTL = theLoaiDAO.getAllTheLoai().get(position).getMaTheLoai();
                String tenTL = theLoaiDAO.getAllTheLoai().get(position).getTenTheLoai();
                String vitri = String.valueOf(theLoaiDAO.getAllTheLoai().get(position).getViTri());
                String mota = theLoaiDAO.getAllTheLoai().get(position).getMoTa();
                bundle.putString("matl", maTL);
                bundle.putString("tentl", tenTL);
                bundle.putString("vitritl", vitri);
                bundle.putString("mota", mota);
                intent.putExtra("tl", bundle);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_the_loai, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.them_the_loai) {
            Intent intent = new Intent(TheLoaiActivity.this, Them_Tloai_Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

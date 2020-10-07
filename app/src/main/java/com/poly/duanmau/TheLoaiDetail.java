package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.duanmau.DAO.TheLoaiDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.TheLoai;

public class TheLoaiDetail extends AppCompatActivity {
    TextView tentl_detail, matl_detail, vitritl_detail, motatl_detail;
    EditText update_tenTL, update_vitriTL, update_motaTL;
    Button btn_update;
    ImageView icon_updateTL;
    String matl;
    LinearLayout layout_updateTL;
    DatabaseBookManager databaseBookManager;
    TheLoaiDAO theLoaiDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai_detail);
        tentl_detail = findViewById(R.id.ten_tl_detail);
        matl_detail = findViewById(R.id.ma_tl_detail);
        vitritl_detail = findViewById(R.id.vitri_tl_detail);
        motatl_detail = findViewById(R.id.mota_tl_detail);
        update_tenTL = findViewById(R.id.update_tenTL);
        update_motaTL = findViewById(R.id.update_motaTL);
        update_vitriTL = findViewById(R.id.update_vitriTL);
        btn_update = findViewById(R.id.update_TL);
        icon_updateTL = findViewById(R.id.icon_updateTL);
        layout_updateTL = findViewById(R.id.layout_updateTL);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("tl");
        String tentl, vitri, mota;
        matl = bundle.getString("matl");
        tentl = bundle.getString("tentl");
        vitri = bundle.getString("vitritl");
        mota = bundle.getString("mota");
        matl_detail.setText("Mã thể loại: " + matl);
        tentl_detail.setText("Tên thể loại: " + tentl);
        vitritl_detail.setText("Vị trí: " + vitri);
        motatl_detail.setText("Mô tả: " + mota);
        icon_updateTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_updateTL.setVisibility(v.VISIBLE);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseBookManager = new DatabaseBookManager(TheLoaiDetail.this);
                theLoaiDAO = new TheLoaiDAO(databaseBookManager);
                String update_ten, update_vitri, update_mota;
                update_ten = update_tenTL.getText().toString();
                update_mota = update_motaTL.getText().toString();
                update_vitri = update_vitriTL.getText().toString();
                tentl_detail.setText("Tên thể loại: " + update_ten);
                vitritl_detail.setText("Vị trí: " + update_vitri);
                motatl_detail.setText("Mô tả: " + update_mota);
                layout_updateTL.setVisibility(v.INVISIBLE);
                TheLoai theLoai = new TheLoai(matl, update_ten, update_mota, Integer.valueOf(update_vitri));
                long result = theLoaiDAO.updateTheLoai(theLoai);
                if (result > 0) {
                    Toast.makeText(TheLoaiDetail.this, "Update thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TheLoaiDetail.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

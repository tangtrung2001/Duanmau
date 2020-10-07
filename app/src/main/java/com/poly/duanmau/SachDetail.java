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

import com.poly.duanmau.DAO.SachDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.Sach;

public class SachDetail extends AppCompatActivity {
    DatabaseBookManager databaseBookManager;
    SachDAO sachDAO;
    TextView maSach_detail, maTl_sachDetail, tenSach_detail, tacGia_sachDetail, nxb_sachDetail, soluong_sachDetail, giaban_sachDetail;
    ImageView icon_updateSach;
    LinearLayout layout_updateSach;
    EditText update_matl_sach, update_tenSach, update_tacgiaSach, update_nxbSach, update_soluongSach, update_giabanSach;
    Button update_Sach;
    String masach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_detail);
        setTitle("Update sách");
        maSach_detail = findViewById(R.id.maSach_detail);
        maTl_sachDetail = findViewById(R.id.maTl_sachDetail);
        tenSach_detail = findViewById(R.id.tenSach_detail);
        tacGia_sachDetail = findViewById(R.id.tacGia_sachDetail);
        nxb_sachDetail = findViewById(R.id.nxb_sachDetail);
        soluong_sachDetail = findViewById(R.id.soluong_sachDetail);
        giaban_sachDetail = findViewById(R.id.giaban_sachDetail);

        icon_updateSach = findViewById(R.id.icon_updateSach);
        layout_updateSach = findViewById(R.id.layout_updateSach);

        update_matl_sach = findViewById(R.id.update_matl_sach);
        update_tenSach = findViewById(R.id.update_tenSach);
        update_tacgiaSach = findViewById(R.id.update_tacgiaSach);
        update_nxbSach = findViewById(R.id.update_nxbSach);
        update_soluongSach = findViewById(R.id.update_soluongSach);
        update_giabanSach = findViewById(R.id.update_giabanSach);
        update_Sach = findViewById(R.id.update_Sach);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("sach");
        String matheloai, tensach, tacgia, nxb;
        Integer soluong;
        Double giaban;
        masach = bundle.getString("masach");
        matheloai = bundle.getString("matlsach");
        tensach = bundle.getString("tensach");
        tacgia = bundle.getString("tacgia");
        nxb = bundle.getString("nxb");
        soluong = bundle.getInt("soluong");
        giaban = bundle.getDouble("giaban");
        maSach_detail.setText("Mã sách: " + masach);
        maTl_sachDetail.setText("Mã thể loại: " + matheloai);
        tenSach_detail.setText("Tên sách: " + tensach);
        tacGia_sachDetail.setText("Tác giả: " + tacgia);
        nxb_sachDetail.setText("NXB: " + nxb);
        soluong_sachDetail.setText("Số lượng: " + soluong);
        giaban_sachDetail.setText("Giá bán: " + giaban);
        icon_updateSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_updateSach.setVisibility(v.VISIBLE);
            }
        });
        update_Sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseBookManager = new DatabaseBookManager(SachDetail.this);
                sachDAO = new SachDAO(databaseBookManager);
                String update_matl, update_tensach, update_tacgia, update_nxb, update_soluong, update_giaban;
                update_matl = update_matl_sach.getText().toString();
                update_tensach = update_tenSach.getText().toString();
                update_tacgia = update_tacgiaSach.getText().toString();
                update_nxb = update_nxbSach.getText().toString();
                update_soluong = update_soluongSach.getText().toString();
                update_giaban = update_giabanSach.getText().toString();
                Sach sach = new Sach(masach, update_matl, update_tensach, update_tacgia, update_nxb, Integer.valueOf(update_soluong), Double.valueOf(update_giaban));
                sachDAO.updateSach(sach);
                maTl_sachDetail.setText("Mã thể loại: " + update_matl);
                tenSach_detail.setText("Tên sách: " + update_tensach);
                tacGia_sachDetail.setText("Tác giả: " + update_tacgia);
                nxb_sachDetail.setText("NXB: " + update_nxb);
                soluong_sachDetail.setText("Số lượng: " + update_soluong);
                giaban_sachDetail.setText("Giá bán: " + update_giaban);
                layout_updateSach.setVisibility(v.INVISIBLE);
            }
        });
    }
}

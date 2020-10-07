package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;

public class NguoiDungDetail extends AppCompatActivity {
    TextView detail_user, detail_hoten, detail_phone;
    ImageView icon_update;
    LinearLayout my_layout;
    EditText update_fullName, update_phone;
    Button btn_update;
    DatabaseBookManager databaseBookManager;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung_detail);
        setTitle("Chi tiết người dùng");
        detail_user = findViewById(R.id.detail_user);
        detail_hoten = findViewById(R.id.detail_hoten);
        detail_phone = findViewById(R.id.detail_phone);
        icon_update = findViewById(R.id.icon_updateND);
        my_layout = findViewById(R.id.layout_updateND);
        update_fullName = findViewById(R.id.update_fullNameND);
        update_phone = findViewById(R.id.update_phoneND);
        btn_update = findViewById(R.id.update_ND);
        icon_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_layout.setVisibility(v.VISIBLE);
            }
        });

        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("datauser");
        detail_user.setText("User name: " + bundle.getString("duser"));
        detail_hoten.setText("Họ tên: " + bundle.getString("dfullname"));
        detail_phone.setText("Số điện thoại: " + bundle.getString("dphone"));
        final String pass = bundle.getString("dpass");
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_update = update_fullName.getText().toString();
                String phone_update = update_phone.getText().toString();
                NguoiDung nguoiDung = new NguoiDung(bundle.getString("duser"), pass, phone_update, name_update);
                databaseBookManager = new DatabaseBookManager(NguoiDungDetail.this);
                nguoiDungDAO = new NguoiDungDAO(databaseBookManager);
                nguoiDungDAO.updateNguoiDung(nguoiDung);
                detail_hoten.setText("Họ tên: " + name_update);
                detail_phone.setText("Số điện thoại: " + phone_update);
                my_layout.setVisibility(v.INVISIBLE);
            }
        });
    }
}

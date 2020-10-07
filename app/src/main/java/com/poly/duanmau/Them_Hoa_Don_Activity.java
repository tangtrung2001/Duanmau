package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.duanmau.DAO.HoaDonDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.HoaDon;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Them_Hoa_Don_Activity extends AppCompatActivity {
    TextView tv_mahoadon, tv_ngaymua;
    Button btn_pickDate, btn_themHD;
    DatabaseBookManager databaseBookManager;
    HoaDonDAO hoaDonDAO;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_hoa_don);
        setTitle("Thêm hóa đơn");
        tv_mahoadon = findViewById(R.id.edt_maHoaDon);
        tv_ngaymua = findViewById(R.id.tv_ngayMuaHD);
        btn_pickDate = findViewById(R.id.btn_picDate);
        btn_themHD = findViewById(R.id.btn_themHD);
        btn_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Them_Hoa_Don_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        tv_ngaymua.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btn_themHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = null;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = simpleDateFormat.parse(tv_ngaymua.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(tv_mahoadon.getText().toString());
                hoaDon.setNgayMua(date);
                databaseBookManager = new DatabaseBookManager(Them_Hoa_Don_Activity.this);
                hoaDonDAO = new HoaDonDAO(databaseBookManager);
                long value = hoaDonDAO.insertHoaDon(hoaDon);
                if (value > 0) {
                    Toast.makeText(Them_Hoa_Don_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Them_Hoa_Don_Activity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(Them_Hoa_Don_Activity.this, ThemHDCTActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maHD", hoaDon.getMaHoaDon());
                intent.putExtra("dataHD", bundle);
                startActivity(intent);
            }
        });
    }
}

package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextTheLoai(View view) {
        Intent intent = new Intent(MainActivity.this, TheLoaiActivity.class);
        startActivity(intent);
    }

    public void nextSach(View view) {
        Intent intent = new Intent(MainActivity.this, BookActivity.class);
        startActivity(intent);
    }

    public void nextTopBook(View view) {
        Intent intent = new Intent(MainActivity.this, TopBookActivity.class);
        startActivity(intent);
    }

    public void nextThongKe(View view) {
        Intent intent = new Intent(MainActivity.this, ThongKeActivity.class);
        startActivity(intent);
    }

    public void nextHoaDon(View view) {
        Intent intent = new Intent(MainActivity.this, HoaDonActivity.class);
        startActivity(intent);
    }

    public void nextNguoiDung(View view) {
        Intent intent = new Intent(MainActivity.this, NguoiDungActivity.class);
        startActivity(intent);
    }
}

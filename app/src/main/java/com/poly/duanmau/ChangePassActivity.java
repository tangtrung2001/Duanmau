package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;

public class ChangePassActivity extends AppCompatActivity {
    EditText edtChangeUser, edtChangePass, edtChangeRePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        setTitle("Đổi mật khẩu");
        edtChangeUser = findViewById(R.id.edt_changeUser);
        edtChangePass = findViewById(R.id.edt_changePass);
        edtChangeRePass = findViewById(R.id.edt_changeRePass);
    }

    public int check() {
        int check = 0;
        if (edtChangeUser.getText().toString().isEmpty() || edtChangePass.getText().toString().isEmpty() ||
                edtChangeRePass.getText().toString().isEmpty()) {
            check = 1;
            Toast.makeText(this, "Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        if (!edtChangePass.getText().toString().equals(edtChangeRePass.getText().toString())) {
            check = 1;
            Toast.makeText(this, "Mật khẩu nhập sai", Toast.LENGTH_SHORT).show();
        }
        return check;
    }

    public void changePass(View view) {
        DatabaseBookManager databaseBookManager = new DatabaseBookManager(ChangePassActivity.this);
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(databaseBookManager);
        String userName = edtChangeUser.getText().toString();
        Log.e("username", userName);
        NguoiDung nguoiDung = new NguoiDung(userName, edtChangePass.getText().toString(), "", "");
        if (check() == 0) {
            if (nguoiDungDAO.changePasswordNguoiDung(nguoiDung) > 0) {
                Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

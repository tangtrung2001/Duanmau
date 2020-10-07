package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName, edtPassWord;
    CheckBox remember;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap);
        setTitle("Đăng nhập");
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        remember = findViewById(R.id.remember);
        btnlogin = findViewById(R.id.btnLogin);
        loadLogin();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void rememberLogin(String user, String pass, boolean check) {
        SharedPreferences sharedPreferences = getSharedPreferences("userFile", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!check) {
            edit.clear();
        } else {
            edit.putString("username", user);
            edit.putString("pass", pass);
            edit.putBoolean("remember", check);
        }
        edit.commit();
    }

    public void login() {
        DatabaseBookManager databaseBookManager = new DatabaseBookManager(LoginActivity.this);
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(databaseBookManager);
        List<NguoiDung> nguoiDungList = nguoiDungDAO.getAllNguoiDung();
        String user = edtUserName.getText().toString();
        String pass = edtPassWord.getText().toString();
        boolean remem = remember.isChecked();
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            if (user.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
                rememberLogin(user, pass, remem);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {

                // test người dùng: user: tobi pass 6566
                for (int i = 0; i < nguoiDungList.size(); i++) {
                    if (user.equals(nguoiDungList.get(i).getUserName()) && pass.equals(nguoiDungList.get(i).getPassWord())) {
                        rememberLogin(user, pass, remem);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "Người dùng không đúng", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
    }

    public void loadLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("userFile", MODE_PRIVATE);
        if (sharedPreferences != null) {
            String user = sharedPreferences.getString("username", null);
            String pass = sharedPreferences.getString("pass", null);
            boolean remem = sharedPreferences.getBoolean("remember", false);
            edtUserName.setText(user);
            edtPassWord.setText(pass);
            remember.setChecked(remem);
        }
    }
}

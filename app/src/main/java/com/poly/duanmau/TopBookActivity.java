package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.poly.duanmau.Adapter.AdapterSach;
import com.poly.duanmau.DAO.SachDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class TopBookActivity extends AppCompatActivity {
    List<Sach> dsSach ;
    ListView lvBook;
    AdapterSach adapter;
    SachDAO sachDAO;
    EditText edThang;
    DatabaseBookManager databaseBookManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_sach_theo_thang);
        databaseBookManager = new DatabaseBookManager(this);
        sachDAO = new SachDAO(databaseBookManager);
        setTitle("Sách bán chạy");
        lvBook = (ListView) findViewById(R.id.lvTopBook);
        edThang = (EditText) findViewById(R.id.edt_thang);
    }

    public void timSachtop10(View view) {
        if (Integer.parseInt(edThang.getText().toString())>13 ||
                Integer.parseInt(edThang.getText().toString())<0){
            Toast.makeText(getApplicationContext(),"Không đúng định dạng tháng (1-12)",Toast.LENGTH_SHORT).show();
        }else {
//            sachDAO = new SachDAO(TopBookActivity.this);
            dsSach = sachDAO.getSachTop10(edThang.getText().toString());
            adapter = new AdapterSach(this, dsSach);
            lvBook.setAdapter(adapter);
        }
    }

}

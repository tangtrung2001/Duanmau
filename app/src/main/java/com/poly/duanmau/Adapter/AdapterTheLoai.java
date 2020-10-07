package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.TheLoaiDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.TheLoai;
import com.poly.duanmau.R;

import java.util.List;

public class AdapterTheLoai extends BaseAdapter {
    Context context;
    List<TheLoai> theLoaiList;

    public AdapterTheLoai(Context context, List<TheLoai> theLoaiList) {
        this.context = context;
        this.theLoaiList = theLoaiList;
    }

    @Override
    public int getCount() {
        return theLoaiList.size();
    }

    @Override
    public TheLoai getItem(int position) {
        return theLoaiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder hoder;
        final TheLoai theLoai = theLoaiList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_the_loai, parent, false);
            hoder = new viewHolder();
            hoder.tentheloai = convertView.findViewById(R.id.ten_the_loai);
            hoder.matheloai = convertView.findViewById(R.id.ma_theLoai);
            hoder.icon_delete_tl = convertView.findViewById(R.id.close_theLoai);
            convertView.setTag(hoder);
        } else {
            hoder = (viewHolder) convertView.getTag();
        }
        hoder.matheloai.setText(theLoai.getMaTheLoai());
        hoder.tentheloai.setText(theLoai.getTenTheLoai());
        hoder.icon_delete_tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseBookManager databaseBookManager = new DatabaseBookManager(parent.getContext());
                TheLoaiDAO theLoaiDAO = new TheLoaiDAO(databaseBookManager);
                theLoaiDAO.deleteTheLoai(theLoai.getMaTheLoai());
                changeDataset(theLoaiDAO.getAllTheLoai());
            }
        });
        return convertView;
    }

    public static class viewHolder {
        TextView matheloai, tentheloai;
        ImageView icon_delete_tl;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<TheLoai> items) {
        this.theLoaiList = items;
        notifyDataSetChanged();
    }
}

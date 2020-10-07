package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.SachDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.R;

import java.util.List;

public class AdapterSach extends BaseAdapter {
    Context context;
    List<Sach> sachList;

    public AdapterSach(Context context, List<Sach> sachList) {
        this.context = context;
        this.sachList = sachList;
    }

    @Override
    public int getCount() {
        return sachList.size();
    }

    @Override
    public Sach getItem(int position) {
        return sachList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder holder;
        final Sach sach = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sach, parent, false);
            holder = new viewHolder();
            holder.tv_tenSach = convertView.findViewById(R.id.ten_sach);
            holder.tv_soLuong = convertView.findViewById(R.id.so_luong_sach);
            holder.img_close_sach = convertView.findViewById(R.id.close_sach);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.tv_tenSach.setText(sach.getTenSach());
        holder.tv_soLuong.setText(String.valueOf(sach.getSoLuong()));
        holder.img_close_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseBookManager databaseBookManager = new DatabaseBookManager(parent.getContext());
                SachDAO sachDAO = new SachDAO(databaseBookManager);
                sachDAO.deleteSach(sach.getMaSach());
                changeDataset(sachDAO.getAllSach());
            }
        });
        return convertView;
    }

    public static class viewHolder {
        TextView tv_tenSach, tv_soLuong;
        ImageView img_close_sach;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Sach> items) {
        this.sachList = items;
        notifyDataSetChanged();
    }
}

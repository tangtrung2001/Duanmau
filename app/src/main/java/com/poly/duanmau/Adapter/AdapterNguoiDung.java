package com.poly.duanmau.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.NguoiDungDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.NguoiDung;
import com.poly.duanmau.NguoiDungActivity;
import com.poly.duanmau.R;

import java.util.List;

public class AdapterNguoiDung extends BaseAdapter {
    List<NguoiDung> nguoiDungList;
    Context context;

    public AdapterNguoiDung(List<NguoiDung> nguoiDungList, Context context) {
        this.nguoiDungList = nguoiDungList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return nguoiDungList.size();
    }

    @Override
    public NguoiDung getItem(int position) {
        return nguoiDungList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder holder;
        final NguoiDung nguoiDung = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nguoi_dung, parent, false);
            holder = new viewHolder();
            holder.phone = convertView.findViewById(R.id.sdt_nguoi_dung);
            holder.hoTen = convertView.findViewById(R.id.ten_nguoi_dung);
            holder.icon_delete = convertView.findViewById(R.id.close_nguoiDung);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.hoTen.setText(nguoiDung.getHoTen());
        holder.phone.setText(nguoiDung.getPhone());
        holder.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseBookManager databaseBookManager = new DatabaseBookManager(parent.getContext());
                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(databaseBookManager);
                nguoiDungDAO.deleteNguoiDung(nguoiDung.getUserName());
                changeDataset(nguoiDungDAO.getAllNguoiDung());
            }
        });
        return convertView;
    }

    public static class viewHolder {
        TextView phone, hoTen;
        ImageView icon_delete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<NguoiDung> items) {
        this.nguoiDungList = items;
        notifyDataSetChanged();
    }

}

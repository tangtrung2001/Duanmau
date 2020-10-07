package com.poly.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.duanmau.DAO.HoaDonDAO;
import com.poly.duanmau.Database.DatabaseBookManager;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class AdapterHoaDon extends BaseAdapter {
    Context context;
    List<HoaDon> hoaDonList;

    public AdapterHoaDon(Context context, List<HoaDon> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @Override
    public int getCount() {
        return hoaDonList.size();
    }

    @Override
    public HoaDon getItem(int position) {
        return hoaDonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        viewHolder holder;
        final HoaDon hoaDon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hoa_don, parent, false);
            holder = new viewHolder();
            holder.tv_mahoadon = convertView.findViewById(R.id.maHoaDon);
            holder.ngay_mua = convertView.findViewById(R.id.ngayHD);
            holder.close_hoadon = convertView.findViewById(R.id.close_hoaDon);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.tv_mahoadon.setText(hoaDon.getMaHoaDon());
        // chuyển ngày sang dạng String
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(hoaDon.getNgayMua());
        holder.ngay_mua.setText(s);

        holder.close_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseBookManager databaseBookManager = new DatabaseBookManager(parent.getContext());
                HoaDonDAO hoaDonDAO = new HoaDonDAO(databaseBookManager);
                hoaDonDAO.deleteHoaDon(hoaDon.getMaHoaDon());
                changeDataset(hoaDonDAO.getAllHoaDon());
            }
        });
        return convertView;
    }

    public static class viewHolder {
        TextView tv_mahoadon;
        TextView ngay_mua;
        ImageView close_hoadon;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDon> items) {
        this.hoaDonList = items;
        notifyDataSetChanged();
    }
}

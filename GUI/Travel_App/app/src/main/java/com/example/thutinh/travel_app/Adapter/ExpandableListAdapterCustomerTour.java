package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.thutinh.travel_app.DTO.ChiTietTour;
import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapterCustomerTour extends BaseExpandableListAdapter {

   private List<ChiTietTour> listChiTietTour = new ArrayList<>();
   private Context mContext;

    public ExpandableListAdapterCustomerTour(List<ChiTietTour> listChiTietTour, Context mContext) {
        this.listChiTietTour = listChiTietTour;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return listChiTietTour.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listChiTietTour.get(groupPosition).getNgay();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChiTietTour.get(groupPosition).getNoiDung();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.lich_trinh_header_tour, parent, false);
        }

        TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
        tvHeader.setText(listChiTietTour.get(groupPosition).getNgay());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.lich_trinh_noidung_tour, parent, false);
        }

        TextView tvNoiDung = (TextView) convertView.findViewById(R.id.lbTourNoiCungLichTrinh);
        tvNoiDung.setText(listChiTietTour.get(groupPosition).getNoiDung());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

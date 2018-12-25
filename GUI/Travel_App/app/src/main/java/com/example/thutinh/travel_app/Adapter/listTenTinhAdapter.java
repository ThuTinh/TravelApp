package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thutinh.travel_app.DTO.TenTinh_class;
import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class listTenTinhAdapter extends BaseAdapter {
     private Context context;
     private int layout;
     private List<TenTinh_class> listTinh=new ArrayList<>();
     private  ArrayList<TenTinh_class> arr;
     public listTenTinhAdapter(Context context, int layout, List<TenTinh_class>listtinh) {
        this.context = context;
        this.layout = layout;
        this.listTinh = listtinh;
        //tạo 1 mảng sang để thực hiện chức năng filter
        this.arr = new ArrayList<>();
        this.arr.addAll(listtinh);

    }

    @Override
    public int getCount() {
        return listTinh.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        //ánh xạ

        TextView txttentinh = (TextView)convertView.findViewById(R.id.txttentinh);
        ImageView img = (ImageView)convertView.findViewById(R.id.imgchuyentrang);

        //gan gia tri
        TenTinh_class item =listTinh.get(position);
        String tentinh  = item.getTenTinh();
        txttentinh.setText(tentinh);
        img.setImageResource(item.getHinh());

        return convertView;
    }

    //fill class
    public  void filter (String text)
    {

        try
        {
            text = text.toLowerCase(Locale.getDefault());
            listTinh.clear();
            if(text.length()==0)
                listTinh.addAll(arr);
            else
            {
                for(TenTinh_class tinh : arr)
                {
                    if(tinh.getTenTinh().toLowerCase(Locale.getDefault()).contains(text))
                    {
                        listTinh.add(tinh);
                    }
                }

            }
            this.notifyDataSetChanged();
        }catch (Exception e)
        {
            Log.d("hix",e.toString());
        }

    }
}

package com.example.thutinh.travel_app.Adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.example.thutinh.travel_app.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class listmota_adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MoTaChiTiet_class> listThongtin;

    public listmota_adapter(Context context, int layout, List<MoTaChiTiet_class> listThongtin) {
        this.context = context;
        this.layout = layout;
        this.listThongtin = listThongtin;
    }

    private  class ViewHolder{
        TextView txtDiaDanh, txtThongTin;
        ImageView img;
    }

    @Override
    public int getCount() {
        return listThongtin.size();
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

      ViewHolder hodler;
      if(convertView==null)
      {
          LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          convertView = inflater.inflate(layout, null);
            hodler = new ViewHolder();
          //ánh xạ
          hodler.txtDiaDanh = (TextView) convertView.findViewById(R.id.txttieude);
          hodler.txtThongTin = (TextView) convertView.findViewById(R.id.txtthongTin);
          hodler.img = (ImageView) convertView.findViewById(R.id.img);
          convertView.setTag(hodler);

          //gan gia tri

      }
      else
      {
          hodler=(ViewHolder)convertView.getTag();
      }

        MoTaChiTiet_class thongTin = listThongtin.get(position);
        hodler.txtDiaDanh.setText(thongTin.getTenDiaDanh());
        hodler.txtThongTin.setText((thongTin.getMoTa()));
        // xet anh lay tư 1 link
        if(thongTin.arrHinh.size()==0)
        {
            hodler.img.setImageResource(R.drawable.noimg);
        }
        else
        {
            Picasso.get().load(thongTin.arrHinh.get(0)).into(hodler.img);
            Log.d("Load hinh","lôi gì đây " );
        }


        return convertView;
    }
}

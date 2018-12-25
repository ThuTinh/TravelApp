package com.example.thutinh.travel_app.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.thutinh.travel_app.R;

import java.util.ArrayList;
import java.util.List;

public class ThemAnhAdapter extends  RecyclerView.Adapter<ThemAnhAdapter.ViewHolder>{

    private List<Bitmap> listAnh = new ArrayList<>() ;
    private  Context mContext;
    public List<String>arrHinh = new ArrayList<>();


    public ThemAnhAdapter(List<Bitmap> listAnh,List<String> arrHinh) {
        this.listAnh = listAnh;
        this.arrHinh = arrHinh;
    }

    @NonNull
    @Override
    public ThemAnhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listAnhView = inflater.inflate(R.layout.list_img,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listAnhView);
        mContext = context;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThemAnhAdapter.ViewHolder viewHolder, final int i) {


        Bitmap bm = listAnh.get(i);
        final ImageView addImg = viewHolder.img;
        addImg.setImageBitmap(bm);

        viewHolder.CardAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(mContext, addImg);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_xoa_anh, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                       if(item.getItemId()==R.id.btnXoaAnh);
                        {
                            listAnh.remove(i);
                            ThemAnhAdapter.this.notifyDataSetChanged();
                            arrHinh.remove(i);


                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
void ShowMenu()
{

}
    @Override
    public int getItemCount() {
        return listAnh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      public ImageView img ;
      private CardView CardAddImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.addImg);
            CardAddImg =(CardView)itemView.findViewById(R.id.CardAddImg);

        }
    }
}



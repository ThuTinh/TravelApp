package com.example.thutinh.travel_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class ListDichVu extends AppCompatActivity {
    private FlipperLayout flipListDichVu;
    private  Bundle bRecieve;
    private  Bundle bSend;
    String tenTinh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dich_vu);
        bRecieve = getIntent().getExtras();
        bSend = new Bundle();
        bSend = bRecieve;
        tenTinh = bRecieve.getString("TenTinh");

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(tenTinh+">Dịch vụ");
        AnhXa();
        Slider();
    }

    public   void AnhXa()
    {
        flipListDichVu = (FlipperLayout) findViewById(R.id.flipListDichVu);
    }
    public void Slider()
    {
        FlipperView view = new FlipperView(getBaseContext());
       view.setImageDrawable(R.drawable.nhao).setDescription("Dịch vụ cung cấp nhà ở").setImageScaleType(ImageView.ScaleType.FIT_XY);
       view.setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
           @Override
           public void onFlipperClick(FlipperView flipperView) {
               Intent it = new Intent(ListDichVu.this, ListHome.class);
               bSend.putString("LoaiDichVu","NhaO");
               it.putExtras(bSend);
               startActivity(it);
           }
       });
        flipListDichVu.addFlipperView(view);
        FlipperView view1 = new FlipperView(getBaseContext());
        view1.setImageDrawable(R.drawable.amthuc).setDescription("Dịch vụ ăn uống").setImageScaleType(ImageView.ScaleType.FIT_XY);
        flipListDichVu.addFlipperView(view1);
        view1.setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
            @Override
            public void onFlipperClick(FlipperView flipperView) {
                Intent it = new Intent(ListDichVu.this, ListAnUong.class);
                bSend.putString("LoaiDichVu","AnUong");
                it.putExtras(bSend);
                startActivity(it);

            }
        });
        FlipperView view2 = new FlipperView(getBaseContext());
        view2.setImageDrawable(R.drawable.tour).setDescription("Dịch vụ theo tour").setImageScaleType(ImageView.ScaleType.FIT_XY);
        flipListDichVu.addFlipperView(view2);
        view2.setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
            @Override
            public void onFlipperClick(FlipperView flipperView) {
                Intent it = new Intent(ListDichVu.this, ListTour.class);
                bSend.putString("LoaiDichVu","Tour");
                it.putExtras(bSend);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            Intent it = new Intent(ListDichVu.this, listthongtin.class);
            it.putExtras(bRecieve);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }
}

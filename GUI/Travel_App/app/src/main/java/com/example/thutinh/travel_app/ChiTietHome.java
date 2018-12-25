package com.example.thutinh.travel_app;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.CommentAdapter;
import com.example.thutinh.travel_app.DTO.Comment_class;
import com.example.thutinh.travel_app.DTO.Home;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class ChiTietHome extends AppCompatActivity {

    private FlipperLayout flipChiTietHome;
    private TextView lbEmail, lbPhone, lbFace, lbNoiDung, lbViTri, lbNameHome,lbCmt;
    private RecyclerView rvComment;
    private Button btnCmt;
    private EditText txtCmt;
    private Bundle bRecieve;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Home item;
    private CommentAdapter commentAdapter;
    private Comment_class cmt;
    private  String name;
    private  String tenMien;
    private String tenTinh;
    private  String loaiDichVu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        name = user.getDisplayName();
        setContentView(R.layout.activity_chi_tiet_home);
        AnhXa();
        bRecieve = getIntent().getExtras();
       tenMien = bRecieve.getString("TenMien");
       tenTinh = bRecieve.getString("TenTinh");
        getSupportActionBar().setTitle(tenTinh+">"+"Nhà ở");
       loaiDichVu = bRecieve.getString("LoaiDichVu");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       GetData();
       lbViTri.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               lbViTri.setTextColor(Color.parseColor("#f12d2d"));
//               Intent it = new Intent(ChiTietHome.this, MapClient.class);
//               Bundle bSend = new Bundle();
//               bSend.putString("location", lbViTri.getText().toString());
//               it.putExtras(bSend);
//               startActivity(it);
               //        Geocoder geocoder = new Geocoder(this);

            String vitri = lbViTri.getText().toString();
            List<Address > listAddress = null;
            Geocoder geocoder = new Geocoder(ChiTietHome.this);
               try {
                   listAddress =  geocoder.getFromLocationName(vitri, 1);
               } catch (IOException e) {
                   e.printStackTrace();
               }

               Address address = listAddress.get(0);
              // LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//               String uri = String.format(Locale.ENGLISH, "geo:%f,%f(Treasure)", address.getLatitude(), address.getLongitude());
//               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//               intent.setPackage("com.google.android.apps.maps");
//               startActivity(intent);
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + address.getLatitude()  + ">,<" + address.getLongitude() + ">?q=<" + address.getLatitude()  + ">,<" + address.getLongitude() + ">(" + lbNameHome.getText().toString() + ")"));
               startActivity(intent);
           }
       });


       if(item.getListFeedBack()!=null)
       {
           commentAdapter = new CommentAdapter(item.getListFeedBack());
           rvComment.setAdapter(commentAdapter);
           LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
           rvComment.setLayoutManager(layoutManager);
           lbCmt.setText("Bình Luận ("+item.getListFeedBack().size()+")");
       }
        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CMT();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(item.getEmailNguoiTao().equals(user.getEmail()))
        {
            getMenuInflater().inflate(R.menu.menu_update, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }


    private void CMT() {

        cmt = new Comment_class();

            if(txtCmt.getText().toString().trim().length()==0)
            {
                Toast.makeText(ChiTietHome.this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
            }
            else
            {
                cmt.setName(name);
                cmt.setCmt(txtCmt.getText().toString());
                //Lay gio he thong
                Date thoiGian = new Date();
                //Khai bao dinh dang ngay thang
                SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
                //parse ngay thang sang dinh dang va chuyen thanh string.
                String showTime = dinhDangThoiGian.format(thoiGian.getTime());
                cmt.setTimeCmt(showTime);
                item.listFeedBack.add(cmt);
                myRef.child("DichVu").child(loaiDichVu).child(tenMien).child(tenTinh).child(item.getKey()).setValue(item);
                commentAdapter.notifyDataSetChanged();
                txtCmt.setText("");
            }
    }
    public  void AnhXa()
    {
        flipChiTietHome = (FlipperLayout)findViewById(R.id.flipChiTietHome);
        lbEmail = (TextView)findViewById(R.id.lbChiTietHomeEmail);
        lbPhone = (TextView)findViewById(R.id.lbChiTietHomePhone);
        lbFace = (TextView)findViewById(R.id.lbChiTietHomeFace);
        lbNoiDung = (TextView)findViewById(R.id.lbChiTietNoiDung);
        lbNameHome = (TextView)findViewById(R.id.lbChiTietNameHome);
        lbViTri = (TextView)findViewById(R.id.lbChiTietHomeViTri);
        lbCmt = (TextView)findViewById(R.id.lbChiTietHomeBinhLuan);
        btnCmt = (Button)findViewById(R.id.btnChiTietHomeCmt);
        txtCmt = (EditText)findViewById(R.id.txtChiTietHomeCmt);
        rvComment = (RecyclerView)findViewById(R.id.rvChiTietHomeCmt);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:

                Intent t = new Intent(getApplicationContext(), ListHome.class);
                t.putExtras(bRecieve);
                startActivity(t);
                return true;
            case R.id.update:
                Intent it = new Intent(ChiTietHome.this, AddHome.class);
                Bundle bSend = new Bundle(bRecieve);
                bSend.putString("Edit","1");
                it.putExtras(bSend);
                startActivity(it);
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void  GetData()
    {
        item = (Home) bRecieve.getSerializable("Item");
        if (item.arrHinh.size() == 0) {
            FlipperView view = new FlipperView(getBaseContext());
            view.setImageDrawable(R.drawable.noimg).setImageScaleType(ImageView.ScaleType.CENTER);
           flipChiTietHome.addFlipperView(view);
            // Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < item.arrHinh.size(); i++) {
                FlipperView view = new FlipperView(getBaseContext());
                view.setImageUrl(item.arrHinh.get(i)).setImageScaleType(ImageView.ScaleType.FIT_XY);
                flipChiTietHome.addFlipperView(view);
            }
        }
        lbViTri.setText(item.getDiaDiem());
        lbNameHome.setText(item.getNameHome());
        lbNoiDung.setText(item.getNoiDung());
        lbFace.setText(item.getFace());
        lbPhone.setText(item.getPhone());
        lbEmail.setText(item.getEmail());

    }

}

package com.example.thutinh.travel_app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thutinh.travel_app.Adapter.CommentAdapter;
import com.example.thutinh.travel_app.Adapter.ThongTinMonAnAdapter;
import com.example.thutinh.travel_app.DTO.Comment_class;
import com.example.thutinh.travel_app.DTO.DanhSachAnUong;
import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import technolifestyle.com.imageslider.FlipperLayout;

public class ChiTietMonAn extends AppCompatActivity {

    private RecyclerView rvListChiTietAnUong, rvChiTietAnUongCmt;
    private FlipperLayout flipAnUongChiTiet;
    private TextView lbChiTietAnUongViTri, lbChiTietAnUongPhone, lbAnUongChiTietCmt,lbChiTietAnUongFace, lbChiTietAnUongEmail, lbChiTietAnUongNoiDung,lbAnOngChiTietTen;
    private EditText txtChiTietAnUongCmt;
    private Button btnChiTietAnUongSave;
    private ImageView imgChiTietMonAn;
    DanhSachAnUong item;
    Bundle bReveive;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Comment_class cmt;
    private CommentAdapter commentAdapter;
    String loaiDichVu;
    String tenTinh;
    String tenMien;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        name = user.getDisplayName();
        setContentView(R.layout.activity_an_uong_chi_tiet);
        AnhXa();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bReveive = getIntent().getExtras();
        loaiDichVu = bReveive.getString("LoaiDichVu");
        tenMien = bReveive.getString("TenMien");
        tenTinh = bReveive.getString("TenTinh");
        getSupportActionBar().setTitle(tenTinh+">"+"Ẩm thực");
        item = (DanhSachAnUong) bReveive.getSerializable("Item");
        cmt = new Comment_class();
        GetData();
        if(item.getListMonAn()!=null)
        {
            ThongTinMonAnAdapter  thongTinMonAnAdapter= new ThongTinMonAnAdapter(item.getListMonAn(),ChiTietMonAn.this);
          //  rvListChiTietAnUong.setHasFixedSize(true);
          //  rvListChiTietAnUong.setLayoutManager(new GridLayoutManager(this, 2));
            StaggeredGridLayoutManager gridLayoutManager =
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
// Attach the layout manager to the recycler view
            rvListChiTietAnUong.setLayoutManager(gridLayoutManager);
            rvListChiTietAnUong.setAdapter(thongTinMonAnAdapter);
            thongTinMonAnAdapter.notifyDataSetChanged();
        }
        if(item.getListCmt()!=null)
        {
            commentAdapter = new CommentAdapter(item.getListCmt());
            rvChiTietAnUongCmt.setAdapter(commentAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvChiTietAnUongCmt.setLayoutManager(layoutManager);
            lbAnUongChiTietCmt.setText("Bình Luận ("+item.getListCmt().size()+")");
        }
        lbChiTietAnUongPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = lbChiTietAnUongPhone.getText().toString();
                if(!TextUtils.isEmpty(phone))
                {
                    String dial = "tel:" + phone;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }
                else
                {
                    Toast.makeText(ChiTietMonAn.this, "Số điện thoại không phù hợp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChiTietAnUongSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CMT();
            }
        });
        lbChiTietAnUongViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              lbChiTietAnUongViTri.setTextColor(Color.parseColor("#f12d2d"));
//                Intent it = new Intent(ChiTietMonAn.this, MapClient.class);
//                Bundle bSend = new Bundle();
//                bSend.putString("location", lbChiTietAnUongViTri.getText().toString());
//                it.putExtras(bSend);
//                startActivity(it);
                String vitri = lbChiTietAnUongViTri.getText().toString();
                List<Address > listAddress = null;
                Geocoder geocoder = new Geocoder(ChiTietMonAn.this);
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
                Intent inten = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + address.getLatitude()  + ">,<" + address.getLongitude() + ">?q=<" + address.getLatitude()  + ">,<" + address.getLongitude() + ">(" + lbAnOngChiTietTen.getText().toString() + ")"));
                startActivity(inten);
            }
        });
    }

    void  GetData()
    {
        lbChiTietAnUongEmail.setText(item.getEmail());
        lbChiTietAnUongFace.setText(item.getFace());
        lbChiTietAnUongNoiDung.setText(item.getNoiDung());
        lbChiTietAnUongPhone.setText(item.getPhone());
        lbChiTietAnUongViTri.setText(item.getDiaDiem());
        lbAnOngChiTietTen.setText(item.getTennhaCungCap());
        if(item.getHinh().length()==0)
        {
            imgChiTietMonAn.setImageResource(R.drawable.noimg);
            imgChiTietMonAn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else
        {
            Glide.with(this).load(item.getHinh()).into(imgChiTietMonAn);
            imgChiTietMonAn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (item.getNguoiTao().equals(user.getEmail())) {
            getMenuInflater().inflate(R.menu.menu_update, menu);
        }
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                Intent t = new Intent(getApplicationContext(), ListAnUong.class);
                t.putExtras(bReveive);
                startActivity(t);
                return true;
            case R.id.update:
                Intent it = new Intent(ChiTietMonAn.this, AddAnUong.class);
                Bundle bSend = new Bundle(bReveive);
                bSend.putString("Edit", "1");
                it.putExtras(bSend);
                startActivity(it);
                return  true;
            case  R.id.MenuHome:
                Intent it1 = new Intent(ChiTietMonAn.this, MainActivity.class);
                startActivity(it1);

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AnhXa() {
        rvListChiTietAnUong = (RecyclerView) findViewById(R.id.rvListChiTietAnUong);
        rvChiTietAnUongCmt = (RecyclerView) findViewById(R.id.rvChiTietAnUongCmt);
        flipAnUongChiTiet = (FlipperLayout) findViewById(R.id.flipAnUongChiTiet);
        lbChiTietAnUongViTri = (TextView) findViewById(R.id.lbChiTietAnUongViTri);
        lbChiTietAnUongPhone = (TextView) findViewById(R.id.lbChiTietAnUongPhone);
        lbChiTietAnUongFace = (TextView) findViewById(R.id.lbChiTietAnUongFace);
        lbChiTietAnUongEmail = (TextView) findViewById(R.id.lbChiTietAnUongEmail);
        lbChiTietAnUongNoiDung = (TextView) findViewById(R.id.lbChiTietAnUongNoiDung);
        txtChiTietAnUongCmt = (EditText) findViewById(R.id.txtChiTietAnUongCmt);
        btnChiTietAnUongSave = (Button) findViewById(R.id.btnChiTietAnUongSave);
        lbAnOngChiTietTen = (TextView)findViewById(R.id.lbAnOngChiTietTen);
        imgChiTietMonAn = (ImageView)findViewById(R.id.imgChiTietMonAn);
        lbAnUongChiTietCmt = (TextView)findViewById(R.id.lbAnUongChiTietCmt);
    }

    private void CMT() {

        cmt = new Comment_class();

        if(txtChiTietAnUongCmt.getText().toString().trim().length()==0)
        {
            Toast.makeText(ChiTietMonAn.this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cmt.setName(name);
            cmt.setCmt(txtChiTietAnUongCmt.getText().toString());
            //Lay gio he thong
            Date thoiGian = new Date();
            //Khai bao dinh dang ngay thang
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
            //parse ngay thang sang dinh dang va chuyen thanh string.
            String showTime = dinhDangThoiGian.format(thoiGian.getTime());
            cmt.setTimeCmt(showTime);
            item.listCmt.add(cmt);
            myRef.child("DichVu").child(loaiDichVu).child(tenMien).child(tenTinh).child(item.getKey()).setValue(item);
            commentAdapter.notifyDataSetChanged();
            txtChiTietAnUongCmt.setText("");
        }
    }

}

package com.example.thutinh.travel_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.ChiTietTourAdapter;
import com.example.thutinh.travel_app.Adapter.CommentAdapter;
import com.example.thutinh.travel_app.Adapter.ExpandableListAdapterCustomerTour;
import com.example.thutinh.travel_app.DTO.Comment_class;
import com.example.thutinh.travel_app.DTO.Home;
import com.example.thutinh.travel_app.DTO.TourDuLich;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class Tour extends AppCompatActivity {

    private TextView lbTourPhone,lbTourFace,lbTourCmt,lbTourEmail,lbTourNoiDung,lbTourTGTu,lbTourTGDen,lbTourGia,lbTourDiaDiemDen,lbTourMaTour,lbTourSoLuongConLai,lbKhoiHanhTai;
   //private ExpandableListView expanlistLichTrinh;
    private Button btnTourSave;
    private EditText txtTourCmt;
    private FlipperLayout flipTour;
    private  Bundle bRecive;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private  String name;
    private  String tenMien;
    private String tenTinh;
    private  String loaiDichVu;
    private TourDuLich tour;
    private RecyclerView rvListChiTietTour, rvTourCmt;
    private ChiTietTourAdapter chiTietTourAdapter;
    private CommentAdapter commentAdapter;
    private  Comment_class cmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        name = user.getDisplayName();
        setContentView(R.layout.activity_tour);
        bRecive = getIntent().getExtras();
        tour = new TourDuLich();
        tenMien = bRecive.getString("TenMien");
        tenTinh = bRecive.getString("TenTinh");
        loaiDichVu = bRecive.getString("LoaiDichVu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AnhXa();
        GetData();
        chiTietTourAdapter = new ChiTietTourAdapter(tour.listChiTietTour);
        rvListChiTietTour.setAdapter(chiTietTourAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListChiTietTour.setLayoutManager(layoutManager);

            commentAdapter = new CommentAdapter(tour.getListFeedBack());
            rvTourCmt.setAdapter(commentAdapter);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvTourCmt.setLayoutManager(layoutManager1);
            lbTourCmt.setText("Bình Luận ("+tour.getListFeedBack().size()+")");


        btnTourSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CMT();
            }
        });
    }

    private void CMT() {

        cmt = new Comment_class();

        if(txtTourCmt.getText().toString().trim().length()==0)
        {
            Toast.makeText(Tour.this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cmt.setName(name);
            cmt.setCmt(txtTourCmt.getText().toString());
            //Lay gio he thong
            Date thoiGian = new Date();
            //Khai bao dinh dang ngay thang
            SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
            //parse ngay thang sang dinh dang va chuyen thanh string.
            String showTime = dinhDangThoiGian.format(thoiGian.getTime());
            cmt.setTimeCmt(showTime);
            tour.listFeedBack.add(cmt);
            myRef.child("DichVu").child(loaiDichVu).child(tenMien).child(tenTinh).child(tour.getKey()).setValue(tour);
            commentAdapter.notifyDataSetChanged();
            txtTourCmt.setText("");
        }
    }

    void AnhXa()
    {

        lbKhoiHanhTai = (TextView)findViewById(R.id.lbKhoiHanhTai);
        lbTourDiaDiemDen = (TextView)findViewById(R.id.lbTourDiaDiemDen);
        lbTourEmail = (TextView)findViewById(R.id.lbTourEmail);
        lbTourFace = (TextView)findViewById(R.id.lbTourFace);
        lbTourPhone = (TextView)findViewById(R.id.lbTourPhone);
        lbTourMaTour = (TextView)findViewById(R.id.lbTourMaTour);
        lbTourGia = (TextView)findViewById(R.id.lbTourGia);
        lbTourTGDen = (TextView)findViewById(R.id.lbTourTGDen);
        lbTourTGTu = (TextView)findViewById(R.id.lbTourTGTu);
       // expanlistLichTrinh = (ExpandableListView)findViewById(R.id.expanlistLichTrinh);
        flipTour = (FlipperLayout)findViewById(R.id.flipTour);
        lbTourNoiDung = (TextView)findViewById(R.id.lbTourNoiDung);
        rvListChiTietTour = (RecyclerView) findViewById(R.id.rvListChiTietTour);
        lbTourSoLuongConLai = (TextView)findViewById(R.id.lbTourSoLuongConLai);
        rvTourCmt = (RecyclerView)findViewById(R.id.rvTourCmt);
        txtTourCmt = (EditText)findViewById(R.id.txtTourCmt);
        btnTourSave = (Button)findViewById(R.id.btnTourSave);
        lbTourCmt = (TextView)findViewById(R.id.lbTourCmt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tour.getNguoiTao().equals(user.getEmail())) {
            getMenuInflater().inflate(R.menu.menu_update, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.update)
        {
            Intent it = new Intent(Tour.this, AddTour.class);
            Bundle bSend = new Bundle(bRecive);
            bSend.putString("Edit","1");
            it.putExtras(bSend);
            startActivity(it);
        }

        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void  GetData()
    {
       tour = (TourDuLich) bRecive.getSerializable("Item");
        if (tour.arrHinh.size() == 0) {
            FlipperView view = new FlipperView(getBaseContext());
            view.setImageDrawable(R.drawable.noimg).setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            flipTour.addFlipperView(view);
            // Toast.makeText(ThongTinDichVuTab.this,""+item.arrHinh.size() , Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < tour.arrHinh.size(); i++) {
                FlipperView view = new FlipperView(getBaseContext());
                view.setImageUrl(tour.arrHinh.get(i)).setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                flipTour.addFlipperView(view);
            }
        }
        lbTourTGTu.setText("Thời gian từ: "+tour.getThoiGianTu());
        lbTourTGDen.setText("Đến: "+tour.getThơiGianDen());
        lbTourGia.setText("Giá: "+tour.getGia());
        lbTourMaTour.setText("Mã tour: "+tour.getMaTour());
        lbTourFace.setText(tour.getFaceBook());
        lbTourEmail.setText(tour.getEmail());
        lbTourPhone.setText(tour.getPhone());
        lbTourDiaDiemDen.setText("Danh sách địa điểm: "+tour.getDsDiaDiem());
        lbKhoiHanhTai.setText("Địa điểm khởi hành: "+tour.getDiaDiemKhoiHanh());
        lbTourNoiDung.setText(tour.getNoiDung());
        lbTourSoLuongConLai.setText("Số lượng trống: "+tour.getSoLuongCon());


    }
}

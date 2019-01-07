package com.example.thutinh.travel_app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.thutinh.travel_app.Adapter.ThemAnhAdapter;
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddThongTinDiaDanh extends AppCompatActivity {

   // private Button btnLuu;
   // private Button btnHuy;
   // private Button btnTake;
    private Button btnChoose;
    private EditText txtTenDiaDanh;
    private EditText txtMoTa;
    private Bundle bl;
    private MoTaChiTiet_class moTaChiTiet;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private int request_code = 1;
    private int request_codeFile = 2;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private RecyclerView rvAddAnh;
    private List<Bitmap> listHinh;
    private ThemAnhAdapter themAnhAdapter;
    private String Edit;
    private TextView txtAddThongTinDiaDanhViTri;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private  String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thong_tin_dia_danh);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        name = user.getDisplayName();


        bl = getIntent().getExtras();
        AnhXa();
        Edit = bl.getString("Edit","0");
        if(Edit.equals("1"))
        {
            moTaChiTiet = (MoTaChiTiet_class)bl.getSerializable("Item");
            txtTenDiaDanh.setText(moTaChiTiet.getTenDiaDanh());
            txtMoTa.setText(moTaChiTiet.getMoTa());
            txtAddThongTinDiaDanhViTri.setText(moTaChiTiet.getViTri());
            for(int i = 0; i<moTaChiTiet.getArrHinh().size(); i++)
          {
            try
            {
                //   Bitmap bm = getBitmapFromURL(item.arrHinh.get(i));
                Glide
                        .with(getApplicationContext())
                        .asBitmap()
                        .load(moTaChiTiet.arrHinh.get(i))
                        .into(new SimpleTarget<Bitmap>(100,100) {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                listHinh.add(resource);
                            }
                        });

            }catch (Exception e)
            {
                Toast.makeText(this, "Lỗi, Thử lại sau", Toast.LENGTH_SHORT).show();
            }
        }

        }
        else
            moTaChiTiet = new MoTaChiTiet_class();

//        btnTake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(it, request_code);
//
//            }
//        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, request_codeFile);
            }
        });
        //tạo nút mũi tên trên thành toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        themAnhAdapter = new ThemAnhAdapter(listHinh,moTaChiTiet.arrHinh);
        rvAddAnh.setAdapter(themAnhAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAddAnh.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_thong_tin, menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.save:
                    Luu();
                    return true;
            case  R.id.MenuHome:
                Intent it = new Intent(AddThongTinDiaDanh.this, MainActivity.class);
                startActivity(it);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void AnhXa() {
      //  btnTake = (Button) findViewById(R.id.btnTake);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        txtMoTa = (EditText) findViewById(R.id.txtMoTa);
        txtTenDiaDanh = (EditText) findViewById(R.id.txtTenDiaDanh);
       // Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.no_img1);
        listHinh = new ArrayList<>();
       // listHinh.add(img);
        rvAddAnh = (RecyclerView) findViewById(R.id.rvAddAnh);
        txtAddThongTinDiaDanhViTri = (EditText)findViewById(R.id.txtAddThongTinDiaDanhViTri);
    }

  Boolean isDone = false;
    void LoadImg(Bitmap bm) {

        String tenDiaDanh = txtTenDiaDanh.getText().toString().trim();
        final StorageReference mountainsRef = storageRef.child(bl.getString("TenTinh") + "_" + tenDiaDanh + System.currentTimeMillis() + ".png");
        //img.setDrawingCacheEnabled(true);
       // img.buildDrawingCache();
       // Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        Bitmap bitmap = bm;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddThongTinDiaDanh.this, "Tạo không thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();
               themAnhAdapter.arrHinh.add(downloadUrl.toString());
              //  isDone = true;
          //  Toast.makeText(AddThongTinDiaDanh.this, moTaChiTiet.arrHinh.size()+"",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private  void Luu()
    {
        try {
            if (txtMoTa.getText().toString().trim().length() == 0 || txtTenDiaDanh.getText().toString().trim().length() == 0) {
                Toast.makeText(AddThongTinDiaDanh.this, "Thông tin chưa đầy đủ", Toast.LENGTH_SHORT).show();
            } else {

                    moTaChiTiet.arrHinh = new ArrayList<>();
                    moTaChiTiet.arrHinh.addAll(themAnhAdapter.arrHinh);


                    //Lay gio he thong
                    Date thoiGian = new Date();
                    //Khai bao dinh dang ngay thang
                    SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
                    //parse ngay thang sang dinh dang va chuyen thanh string.
                    String showTime = dinhDangThoiGian.format(thoiGian.getTime());

                moTaChiTiet.setViTri(txtAddThongTinDiaDanhViTri.getText().toString());
                moTaChiTiet.listEdit.add(showTime + " Edit by: "+name);
                moTaChiTiet.setMoTa(txtMoTa.getText().toString());
                moTaChiTiet.setTenDiaDanh(txtTenDiaDanh.getText().toString());
                if(Edit.equals("1"))
                {

                    myRef.child("ThongTin").child(bl.getString("TenMien")).child(bl.getString("TenTinh")).child(moTaChiTiet.getKey()).setValue(moTaChiTiet);
                    Toast.makeText(AddThongTinDiaDanh.this, "Edit Succces", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(AddThongTinDiaDanh.this, listthongtin.class);
                    it.putExtras(bl);
                    startActivity(it);

                }
                else
                {

                    myRef.child("ThongTin").child(bl.getString("TenMien")).child(bl.getString("TenTinh")).push().setValue(moTaChiTiet);
                    Toast.makeText(AddThongTinDiaDanh.this, "Add Succces", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(AddThongTinDiaDanh.this, listthongtin.class);
                    it.putExtras(bl);
                   // finish();
                    startActivity(it);
                }

            }

        } catch (Exception e) {
            Toast.makeText(AddThongTinDiaDanh.this, "Lỗi, Thử lại sau", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bitmap;

        if (requestCode == request_code && resultCode == RESULT_OK && data != null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            listHinh.add(bitmap);
        }
        if (requestCode == request_codeFile && resultCode == RESULT_OK && data != null) {

                Uri uri = data.getData();
                try {
                    Bitmap bt = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    listHinh.add(bt);

//                Uri uri = data.getData();
//                img1.setImageURI(uri);
// LoadImg(bt);
                LoadImg(bt);

                } catch (IOException e) {
                    Log.d("166", e.toString());
                }
        }

        themAnhAdapter.notifyDataSetChanged();
            super.onActivityResult(requestCode, resultCode, data);

        }


}

package com.example.thutinh.travel_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.thutinh.travel_app.Adapter.ThemAnhAdapter;
import com.example.thutinh.travel_app.DTO.Home;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddHome extends AppCompatActivity {
    private List<Bitmap> listHinh;
    private ThemAnhAdapter themAnhAdapter;
    private RecyclerView rvImgAddHome;
    private EditText txtHomePhone;
    private EditText txtHomeFace;
    private EditText txtHomeEmail;
    private EditText txtHomeViTri;
    private EditText txtHomeNoiDung;
    private Button btnHomeChooseAPicture;
    private int request_codeFile = 1;
    private EditText txtHomeName;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private Bundle bRevieve;
    private DatabaseReference myRef;
    private Home item;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private Bundle bSend;
    private String Edit = "0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setContentView(R.layout.activity_add_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm nhà ở");
        AnhXa();
        item = new Home();
        bRevieve = getIntent().getExtras();
        bSend = new Bundle();
        bSend = bRevieve;
        Edit = bRevieve.getString("Edit","0");

        btnHomeChooseAPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, request_codeFile);
            }
        });
        if(Edit.equals("1"))
        {

            item = (Home) bRevieve.getSerializable("Item");
            txtHomeName.setText(item.getNameHome());
            txtHomePhone.setText(item.getPhone());
            txtHomeViTri.setText(item.getDiaDiem());
            txtHomeNoiDung.setText(item.getNoiDung());
            txtHomeFace.setText(item.getFace());
           txtHomeEmail.setText(item.getEmail());
           for(int i = 0; i<item.getArrHinh().size(); i++)
           {
            try
            {
             //   Bitmap bm = getBitmapFromURL(item.arrHinh.get(i));
                Glide
                        .with(getApplicationContext())
                        .asBitmap()
                        .load(item.arrHinh.get(i))
                        .into(new SimpleTarget<Bitmap>(100,100) {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                listHinh.add(resource);
                            }
                        });

            }catch (Exception e)
            {
                Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
            }

           }
        }
        themAnhAdapter = new ThemAnhAdapter(listHinh, item.arrHinh);
        rvImgAddHome.setAdapter(themAnhAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvImgAddHome.setLayoutManager(layoutManager);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == request_codeFile && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            try {
                Bitmap bt = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                listHinh.add(bt);
                LoadImg(bt);


            } catch (IOException e) {
                Log.d("166", e.toString());
            }
//            if(checkIsFirst) {
//                listHinh.remove(0);
//                checkIsFirst = false;
//            }
           // themAnhAdapter.notifyDataSetChanged();
        }
        themAnhAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }
   // Boolean isTrue = false;
    void LoadImg(Bitmap bm) {


        String nameHome = txtHomeName.getText().toString().trim();
        final StorageReference mountainsRef = storageRef.child(bRevieve.getString("TenTinh") + "_" + nameHome + System.currentTimeMillis() + ".png");
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
                Toast.makeText(AddHome.this, "Tạo không thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();

                themAnhAdapter.arrHinh.add(downloadUrl.toString());


              // Toast.makeText(AddHome.this, "link "+ item.arrHinh.get(0), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AnhXa() {
        rvImgAddHome = (RecyclerView) findViewById(R.id.rvImgAddHome);
        btnHomeChooseAPicture = (Button) findViewById(R.id.btnHomeChooseAPicture);
        txtHomeName = (EditText) findViewById(R.id.txtHomeName);
      //  Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.no_img1);
        listHinh = new ArrayList<>();
       // listHinh.add(img);
        txtHomeEmail = (EditText) findViewById(R.id.txtHomeEmail);
        txtHomeFace = (EditText) findViewById(R.id.txtHomeFace);
        txtHomeNoiDung = (EditText) findViewById(R.id.txtHomeMoTa);
        txtHomeViTri = (EditText) findViewById(R.id.txtHomeViTri);
        txtHomePhone = (EditText) findViewById(R.id.txtHomePhone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_thong_tin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==R.id.save)
        {


            Luu();
        }
        if(item.getItemId()==android.R.id.home) {
//            Intent it = new Intent(AddHome.this, ChiTietHome.class);
//            it.putExtras(bRevieve);
//            startActivity(it);
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void Luu() {
        try {

            String tenDiaDiem = "";
            String homName = "";
            String email = "";
            String phone = "";
            String face = "";
            String noiDung = "";
            tenDiaDiem = txtHomeViTri.getText().toString().trim();
            homName = txtHomeName.getText().toString().trim();
            email = txtHomeEmail.getText().toString().trim();
            phone = txtHomePhone.getText().toString().trim();
            face = txtHomeFace.getText().toString().trim();
            noiDung = txtHomeNoiDung.getText().toString().trim();

            if (tenDiaDiem.length() == 0 || homName.length() == 0 || email.length() == 0 || phone.length() == 0 || noiDung.length() == 0) {
                Toast.makeText(AddHome.this, "Bạn chưa cung cấp đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                item.arrHinh = new ArrayList<>();
                item.arrHinh.addAll(themAnhAdapter.arrHinh);
                    item.setDiaDiem(tenDiaDiem);
                    item.setEmail(email);
                    item.setFace(face);
                    item.setNameHome(homName);
                    item.setNoiDung(noiDung);
                    item.setPhone(phone);
                    item.setEmailNguoiTao(mAuth.getCurrentUser().getEmail());


                  //  Toast.makeText(this, "Số hình trong mảng là "+ listHinh.size()+ "Sô shinhf trng list arr là "+ item.arrHinh.size(),Toast.LENGTH_LONG).show();
                    if (Edit.equals("1")) {
                        myRef.child("DichVu").child(bRevieve.getString("LoaiDichVu")).child(bRevieve.getString("TenMien")).child(bRevieve.getString("TenTinh")).child(item.getKey()).setValue(item);
                        Toast.makeText(AddHome.this, "cập nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(AddHome.this, ListHome.class);
                        it.putExtras(bSend);
                        startActivity(it);

                    } else {

                        myRef.child("DichVu").child(bRevieve.getString("LoaiDichVu")).child(bRevieve.getString("TenMien")).child(bRevieve.getString("TenTinh")).push().setValue(item);
                        Toast.makeText(AddHome.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(AddHome.this, ListHome.class);
                        it.putExtras(bSend);
                        // finish();
                        startActivity(it);
                    }
                }

        } catch (Exception a) {
        }
    }
}





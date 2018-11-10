package com.example.thutinh.travel_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

public class AddThongTinDiaDanh extends AppCompatActivity {

    private Button btnTakeAPicture;
    private  Button btnChooseFromFile;
    private  Button btnLuu;
    private  Button btnHuy;
    private EditText txtTenDiaDanh;
    private  EditText txtMoTa;
    private ImageView img1,img2, img3;
    private  Bundle bl;
    MoTaChiTiet_class moTaChiTiet;
    FirebaseStorage storage;
    StorageReference storageRef ;
    private  int request_code1 = 1;
    private  int request_code2 = 2;
    private  int request_code3 = 3;
    private  int request_codeFile1 = 4;
    private  int request_codeFile2 = 5;
    private  int request_codeFile3 = 6;
    FirebaseDatabase database ;
    DatabaseReference myRef ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thong_tin_dia_danh);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        moTaChiTiet = new MoTaChiTiet_class();
        bl = getIntent().getExtras();
        AnhXa();

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupMenu1();

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupMenu2();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupMenu3();
            }
        });
       // DatabaseReference myRef;
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(txtMoTa.getText().length()==0 || txtTenDiaDanh.getText().length()==0)
                    {
                        Toast.makeText(AddThongTinDiaDanh.this, "Thông tin chưa đầy đủ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        moTaChiTiet.setMoTa(txtMoTa.getText().toString());
                        moTaChiTiet.setTenDiaDanh(txtTenDiaDanh.getText().toString());
                        myRef.child(bl.getString("TenMien")).child(bl.getString("TenTinh")).push().setValue(moTaChiTiet);
                        Toast.makeText(AddThongTinDiaDanh.this, "Add Succces",Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(AddThongTinDiaDanh.this, listthongtin.class);
                        it.putExtras(bl);
                        finish();
                        startActivity(it);

                    }

                }catch (Exception e)
                {
                    Toast.makeText(AddThongTinDiaDanh.this, "Add Fail, Try Again!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        myRef.child(bl.getString("TenMien")).child(bl.getString("TenTinh")).addValueEventListener(new ValueEventListener(){
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot){
//                /* This method is called once with the initial value and again whenever data at this location is updated.*/
//                long value=dataSnapshot.getChildrenCount();
//                Log.d("So luong ","no of children: "+value);
//
//                GenericTypeIndicator<List<MoTaChiTiet_class>> genericTypeIndicator =new GenericTypeIndicator<List<MoTaChiTiet_class>>(){};
//
//                List<MoTaChiTiet_class> taskDesList=dataSnapshot.getValue(genericTypeIndicator);
//
//                for(int i=0;i<taskDesList.size();i++){
//                    Toast.makeText(AddThongTinDiaDanh.this,"TaskTitle = "+taskDesList.get(i).getTenDiaDanh(),Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error){
//                // Failed to read value
//                Log.w("Erooo ","Failed to read value.",error.toException());
//            }
//        });
        // Demo

    }

    void AnhXa()
    {

        btnHuy = (Button)findViewById(R.id.btnHuy);
        btnLuu = (Button)findViewById(R.id.btnLuu);
        txtMoTa = (EditText)findViewById(R.id.txtMoTa);
        txtTenDiaDanh = (EditText)findViewById(R.id.txtTenDiaDanh);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3= (ImageView)findViewById(R.id.img3);

    }

    //ham load anh len storge
     void LoadImg(ImageView img)
    {

            String tenDiaDanh = txtTenDiaDanh.getText().toString().trim();
            final StorageReference mountainsRef = storageRef.child(bl.getString("TenTinh")+"_"+tenDiaDanh+System.currentTimeMillis()+".png");
            img.setDrawingCacheEnabled(true);
            img.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(AddThongTinDiaDanh.this,"Tạo không thành công", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();
                   // Log.d("tao so may roi day", downloadUrl.toString());
                    moTaChiTiet.arrHinh.add(downloadUrl.toString());
                }
            });
    }

    void ShowPopupMenu1()
    {
        PopupMenu popup = new PopupMenu(AddThongTinDiaDanh.this,img1);
        popup.getMenuInflater().inflate(R.menu.pupmenu_chon_anh,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               if(item.getItemId()==R.id.itemTakeAPicture)
               {
                   Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   startActivityForResult(it,request_code1);

               }
               if (item.getItemId()==R.id.itemChooseFromFile)
               {
                   Intent it = new Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   it.setType("image/*");
                   startActivityForResult(it.createChooser(it,"Select file"), request_codeFile1);
               }
               return  false;
            }
        });

        popup.show();
    }

    void ShowPopupMenu2()
    {
        PopupMenu popup = new PopupMenu(AddThongTinDiaDanh.this,img2);
        popup.getMenuInflater().inflate(R.menu.pupmenu_chon_anh,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.itemTakeAPicture)
                {
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it,request_code2);
                }
                if (item.getItemId()==R.id.itemChooseFromFile)
                {

                    Intent it = new Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    it.setType("image/*");
                    startActivityForResult(it.createChooser(it,"Select file"), request_codeFile2);

                }
                return  false;
            }
        });

        popup.show();
    }

    void ShowPopupMenu3()
    {
        PopupMenu popup = new PopupMenu(AddThongTinDiaDanh.this,img3);
        popup.getMenuInflater().inflate(R.menu.pupmenu_chon_anh,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.itemTakeAPicture)
                {
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it,request_code3);
                }
                if (item.getItemId()==R.id.itemChooseFromFile)
                {
                    Intent it = new Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    it.setType("image/*");
                    startActivityForResult(it.createChooser(it,"Select file"), request_codeFile3);
                }
                return  false;
            }

        });

        popup.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bitmap;
        if(requestCode==request_code1&& resultCode==RESULT_OK&&data!=null)
        {
            bitmap = (Bitmap)data.getExtras().get("data");
            img1.setImageBitmap(bitmap);
            LoadImg(img1);
        }
        if(requestCode==request_code2&& resultCode==RESULT_OK&&data!=null)
        {
            bitmap = (Bitmap)data.getExtras().get("data");
            img2.setImageBitmap(bitmap);
            LoadImg(img2);
        }
        if(requestCode==request_code3&& resultCode==RESULT_OK&&data!=null)
        {
            bitmap = (Bitmap)data.getExtras().get("data");
            img3.setImageBitmap(bitmap);
            LoadImg(img3);

        }
        if(requestCode==request_codeFile1 && resultCode==RESULT_OK &&data!=null)
        {
            Uri uri = data.getData();
            img1.setImageURI(uri);
            LoadImg(img1);

        }
        if(requestCode==request_codeFile2 && resultCode==RESULT_OK &&data!=null)
        {
            Uri uri = data.getData();
            img2.setImageURI(uri);
            LoadImg(img2);


        }
        if(requestCode==request_codeFile3 && resultCode==RESULT_OK &&data!=null)
        {
            Uri uri = data.getData();
            img3.setImageURI(uri);
            LoadImg(img3);


        }
        super.onActivityResult(requestCode, resultCode, data);

    }


}

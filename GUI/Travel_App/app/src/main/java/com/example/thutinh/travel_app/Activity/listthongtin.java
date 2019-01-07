package com.example.thutinh.travel_app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.listmota_adapter;
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class listthongtin extends AppCompatActivity {

    private RecyclerView rvThongTin;
    private List<MoTaChiTiet_class> arrmota;
    private listmota_adapter adapter;
    private Bundle bl;
    private FirebaseAuth mAuth;
    private  Bundle blSend;
    private  FirebaseDatabase database;
    private DatabaseReference myRef;
    public String tenMien,tenTinh;
    private  String ga;
    //FirebaseDatabase.getInstance().getReference().child("ABC").push().setValue("XYZ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ket noi vơi firebase
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        arrmota = new ArrayList<>();
        setContentView(R.layout.activity_listthongtin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bl = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        tenMien= bl.getString("TenMien");
        tenTinh = bl.getString("TenTinh");
        blSend = new Bundle();
        rvThongTin = (RecyclerView) findViewById(R.id.rvthongtin);
        getSupportActionBar().setTitle(tenTinh+">List địa điểm");
        getData();

            adapter = new listmota_adapter(arrmota,listthongtin.this, tenMien, tenTinh);
            Log.d("111", arrmota.size()+"");
            rvThongTin.setAdapter(adapter);
            //su kien click vao item cua lítView

            rvThongTin.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rvThongTin.setLayoutManager(layoutManager);






//       rvThongTin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try {
//
//
//                MoTaChiTiet_class item = arrmota.get(position);
//                // Toast.makeText(listthongtin.this, item.getTenDiaDanh()+item.getMoTa()+item.arrHinh.size(), Toast.LENGTH_LONG).show();blSend.putSerializable("Item", item);
//                Intent it = new Intent(listthongtin.this, ThongTinDichVuTab.class);
//                blSend.putSerializable("Item", item);
//                blSend.putString("TenMien", tenMien);
//                blSend.putString("TenTinh", tenTinh);
//                it.putExtras(blSend);
//                startActivity(it);
//                }
//                catch (Exception a)
//                {
//                    Log.d("loi123",a.toString());
//                }
//            }
//
//        });
    }


    void getData() {
        try {
                myRef.child("ThongTin").child(tenMien).child(tenTinh).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            MoTaChiTiet_class test = dataSnapshot.getValue(MoTaChiTiet_class.class);

                            test.setKey(dataSnapshot.getKey());
                            // Toast.makeText(listthongtin.this, test.arrHinh.get(0).toString(),Toast.LENGTH_SHORT).show();
                            // Toast.makeText(listthongtin.this, test.listCmt.get(0).getTimeCmt(), Toast.LENGTH_SHORT).show();
                            arrmota.add(test);
                            adapter.arr.add(test);
                            adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            }catch(Exception ex)
            {
                Toast.makeText(listthongtin.this, "Lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                Log.d("Hix", ex.toString());
            }
        }


    void  ThongBao()
    {

            AlertDialog.Builder dialog = new AlertDialog.Builder(listthongtin.this);
            dialog.setTitle("Thông báo");
            dialog.setMessage("Không có nội dung để hiển thị. Bạn có thể thêm vào những địa danh du lịch để tạo nên những điều thú vị");
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = dialog.create();
            alertDialog.show();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.menu_list_thong_tin,menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        android.support.v7.widget.SearchView searchView =(android.support.v7.widget.SearchView) menu.findItem(R.id.seachTinh).getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               adapter.filter(s.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               adapter.filter(s.trim());
                return false;
            }
        });
       return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent t = new Intent(getApplicationContext(), listTenTinh.class);
                Bundle bundle = new Bundle();
                bundle.putString("TenMien", tenMien);
                t.putExtra("DuLieu",bundle);
            startActivity(t);
            return true;
            case R.id.create:
                Intent it = new Intent(getApplicationContext(),AddThongTinDiaDanh.class);
                it.putExtras(bl);
                startActivity(it);
                return  true;
            case R.id.dichvu:
                Intent it1 = new Intent(getApplicationContext(),ListDichVu.class);
                it1.putExtras(bl);
                startActivity(it1);
                return  true;
            case  R.id.MenuHome:
                Intent it2 = new Intent(listthongtin.this, MainActivity.class);
                startActivity(it2);
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }


}

package com.example.thutinh.travel_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.HomeAdapter;
import com.example.thutinh.travel_app.DTO.Home;
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListHome extends AppCompatActivity {

Bundle blReceive;
String tenMien;
String tenTinh;
private List<Home> listHome;
private HomeAdapter homeAdapter;
private RecyclerView rvHome;
private FirebaseDatabase database;
private DatabaseReference myRef;
Bundle bSend;
String LoaiDichVu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_home);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
     blReceive = getIntent().getExtras();
     LoaiDichVu = blReceive.getString("LoaiDichVu");
     tenMien = blReceive.getString("TenMien");
     tenTinh = blReceive.getString("TenTinh");
     bSend = new Bundle();
     bSend = blReceive;
        AnhXa();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listHome = new ArrayList<>();
        getData();
        getSupportActionBar().setTitle(tenTinh+">List nhà ở");
        homeAdapter = new HomeAdapter(listHome, this,tenTinh, tenMien ,LoaiDichVu);
        rvHome.setHasFixedSize(true);
        rvHome.setLayoutManager(new GridLayoutManager(this, 2));
        rvHome.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.create_back,menu);
        android.support.v7.widget.SearchView searchView =(android.support.v7.widget.SearchView) menu.findItem(R.id.seachTinh).getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
              homeAdapter.filter(s.trim());
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
               homeAdapter.filter(s.trim());
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
                Intent t = new Intent(getApplicationContext(), ListDichVu.class);
                t.putExtras(bSend);
                startActivity(t);
                return true;
            case R.id.cb_create:
                Intent it = new Intent(getApplicationContext(),AddHome.class);
              it.putExtras(bSend);
                startActivity(it);
                return  true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
    void  AnhXa()
    {
        rvHome = (RecyclerView)findViewById(R.id.rvHome);
    }

    void getData()
    {
        try {

            myRef.child("DichVu").child(LoaiDichVu).child(tenMien).child(tenTinh).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   Home test = dataSnapshot.getValue(Home.class);
                    test.setKey(dataSnapshot.getKey());
                    // Toast.makeText(listthongtin.this, test.arrHinh.get(0).toString(),Toast.LENGTH_SHORT).show();
                    // Toast.makeText(listthongtin.this, test.listCmt.get(0).getTimeCmt(), Toast.LENGTH_SHORT).show();
                   listHome.add(test);
                   homeAdapter.arr.add(test);
                    homeAdapter.notifyDataSetChanged();
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

        }catch (Exception ex)
        {
            Toast.makeText(ListHome.this, "Lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
          //  Log.d("Hix", ex.toString() + LoaiDichVu);

        }
    }
}

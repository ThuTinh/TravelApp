package com.example.thutinh.travel_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thutinh.travel_app.Adapter.listmota_adapter;
import com.example.thutinh.travel_app.DTO.MoTaChiTiet_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listthongtin extends AppCompatActivity {

    private ListView list;
    private List<MoTaChiTiet_class> arrmota;
    private listmota_adapter adapter;
    private Bundle bl;
    private FirebaseAuth mAuth;
    private  Bundle blSend;
    private  FirebaseDatabase database;
    private DatabaseReference myRef;
    private String tenMien,tenTinh;
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
        bl = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        tenMien= bl.getString("TenMien");
        tenTinh = bl.getString("TenTinh");
        blSend = new Bundle();
        list = (ListView) findViewById(R.id.listthongtin);
        adapter = new listmota_adapter(listthongtin.this, R.layout.motachitiet, arrmota);
        list.setAdapter(adapter);
        getData();

        //su kien click vao item cua lítView
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MoTaChiTiet_class item = arrmota.get(position);
         // Toast.makeText(listthongtin.this, item.getTenDiaDanh()+item.getMoTa()+item.arrHinh.size(), Toast.LENGTH_LONG).show();blSend.putSerializable("Item", item);
            Intent it = new Intent(listthongtin.this, ThongTinDichVuTab.class);
            blSend.putSerializable("Item",item);
            it.putExtras(blSend);
            startActivity(it);
            }
        });
    }

    void getData()
    {
        try {



            myRef.child(tenMien).child(tenTinh).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    MoTaChiTiet_class test = dataSnapshot.getValue(MoTaChiTiet_class.class);
                    // Toast.makeText(listthongtin.this, test.arrHinh.get(0).toString(),Toast.LENGTH_SHORT).show();
                    arrmota.add(test);
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
        }catch (Exception ex)
        {
            Toast.makeText(listthongtin.this, "Error ", Toast.LENGTH_SHORT).show();
            Log.d("Hix", ex.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_back,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.cb_back){
            finish();
        }

        if(item.getItemId()==R.id.cb_create)
        {
            Intent it = new Intent(getApplicationContext(),AddThongTinDiaDanh.class);
            it.putExtras(bl);

            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }




}

package com.example.thutinh.travel_app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

private Button btnMienBac;
private Button btnMienTrung;
private Button btnMienNam;
private FirebaseAuth mAuth;
private ImageView arr1;
private ImageView arr2;
private ImageView arr3;
private ViewFlipper flip1;
private ViewFlipper flip2;
private ViewFlipper flip3;
//private Animation animIn;
//private Animation animOut;
private TextView lbName;
private  TextView lbEmail;
private  ImageView imgPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);


        // CHuyển màn hình
        // ánh xạ
        //Xet animation cho ViewFliper
      //  animIn = AnimationUtils.loadAnimation(this, R.anim.anim_in);
      //  animOut = AnimationUtils.loadAnimation(this, R.anim.anim_out);
        //  flip2.setInAnimation(animIn);
        //flip2.setOutAnimation(animOut);
        //flip3.setInAnimation(animIn);
        //flip3.setOutAnimation(animOut);
        flip1 = (ViewFlipper)findViewById(R.id.flip1);
      //  flip1.setInAnimation(animIn);
     //   flip1.setOutAnimation(animOut);
        flip1.setFlipInterval(4000);
        flip1.setAutoStart(true);
        flip2 = (ViewFlipper)findViewById(R.id.flip2);
        flip2.setFlipInterval(2000);
        flip2.setAutoStart(true);
        flip3 = (ViewFlipper)findViewById(R.id.flip3);
        flip3.setFlipInterval(3000);
        flip3.setAutoStart(true);

        //Tao Animation cho button
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_btn_main);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.anim_btn_main1);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.anim_btn_main2);
        // tao animation cho anh ViewFliper



       // arr1 = (ImageView)findViewById(R.id.arr1);
       // arr1.startAnimation(anim);
      //  arr2 =(ImageView)findViewById(R.id.arr2);
      //  arr2.startAnimation(anim1);
       // arr3 = (ImageView)findViewById(R.id.arr3);
      //  arr3.startAnimation(anim2);
        btnMienBac = (Button)findViewById(R.id.btnMienBac);
        //xet manination
        btnMienTrung = (Button)findViewById(R.id.btnMienTrung);
       // Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.anim_btn_main);
        btnMienNam = (Button)findViewById(R.id.btnMienNam);
     //   Animation anim3 = AnimationUtils.loadAnimation(this, R.anim.anim_btn_main);
        btnMienBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), listTenTinh.class);
               Bundle bundle = new Bundle();
               bundle.putString("TenMien", "MienBac");
           // it.putExtras(bundle);
               // it.putExtra("TenMien", "MienBac");
                it.putExtra("DuLieu",bundle);
                startActivity(it);
            }
        });

        //kiểm tra người dùng có đăng nhập chưa


        btnMienTrung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), listTenTinh.class);
              Bundle bundle = new Bundle();
              bundle.putString("TenMien", "MienTrung");
                it.putExtra("DuLieu",bundle);
            // it.putExtras(bundle);
             startActivity(it);
            }
        });

        btnMienNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), listTenTinh.class);
                Bundle bundle = new Bundle();
                // bundle.putInt("TenMien",1);
                bundle.putString("TenMien", "MienNam");
                it.putExtra("DuLieu",bundle);
                startActivity(it);
            }
        });


       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                      .setAction("Action", null).show();
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


         final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         View v = navigationView.getHeaderView(R.id.nav_view);

        //lbName.setText("df");
      //  lbEmail.setText("sdg");
        if(mAuth.getCurrentUser()!=null)
        {
            View headerLayout =  navigationView.getHeaderView(0);
            lbEmail = headerLayout.findViewById(R.id.lbProfileEmail);
            imgPhoto = headerLayout.findViewById(R.id.imgProfile);
            lbName = headerLayout.findViewById(R.id.lbProfileName);
            lbName.setText(mAuth.getCurrentUser().getDisplayName());
            lbEmail.setText(mAuth.getCurrentUser().getEmail());

           // imgPhoto.setImageURI(mAuth.getCurrentUser().getPhotoUrl());
           // Log.d("hinh123", mAuth.getCurrentUser().getPhotoUrl().toString());
            //  Glide.with(MainActivity.this).load(mAuth.getCurrentUser().getPhotoUrl()).into(imgPhoto);
        }


// panel won't be null

        navigationView.setNavigationItemSelectedListener(this);

        };




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   // @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
       // return true;
   //}
    private  void KiemTra(NavigationView navigationView)
    {

       // Toast.makeText(MainActivity.this, "co chạy vo khong?", Toast.LENGTH_SHORT).show();
        Menu menu = navigationView.getMenu();
        if(mAuth.getCurrentUser()==null)
        {
            menu.getItem(R.id.nav_LogOut).setVisible(true);
            menu.getItem(R.id.nav_signIn).setVisible(false);

        }
        else
        {
            menu.getItem(R.id.nav_signIn).setVisible(true);
            menu.getItem(R.id.nav_LogOut).setVisible(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Intent it;

        if (id == R.id.nav_signIn) {
            if(mAuth.getCurrentUser()==null)
            {
                // Handle the camera action
                it = new Intent(getApplicationContext(),dangnhap.class);
                startActivity(it);
            }
            else
            {
                Toast.makeText(MainActivity.this, "Bạn đã đăng nhập", Toast.LENGTH_SHORT).show();
            }

//
//        } else if (id == R.id.nav_signUp) {
//            it = new Intent(getApplicationContext(),dangki.class);
//            startActivity(it);
//        } else if (id == R.id.nav_infomation) {
//           // it = new Intent(getApplicationContext(),listthongtin.class);
//           // startActivity(it);
//        } else if (id == R.id.nav_transaction) {
          //  it = new Intent(getApplicationContext(),listTenTinh.class);
          //  startActivity(it);

        } else if (id == R.id.nav_share) {



        } else if (id == R.id.nav_send) {

        }else if(id == R.id.nav_LogOut){
          try {
              if(mAuth.getCurrentUser()!=null)
              {
                  AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                  dialog.setTitle("Thông báo");
                  dialog.setMessage("Bạn có muốn đăng xuất không?");
                  dialog.setCancelable(false);
                  dialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  dialog.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          mAuth.signOut();

                          Toast.makeText(MainActivity.this, "Đăng xuất thành công.", Toast.LENGTH_SHORT).show();
                      }
                  });
                  AlertDialog alertDialog = dialog.create();
                  alertDialog.show();
              }
              else
              {
                  Toast.makeText(MainActivity.this, "Bạn đã đăng xuất, Tác vụ không được thực hiện",Toast.LENGTH_SHORT).show();
              }



          }catch (Exception e)
          {
              Toast.makeText(MainActivity.this, "Đăng xuất không thành công", Toast.LENGTH_SHORT).show();
          }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

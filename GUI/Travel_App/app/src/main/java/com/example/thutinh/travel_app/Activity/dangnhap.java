package com.example.thutinh.travel_app.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thutinh.travel_app.MainActivity;
import com.example.thutinh.travel_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class dangnhap extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtEmail;
    private  EditText txtPassword;
    private Button btnDangNhap;
    private TextView lbQuenMatKhau;
    private TextView lbCreateAcount;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_dangnhap);
        AnhXa();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangNhap();
            }
        });
        lbQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(dangnhap.this, ResetPassword.class);
                startActivity(it);
                
            }
        });
        lbCreateAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),dangki.class);
                startActivity(it);
            }
        });
    }

    //Anh xa
    void AnhXa()
    {
        txtEmail = (EditText) findViewById(R.id.txtLogInEmail);
        txtPassword = (EditText)findViewById(R.id.txtLogInPassword);
        btnDangNhap = (Button)findViewById(R.id.btnLogin);
        lbCreateAcount  = (TextView)findViewById(R.id.lbCreateAcount);
        lbQuenMatKhau  = (TextView)findViewById(R.id.lbQuenMatKhau);
    }

    void DangNhap()
    {
        try {
            String email;
            String password;
            email = txtEmail.getText().toString().trim();
            password = txtPassword.getText().toString().trim();
            if (email.length() == 0 || password.length() == 0) {
                Toast.makeText(dangnhap.this, "Thông tin nhập chưa đầy đủ!", Toast.LENGTH_SHORT).show();

            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("Thong báo", "signInWithEmail:onComplete:" + task.isSuccessful());


                                // Nếu không đăng nhập được, thì cho hiện Toast thông báo.
                                if (!task.isSuccessful()) {
                                    Log.w("loi", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(dangnhap.this, MainActivity.class);
                                    startActivity(it);
                                }

                                // Nếu đăng nhập được, thì thực hiện các thao tác khác.
                                // doSomething();
                            }
                        });
            }
        }
        catch(Exception e)
        {
            Toast.makeText(dangnhap.this, "Đăng nhập lỗi!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}

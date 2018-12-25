package com.example.thutinh.travel_app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private Button btnRestPassword;
    private  Button btnBack;
    private TextView txtRestEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_reset_password);
        AnhXa();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRestPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()==null)
                {
                    Toast.makeText(ResetPassword.this, "Bạn chưa đăng nhập",Toast.LENGTH_SHORT).show();

                }
                else {
                    String txtEmail = txtRestEmail.getText().toString();
                    if(TextUtils.isEmpty(txtEmail))
                    {
                        Toast.makeText(ResetPassword.this, "Email bạn nhập chưa đủ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mAuth.sendPasswordResetEmail(txtEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ResetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(ResetPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();

                                        }

                                      //  progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }
                }
            }
        });
    }
    private void AnhXa()
    {
        btnBack = (Button)findViewById(R.id.btnBack);
        btnRestPassword = (Button)findViewById(R.id.btnResetPassword);
        txtRestEmail = (TextView)findViewById(R.id.txtRestEmail);
    }
}


package com.example.thutinh.travel_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class dangnhap extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtEmail;
    private  EditText txtPassword;
    private Button btnDangNhap;
    private CheckBox chkRemember;

    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_dangnhap);
        AnhXa();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangNhap();
            }
        });


    }


    //Anh xa
    void AnhXa()
    {
        txtEmail = (EditText) findViewById(R.id.txtLogInEmail);
        txtPassword = (EditText)findViewById(R.id.txtLogInPassword);
        btnDangNhap = (Button)findViewById(R.id.btnLogin);
        chkRemember = (CheckBox)findViewById(R.id.chkRemember);

    }

    void DangNhap()
    {
        String email;
        String password;
        email = txtEmail.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        if(txtPassword.equals(null)|| txtEmail.equals(null))
        {

        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Thong báo", "signInWithEmail:onComplete:" + task.isSuccessful());


                            // Nếu không đăng nhập được, thì cho hiện Toast thông báo.
                            if (!task.isSuccessful()) {
                                Log.w("loi", "signInWithEmail:failed", task.getException());
                                Toast.makeText(getApplicationContext(), "Failed to sign in", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Susscess to sign in", Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(dangnhap.this, MainActivity.class);
                                startActivity(it);
                            }

                            // Nếu đăng nhập được, thì thực hiện các thao tác khác.
                            // doSomething();
                        }
                    });
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.back)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}

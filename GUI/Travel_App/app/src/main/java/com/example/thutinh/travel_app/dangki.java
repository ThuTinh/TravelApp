package com.example.thutinh.travel_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dangki extends AppCompatActivity {

    TextView txtEmail;
    TextView txtPassword;
    Button btnDangKi;
    private FirebaseAuth mAuth;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        AnhXa();
         mAuth = FirebaseAuth.getInstance();
         btnDangKi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DangKi();
             }
         });


    }


    //anhXa
  private   void AnhXa()
    {
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtPassword=(TextView)findViewById(R.id.txtPassword);
        btnDangKi = (Button)findViewById(R.id.btnSignup);
    }

   private void DangKi()
   {
       try {
           String email = txtEmail.getText().toString().trim();
           String password = txtPassword.getText().toString().trim();
           if (email.equals(null) || password.equals(null)) {
               Toast.makeText(dangki.this, "Thông tin chưa đầy đủ", Toast.LENGTH_SHORT).show();

           } else {
               mAuth.createUserWithEmailAndPassword(email, password)
                       .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   // Sign in success, update UI with the signed-in user's information
                                   Toast.makeText(dangki.this, "Tạo thành công.",
                                           Toast.LENGTH_SHORT).show();
                                   Intent it = new Intent(dangki.this, MainActivity.class);
                                   startActivity(it);
                                   //   FirebaseUser user = mAuth.getCurrentUser();
                                   //  updateUI(user);
                               } else {
                                   Toast.makeText(dangki.this, "Tạo thất bại",
                                           Toast.LENGTH_SHORT).show();
                                   Log.w("Loi gi day", "createUserWithEmail:failure", task.getException());
                                   // updateUI(null);
                               }
                               // ...
                           }
                       });
           }
       }catch (Exception e)
       {
           Toast.makeText(dangki.this, "Create Error", Toast.LENGTH_SHORT).show();
       }
   }

    //tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.back)
            this.finish();
        return super.onOptionsItemSelected(item);
    }
}

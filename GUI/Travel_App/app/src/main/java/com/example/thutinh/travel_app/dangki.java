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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class dangki extends AppCompatActivity {

    private TextView txtEmail;
    private TextView txtPassword;
    private TextView txtCormPassword;
    private Button btnDangKi;
    private FirebaseAuth mAuth;
    private EditText txtName;
    private  String name;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        AnhXa();
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        txtCormPassword = (TextView)findViewById(R.id.txtConfirmPassword);
        txtName = (EditText)findViewById(R.id.txtUserName);
    }

   private void DangKi()
   {
       try {

           String email = txtEmail.getText().toString().trim();
           String password = txtPassword.getText().toString().trim();
           String comfirmPassword = txtCormPassword.getText().toString().trim();
           name = txtName.getText().toString().trim();

           if (email.length()==0 || password.length()==0 || password.equals(comfirmPassword)==false||name.length()==0) {
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

                                   FirebaseUser user = mAuth.getCurrentUser();

                                   UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                           .setDisplayName(name).build();

                                   user.updateProfile(profileUpdates);

                                   Intent it = new Intent(dangki.this, MainActivity.class);
                                   startActivity(it);


                                   //  updateUI(user);
                               } else {
                                   Toast.makeText(dangki.this, "Tạo thất bại",
                                           Toast.LENGTH_SHORT).show();
                                   Log.w("Loi gi day", "createUserWithEmail:failure", task.getException());
                                   // updateUI(null);
                               }

                           }
                       });
           }
       }catch (Exception e)
       {
           Toast.makeText(dangki.this, "Lỗi, Thử lại sau", Toast.LENGTH_SHORT).show();
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

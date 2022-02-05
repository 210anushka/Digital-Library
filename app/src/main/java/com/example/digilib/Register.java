 package com.example.digilib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

 public class Register extends AppCompatActivity {

     public static final String  TAG = "TAG";
     EditText mFullName,muserName,mPassword,mPhone,mcpass;
    Button mRegisterBtn;
    TextView mloginbutton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        muserName = findViewById(R.id.username);
        mPhone = findViewById(R.id.ph);
        mPassword = findViewById(R.id.pass);
        mcpass = findViewById(R.id.cpass);
        mRegisterBtn = findViewById(R.id.signup);
        mloginbutton = findViewById(R.id.loginHere);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);


        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String uname = muserName.getText().toString().trim();
                String name = mFullName.getText().toString();
                String  password = mPassword.getText().toString().trim();
                String cpassword = mcpass.getText().toString().trim();
                String phone = mPhone.getText().toString();
                if(TextUtils.isEmpty(name)) {
                    mFullName.setError("Name is Required.");
                    return;
                }

                if(TextUtils.isEmpty(uname)){
                    muserName.setError("Username is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }
                if(!cpassword.equals(password)){
                    mcpass.setError("Password doesn't match.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(uname,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            userid = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userid);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",name);
                            user.put("email",uname);
                            user.put("phone",phone);
                            user.put("password",password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userid);

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error!!!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            }

    });
        mloginbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });

    }
}
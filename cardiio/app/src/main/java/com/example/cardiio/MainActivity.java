package com.example.cardiio;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    //private TextView Info ;
    private Button Login;
   // private int counter =5 ;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPasword;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);




        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
       // Info = (TextView) findViewById(R.id.tvinfo);
        Login = (Button)findViewById(R.id.btnLogin);
        TextView userRegistration = (TextView) findViewById(R.id.tvRegister);
        forgotPasword = (TextView)findViewById(R.id.tvForgotPassword);

        //Info.setText("No of attempts remaining : 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {

            if ((user.getEmail()).equals("c")) {
                finish();
                startActivity(new Intent(MainActivity.this, AdminHome.class));
            } else {
                finish();
                startActivity(new Intent(MainActivity.this, SecondActivity.class));

            }
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredential();
               validate(Name.getText() .toString(),Password.getText().toString());

            }

            private void checkCredential() {
                password = Password.getText().toString();
                email = Name.getText().toString();

                if (email.isEmpty() || !email.contains("@")){
                    showError(Name,"Email is not valid");
                }
                else if(password.isEmpty() || password.length()<7){
                    showError(Password,"Password Must be 7 character");
                }
                else if(password.isEmpty() || email.isEmpty() ) {
                    Toast.makeText(MainActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();

                }
                else{
                }
            }

            private void showError(EditText user, String s) {
                user.setError(s);
                user.requestFocus();
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        forgotPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });
    }

    private void validate (String userName ,String userPassword){

        progressDialog.setMessage("Welcome to Cardiio");
        progressDialog.show();



        if(userName.equals("mightblack2@gmail.com")) {

            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        checkEmailVerification();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(MainActivity.this, AdminHome.class));

                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }else{
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        checkEmailVerification();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));

                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            //startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }else{
            Toast.makeText(this,"Verity Your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
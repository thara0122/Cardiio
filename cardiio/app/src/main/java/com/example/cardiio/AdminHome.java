package com.example.cardiio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.teal_200))));


        firebaseAuth = FirebaseAuth.getInstance();



    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(AdminHome.this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmenu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu:
                startActivity(new Intent(AdminHome.this,ProfileActivity.class));
                break;

            case R.id.ListOfUsersMenu:
                startActivity(new Intent(AdminHome.this,UserList.class));
                break;

            case R.id.AboutUsMenu:
                startActivity(new Intent(AdminHome.this,AboutUs.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
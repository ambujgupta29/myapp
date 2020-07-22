package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class bottomnavigation extends AppCompatActivity {
    String  emailget,email_get_login;
    String emailorig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigation);
        BottomNavigationView bottomnav = findViewById(R.id.bottom_nav);
        bottomnav.setOnNavigationItemSelectedListener(navlistener);
        getSupportFragmentManager().beginTransaction().replace(R.id.framgent_container, new Homefragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Homefragment();
                            break;
                        case R.id.nav_add:
                            selectedFragment = new Addfragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new Profilefragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.framgent_container, selectedFragment).commit();
                    return true;
                }
            };
    public String getmydata(){
         emailget=getIntent().getStringExtra("email_pass1");
         email_get_login=getIntent().getStringExtra("email_pass2");
         if(emailget==null) {
             return email_get_login;

         }
         else{
             return emailget;
         }
    }
}

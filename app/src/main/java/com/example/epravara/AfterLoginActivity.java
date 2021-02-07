package com.example.epravara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.epravara.Fragments.DashboardFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AfterLoginActivity extends AppCompatActivity
                            implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private LinearLayout gotowebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.item_dashboard);
            getSupportActionBar().setTitle("Dashboard");
        }

        gotowebsite = findViewById(R.id.goToWebsite);
        gotowebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gaurav241.rf.gd/?i=1"));
                startActivity(Getintent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AfterLoginActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.drawable.logo);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent a = new Intent(Intent.ACTION_MAIN);
                            a.addCategory(Intent.CATEGORY_HOME);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(a);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.item_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
                getSupportActionBar().setTitle("Dashboard");
                break;
            case R.id.item_fillinfo:
                startActivity(new Intent(AfterLoginActivity.this, AddInformationActivity.class));
                break;
            case R.id.item_profile:
                startActivity(new Intent(AfterLoginActivity.this, ProfileActivity.class));
                break;
            case R.id.item_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AfterLoginActivity.this, LoginActivity.class));
                break;
            case R.id.item_help:
                startActivity(new Intent(AfterLoginActivity.this, HelpActivity.class));
                break;
            case R.id.item_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Get the most preferable medical data storage application";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ePravaraCare");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.mot3afy.mot3afy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mot3afy.mot3afy.PrefManager;
import com.mot3afy.mot3afy.R;

import static com.mot3afy.mot3afy.Activities.Activity_Welcome.prefManager;

public class Activity_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView user_name;
    private TextView user_email;
    private ImageView user_image;
   // private PrefManager prefManager;
    String User_email, User_id, User_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefManager = new PrefManager(this);
        User_email = prefManager.getUser_email();
        User_id = prefManager.getUser_id();
        User_name = prefManager.getUser_name();
        Log.d("data",User_id+User_name+User_email);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        user_name = navigationView.getHeaderView(0).findViewById(R.id.user_name_id);
        user_email = navigationView.getHeaderView(0).findViewById(R.id.user_email_id);
        user_image = navigationView.getHeaderView(0).findViewById(R.id.user_image_id);

        user_name.setText(User_name);
        user_email.setText(User_email);
        user_image.setImageResource(R.drawable.muscle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {
            startActivity(new Intent(Activity_Main.this, Activity_slideshow.class));

        } else if (id == R.id.nav_fav) {
            startActivity(new Intent(Activity_Main.this, Activity_Favourite.class));

        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.whatsapp");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_EMAIL, "Hind_Ahmed31@hotmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

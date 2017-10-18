package com.mot3afy.mot3afy.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mot3afy.mot3afy.Post;
import com.mot3afy.mot3afy.Post_Adapter;
import com.mot3afy.mot3afy.PrefManager;
import com.mot3afy.mot3afy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mot3afy.mot3afy.Activities.Activity_Welcome.prefManager;

public class Activity_Main extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView user_name;
    private TextView user_email;
    private ImageView user_image;
   // private PrefManager prefManager;
    String User_email, User_id, User_name;
    DatabaseReference mFirebaseDatabase_Posts ;
    private ListView listView ;
    private List<Post> post_s = new ArrayList<>();
    private Post_Adapter post_adapter ;


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




        listView = (ListView) findViewById(R.id.List_of_all_posts);

        mFirebaseDatabase_Posts = FirebaseDatabase.getInstance().getReference("Posts");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Write_new_post();
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


        ///
        mFirebaseDatabase_Posts.child("posts").addValueEventListener(new ValueEventListener() {
            long childrenCount;
            Object value;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post postss = null;
                post_s.clear();
//                post_adapter.notifyDataSetChanged();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                     postss = noteDataSnapshot.getValue(Post.class);
//                    childrenCount = noteDataSnapshot.getChildrenCount();
//                    value = noteDataSnapshot.getValue();
                    post_s.add(postss);
                }
                int size = post_s.size();
                update_posts();
                Toast.makeText(Activity_Main.this, childrenCount + "//" + value + "", Toast.LENGTH_LONG).show();
                Toast.makeText(Activity_Main.this, size + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void update_posts() {
         post_adapter = new Post_Adapter(this, post_s);
        listView.setAdapter(post_adapter);

    }


    private void Write_new_post() {

        // get prompts.xml view
        LayoutInflater add_design_Xml = LayoutInflater.from(this);
        View promptsView = add_design_Xml.inflate(R.layout.add_post_design, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        final EditText hashtag = (EditText) promptsView
                .findViewById(R.id.Hashtag);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Share your story..")
                .setIcon(R.drawable.muscle)
                .setPositiveButton("Post",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                String title = hashtag.getText().toString();
                                String body_of_post = userInput.getText().toString()+"#"+hashtag.getText().toString();
                                writeNewPost(User_id,User_name,title,body_of_post,0);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    private void writeNewPost(String userId, String username, String title, String body, int fav) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mFirebaseDatabase_Posts.push().getKey();
        Post post = new Post(userId, username, title, body,fav);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
        childUpdates.put("/user-Hashtag/" + title + "/" + key, postValues);

        mFirebaseDatabase_Posts.updateChildren(childUpdates);
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

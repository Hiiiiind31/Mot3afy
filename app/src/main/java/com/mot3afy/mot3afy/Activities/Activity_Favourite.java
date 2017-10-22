package com.mot3afy.mot3afy.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mot3afy.mot3afy.Adapters.Fav_post_Adapter;
import com.mot3afy.mot3afy.Activities.Models.Post;
import com.mot3afy.mot3afy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mot3afy.mot3afy.Activities.Activity_Welcome.prefManager;

public class Activity_Favourite extends AppCompatActivity {

    DatabaseReference mFirebaseDatabase_users;
    private ListView listView;
    private List<Post> post_s = new ArrayList<>();
    private Fav_post_Adapter fav_post_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__favourite);

        listView = (ListView) findViewById(R.id.List_of_fav_posts);
        mFirebaseDatabase_users = FirebaseDatabase.getInstance().getReference("Users").child(prefManager.getUser_id());

        mFirebaseDatabase_users.child("user-fav-posts").addValueEventListener(new ValueEventListener() {
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
                update_posts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void update_posts() {
        Collections.reverse(post_s);
        fav_post_adapter = new Fav_post_Adapter(this, post_s);
        listView.setAdapter(fav_post_adapter);

    }

}

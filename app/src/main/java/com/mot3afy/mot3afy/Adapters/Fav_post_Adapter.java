package com.mot3afy.mot3afy.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mot3afy.mot3afy.Activities.Models.Post;
import com.mot3afy.mot3afy.R;

import java.util.List;

import static com.mot3afy.mot3afy.Activities.Activity_Welcome.prefManager;

/**
 * Created by Hind on 10/20/2017.
 */

public class Fav_post_Adapter extends BaseAdapter {

    Context context;
    List<Post> posts;

    public Fav_post_Adapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Fav_post_Adapter.ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.all_posts_list, viewGroup, false);
            holder = new Fav_post_Adapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (Fav_post_Adapter.ViewHolder) view.getTag();
        }

        final Post item = getItem(i);
        holder.AuthorView.setText(item.getAuthor());
        holder.BodyView.setText(item.getBody());
        holder.post_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        holder.post_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_fav_posts(item.getUid());

            }
        });

        return view;
    }

    private void delete_fav_posts(String uid) {

        DatabaseReference mFirebaseDatabase_users;
        mFirebaseDatabase_users = FirebaseDatabase.getInstance().getReference("Users").child(prefManager.getUser_id());

        Query applesQuery = mFirebaseDatabase_users.child("user-fav-posts").orderByChild("uid").equalTo(uid);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Log.d("id",dataSnapshot.getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", "onCancelled", databaseError.toException());
            }
        });
    }

    class ViewHolder {

        TextView AuthorView, BodyView;
        ImageView post_fav;

        ViewHolder(View v) {
            AuthorView = (TextView) v.findViewById(R.id.Author_id);
            BodyView = (TextView) v.findViewById(R.id.body_id);
            post_fav = (ImageView) v.findViewById(R.id.post_fav_id);

        }
    }

}

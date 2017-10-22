package com.mot3afy.mot3afy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mot3afy.mot3afy.Models.Post;
import com.mot3afy.mot3afy.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mot3afy.mot3afy.Activities.Activity_Welcome.prefManager;

/**
 * Created by Hind on 10/18/2017.
 */

public class Post_Adapter extends BaseAdapter {


    Context context;
    List<Post> posts;

    public Post_Adapter(Context context, List<Post> posts) {
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
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.all_posts_list, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Post item = getItem(i);
        holder.AuthorView.setText(item.getAuthor());
        holder.BodyView.setText(item.getBody());
        final ViewHolder finalHolder = holder;
        holder.post_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.post_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                finalHolder.post_fav.setClickable(false);
                add_fav_posts(item.getUid(), item.getAuthor(), item.getTitle(), item.getBody(), 0);

            }
        });

        return view;
    }

    private void add_fav_posts(String uid, String author, String title, String body, int fav) {

        DatabaseReference mFirebaseDatabase_users;
        mFirebaseDatabase_users = FirebaseDatabase.getInstance().getReference("Users").child(prefManager.getUser_id());

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mFirebaseDatabase_users.push().getKey();
        Post post = new Post(uid, author, title, body, fav);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-fav-posts/"+ "/" + key, postValues);

        mFirebaseDatabase_users.updateChildren(childUpdates);
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

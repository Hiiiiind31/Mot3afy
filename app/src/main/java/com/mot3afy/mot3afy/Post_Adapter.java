package com.mot3afy.mot3afy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.mode;

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

        Post item = getItem(i);
        holder.AuthorView.setText(item.getAuthor());
        holder.BodyView.setText(item.getBody());
        final ViewHolder finalHolder = holder;
        holder.post_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finalHolder.post_fav.setImageResource(R.drawable.ic_favorite_black_24dp);

            }
        });

        return view;
    }

    class ViewHolder {

        TextView AuthorView, BodyView;
        ImageView post_fav ;

        ViewHolder(View v) {
            AuthorView = (TextView) v.findViewById(R.id.Author_id);
            BodyView = (TextView) v.findViewById(R.id.body_id);
            post_fav = (ImageView) v.findViewById(R.id.post_fav);

        }
    }

}

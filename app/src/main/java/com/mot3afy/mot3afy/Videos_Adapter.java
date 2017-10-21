package com.mot3afy.mot3afy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Hind on 10/21/2017.
 */

public class Videos_Adapter extends BaseAdapter implements YouTubePlayer.OnInitializedListener, Videos_interface   {

    Context context;
    String[] Video;
    String[] Video_title;
    String val ;

    public Videos_Adapter(Context context, String[] video, String[] video_title) {
        this.context = context;
        Video = video;
        Video_title = video_title;
    }

    @Override
    public int getCount() {
        return Video.length;
    }

    @Override
    public Object getItem(int position) {
        return Video[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Videos_Adapter.ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.videos_list_item, parent, false);
            holder = new Videos_Adapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (Videos_Adapter.ViewHolder) view.getTag();
        }
        holder.youTubeView.initialize(Config.YOUTUBE_API_KEY,this);
        val= Video[position];
        holder.TitleView.setText(Video_title[position]);
        return view ;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(val); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {

        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog((Activity) context, 1).show();
        } else {
            String error = String.format(String.valueOf((R.string.player_error)), errorReason.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        Videos_Adapter.ViewHolder holder = null;

        return holder.youTubeView ;
    }

    class ViewHolder {

        TextView TitleView ;
         YouTubePlayerView youTubeView;


        ViewHolder(View v) {
            TitleView = (TextView) v.findViewById(R.id.videos_title_id);
            youTubeView = (YouTubePlayerView) v.findViewById(R.id.youtube_view);


        }
    }

}
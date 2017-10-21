package com.mot3afy.mot3afy.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mot3afy.mot3afy.Config;
import com.mot3afy.mot3afy.R;
import com.mot3afy.mot3afy.Videos_Adapter;

public class Activity_slideshow extends  YouTubeBaseActivity {

    private static final int RECOVERY_REQUEST = 1;
    String videos[];
    YouTubePlayerView youTubeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_slideshow);

        String videos[] = { "rhcJNJbRJ6U", "M5QBH3wDrQY", "F8W5UgKHJrI", "tiAUMnjwqsc", "0_qifv1kkpI", "dWKSFfxeL9k","f6rSuJ2YheQ"};
        String videos_title[] = { "Dying to be me! Anita Moorjani at TEDxBayArea\n", "My survival story -- what I learned from having cancer | Martin Inderbitzin | TEDxZurich\n",
                "Hope - Preston’s Story about Drug Addiction Recovery\n", "Confession - Jessica’s Story about Food Addiction Recovery\n", "Personal Revelation - Lindsay’s Story about Heroin Addiction Recovery\n"
                , "Restitution and Reconciliation - John’s Story on Drug Addiction Recovery\n","Starving cancer away | Sophia Lunt | TEDxMSU\n"};

        ListView listView = (ListView) findViewById(R.id.videos_listview_id);

        Videos_Adapter adapter = new Videos_Adapter(this,videos,videos_title);
        listView.setAdapter(adapter);

    }
}

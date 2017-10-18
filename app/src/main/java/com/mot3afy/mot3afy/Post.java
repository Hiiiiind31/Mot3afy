package com.mot3afy.mot3afy;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hind on 10/18/2017.
 */

public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;
    public int starCount = 0;

    public Post(String uid, String author, String title, String body, int starCount) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.starCount = starCount;
    }



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        return result;
    }
}

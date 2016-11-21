package com.ucsdcse110.tritonnections;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class PostObj {
    public String author;
    public Object time;
    public String title;
    public String body;

    public PostObj() {}

    public PostObj(String author, String title, String content) {
        this.author = author;
        this.time = ServerValue.TIMESTAMP;
        this.title = title;
        this.body = content;
    }

    public PostObj(Map<String, Object> map) {
        this.author = map.get("author").toString();
        this.time = map.get("time");
        this.title = map.get("title").toString();
        this.body = map.get("body").toString();
    }

    Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("author", author);
        result.put("time", time);
        result.put("title", title);
        result.put("body", body);
        return result;
    }
}

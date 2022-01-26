package pl.edu.pb.android_reddit_client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("selftext")
    private String selftext;

    @SerializedName("subreddit")
    private String subreddit;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("medias")
    private List<String> medias; // change to proper type later

    @SerializedName("score")
    private int score;

    @SerializedName("timeInHours")
    private String timeInHours;

    @SerializedName("likes")
    private boolean likes;
}

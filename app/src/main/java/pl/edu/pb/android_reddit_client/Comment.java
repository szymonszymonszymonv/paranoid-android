package pl.edu.pb.android_reddit_client;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    private String id;

    @SerializedName("body")
    private String body;

    @SerializedName("author")
    private String author;

    @SerializedName("score")
    private int score;

    @SerializedName("timeInHours")
    private String timeInHours;

    @SerializedName("likes")
    private boolean likes;
}

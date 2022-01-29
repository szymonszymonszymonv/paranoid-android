package pl.edu.pb.android_reddit_client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Comment {



    @SerializedName("id")
    private String id;


    @SerializedName("body")
    private String body;

    @SerializedName("author")
    private String author;

    @SerializedName("score")
    private int score;

    @SerializedName("replies")
    private List<Comment> replies;

    @SerializedName("timeInHours")
    private String timeInHours;

    @SerializedName("likes")
    private Boolean likes;





}

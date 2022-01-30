package pl.edu.pb.android_reddit_client;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Subreddit {

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String description;

    @SerializedName("subscribers")
    private String subscribers;

}

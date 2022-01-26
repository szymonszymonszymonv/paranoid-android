package pl.edu.pb.android_reddit_client;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface RedditAPI {

    @GET("r/all")
    Call<List<Post>> getPosts();

    @GET("r/all/sdb8n8")
    Call<List<Comment>> getComments();

}

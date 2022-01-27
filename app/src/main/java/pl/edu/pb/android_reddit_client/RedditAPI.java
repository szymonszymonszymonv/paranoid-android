package pl.edu.pb.android_reddit_client;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RedditAPI {

    @GET("r/all")
    Call<List<Post>> getPosts();

    @GET("r/all/{id}")
    Call<List<Comment>> getComments(@Path("id") String postId);

}

package pl.edu.pb.android_reddit_client;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RedditAPI {

    @GET("r/all")
    Call<List<Post>> getPosts();

    @GET("r/all/{id}")
    Call<List<Comment>> getComments(@Path("id") String postId);


    @POST("comment/{id}/upvote")
    Call<Comment> commentUpvote(@Path("id") String postId);

    @POST("{id}/upvote")
    Call<Post> postUpvote(@Path("id") String postId);

    @POST("comment/{id}/downvote")
    Call<Comment> commentDownvote(@Path("id") String postId);

    @POST("{id}/downvote")
    Call<Post> postDownvote(@Path("id") String postId);

    @POST("comment/{id}/unvote")
    Call<Comment> commentUnvote(@Path("id") String postId);

    @POST("{id}/unvote")
    Call<Post> postUnvote(@Path("id") String postId);

    @GET("search/{query}")
    Call<List<Subreddit>> searchSubreddit(@Path("query") String query);



}

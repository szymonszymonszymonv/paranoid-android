package pl.edu.pb.android_reddit_client;


import retrofit2.Retrofit;

public class RedditAPI {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://localhost:5000")
            .build();


}

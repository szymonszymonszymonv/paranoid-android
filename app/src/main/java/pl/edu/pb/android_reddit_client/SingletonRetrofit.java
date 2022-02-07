package pl.edu.pb.android_reddit_client;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingletonRetrofit {
    private static SingletonRetrofit instance = null;

    @Getter
    private static Retrofit retrofit;


    private SingletonRetrofit(){
        System.out.println("Singleton created");
    }

    public static synchronized SingletonRetrofit getInstance(){
        if(instance == null){
            instance = new SingletonRetrofit();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.14:5000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }


}

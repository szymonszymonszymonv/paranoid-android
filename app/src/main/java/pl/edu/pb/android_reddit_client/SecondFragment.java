package pl.edu.pb.android_reddit_client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pb.android_reddit_client.databinding.FragmentSecondBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private String postId;

    private CommentsAdapter commentsAdapter;
    private List<Comment> parentCommentArrayList;
    private RecyclerView recyclerViewParrent;
    private RecyclerView.LayoutManager layoutManager;
    private RedditAPI redditAPI;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.14:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        displayComments(retrofit);


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    public void initRecyclerViewParent() {
        recyclerViewParrent = binding.RecycleViewCommentsParrent;
        commentsAdapter = new CommentsAdapter(parentCommentArrayList, getActivity(), redditAPI);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewParrent.setLayoutManager(layoutManager);
        recyclerViewParrent.setAdapter(commentsAdapter);

    }

    public void displayComments(Retrofit retrofit) {
        redditAPI = retrofit.create(RedditAPI.class);

        if(getArguments() != null) {
            postId = SecondFragmentArgs.fromBundle(getArguments()).getPostId();
            Log.d("onViewCreated", postId);
        }

        Call<List<Comment>> call = redditAPI.getComments(postId);


        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();

                parentCommentArrayList = comments;


                initRecyclerViewParent();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.d("getComments", t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
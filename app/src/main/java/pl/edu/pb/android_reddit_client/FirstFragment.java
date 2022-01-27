package pl.edu.pb.android_reddit_client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pb.android_reddit_client.databinding.FragmentFirstBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstFragment extends Fragment implements PostsAdapter.OnPostClickListener {
    private final String TAG = "FirstFragment";
    private FragmentFirstBinding binding;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Post> postList;
    private RedditAPI redditAPI;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        redditAPI = retrofit.create(RedditAPI.class);
        getPosts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getPosts() {
        Call<List<Post>> call = redditAPI.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                List<Post> posts = response.body();
                postList = posts;
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void initRecyclerView() {
        recyclerView = binding.recyclerView;
        postsAdapter = new PostsAdapter(postList, this);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(postsAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPostClick(int position) {
        Post post = postList.get(position);
        Log.d(TAG, "clicked a post " + post.getId());
        FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
        action.setPostId(post.getId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }
}
package pl.edu.pb.android_reddit_client;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pb.android_reddit_client.databinding.PostItemBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    private List<Post> posts;
    private PostItemBinding binding;
    private OnPostClickListener onPostClickListener;
    private RedditAPI redditAPI;


    public PostsAdapter(List<Post> p, OnPostClickListener listener, RedditAPI redditAPI){
        posts = p;
        onPostClickListener = listener;
        this.redditAPI = redditAPI;

    }

    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnPostClickListener onPostClickListener;

        public PostsViewHolder(@NonNull PostItemBinding binding, OnPostClickListener listener) {
            super(binding.getRoot());
            onPostClickListener = listener;
            binding.getRoot().setOnClickListener(this);

        }
        public void bind(Post post) {
            binding.authorText.setText(post.getAuthor());
            binding.subredditText.setText(post.getSubreddit());
            binding.titleText.setText(post.getTitle());
            binding.timeText.setText(post.getTimeInHours());
            binding.includeSubmissionScore.submissionScore.setText(post.getScore()+"");
            if(post.getLikes() != null){
                boolean likes = post.getLikes();
                if(likes){
                    binding.includeSubmissionScore.submissionUpvote.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                }
                else{
                    binding.includeSubmissionScore.submissionDownvote.setButtonTintList(ColorStateList.valueOf(Color.RED));
                }
            }
            else{
                binding.includeSubmissionScore.submissionDownvote.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
                binding.includeSubmissionScore.submissionUpvote.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
            }



            binding.includeSubmissionScore.submissionUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<Post> callPostUpvote = redditAPI.postUpvote(post.getId());
                    ToggleButton button = v.findViewById(R.id.submission_upvote);
                    if(post.getLikes() == null){

                        button.setButtonTintList(ColorStateList.valueOf(Color.GREEN));

                        callPostUpvote.enqueue(new Callback<Post>() {

                            @Override
                            public void onResponse(Call<Post> callPostUpvote, Response<Post> response) {


                                Log.d("upvote", "post upvote success");
//                            Post upvoted = response.body();

                            }
                            @Override
                            public void onFailure(Call<Post> callPostUpvote, Throwable t) {
                                Log.d("getComments", "error post upvote");
                            }
                        });
                        Log.d("positionUpvote", String.valueOf(getBindingAdapterPosition()));
                        post.setLikes(true);
//                        notifyItemChanged(getBindingAdapterPosition());




                    }else{
                        boolean likes = post.getLikes();
                        if(likes){
                            button.setButtonTintList(ColorStateList.valueOf(Color.GRAY));

                            Call<Post> callPostUnvote = redditAPI.postUnvote(post.getId());

                            callPostUnvote.enqueue(new Callback<Post>() {
                                @Override
                                public void onResponse(Call<Post> call, Response<Post> response) {

                                    Log.d("unvote", "post unvote success");
                                }

                                @Override
                                public void onFailure(Call<Post> call, Throwable t) {
                                    Log.d("getComments", "error post unvote");
                                }
                            });


                            Log.d("positionUnvote", String.valueOf(getBindingAdapterPosition()));
                            post.setLikes(null);
//                            notifyItemChanged(getBindingAdapterPosition());




                            //unvote
                        }
                    }

                }
            });

            binding.includeSubmissionScore.submissionDownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Post> callPostDownvote = redditAPI.postDownvote(post.getId());
                    ToggleButton button = v.findViewById(R.id.submission_downvote);
                    if(post.getLikes() == null) {

                        button.setButtonTintList(ColorStateList.valueOf(Color.RED));

                        callPostDownvote.enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> callPostDownvote, Response<Post> response) {
                                Post post = response.body();

//                                notifyItemChanged(post)
                                Log.d("downvote", "post downvote success");
//                            Post downvoted = response.body();
                            }
                            @Override
                            public void onFailure(Call<Post> callPostDownvote, Throwable t) {
                                Log.d("getComments", "error post downvote");
                            }
                        });


                        Log.d("positionDownvote", String.valueOf(getBindingAdapterPosition()));
                        post.setLikes(false);
//                        notifyItemChanged(getBindingAdapterPosition());


                    }
                        else{
                            boolean likes = post.getLikes();
                            if(!likes){
                                button.setButtonTintList(ColorStateList.valueOf(Color.GRAY));

                                Call<Post> callPostUnvote = redditAPI.postUnvote(post.getId());

                                callPostUnvote.enqueue(new Callback<Post>() {
                                    @Override
                                    public void onResponse(Call<Post> call, Response<Post> response) {

                                        Log.d("unvote", "post unvote success");
                                    }

                                    @Override
                                    public void onFailure(Call<Post> call, Throwable t) {
                                        Log.d("getComments", "error post unvote");
                                    }
                                });

                                Log.d("positionUnvote", String.valueOf(getBindingAdapterPosition()));
                                post.setLikes(null);
//                                notifyItemChanged(getBindingAdapterPosition());




                                //unvote
                            }
                        }

                }
            });

        }

        @Override
        public void onClick(View v) {
            onPostClickListener.onPostClick(getBindingAdapterPosition());
        }
    }


    public static Drawable setTint(Drawable drawable, int color) {
        final Drawable newDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = PostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        return new PostsViewHolder(binding, onPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        holder.bind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public interface OnPostClickListener {
        void onPostClick(int position);
    }

}

package pl.edu.pb.android_reddit_client;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.edu.pb.android_reddit_client.databinding.CommentsListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Activity activity;
    private RedditAPI redditAPI;
    List<Comment> parentCommentArrayList;
    private CommentsListBinding binding;
//    private OnPostClickListener onPostClickListener;

    public CommentsAdapter(List<Comment> parentCommentArrayList, Activity activity, RedditAPI redditAPI) {
        this.activity = activity;
        this.parentCommentArrayList = parentCommentArrayList;
        this.redditAPI = redditAPI;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("create comment", "create comment");
        binding = CommentsListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = parentCommentArrayList.get(position);
        holder.bind(comment);

//        boolean isExpanded = comment.expanded;
//        holder.itemView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        Log.d("clickles mnie", "TEST");


//        holder.itemView.setOnClickListener(v -> {
//            // Get the current state of the item
//            boolean expanded = comment.isExpanded();
//            // Change the state
//            comment.setExpanded(!expanded);
//            // Notify the adapter that item has changed
//            holder.g .setVisibility(expanded ? View.VISIBLE : View.GONE);
//            notifyItemChanged(position);
//        });
    }

    @Override
    public int getItemCount() {
        return parentCommentArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{



        public ViewHolder(@NonNull CommentsListBinding binding) {

            super(binding.getRoot());



        }
        public void bind(Comment comment) {

            Log.d("bind", "comment");

//            boolean expanded = comment.isExpanded();

//            comment.getReplies().setVisibility(expanded ? View.VISIBLE : View.GONE);



            binding.parentAuthor.setText(comment.getAuthor());
            binding.parentBody.setText(comment.getBody());
            binding.parentTime.setText(comment.getTimeInHours());
            binding.parentScore.submissionScore.setText(comment.getScore()+"");



            ReplyAdapter replyAdapter = new ReplyAdapter(comment.getReplies(), activity);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            binding.nestedReply.setLayoutManager(linearLayoutManager);
            binding.nestedReply.setAdapter(replyAdapter);

//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                NotificationChannel channel = new NotificationChannel("upvote", "upvote", NotificationManager.IMPORTANCE_DEFAULT);
//                NotificationManager manager = activity.getSystemService(NotificationManager.class);
//                manager.createNotificationChannel(channel);
//            }

            binding.parentScore.submissionUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, "upvote" );
                    builder.setContentTitle(comment.getAuthor());
                    builder.setContentText("successfully upvoted");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(activity);
                    managerCompat.notify(1, builder.build());

                    Call<Comment> call = redditAPI.commentUpvote(comment.getId());



                    call.enqueue(new Callback<Comment>() {
                        @Override
                        public void onResponse(Call<Comment> call, Response<Comment> response) {
                            Comment upvoted = response.body();

                            Log.d("faktycznie upvote", "upvoted");
                        }

                        @Override
                        public void onFailure(Call<Comment> call, Throwable t) {
                            Log.d("getComments", t.getMessage());
                        }
                    });



                }
            });

        }


    }

}

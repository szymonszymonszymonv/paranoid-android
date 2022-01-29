package pl.edu.pb.android_reddit_client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

    Integer mExpandedPosition = 1;
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

        LinearLayout recyclerView = binding.trollId;
//        boolean expanded = comment.isExpanded();
//        recyclerView.setVisibility(expanded ? View.VISIBLE : View.GONE);

        final boolean isExpanded = position==mExpandedPosition;
        recyclerView.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(holder.getBindingAdapterPosition());
            }
        });

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


    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // Check for an expanded view, collapse if you find one
        if (mExpandedPosition >= 0) {
            int prev = mExpandedPosition;
            notifyItemChanged(prev);
        }
        // Set the current position to "expanded"
        mExpandedPosition = holder.getPosition();
        notifyItemChanged(mExpandedPosition);


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


            binding.parentAuthor.setText(comment.getAuthor());
            binding.parentBody.setText(comment.getBody());
            binding.parentTime.setText(comment.getTimeInHours());
            binding.parentScore.submissionScore.setText(comment.getScore()+"");

            if(comment.getLikes() != null){
                boolean likes = comment.getLikes();
                if(likes){
                    binding.parentScore.submissionUpvote.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                }
                else{
                    binding.parentScore.submissionDownvote.setButtonTintList(ColorStateList.valueOf(Color.RED));
                }
            }
            else{
                binding.parentScore.submissionDownvote.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
                binding.parentScore.submissionUpvote.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
            }

            ReplyAdapter replyAdapter = new ReplyAdapter(comment.getReplies(), activity, redditAPI);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            binding.nestedReply.setLayoutManager(linearLayoutManager);
            binding.nestedReply.setAdapter(replyAdapter);

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


                    Call<Comment> callCommentUpvote = redditAPI.commentUpvote(comment.getId());

                    ToggleButton button = v.findViewById(R.id.submission_upvote);
                    if(comment.getLikes() == null){

                        button.setButtonTintList(ColorStateList.valueOf(Color.GREEN));

                        callCommentUpvote.enqueue(new Callback<Comment>() {

                            @Override
                            public void onResponse(Call<Comment> callCommentUpvote, Response<Comment> response) {


                                Log.d("upvote", "comment upvote success");
//                            Post upvoted = response.body();

                            }
                            @Override
                            public void onFailure(Call<Comment> callPostUpvote, Throwable t) {
                                Log.d("getComments", "error comment upvote");
                            }
                        });
                        Log.d("positionUpvote", String.valueOf(getBindingAdapterPosition()));
                        comment.setLikes(true);
//                        notifyItemChanged(getBindingAdapterPosition());




                    }else{
                        boolean likes = comment.getLikes();
                        if(likes){
                            button.setButtonTintList(ColorStateList.valueOf(Color.GRAY));

                            Call<Comment> callCommentUnvote = redditAPI.commentUnvote(comment.getId());

                            callCommentUnvote.enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {

                                    Log.d("unvote", "comment unvote success");
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Log.d("getComments", "error comment unvote");
                                }
                            });


                            Log.d("positionUnvote", String.valueOf(getBindingAdapterPosition()));
                            comment.setLikes(null);
//                            notifyItemChanged(getBindingAdapterPosition());




                            //unvote
                        }
                    }




                }
            });

            binding.parentScore.submissionDownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<Comment> callPostDownvote = redditAPI.commentDownvote(comment.getId());
                    ToggleButton button = v.findViewById(R.id.submission_downvote);
                    if(comment.getLikes() == null) {

                        button.setButtonTintList(ColorStateList.valueOf(Color.RED));

                        callPostDownvote.enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> callPostDownvote, Response<Comment> response) {
                                Comment comment = response.body();

//                                notifyItemChanged(post)
                                Log.d("downvote", "comment downvote success");
//                            Post downvoted = response.body();
                            }
                            @Override
                            public void onFailure(Call<Comment> callPostDownvote, Throwable t) {
                                Log.d("getComments", "error comment downvote");
                            }
                        });


                        Log.d("positionDownvote", String.valueOf(getBindingAdapterPosition()));
                        comment.setLikes(false);
//                        notifyItemChanged(getBindingAdapterPosition());


                    }
                    else{
                        boolean likes = comment.getLikes();
                        if(!likes){
                            button.setButtonTintList(ColorStateList.valueOf(Color.GRAY));

                            Call<Comment> callCommentUnvote = redditAPI.commentUnvote(comment.getId());

                            callCommentUnvote.enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {

                                    Log.d("unvote", "comment unvote success");
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Log.d("getComments", "error comment unvote");
                                }
                            });

                            Log.d("positionUnvote", String.valueOf(getBindingAdapterPosition()));
                            comment.setLikes(null);
//                                notifyItemChanged(getBindingAdapterPosition());


                            //unvote
                        }
                    }
                }
            });

//            binding.commentArrow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    boolean expanded = comment.isExpanded();
//
//                    Log.d("log", String.valueOf(expanded));
////                    RecyclerView recycler = binding.nestedReply;
////
////                    recycler.setVisibility(expanded ? View.VISIBLE : View.GONE);
//
//                    comment.setExpanded(!comment.isExpanded());
//
////                    notifyDataSetChanged();
//                    notifyItemChanged(getBindingAdapterPosition());
//                }
//            });

        }


    }

}

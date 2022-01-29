package pl.edu.pb.android_reddit_client;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;


import pl.edu.pb.android_reddit_client.databinding.CommentsListBinding;
import pl.edu.pb.android_reddit_client.databinding.ReplyListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    private ReplyListBinding binding;
    private CommentsListBinding binding1;
    List<Comment> childCommentArrayList;
    private Activity activity;
    private OnReplyClickListener OnReplyClickListener;
    private RedditAPI redditAPI;

    private RecyclerView recyclerViewParrent;
    private RecyclerView.LayoutManager layoutManager;

    List<Comment> commentList;

    private CommentsAdapter commentsAdapter;

    public ReplyAdapter(List<Comment> childCommentArrayList, Activity activity, RedditAPI redditAPI) {
        this.activity = activity;
        this.childCommentArrayList = childCommentArrayList;
        this.redditAPI = redditAPI;

    }

    public interface OnReplyClickListener {
        void onReplyClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("create reply", "create reply");
        binding = ReplyListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReplyAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = childCommentArrayList.get(position);
        holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return childCommentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(@NonNull ReplyListBinding  binding) {
//            super(itemView);
            super(binding.getRoot());


        }




        public void bind(Comment comment) {



            try{
                binding.childAuthor.setText(comment.getAuthor());
                binding.childBody.setText(comment.getBody());
                binding.childTime.setText(comment.getTimeInHours());
                binding.childScore.submissionScore.setText(comment.getScore()+"");
                Log.d("test", comment.getAuthor());

                if(comment.getLikes() != null){
                    boolean likes = comment.getLikes();
                    if(likes){
                        binding.childScore.submissionUpvote.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    }
                    else{
                        binding.childScore.submissionDownvote.setButtonTintList(ColorStateList.valueOf(Color.RED));
                    }
                }
                else{
                    binding.childScore.submissionDownvote.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
                    binding.childScore.submissionUpvote.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
                }

            }catch (IndexOutOfBoundsException e) {
                System.out.println("brak replies: ");
            }

            binding.childScore.submissionUpvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, "upvote" );
                    builder.setContentTitle(comment.getAuthor());
                    builder.setContentText("successfully upvoted replies");
                    builder.setSmallIcon(R.drawable.ic_launcher_background);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(activity);
                    managerCompat.notify(1, builder.build());

                    Snackbar.make(binding.getRoot() , "Button clicked", Snackbar.LENGTH_LONG)
                            .setAction("close", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setActionTextColor(Color.RED)
                            .show();


                    Call<Comment> callCommentUpvote = redditAPI.commentUpvote(comment.getId());

                    ToggleButton button = v.findViewById(R.id.submission_upvote);
                    if(comment.getLikes() == null){

                        button.setButtonTintList(ColorStateList.valueOf(Color.GREEN));

                        callCommentUpvote.enqueue(new Callback<Comment>() {

                            @Override
                            public void onResponse(Call<Comment> callCommentUpvote, Response<Comment> response) {


                                Log.d("upvote", "reply upvote success");
//                            Post upvoted = response.body();

                            }
                            @Override
                            public void onFailure(Call<Comment> callPostUpvote, Throwable t) {
                                Log.d("getComments", "error reply upvote");
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

                                    Log.d("unvote", "reply unvote success");
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Log.d("getComments", "error reply unvote");
                                }
                            });


                            Log.d("positionUnvote", String.valueOf(getBindingAdapterPosition()));
                            comment.setLikes(null);
//                            notifyItemChanged(getBindingAdapterPosition());


                        }
                    }




                }
            });

            binding.childScore.submissionDownvote.setOnClickListener(new View.OnClickListener() {
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
                                Log.d("downvote", "reply downvote success");
//                            Post downvoted = response.body();
                            }
                            @Override
                            public void onFailure(Call<Comment> callPostDownvote, Throwable t) {
                                Log.d("getComments", "error reply downvote");
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

                                    Log.d("unvote", "reply unvote success");
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Log.d("getComments", "error reply unvote");
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

            binding.replyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String commentId = comment.getId();


//                    Call<List<Comment>> call = redditAPI.getComments(commentId);
//
//                    call.enqueue(new Callback<List<Comment>>() {
//                        @Override
//                        public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
//                            List<Comment> comments = response.body();
//
//                            commentList = comments;
//
//                            initRecyclerViewParent();
////
//                        }
//
//                        @Override
//                        public void onFailure(Call<List<Comment>> call, Throwable t) {
//                            Log.d("getComments", t.getMessage());
//                        }
//                    });


//                    recyclerViewParrent = v.findViewById(R.id.RecycleViewCommentsParrent);
//                    commentsAdapter = new CommentsAdapter(comment.getReplies(), activity, redditAPI);
////                    layoutManager = new LinearLayoutManager(activity);
//
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
//                    recyclerViewParrent.addView(binding.getRoot());
////
////                    recyclerViewParrent.setLayoutManager(linearLayoutManager);
//                    recyclerViewParrent.setAdapter(commentsAdapter);




                }
            });


//            ReplyAdapter replyAdapter = new ReplyAdapter(comment.getReplies(), activity);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

//            linearLayoutManager.addView(binding1.nestedReply);
//            notifydatasetchanged
//            binding1.nestedReply.setLayoutManager(linearLayoutManager);
//            binding1.nestedReply.setAdapter(replyAdapter);
        }

    }



}

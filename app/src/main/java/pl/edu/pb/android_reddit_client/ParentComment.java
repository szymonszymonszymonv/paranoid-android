package pl.edu.pb.android_reddit_client;

public class ParentComment  {

    private String commentId, author, body, score;


    public ParentComment(String commentId, String author, String body, String score) {
        this.commentId = commentId;
        this.author = author;
        this.body = body;
        this.score = score;
    }
}

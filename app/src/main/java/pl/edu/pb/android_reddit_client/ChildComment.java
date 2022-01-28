package pl.edu.pb.android_reddit_client;

public class ChildComment {
    private String replyId, author, body, score;


    public ChildComment(String replyId, String author, String body, String score) {
        this.replyId = replyId;
        this.author = author;
        this.body = body;
        this.score = score;
    }

}

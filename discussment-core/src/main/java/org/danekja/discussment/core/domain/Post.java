package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Martin Bláha on 19.01.17.
 */
@Entity
public class Post extends BaseEntity implements Serializable {

    @ManyToOne
    private User user;

    private String text;

    private boolean disabled;

    private int level;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne
    private Discussion discussion;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Post> replies = new ArrayList<Post>();

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Post() {}

    public Post(String text) {
        this.text = text;
    }

    public Post(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedFormat() {
        SimpleDateFormat formatData = new SimpleDateFormat("d.M.yyyy H:mm:ss");

        return formatData.format(created);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setReplies(List<Post> replies) {
        this.replies = replies;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Post> getReplies() {
        return replies;
    }

    public void addReply(Post reply) {
        replies.add(reply);
        if (reply.getPost() != this) {
            reply.setPost(this);
        }
    }

    public int getNumberOfReplies() {

        return getNumberOfReplies(this, 0);
    }

    private int getNumberOfReplies(Post postModel, int count) {

        count++;

        for (Post post: postModel.getReplies()) {
            count = getNumberOfReplies(post, count);
        }
        return count;
    }

    public Post getLastPost() {
        return getLastPost(this);
    }

    private Post getLastPost(Post postModel) {
        Post lastPost = null;

        for (Post post: postModel.getReplies()) {
            lastPost = getLastPost(post);

            if (post.getCreated().compareTo(lastPost.getCreated()) > 0) {
                lastPost = post;
            }
        }

        if (lastPost == null) {
            return this;
        } else {
            return lastPost;
        }

    }


}

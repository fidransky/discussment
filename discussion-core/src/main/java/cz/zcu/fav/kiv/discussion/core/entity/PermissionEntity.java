package cz.zcu.fav.kiv.discussion.core.entity;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by Martin Bláha on 19.01.17.
 */
@Entity
public class PermissionEntity extends BaseEntity implements Serializable {

    public static final int CREATE_CATEGORY = 1;
    public static final int REMOVE_CATEGORY = 2;

    public static final int CREATE_TOPIC = 3;
    public static final int REMOVE_TOPIC = 4;

    public static final int CREATE_DISCUSSION = 5;
    public static final int REMOVE_DISCUSSION = 6;

    public static final int CREATE_POST = 7;
    public static final int REMOVE_POST = 8;
    public static final int DISABLE_POST = 9;

    public static final int READ_PRIVATE_DISCUSSION = 10;


    @OneToOne
    private UserEntity user;

    private boolean createCategory;
    private boolean removeCategory;

    private boolean createTopic;
    private boolean removeTopic;

    private boolean createDiscussion;
    private boolean removeDiscussion;

    private boolean createPost;
    private boolean removePost;
    private boolean disablePost;

    private boolean readPrivateDiscussion;

    public void setPermissions(Map<Integer, Boolean> permissions) {
        for (Map.Entry<Integer, Boolean> entry : permissions.entrySet()) {
            setPermission(entry.getKey(), entry.getValue());
        }
    }

    public void setPermission(int permission, boolean value) {
        switch (permission) {
            case CREATE_CATEGORY:
                setCreateCategory(value);
                break;
            case REMOVE_CATEGORY:
                setRemoveCategory(value);
                break;
            case CREATE_TOPIC:
                setCreateTopic(value);
                break;
            case REMOVE_TOPIC:
                setRemoveTopic(value);
                break;
            case CREATE_DISCUSSION:
                setCreateDiscussion(value);
                break;
            case REMOVE_DISCUSSION:
                setRemoveDiscussion(value);
                break;
            case CREATE_POST:
                setCreatePost(value);
                break;
            case REMOVE_POST:
                setRemovePost(value);
                break;
            case DISABLE_POST:
                setDisablePost(value);
                break;
            case READ_PRIVATE_DISCUSSION:
                setReadPrivateDiscussion(value);
                break;
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isCreateCategory() {
        return createCategory;
    }

    public void setCreateCategory(boolean createCategory) {
        this.createCategory = createCategory;
    }

    public boolean isRemoveCategory() {
        return removeCategory;
    }

    public void setRemoveCategory(boolean removeCategory) {
        this.removeCategory = removeCategory;
    }

    public boolean isCreateTopic() {
        return createTopic;
    }

    public void setCreateTopic(boolean createTopic) {
        this.createTopic = createTopic;
    }

    public boolean isRemoveTopic() {
        return removeTopic;
    }

    public void setRemoveTopic(boolean removeTopic) {
        this.removeTopic = removeTopic;
    }

    public boolean isCreateDiscussion() {
        return createDiscussion;
    }

    public void setCreateDiscussion(boolean createDiscussion) {
        this.createDiscussion = createDiscussion;
    }

    public boolean isRemoveDiscussion() {
        return removeDiscussion;
    }

    public void setRemoveDiscussion(boolean removeDiscussion) {
        this.removeDiscussion = removeDiscussion;
    }

    public boolean isCreatePost() {
        return createPost;
    }

    public void setCreatePost(boolean createPost) {
        this.createPost = createPost;
    }

    public boolean isRemovePost() {
        return removePost;
    }

    public void setRemovePost(boolean removePost) {
        this.removePost = removePost;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public boolean isReadPrivateDiscussion() {
        return readPrivateDiscussion;
    }

    public void setReadPrivateDiscussion(boolean readPrivateDiscussion) {
        this.readPrivateDiscussion = readPrivateDiscussion;
    }
}

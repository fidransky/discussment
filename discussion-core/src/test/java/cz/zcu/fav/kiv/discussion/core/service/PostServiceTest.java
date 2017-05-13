package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PermissionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class PostServiceTest {

    private DiscussionEntity discussion;
    private UserEntity user;

    @Before
    public void setUp() throws Exception {
        discussion = DiscussionService.createDiscussion(new DiscussionEntity("test"));
        user = UserService.addUser(new UserEntity("test", "test", "test"), new PermissionEntity());
    }

    @After
    public void tearDown() throws Exception {
        DiscussionService.removeDiscussion(discussion);
        UserService.removeUser(user);
    }

    @Test
    public void removePost() throws Exception {
        PostEntity post = new PostEntity();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        PostEntity reply1 = new PostEntity();
        reply1.setText("reply1Text");
        reply1.setUser(user);
        reply1 = PostService.sendReply(reply1, post);

        PostEntity reply2 = new PostEntity();
        reply2.setText("reply2Text");
        reply2.setUser(user);
        reply2 = PostService.sendReply(reply2, post);

        PostEntity reply3 = new PostEntity();
        reply3.setText("reply3Text");
        reply3.setUser(user);
        reply3 = PostService.sendReply(reply3, reply2);

        PostService.removePost(reply2);
        assertNull(PostService.getPostById(reply2.getId()));

        PostService.removePost(post);
        assertNull(PostService.getPostById(post.getId()));
    }

    @Test
    public void sendPost() throws Exception {
        //test
        PostEntity post = new PostEntity();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        assertEquals(0, post.getLevel());
        assertEquals("text", post.getText());

        //clear
        PostService.removePost(post);
    }

    @Test
    public void sendReply() throws Exception {
        //prepare
        PostEntity post = new PostEntity();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        //test
        PostEntity reply = new PostEntity();
        reply.setText("replyText");
        reply.setUser(user);
        reply = PostService.sendReply(reply, post);

        assertEquals(1, reply.getLevel());
        assertEquals("replyText", reply.getText());

        //clear
        PostService.removePost(post);
    }

    @Test
    public void disablePost() throws Exception {
        //prepare
        PostEntity post = new PostEntity();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        PostService.enablePost(post);

        //test
        post = PostService.disablePost(post);
        assertTrue(post.isDisabled());

        //clear
        PostService.removePost(post);
    }

    @Test
    public void enablePost() throws Exception {
        //prepare
        PostEntity post = new PostEntity();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        PostService.disablePost(post);

        //test
        post = PostService.enablePost(post);
        assertFalse(post.isDisabled());

        //clear
        PostService.removePost(post);
    }

}
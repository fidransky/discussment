package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.jpa.DiscussionJPA;
import org.danekja.discussment.core.dao.jpa.PermissionJPA;
import org.danekja.discussment.core.dao.jpa.PostJPA;
import org.danekja.discussment.core.dao.jpa.UserJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.imp.DiscussionService;
import org.danekja.discussment.core.service.imp.PostService;
import org.danekja.discussment.core.service.imp.UserService;
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

    private IUserService userService;
    private IDiscussionService discussionService;
    private IPostService postService;

    private Discussion discussion;
    private User user;

    @Before
    public void setUp() throws Exception {
        userService = new UserService(new UserJPA(), new PermissionJPA());
        discussionService = new DiscussionService(new DiscussionJPA());
        postService = new PostService(new PostJPA());

        discussion = new Discussion("test");
        discussion = discussionService.createDiscussion(discussion);
        user = userService.addUser(new User("test", "test", "test"), new Permission());
    }

    @After
    public void tearDown() throws Exception {
        discussionService.removeDiscussion(discussion);
        userService.removeUser(user);
    }

    @Test
    public void removePost() throws Exception {
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = postService.sendPost(discussion, post);

        Post reply1 = new Post();
        reply1.setText("reply1Text");
        reply1.setUser(user);
        reply1 = postService.sendReply(reply1, post);

        Post reply2 = new Post();
        reply2.setText("reply2Text");
        reply2.setUser(user);
        reply2 = postService.sendReply(reply2, post);

        Post reply3 = new Post();
        reply3.setText("reply3Text");
        reply3.setUser(user);
        reply3 = postService.sendReply(reply3, reply2);

        postService.removePost(reply2);
        assertNull(postService.getPostById(reply2.getId()));

        postService.removePost(post);
        assertNull(postService.getPostById(post.getId()));
    }

    @Test
    public void sendPost() throws Exception {
        //test
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = postService.sendPost(discussion, post);

        assertEquals(0, post.getLevel());
        assertEquals("text", post.getText());

        //clear
        postService.removePost(post);
    }

    @Test
    public void sendReply() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = postService.sendPost(discussion, post);

        //test
        Post reply = new Post();
        reply.setText("replyText");
        reply.setUser(user);
        reply = postService.sendReply(reply, post);

        assertEquals(1, reply.getLevel());
        assertEquals("replyText", reply.getText());

        //clear
        //postService.removePost(post);
    }

    @Test
    public void disablePost() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = postService.sendPost(discussion, post);

        postService.enablePost(post);

        //test
        post = postService.disablePost(post);
        assertTrue(post.isDisabled());

        //clear
        postService.removePost(post);
    }

    @Test
    public void enablePost() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = postService.sendPost(discussion, post);

        postService.disablePost(post);

        //test
        post = postService.enablePost(post);
        assertFalse(post.isDisabled());

        //clear
        postService.removePost(post);
    }

}
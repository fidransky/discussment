package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with discussions
 */
public interface DiscussionService {

    /**
     * Create a new discussion without a topic
     *
     * @param discussion new discussion
     * @return new discussion
     */
    Discussion createDiscussion(Discussion discussion);

    /**
     * Create a new discussion in the topic
     *
     * @param discussion new discussion
     * @param topic topic of the discussion
     * @return new discussion
     */
    Discussion createDiscussion(Discussion discussion, Topic topic);

    /**
     * Get all discussions in the forum based on its topic.
     *
     * @param topic topic of the discussions
     * @return list of Discussion
     */
    List<Discussion> getDiscussionsByTopic(Topic topic);

    /**
     * Get a discussion in the forum based on its id.
     *
     * @param discussionId discussion id
     * @return discussion by id
     */
    Discussion getDiscussionById(long discussionId);

    /**
     * Remove a discussion
     *
     * @param discussion discussion to remove
     */
    void removeDiscussion(Discussion discussion);

    /**
     * Add user access to a discussion
     *
     * @param entity user
     * @param en discussion
     */
    void addAccessToDiscussion(IDiscussionUser entity, Discussion en);

    /**
     * Adds access for currently logged user to discussion.
     * @param en
     */
    void addCurrentUserToDiscussion(Discussion en);

    /**
     * Whether or has user access to discussion.
     * @param user User.
     * @param discussion Discussion.
     * @return True if the user has access to discussion.
     */
    boolean isAccessToDiscussion(IDiscussionUser user, Discussion discussion);

    /**
     * Whether or not has currently logged user access to discussion.
     * @param discussion Discussion.
     * @return True if the user has access to discussion and false if not or no user is logged in.
     */
    boolean hasCurrentUserAccessToDiscussion(Discussion discussion);

    /**
     * Returns the username of the last post in the discussion.
     * @param discussion
     * @return Username. Empty string if discussion has no posts.
     */
    String getLastPostAuthor(Discussion discussion) throws DiscussionUserNotFoundException;
}

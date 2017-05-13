package cz.zcu.fav.kiv.discussion.gui.list;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.service.DiscussionService;
import cz.zcu.fav.kiv.discussion.gui.form.PasswordForm;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class DiscussionListView extends ListView<DiscussionEntity> {

    private PasswordForm passwordForm;

    public DiscussionListView(String id, IModel<? extends List<DiscussionEntity>> model, PasswordForm passwordForm) {
        super(id, model);

        this.passwordForm = passwordForm;
    }

    protected void populateItem(ListItem<DiscussionEntity> listItem) {
        final DiscussionEntity discussion = listItem.getModelObject();

        final boolean access;

        WebMarkupContainer div = new WebMarkupContainer("passDiv");

        UserEntity user = (UserEntity) getSession().getAttribute("user");

        if (discussion.getPass() == null || user != null && user.getPermissions().isReadPrivateDiscussion() || discussion.getUserAccessList().contains(user)) {
            div.add(new AttributeModifier("href", "#"));
            div.add(new AttributeModifier("data-target", "#"));
            access = true;
        } else {
            access = false;
        }

        AjaxLink link = new AjaxLink("nameLink") {

            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                if (access) {
                    PageParameters pageParameters = new PageParameters();
                    pageParameters.add("discussionId", discussion.getId());

                    setResponsePage(getWebPage().getClass(), pageParameters);
                } else {
                    passwordForm.setDiscussionId(discussion.getId());
                }
            }
        };

        link.setBody(Model.of(discussion.getName()));
        div.add(link);
        listItem.add(div);

        listItem.add(new Label("numberOfPosts", discussion.getNumberOfPosts()));

        PostEntity lastPost = discussion.getLastPost();

        listItem.add(new Label("lastUsername", lastPost == null ? "" : lastPost.getUser().getUsername()));
        listItem.add(new Label("lastCreated", lastPost == null ? "" : lastPost.getCreatedFormat()));


        Link remove = new Link("remove") {
            @Override
            public void onClick() {
                DiscussionService.removeDiscussion(discussion);
            }
        };
        listItem.add(remove);

        if (user != null && user.getPermissions().isRemoveDiscussion()) {
            remove.setVisible(true);
        } else {
            remove.setVisible(false);
        }
    }
}

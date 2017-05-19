package org.danekja.discussment.ui.wicket.form.post;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Post;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PostFormComponent extends Panel {

    public PostFormComponent(String id, IModel<Post> postModel) {
        super(id, postModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextArea<String> text = new TextArea<String>("text", new PropertyModel<String>(getDefaultModel(), "text"));
        text.setRequired(true);
        add(text);
    }
    
}

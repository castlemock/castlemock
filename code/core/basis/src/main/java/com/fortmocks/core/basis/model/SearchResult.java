package com.fortmocks.core.basis.model;

/**
 * The search result is the result from executing the search functionality.
 * The result contains a title and a link to the resource
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SearchResult {

    private String title;
    private String link;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

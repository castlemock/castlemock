package com.castlemock.model.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

/**
 * The search result is the result from executing the search functionality.
 * The result contains a title and a link to the resource
 * @author Karl Dahlgren
 * @since 1.0
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SearchResult.Builder.class)
public class SearchResult {

    private final String title;
    private final String link;
    private final String description;

    private SearchResult(final Builder builder) {
        this.title = Objects.requireNonNull(builder.title, "title");
        this.link = Objects.requireNonNull(builder.link, "link");
        this.description = builder.description;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SearchResult that = (SearchResult) o;
        return Objects.equals(title, that.title) && Objects.equals(link, that.link)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, description);
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String title;
        private String link;
        private String description;

        private Builder() {
        }


        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder link(final String link) {
            this.link = link;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public SearchResult build() {
           return new SearchResult(this);
        }
    }
}

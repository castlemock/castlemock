/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortmocks.web.basis.model;

import java.util.List;

/**
 * The content item group is responsible for grouping content items and display them as a group.
 * @author Karl Dahlgren
 * @since 1.0
 * @see ContentItem
 */
public class ContentItemGroup {

    private String title;
    private List<ContentItem> contentItems;

    /**
     * The constructor create a content item group with a title and a list of content items
     * @param title The title that will be displayed to the user with the content items
     * @param contentItems The content items that belongs with the content item group
     */
    public ContentItemGroup(String title, List<ContentItem> contentItems) {
        this.title = title;
        this.contentItems = contentItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContentItem> getContentItems() {
        return contentItems;
    }

    public void setContentItems(List<ContentItem> contentItems) {
        this.contentItems = contentItems;
    }
}

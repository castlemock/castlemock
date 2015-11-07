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

package com.fortmocks.web.core.model;

import com.fortmocks.core.model.user.Role;

/**
 * The content item class is responsible for representing a side menu item.
 * @author Karl Dahlgren
 * @since 1.0
 * @see ContentItemGroup
 */
public class ContentItem {

    private String title;
    private String url;
    private Role role;
    private String icon;

    /**
     * The constructor is used to create a content item with a title, url, role and an icon
     * @param title The title that will be displayed on the page
     * @param url The url which the item will redirect the user to
     * @param role The minimum role required for this item to be displayed
     * @param icon The icon that will be displayed on the page
     */
    public ContentItem(String title, String url, Role role, String icon) {
        this.title = title;
        this.url = url;
        this.role = role;
        this.icon = icon;
    }

    /**
     * The constructor is used to create a content item with a title, url and role
     * @param title The title that will be displayed on the page
     * @param url The url which the item will redirect the user to
     * @param role The minimum role required for this item to be displayed
     */
    public ContentItem(String title, String url, Role role) {
        this.title = title;
        this.url = url;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

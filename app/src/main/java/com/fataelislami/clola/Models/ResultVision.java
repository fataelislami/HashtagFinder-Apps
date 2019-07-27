
package com.fataelislami.clola.Models;

import java.util.HashMap;
import java.util.Map;

public class ResultVision {

    private String id;
    private String name;
    private Integer media_count;
    private Object follow_status;
    private Object following;
    private Object allow_following;
    private Object allow_muting_story;
    private String profile_pic_url;
    private Object non_violating;
    private Object related_tags;
    private Object subtitle;
    private Object social_context;
    private Object social_context_profile_links;
    private Object follow_button_text;
    private Object show_follow_drop_down;
    private String formatted_media_count;
    private Object debug_info;
    private String search_result_subtitle;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMedia_count() {
        return media_count;
    }

    public void setMedia_count(Integer media_count) {
        this.media_count = media_count;
    }

    public Object getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(Object follow_status) {
        this.follow_status = follow_status;
    }

    public Object getFollowing() {
        return following;
    }

    public void setFollowing(Object following) {
        this.following = following;
    }

    public Object getAllow_following() {
        return allow_following;
    }

    public void setAllow_following(Object allow_following) {
        this.allow_following = allow_following;
    }

    public Object getAllow_muting_story() {
        return allow_muting_story;
    }

    public void setAllow_muting_story(Object allow_muting_story) {
        this.allow_muting_story = allow_muting_story;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public Object getNon_violating() {
        return non_violating;
    }

    public void setNon_violating(Object non_violating) {
        this.non_violating = non_violating;
    }

    public Object getRelated_tags() {
        return related_tags;
    }

    public void setRelated_tags(Object related_tags) {
        this.related_tags = related_tags;
    }

    public Object getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Object subtitle) {
        this.subtitle = subtitle;
    }

    public Object getSocial_context() {
        return social_context;
    }

    public void setSocial_context(Object social_context) {
        this.social_context = social_context;
    }

    public Object getSocial_context_profile_links() {
        return social_context_profile_links;
    }

    public void setSocial_context_profile_links(Object social_context_profile_links) {
        this.social_context_profile_links = social_context_profile_links;
    }

    public Object getFollow_button_text() {
        return follow_button_text;
    }

    public void setFollow_button_text(Object follow_button_text) {
        this.follow_button_text = follow_button_text;
    }

    public Object getShow_follow_drop_down() {
        return show_follow_drop_down;
    }

    public void setShow_follow_drop_down(Object show_follow_drop_down) {
        this.show_follow_drop_down = show_follow_drop_down;
    }

    public String getFormatted_media_count() {
        return formatted_media_count;
    }

    public void setFormatted_media_count(String formatted_media_count) {
        this.formatted_media_count = formatted_media_count;
    }

    public Object getDebug_info() {
        return debug_info;
    }

    public void setDebug_info(Object debug_info) {
        this.debug_info = debug_info;
    }

    public String getSearch_result_subtitle() {
        return search_result_subtitle;
    }

    public void setSearch_result_subtitle(String search_result_subtitle) {
        this.search_result_subtitle = search_result_subtitle;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

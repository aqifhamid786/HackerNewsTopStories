package com.aqif.hackernews.restapi.hackernews.requests.item.responsedao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aqifhamid.
 */


public class ItemDao
{

    @SerializedName("by")
    @Expose
    private String by;

    @SerializedName("descendants")
    @Expose
    private int descendants;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("deleted")
    @Expose
    private boolean deleted;

    @SerializedName("parent")
    @Expose
    private int parent;

    @SerializedName("kids")
    @Expose
    private List<Integer> kids = null;

    @SerializedName("score")
    @Expose
    private int score;

    @SerializedName("time")
    @Expose
    private int time;

    @SerializedName("title")
    @Expose
    private String title;
    
    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("url")
    @Expose
    private String url;

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
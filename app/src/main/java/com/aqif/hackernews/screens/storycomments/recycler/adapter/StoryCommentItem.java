package com.aqif.hackernews.screens.storycomments.recycler.adapter;

/**
 * Created by aqifhamid.
 */

public class StoryCommentItem
{

    public static final int LIST_ITEM_COMMENT = 0;
    public static final int LIST_ITEM_LOADER = 1;

    private StoryCommentBinderModel mStoryCommentBinderModel;

    private int mType;

    public StoryCommentItem(int type)
    {
        this(null, type);
    }

    public StoryCommentItem(StoryCommentBinderModel storyCommentBinderModel, int type)
    {
        mStoryCommentBinderModel = storyCommentBinderModel;
        mType = type;
    }

    public StoryCommentBinderModel getStoryCommentBinderModel()
    {
        return mStoryCommentBinderModel;
    }
    public int getType()
    {
        return mType;
    }

}

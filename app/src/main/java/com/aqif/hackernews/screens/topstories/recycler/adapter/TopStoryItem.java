package com.aqif.hackernews.screens.topstories.recycler.adapter;

import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;

/**
 * Created by aqifhamid.
 */

public class TopStoryItem
{

    public static final int LIST_ITEM_TOP_STORY = 0;
    public static final int LIST_ITEM_LOADER = 1;

    private TopStoryBinderModel mTopStoryBinderModel;
    private int mType;

    public TopStoryItem(TopStoryBinderModel topStoryBinderModel, int type)
    {
        mTopStoryBinderModel = topStoryBinderModel;
        mType = type;
    }

    public TopStoryBinderModel getTopStoryBinderModel()
    {
        return mTopStoryBinderModel;
    }

    public int getType()
    {
        return mType;
    }


}

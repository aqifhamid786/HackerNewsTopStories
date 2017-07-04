package com.aqif.hackernews.restapi.hackernews.batchprocessors.items;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemsBatchRequest
{
    private int[] mItemIds;
    private Object mTag;

    public HackerNewsItemsBatchRequest(int[] itemIds, Object tag)
    {
        mItemIds = itemIds;
        mTag = tag;
    }

    public int[] getItemIds()
    {
        return mItemIds;
    }

    public Object getTag()
    {
        return mTag;
    }

}


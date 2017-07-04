package com.aqif.hackernews.screens.topstories.recycler.adapter;

import android.view.View;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqifhamid.
 */

public class TopStoryBinderModel
{

    private int mId;
    private String mTitle;
    private int mScore;
    private String mPostBy;
    private int mCommentsCount;
    private List<Integer> mKidsIds;
    private boolean mIsDeleted;

    private OnTopStoryViewItemClickListener mOnTopStoryViewItemClickListener;

    public TopStoryBinderModel(int id,
                                String title,
                                int score,
                                String postBy,
                                int commentsCount,
                                List<Integer> kidsIds,
                               boolean isDeleted)
    {
        mId = id;
        mTitle = title;
        mScore = score;
        mPostBy = postBy;
        mCommentsCount = commentsCount;
        mKidsIds = kidsIds;
        mIsDeleted = isDeleted;
    }

    public void setOnTopStoryViewItemClickListener(OnTopStoryViewItemClickListener onTopStoryViewItemClickListener)
    {
        mOnTopStoryViewItemClickListener = onTopStoryViewItemClickListener;
    }

    public OnTopStoryViewItemClickListener getOnTopStoryViewItemClickListener()
    {
        return mOnTopStoryViewItemClickListener;
    }

    public int getId()
    {
        return mId;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public List<Integer> getKidsIds()
    {
        return mKidsIds;
    }

    public String getCommentsCount()
    {
        if(mCommentsCount==1)
            return String.format("%d comment", mCommentsCount);
        else
            return String.format("%d comments", mCommentsCount);
    }

    public String getBy()
    {
        return String.format("%d points by %s", mScore, mPostBy);
    }

    public boolean isDeleted()
    {
        return mIsDeleted;
    }

    public void onTopStoryViewItemClicked()
    {
        if(mOnTopStoryViewItemClickListener!=null)
        {
            mOnTopStoryViewItemClickListener.onTopStoryViewItemClicked(this);
        }
    }

    /*********** Listner for click event ***********/
    public interface OnTopStoryViewItemClickListener
    {
        void onTopStoryViewItemClicked(TopStoryBinderModel topStoryBinderModel);
    }

}

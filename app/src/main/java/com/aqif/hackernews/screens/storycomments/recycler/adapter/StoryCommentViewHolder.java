package com.aqif.hackernews.screens.storycomments.recycler.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by aqifhamid.
 */
public class StoryCommentViewHolder extends RecyclerView.ViewHolder
{

    private ViewDataBinding mDataBinding;

    public StoryCommentViewHolder(ViewDataBinding dataBindind)
    {
        super(dataBindind.getRoot());
        mDataBinding = dataBindind;
        mDataBinding.executePendingBindings();
    }

    public ViewDataBinding getViewDataBinding()
    {
        return mDataBinding;
    }

}

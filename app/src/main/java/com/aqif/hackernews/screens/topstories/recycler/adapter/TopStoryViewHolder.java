package com.aqif.hackernews.screens.topstories.recycler.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by aqifhamid.
 */
public class TopStoryViewHolder extends RecyclerView.ViewHolder
{

    private ViewDataBinding mDataBinding;

    public TopStoryViewHolder(ViewDataBinding dataBindind)
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

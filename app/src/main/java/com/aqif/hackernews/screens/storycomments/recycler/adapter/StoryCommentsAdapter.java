package com.aqif.hackernews.screens.storycomments.recycler.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aqif.hackernews.BR;
import com.aqif.hackernews.R;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryItem;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by aqifhamid.
 */
public class StoryCommentsAdapter extends RecyclerView.Adapter<StoryCommentViewHolder>
{
    private List<StoryCommentItem> mStoryCommentItems;
    private LayoutInflater mLayoutInflater;

    public StoryCommentsAdapter(LayoutInflater layoutInflater)
    {
        this(layoutInflater, null);
    }

    public StoryCommentsAdapter(LayoutInflater layoutInflater, ArrayList<StoryCommentItem> storyCommentItems)
    {

        mLayoutInflater = layoutInflater;
        mStoryCommentItems = storyCommentItems;

        if(mStoryCommentItems==null)
        {
            mStoryCommentItems = new ArrayList<>();
        }
    }

    @Override
    public StoryCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ViewDataBinding viewDataBinding = null;
        switch(viewType)
        {
            case StoryCommentItem.LIST_ITEM_COMMENT:
                viewDataBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.story_comment_card_view, parent, false);
                break;

            case StoryCommentItem.LIST_ITEM_LOADER:
                viewDataBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.loading_view_card, parent, false);
                break;
        }

        return new StoryCommentViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(StoryCommentViewHolder holder, int position)
    {

        switch(mStoryCommentItems.get(position).getType())
        {
            case StoryCommentItem.LIST_ITEM_COMMENT:
                holder.getViewDataBinding().setVariable(BR.storyCommentBinder, mStoryCommentItems.get(position).getStoryCommentBinderModel());
                break;

            case StoryCommentItem.LIST_ITEM_LOADER:
                break;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return mStoryCommentItems.get(position).getType();
    }

    @Override
    public int getItemCount()
    {
        return  mStoryCommentItems.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void updateData(List<StoryCommentItem> storyCommentsData)
    {

        while(mStoryCommentItems.size()>storyCommentsData.size())
        {
            int removeIndex = mStoryCommentItems.size()-1;
            notifyItemRemoved(removeIndex);
            mStoryCommentItems.remove(removeIndex);
        }

        for(int lop = 0; lop< mStoryCommentItems.size() && lop<storyCommentsData.size(); lop++)
        {
            mStoryCommentItems.set(lop, storyCommentsData.get(lop));
            notifyItemChanged(lop);
        }

        while(mStoryCommentItems.size()<storyCommentsData.size())
        {
            mStoryCommentItems.add(storyCommentsData.get(mStoryCommentItems.size()));
            notifyItemInserted(mStoryCommentItems.size()-1);
        }

    }

}

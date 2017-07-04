package com.aqif.hackernews.screens.topstories.recycler.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aqif.hackernews.BR;
import com.aqif.hackernews.R;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by aqifhamid.
 */
public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoryViewHolder>
{

    private List<TopStoryItem> mTopStoryItems;
    private LayoutInflater mLayoutInflater;


    public TopStoriesAdapter(LayoutInflater layoutInflater)
    {
        this(layoutInflater, null);
    }

    public TopStoriesAdapter(LayoutInflater layoutInflater, List<TopStoryItem> topStoryItems)
    {
        mLayoutInflater = layoutInflater;

        mTopStoryItems = topStoryItems;
        if(mTopStoryItems==null)
        {
            mTopStoryItems = new ArrayList<>();
        }
    }

    @Override
    public TopStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ViewDataBinding viewDataBinding = null;
        switch(viewType)
        {
            case TopStoryItem.LIST_ITEM_TOP_STORY:
                viewDataBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.top_story_card_view, parent, false);
                break;

            case TopStoryItem.LIST_ITEM_LOADER:
                viewDataBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.loading_view_card, parent, false);
                break;
        }

        if(viewDataBinding!=null)
        {
            return new TopStoryViewHolder(viewDataBinding);
        }
        else
        {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(TopStoryViewHolder holder, int position)
    {

        System.out.println(mTopStoryItems.get(position).getType());
        switch(mTopStoryItems.get(position).getType())
        {
            case TopStoryItem.LIST_ITEM_TOP_STORY:
                holder.getViewDataBinding().setVariable(BR.topStoryBinder, mTopStoryItems.get(position).getTopStoryBinderModel());
                break;

            case TopStoryItem.LIST_ITEM_LOADER:
                break;
        }

    }

    @Override
    public int getItemViewType(int position)
    {
        return mTopStoryItems.get(position).getType();
    }

    @Override
    public int getItemCount()
    {
        return mTopStoryItems.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    public void updateData(List<TopStoryItem> topStoriesData)
    {

        while(mTopStoryItems.size()>topStoriesData.size())
        {
            int removeIndex = mTopStoryItems.size()-1;
            notifyItemRemoved(removeIndex);
            mTopStoryItems.remove(removeIndex);
        }

        for(int lop = 0; lop< mTopStoryItems.size() && lop<topStoriesData.size(); lop++)
        {
            mTopStoryItems.set(lop, topStoriesData.get(lop));
            notifyItemChanged(lop);
        }

        while(mTopStoryItems.size()<topStoriesData.size())
        {
            mTopStoryItems.add(topStoriesData.get(mTopStoryItems.size()));
            notifyItemInserted(mTopStoryItems.size()-1);
        }

    }
}

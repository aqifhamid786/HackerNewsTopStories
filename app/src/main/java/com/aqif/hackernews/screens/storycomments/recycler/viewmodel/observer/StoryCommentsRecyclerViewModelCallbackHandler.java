package com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by aqifhamid.
 */

public class StoryCommentsRecyclerViewModelCallbackHandler implements IStoryCommentsRecyclerViewModelCallbackHandler
{

    protected List<IStoryCommentsRecyclerViewModelCallbackObserver> mStoryCommentsRecyclerViewModelCallbackObservers;

    public StoryCommentsRecyclerViewModelCallbackHandler(List<IStoryCommentsRecyclerViewModelCallbackObserver> storyCommentsRecyclerViewModelCallbackObservers)
    {
        mStoryCommentsRecyclerViewModelCallbackObservers = storyCommentsRecyclerViewModelCallbackObservers;
    }

    @Override
    public void registerStoryCommentsRecyclerViewModelCallbackObserver(IStoryCommentsRecyclerViewModelCallbackObserver storyCommentsRecyclerViewModelCallbackObserver)
    {
        if(storyCommentsRecyclerViewModelCallbackObserver!=null && !mStoryCommentsRecyclerViewModelCallbackObservers.contains(storyCommentsRecyclerViewModelCallbackObserver))
        {
            mStoryCommentsRecyclerViewModelCallbackObservers.add(storyCommentsRecyclerViewModelCallbackObserver);
        }
    }

    @Override
    public void unregisterStoryCommentsRecyclerViewModelCallbackObserver(IStoryCommentsRecyclerViewModelCallbackObserver storyCommentsRecyclerViewModelCallbackObserver)
    {
        if(storyCommentsRecyclerViewModelCallbackObserver!=null && mStoryCommentsRecyclerViewModelCallbackObservers.contains(storyCommentsRecyclerViewModelCallbackObserver))
        {
            mStoryCommentsRecyclerViewModelCallbackObservers.remove(storyCommentsRecyclerViewModelCallbackObserver);
        }
    }

    @Override
    public void notifyLoadMoreStoryComments()
    {
        for(int lop=0; lop<mStoryCommentsRecyclerViewModelCallbackObservers.size(); lop++)
        {
            mStoryCommentsRecyclerViewModelCallbackObservers.get(lop).loadMoreStoryComments();
        }
    }

    @Override
    public void notifyStoryCommentClicked(StoryCommentBinderModel storyCommentBinderModel)
    {
        for(int lop=0; lop<mStoryCommentsRecyclerViewModelCallbackObservers.size(); lop++)
        {
            mStoryCommentsRecyclerViewModelCallbackObservers.get(lop).storyCommentClicked(storyCommentBinderModel);
        }
    }

    @Override
    public void clear()
    {
        mStoryCommentsRecyclerViewModelCallbackObservers.clear();
    }
}

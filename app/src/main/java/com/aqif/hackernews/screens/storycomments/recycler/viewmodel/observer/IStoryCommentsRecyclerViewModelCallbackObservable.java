package com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer;

/**
 * Created by aqifhamid.
 */
public interface IStoryCommentsRecyclerViewModelCallbackObservable
{
    void registerStoryCommentsRecyclerViewModelCallbackObserver(IStoryCommentsRecyclerViewModelCallbackObserver storyCommentsRecyclerViewModelCallbackObserver);
    void unregisterStoryCommentsRecyclerViewModelCallbackObserver(IStoryCommentsRecyclerViewModelCallbackObserver storyCommentsRecyclerViewModelCallbackObserver);
    void clear();
}

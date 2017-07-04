package com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;

/**
 * Created by aqifhamid.
 */

public interface IStoryCommentsRecyclerViewModelCallbackObserver
{
    void loadMoreStoryComments();
    void storyCommentClicked(StoryCommentBinderModel storyCommentBinderModel);
}

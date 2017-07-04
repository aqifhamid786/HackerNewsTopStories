package com.aqif.hackernews.screens.storycomments.recycler.viewmodel;

import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackObservable;

import java.util.List;

/**
 * Created by aqifhamid.
 */

public interface IStoryCommentsRecyclerViewModel
{
    IStoryCommentsRecyclerViewModelCallbackObservable getStoryCommentsRecyclerViewModelObservable();
    void updateStoryCommentsDataForTag(List<StoryCommentBinderModel> storyCommentBinderModels, Object tag, boolean isAllDataLoaded);

    void stopLoadingDataAtListEnd();
    void onActivityDestroyCalled();

}

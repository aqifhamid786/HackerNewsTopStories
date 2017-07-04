package com.aqif.hackernews.screens.topstories.recycler.viewmodel;

import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackObservable;

import java.util.List;

/**
 * Created by aqifhamid.
 */

public interface ITopStoriesRecyclerViewModel
{
    ITopStoriesRecyclerViewModelCallbackObservable getTopStoriesRecyclerViewModelObserver();
    void setRecyclerViewData(List<TopStoryBinderModel> topStoryBinderModels, boolean isAllDataLoaded);
    void onLoadDataFailed();
    void onActivityDestroyCalled();

}

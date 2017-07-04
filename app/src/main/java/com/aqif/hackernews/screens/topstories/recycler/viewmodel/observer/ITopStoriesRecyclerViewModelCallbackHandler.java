package com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;

/**
 * Created by aqifhamid.
 */
public interface ITopStoriesRecyclerViewModelCallbackHandler extends ITopStoriesRecyclerViewModelCallbackObservable
{
    void notifyLoadMoreTopStories();
    void notifyTopStoryClicked(TopStoryBinderModel topStoryBinderModel);
}

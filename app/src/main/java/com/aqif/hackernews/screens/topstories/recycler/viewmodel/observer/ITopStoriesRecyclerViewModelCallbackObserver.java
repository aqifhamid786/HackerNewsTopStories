package com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer;

import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;

/**
 * Created by aqifhamid.
 */

public interface ITopStoriesRecyclerViewModelCallbackObserver
{
    void loadMoreTopStories();
    void topStoryClicked(TopStoryBinderModel topStoryBinderModel);
}

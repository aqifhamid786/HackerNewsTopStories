package com.aqif.hackernews.screens.topstories.recycler.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoriesAdapter;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryItem;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackHandler;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackObservable;
import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqifhamid.
 */


public class TopStoriesRecyclerViewModel implements ITopStoriesRecyclerViewModel,
        RecyclerViewScrollToEndObserver.IOnLoadMoreRecycleViewDataListner,
        TopStoryBinderModel.OnTopStoryViewItemClickListener {

    private RecyclerView mRecyclerView;
    private TopStoriesAdapter mTopStoriesAdapter;
    private ITopStoriesRecyclerViewModelCallbackHandler mTopStoriesRecyclerViewModelCallbackHandler;
    private RecyclerViewScrollToEndObserver mRecyclerViewScrollToEndObserver;

    private TopStoryItem mTopStoryLoadingMoreItem;
    private List<TopStoryItem> mTopStoryItems;

    private boolean mIsLastPageLoaded;

    public TopStoriesRecyclerViewModel(RecyclerView recyclerView,
                                       TopStoriesAdapter topStoriesAdapter,
                                       ITopStoriesRecyclerViewModelCallbackHandler topStoriesRecyclerViewModelCallbackHandler,
                                       RecyclerViewScrollToEndObserver recyclerViewScrollToEndObserver)
    {
        this(recyclerView,
                topStoriesAdapter,
                topStoriesRecyclerViewModelCallbackHandler,
                recyclerViewScrollToEndObserver,
                null);
    }

    public TopStoriesRecyclerViewModel(RecyclerView recyclerView,
                                       TopStoriesAdapter topStoriesAdapter,
                                       ITopStoriesRecyclerViewModelCallbackHandler topStoriesRecyclerViewModelCallbackHandler,
                                       RecyclerViewScrollToEndObserver recyclerViewScrollToEndObserver,
                                       List<TopStoryItem> topStoryItems)
    {
        mRecyclerView = recyclerView;
        mTopStoriesAdapter = topStoriesAdapter;
        mTopStoriesRecyclerViewModelCallbackHandler = topStoriesRecyclerViewModelCallbackHandler;
        mRecyclerViewScrollToEndObserver = recyclerViewScrollToEndObserver;



        mTopStoryItems = topStoryItems;
        if(mTopStoryItems==null)
        {
            mTopStoryItems = new ArrayList<>();
        }

        mIsLastPageLoaded = false;

        mTopStoryLoadingMoreItem = new TopStoryItem(null, TopStoryItem.LIST_ITEM_LOADER);

        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.addOnScrollListener(mRecyclerViewScrollToEndObserver);
        mRecyclerView.setAdapter(mTopStoriesAdapter);

        mRecyclerViewScrollToEndObserver.setOnLoadMoreRecycleViewDataListner(this);

    }

    @Override
    public ITopStoriesRecyclerViewModelCallbackObservable getTopStoriesRecyclerViewModelObserver()
    {
        return mTopStoriesRecyclerViewModelCallbackHandler;
    }

    @Override
    public  void onLoadDataFailed()
    {
        mIsLastPageLoaded = true;
        mTopStoryItems.remove(mTopStoryLoadingMoreItem);
        mTopStoriesAdapter.updateData(mTopStoryItems);
        mRecyclerViewScrollToEndObserver.setLoadingData(mIsLastPageLoaded);
    }

    @Override
    public void setRecyclerViewData(List<TopStoryBinderModel> topStoryBinderModels, boolean isLastPage)
    {

        mTopStoryItems.clear();
        if(topStoryBinderModels!=null)
        {
            for(int lop=0; lop<topStoryBinderModels.size(); lop++)
            {
                TopStoryBinderModel topStoryBinderModel = topStoryBinderModels.get(lop);
                if(topStoryBinderModel.isDeleted())
                {
                    continue;
                }
                topStoryBinderModel.setOnTopStoryViewItemClickListener(this);
                mTopStoryItems.add(new TopStoryItem(topStoryBinderModel, TopStoryItem.LIST_ITEM_TOP_STORY));
            }
        }

        mTopStoriesAdapter.updateData(mTopStoryItems);

        mIsLastPageLoaded = isLastPage;
        mRecyclerViewScrollToEndObserver.setLoadingData(mIsLastPageLoaded);

    }

    // Load more at end of the list.
    @Override
    public void onLoadMoreRecycleViewDataListner(int page, int totalItemsCount, RecyclerView view)
    {

        mRecyclerViewScrollToEndObserver.setLoadingData(true);
        if(!mIsLastPageLoaded)
        {
            mRecyclerView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    mTopStoryItems.add(mTopStoryLoadingMoreItem);
                    mTopStoriesAdapter.updateData(mTopStoryItems);
                    mTopStoriesRecyclerViewModelCallbackHandler.notifyLoadMoreTopStories();
                }
            });
        }
    }

    @Override
    public void onTopStoryViewItemClicked(TopStoryBinderModel topStoryBinderModel)
    {
        mTopStoriesRecyclerViewModelCallbackHandler.notifyTopStoryClicked(topStoryBinderModel);
    }

    @Override
    public void onActivityDestroyCalled()
    {
        mTopStoryItems.remove(mTopStoryLoadingMoreItem);
        for(int lop = 0; lop< mTopStoryItems.size(); lop++)
        {
            mTopStoryItems.get(lop).getTopStoryBinderModel().setOnTopStoryViewItemClickListener(null);
        }
        mTopStoryItems.clear();

        mTopStoriesAdapter.updateData(new ArrayList<TopStoryItem>());
        mTopStoriesAdapter = null;

        mRecyclerViewScrollToEndObserver.setOnLoadMoreRecycleViewDataListner(null);
        mRecyclerViewScrollToEndObserver = null;

        mRecyclerView = null;
        mTopStoriesRecyclerViewModelCallbackHandler = null;

    }

}

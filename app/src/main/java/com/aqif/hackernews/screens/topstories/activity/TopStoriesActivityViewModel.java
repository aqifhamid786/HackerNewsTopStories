package com.aqif.hackernews.screens.topstories.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.aqif.hackernews.R;
import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;
import com.aqif.hackernews.screens.constants.ScreensConstants;
import com.aqif.hackernews.screens.storycomments.activity.StoryCommentsActivity;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoryBinderModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.ITopStoriesRecyclerViewModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackObserver;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.ITopStoriesRefreshViewModel;
import com.aqif.hackernews.screens.topstories.refresh.viewmodel.observer.ITopStoriesRefreshViewModelCallbackObserver;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public class TopStoriesActivityViewModel implements ITopStoriesActivityViewModel,
        ITopStoriesRecyclerViewModelCallbackObserver,
        ITopStoriesRefreshViewModelCallbackObserver,
        IHackerNewsApiManagerTopStoriesCallbackObserver,
        IHackerNewsApiManagerItemsBatchProcessorCallbackObserver
{

    private static final int ITEMS_PER_BATCH = 10;

    private AppCompatActivity mActivity;
    private Toolbar mToolbar;
    private TextView mTextView;

    private ITopStoriesRecyclerViewModel mTopStoriesRecyclerViewModel;
    private ITopStoriesRefreshViewModel mTopStoriesRefreshViewModel;
    private IHackerNewsApiManager mHackerNewsApiManager;

    private int[] mTopStoriesIds;
    private ArrayList<TopStoryBinderModel> mTopStoryBinderModels;

    private boolean mIsRefreshing;

    public TopStoriesActivityViewModel(AppCompatActivity activity,
                                       Toolbar toolbar,
                                       TextView textView,
                                       ITopStoriesRecyclerViewModel topStoriesRecyclerViewModel,
                                       ITopStoriesRefreshViewModel topStoriesRefreshViewModel,
                                       IHackerNewsApiManager hackerNewsApiManager)
    {
        this(activity, toolbar, textView,
                topStoriesRecyclerViewModel,
                topStoriesRefreshViewModel,
                hackerNewsApiManager,
                null);
    }

    public TopStoriesActivityViewModel(AppCompatActivity activity,
                                       Toolbar toolbar,
                                       TextView textView,
                                       ITopStoriesRecyclerViewModel topStoriesRecyclerViewModel,
                                       ITopStoriesRefreshViewModel topStoriesRefreshViewModel,
                                       IHackerNewsApiManager hackerNewsApiManager,
                                       ArrayList<TopStoryBinderModel> topStoryBinderModels)
    {
        mActivity = activity;
        mToolbar = toolbar;
        mTextView = textView;

        mIsRefreshing = true;
        mTopStoriesIds = new int[]{};

        mTopStoriesRecyclerViewModel = topStoriesRecyclerViewModel;
        mTopStoriesRecyclerViewModel.getTopStoriesRecyclerViewModelObserver().registerTopStoriesRecyclerViewModelCallbackObserver(TopStoriesActivityViewModel.this);

        mTopStoriesRefreshViewModel = topStoriesRefreshViewModel;
        mTopStoriesRefreshViewModel.getTopStoriesRefreshViewModelObservable().registerTopStoriesRefreshViewModelCallbackObserver(TopStoriesActivityViewModel.this);

        mHackerNewsApiManager = hackerNewsApiManager;

        mTopStoryBinderModels = topStoryBinderModels;
        if(mTopStoryBinderModels == null)
        {
            mTopStoryBinderModels = new ArrayList<>();
        }
    }


    @Override
    public void onActivityCreateCalled(AppCompatActivity activity)
    {
        mHackerNewsApiManager.getHackerNewsApiManagerTopStoriesObservable().registerHackerNewsApiManagerTopStoriesCallbackObserver(TopStoriesActivityViewModel.this);
        activity.setSupportActionBar(mToolbar);

        mIsRefreshing = true;
        mTopStoriesRefreshViewModel.showLoader();
        mHackerNewsApiManager.fetchTopStories();
    }

    @Override
    public void onActivityStartCalled() {}

    @Override
    public void onActivityResumeCalled() {}

    @Override
    public void onActivityPauseCalled() {}

    @Override
    public void onActiviyStopCalled() {}

    @Override
    public void onActivityDestroyCalled()
    {
        mHackerNewsApiManager.clear();

        mTopStoriesRecyclerViewModel.getTopStoriesRecyclerViewModelObserver().unregisterTopStoriesRecyclerViewModelCallbackObserver(TopStoriesActivityViewModel.this);
        mTopStoriesRecyclerViewModel.onActivityDestroyCalled();

        mTopStoriesRefreshViewModel.getTopStoriesRefreshViewModelObservable().unregisterTopStoriesRefreshViewModelCallbackObserver(TopStoriesActivityViewModel.this);
        mTopStoriesRefreshViewModel.onActivityDestroyCalled();
    }

    void setTopStoriesIds(int[] ids)
    {
        mTopStoriesIds = ids;
    }

    int[] getTopStoriesIds()
    {
        return mTopStoriesIds;
    }

    void setRefreshing(boolean isRefreshing)
    {
        mIsRefreshing = isRefreshing;
    }

    boolean isRefreshing()
    {
        return mIsRefreshing;
    }

    @Override
    public boolean onBackPressed()
    {
        return false;
    }

    /******* Recycler View Model Observer calls *******/

    @Override
    public void loadMoreTopStories()
    {
        if(mTopStoriesIds.length > mTopStoryBinderModels.size())
        {
            processNextBatchOfTopStories();
        }
    }

    @Override
    public void topStoryClicked(TopStoryBinderModel topStoryBinderModel)
    {

        ArrayList<Integer> kidsArrayList = new ArrayList<>();

        if(topStoryBinderModel.getKidsIds()!=null)
        {
            kidsArrayList.addAll(topStoryBinderModel.getKidsIds());
        }

        Intent intent = new Intent(mActivity, StoryCommentsActivity.class);
        intent.putExtra(ScreensConstants.Top_Stories__Intnent__Key_Top_Stories_Title, topStoryBinderModel.getTitle());
        intent.putIntegerArrayListExtra(ScreensConstants.Top_Stories__Intnent__Key_Top_Kids_Ids, kidsArrayList);

        mActivity.startActivity(intent);

    }

    void processNextBatchOfTopStories()
    {

        int startIndex = mTopStoryBinderModels.size();
        int remainingItemsCounts = mTopStoriesIds.length - mTopStoryBinderModels.size();
        int batchSize = remainingItemsCounts > ITEMS_PER_BATCH ? ITEMS_PER_BATCH : remainingItemsCounts;

        int[] itemIds = new int[batchSize];
        for(int lop=0; lop<batchSize; lop++)
        {
            itemIds[lop] = mTopStoriesIds[startIndex+lop];
        }

        mHackerNewsApiManager.fetchItemsInABatch(itemIds, 0);
    }

    /******* (Pull to) Refresh View Model Observer calls *******/

    @Override
    public void onRefresh()
    {
        mHackerNewsApiManager.getHackerNewsApiManagerItemsBatchProcessorObservable().unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(TopStoriesActivityViewModel.this);
        mHackerNewsApiManager.cancelAllRequests();

        mIsRefreshing = true;

        mTopStoryBinderModels.clear();
        mTopStoriesRecyclerViewModel.setRecyclerViewData(mTopStoryBinderModels, true);

        mTextView.setText(mActivity.getResources().getString(R.string.refreshing_top_stories));
        mTextView.setVisibility(View.VISIBLE);

        mHackerNewsApiManager.fetchTopStories();
    }

    /******* (Pull to) Api Manager Top Stories Observer calls *******/

    @Override
    public void onTopStoriesSuccess(int[] storiesIds)
    {
        mTopStoriesIds = storiesIds;
        mHackerNewsApiManager.getHackerNewsApiManagerItemsBatchProcessorObservable().registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(TopStoriesActivityViewModel.this);
        processNextBatchOfTopStories();
    }

    @Override
    public void onTopStoriesFailure(String message)
    {
        mTextView.setText(message);
        mTextView.setVisibility(View.VISIBLE);

        if(mIsRefreshing)
        {
            mTopStoriesRefreshViewModel.hideLoader();
            mIsRefreshing = false;
        }

        mTopStoriesRecyclerViewModel.onLoadDataFailed();

    }

    /******* Api Manager Items Batch Observer calls *******/


    @Override
    public void onItemsBatchSuccess(ArrayList<ItemDao> items, Object tag)
    {
        for(int lop=0; lop<items.size(); lop++)
        {
            TopStoryBinderModel topStoryBinderModel = new TopStoryBinderModel(items.get(lop).getId(),
                    items.get(lop).getTitle(),
                    items.get(lop).getScore(),
                    items.get(lop).getBy(),
                    items.get(lop).getDescendants(),
                    items.get(lop).getKids(),
                    items.get(lop).getDeleted());
            mTopStoryBinderModels.add(topStoryBinderModel);
        }

        if(mIsRefreshing)
        {
            mTextView.setVisibility(View.INVISIBLE);
            mTopStoriesRefreshViewModel.hideLoader();
            mIsRefreshing = false;
        }

        mTopStoriesRecyclerViewModel.setRecyclerViewData(mTopStoryBinderModels, mTopStoryBinderModels.size() == mTopStoriesIds.length);
    }

    @Override
    public void onItemsBatchFailure(String message, Object tag)
    {
        // For now cancel all loadings and populate data with what we have.
        if(mTopStoryBinderModels.size()==0)
        {
            mTextView.setText(message);
            mTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            mTextView.setVisibility(View.INVISIBLE);
        }
        mTopStoriesRecyclerViewModel.setRecyclerViewData(mTopStoryBinderModels, mTopStoryBinderModels.size() == mTopStoriesIds.length);
        mTopStoriesRecyclerViewModel.onLoadDataFailed();

        if(mIsRefreshing)
        {
            mTopStoriesRefreshViewModel.hideLoader();
            mIsRefreshing = false;
        }
    }
}





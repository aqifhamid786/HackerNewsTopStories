package com.aqif.hackernews.screens.storycomments.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqif.hackernews.R;
import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;
import com.aqif.hackernews.screens.constants.ScreensConstants;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.IStoryCommentsRecyclerViewModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aqifhamid.
 */

public class StoryCommentsActivityViewModel implements IStoryCommentsActivityViewModel,
        IStoryCommentsRecyclerViewModelCallbackObserver,
        IHackerNewsApiManagerItemsBatchProcessorCallbackObserver
{

    private static final int COMMENTS_PER_PAGE = 10;

    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private TextView mEmptyListTextView;
    private TextView mTopStoryTitleTextView;

    private IStoryCommentsRecyclerViewModel mStoryCommentsRecyclerViewModel;
    private IHackerNewsApiManager mHackerNewsApiManager;

    private int mTotalCommentsLoadedCount;
    private List<Integer> mCommentsIds;
    private HashMap<String, ArrayList<StoryCommentBinderModel>> mTagToCommentDataHashMap;

    public StoryCommentsActivityViewModel(Toolbar toolbar,
                                          ProgressBar progressBar,
                                          TextView emptyListTextView,
                                          TextView topStoryTitleTextView,
                                          IStoryCommentsRecyclerViewModel storyCommentsRecyclerViewModel,
                                          IHackerNewsApiManager hackerNewsApiManager

    )
    {
        this(toolbar,
                progressBar,
                emptyListTextView,
                topStoryTitleTextView,
                storyCommentsRecyclerViewModel,
                hackerNewsApiManager,
                null,
                null);
    }
    public StoryCommentsActivityViewModel(Toolbar toolbar,
                                          ProgressBar progressBar,
                                          TextView emptyListTextView,
                                          TextView topStoryTitleTextView,
                                          IStoryCommentsRecyclerViewModel storyCommentsRecyclerViewModel,
                                          IHackerNewsApiManager hackerNewsApiManager,
                                          List<Integer> commentsIds,
                                          HashMap<String, ArrayList<StoryCommentBinderModel>> tagToCommentDataHashMap)
    {
        mToolbar = toolbar;

        mProgressBar = progressBar;
        mEmptyListTextView = emptyListTextView;
        mTopStoryTitleTextView = topStoryTitleTextView;

        mStoryCommentsRecyclerViewModel = storyCommentsRecyclerViewModel;
        mStoryCommentsRecyclerViewModel.getStoryCommentsRecyclerViewModelObservable().registerStoryCommentsRecyclerViewModelCallbackObserver(this);

        mHackerNewsApiManager = hackerNewsApiManager;
        mHackerNewsApiManager.getHackerNewsApiManagerItemsBatchProcessorObservable().registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(StoryCommentsActivityViewModel.this);

        mTagToCommentDataHashMap = tagToCommentDataHashMap;
        if(mTagToCommentDataHashMap == null)
        {
            mTagToCommentDataHashMap = new HashMap<>();
        }

        mCommentsIds = commentsIds;
        if(mCommentsIds == null)
        {
            mCommentsIds = new ArrayList<>();
        }
    }

    @Override
    public void onActivityCreateCalled(AppCompatActivity activity)
    {
        activity.setSupportActionBar(mToolbar);

        mTopStoryTitleTextView.setText(activity.getIntent().getStringExtra(ScreensConstants.Top_Stories__Intnent__Key_Top_Stories_Title));
        mCommentsIds.addAll(activity.getIntent().getIntegerArrayListExtra(ScreensConstants.Top_Stories__Intnent__Key_Top_Kids_Ids));

        if(mCommentsIds.size()==0)
        {
            mEmptyListTextView.setText(activity.getResources().getString(R.string.no_story_comments_message));
            mProgressBar.setVisibility(View.INVISIBLE);
            mStoryCommentsRecyclerViewModel.stopLoadingDataAtListEnd();
        }
        else
        {
            processNextBatchOfBaseComments();
        }
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
        mHackerNewsApiManager.getHackerNewsApiManagerItemsBatchProcessorObservable().unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(StoryCommentsActivityViewModel.this);

        mStoryCommentsRecyclerViewModel.getStoryCommentsRecyclerViewModelObservable().unregisterStoryCommentsRecyclerViewModelCallbackObserver(StoryCommentsActivityViewModel.this);
        mStoryCommentsRecyclerViewModel.onActivityDestroyCalled();
    }

    void setTotalCommentsLoadedCount(int totalCommentsLoadedCount)
    {
        mTotalCommentsLoadedCount = totalCommentsLoadedCount;
    }


    @Override
    public boolean onBackPressed()
    {
        return false;
    }

    void processNextBatchOfBaseComments() // base level comments.
    {
        int startIndex = mTotalCommentsLoadedCount;
        int remainingItemsCounts = mCommentsIds.size() - mTotalCommentsLoadedCount;
        int batchSize = remainingItemsCounts > COMMENTS_PER_PAGE ? COMMENTS_PER_PAGE : remainingItemsCounts;

        int[] itemIds = new int[batchSize];
        for(int lop=0; lop<batchSize; lop++)
        {
            itemIds[lop] = mCommentsIds.get(startIndex+lop);
        }

        mHackerNewsApiManager.fetchItemsInABatch(itemIds, ScreensConstants.Item_Request_Base_Tag);
    }

    /******* Recycler View Model Observer calls *******/

    @Override
    public void loadMoreStoryComments()
    {
        if(mCommentsIds.size() > mTotalCommentsLoadedCount)
        {
            processNextBatchOfBaseComments();
        }
    }

    @Override
    public void storyCommentClicked(StoryCommentBinderModel storyCommentBinderModel)
    {
        String tag = storyCommentBinderModel.getTag();
        if(mTagToCommentDataHashMap.containsKey(tag))
        {
            mStoryCommentsRecyclerViewModel.updateStoryCommentsDataForTag(mTagToCommentDataHashMap.get(tag), tag, mTotalCommentsLoadedCount == mCommentsIds.size());
        }
        else
        {
            int[] ids = new int[storyCommentBinderModel.getKidsIds().size()];
            for (int lop= 0; lop<ids.length; lop++)
            {
                ids[lop] = storyCommentBinderModel.getKidsIds().get(lop);
            }
            mHackerNewsApiManager.fetchItemsInABatch(ids, tag);
        }
    }

    /******* Api Manager Items Batch Observer calls *******/

    @Override
    public void onItemsBatchSuccess(ArrayList<ItemDao> items, Object tag)
    {

        String tagString = tag.toString();

        ArrayList<StoryCommentBinderModel> storyCommentBinderModels = new ArrayList<>();
        for(int lop=0; lop<items.size(); lop++)
        {
            String storyCommentBinderModelTag = String.format("%s%d_", tagString, tag.equals(ScreensConstants.Item_Request_Base_Tag) ? mTotalCommentsLoadedCount+lop+1 : lop+1);
            int storyCommentBinderModelLevel = getCharacterOccurrences(storyCommentBinderModelTag, '_')-2;

            StoryCommentBinderModel storyCommentBinderModel = new StoryCommentBinderModel(items.get(lop).getId(),
                    items.get(lop).getText(),
                    items.get(lop).getBy(),
                    items.get(lop).getKids(),
                    storyCommentBinderModelLevel,
                    storyCommentBinderModelTag,
                    items.get(lop).getDeleted());

            storyCommentBinderModels.add(storyCommentBinderModel);
        }

        if(mTagToCommentDataHashMap.containsKey(tagString))
        {
            mTagToCommentDataHashMap.get(tagString).addAll(storyCommentBinderModels);
        }
        else
        {
            mTagToCommentDataHashMap.put(tagString, storyCommentBinderModels);
        }

        if(tag.equals(ScreensConstants.Item_Request_Base_Tag))
        {
            mTotalCommentsLoadedCount += items.size();
        }

        if(mProgressBar.getVisibility()==View.VISIBLE)
        {
            mProgressBar.setVisibility(View.INVISIBLE);
            mEmptyListTextView.setVisibility(View.INVISIBLE);
        }

        mStoryCommentsRecyclerViewModel.updateStoryCommentsDataForTag(storyCommentBinderModels, tag, mTotalCommentsLoadedCount == mCommentsIds.size());

    }

    int getCharacterOccurrences(String data, char c)
    {
        int occurrences = 0;
        for(int lop=0; lop<data.length(); lop++)
        {
            if(data.charAt(lop)==c)
            {
                occurrences++;
            }
        }
        return occurrences;
    }

    @Override
    public void onItemsBatchFailure(String message, Object tag)
    {
        if(mTotalCommentsLoadedCount==0)
        {
            mEmptyListTextView.setText(message);
            mEmptyListTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            mEmptyListTextView.setVisibility(View.INVISIBLE);
            mStoryCommentsRecyclerViewModel.stopLoadingDataAtListEnd();
        }

        if(mProgressBar.getVisibility()==View.VISIBLE)
        {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}





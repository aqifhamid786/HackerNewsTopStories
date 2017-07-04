package com.aqif.hackernews.screens.storycomments.recycler.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;
import com.aqif.hackernews.screens.constants.ScreensConstants;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentItem;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentsAdapter;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackHandler;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqifhamid.
 */


public class StoryCommentsRecyclerViewModel implements IStoryCommentsRecyclerViewModel,
        RecyclerViewScrollToEndObserver.IOnLoadMoreRecycleViewDataListner,
        StoryCommentBinderModel.IOnStoryCommentViewItemClickListener{

    private RecyclerView mRecyclerView;
    private StoryCommentsAdapter mStoryCommentsAdapter;
    private IStoryCommentsRecyclerViewModelCallbackHandler mStoryCommentsRecyclerViewModelCallbackHandler;
    private RecyclerViewScrollToEndObserver mRecyclerViewScrollToEndObserver;

    private List<String> mTagsOfExpandedComments;

    private StoryCommentItem mMoreLoaderStoryCommentsItem;
    private List<StoryCommentItem> mStoryCommentItems;
    private boolean mIsLastPageLoaded;

    public StoryCommentsRecyclerViewModel(RecyclerView recyclerView,
                                          StoryCommentsAdapter storyCommentsAdapter,
                                          IStoryCommentsRecyclerViewModelCallbackHandler storyCommentsRecyclerViewModelCallbackHandler,
                                          RecyclerViewScrollToEndObserver recyclerViewScrollToEndObserver)
    {
        this(recyclerView,
                storyCommentsAdapter,
                storyCommentsRecyclerViewModelCallbackHandler,
                recyclerViewScrollToEndObserver,
                null, null);
    }

    public StoryCommentsRecyclerViewModel(RecyclerView recyclerView,
                                          StoryCommentsAdapter storyCommentsAdapter,
                                          IStoryCommentsRecyclerViewModelCallbackHandler storyCommentsRecyclerViewModelCallbackHandler,
                                          RecyclerViewScrollToEndObserver recyclerViewScrollToEndObserver,
                                          List<StoryCommentItem> storyCommentItems,
                                          List<String> tagsOfExpandedComments)
    {

        mRecyclerView = recyclerView;
        mStoryCommentsAdapter = storyCommentsAdapter;
        mRecyclerViewScrollToEndObserver = recyclerViewScrollToEndObserver;
        mStoryCommentsRecyclerViewModelCallbackHandler = storyCommentsRecyclerViewModelCallbackHandler;

        mStoryCommentItems = storyCommentItems;
        if(mStoryCommentItems==null)
        {
            mStoryCommentItems = new ArrayList<>();
        }

        mTagsOfExpandedComments = tagsOfExpandedComments;
        if(mTagsOfExpandedComments==null)
        {
            mTagsOfExpandedComments = new ArrayList<>();
        }

        mIsLastPageLoaded = false;

        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.addOnScrollListener(mRecyclerViewScrollToEndObserver);
        mRecyclerView.setAdapter(mStoryCommentsAdapter);

        mRecyclerViewScrollToEndObserver.setOnLoadMoreRecycleViewDataListner(this);

        mMoreLoaderStoryCommentsItem = new StoryCommentItem(StoryCommentItem.LIST_ITEM_LOADER);

    }



    @Override
    public IStoryCommentsRecyclerViewModelCallbackObservable getStoryCommentsRecyclerViewModelObservable()
    {
        return mStoryCommentsRecyclerViewModelCallbackHandler;
    }

    @Override
    public  void stopLoadingDataAtListEnd()
    {
        mIsLastPageLoaded = true;
        mRecyclerViewScrollToEndObserver.setLoadingData(mIsLastPageLoaded);
        mStoryCommentItems.remove(mMoreLoaderStoryCommentsItem);
        mStoryCommentsAdapter.updateData(mStoryCommentItems);
    }

    @Override
    public void updateStoryCommentsDataForTag(List<StoryCommentBinderModel> storyCommentBinderModels, Object tag, boolean isLastPage)
    {

        String tagString = tag.toString();
        if(!mTagsOfExpandedComments.contains(tagString) && !tagString.equals(ScreensConstants.Item_Request_Base_Tag))
        {
            return;
        }

        mTagsOfExpandedComments.remove(tagString);

        ArrayList<StoryCommentItem> storyCommentItems = new ArrayList<>();
        if(storyCommentBinderModels!=null)
        {
            for(int lop=0; lop<storyCommentBinderModels.size(); lop++)
            {
                StoryCommentBinderModel storyCommentBinderModel = storyCommentBinderModels.get(lop);
                if(storyCommentBinderModel.isDeleted())
                {
                    // Since server has deleted this item. We shouldn't display it. (Values are null)
                    continue;
                }
                storyCommentBinderModel.setOnStoryCommentViewItemClickListener(this);
                storyCommentItems.add(new StoryCommentItem(storyCommentBinderModel, StoryCommentItem.LIST_ITEM_COMMENT));
            }
        }

        mIsLastPageLoaded = isLastPage;
        mStoryCommentItems.remove(mMoreLoaderStoryCommentsItem);

        int parentIndex = 0;
        for(; parentIndex<mStoryCommentItems.size(); parentIndex++)
        {
            if(mStoryCommentItems.get(parentIndex).getStoryCommentBinderModel().getTag().equals(tagString))
            {
                mStoryCommentItems.get(parentIndex).getStoryCommentBinderModel().collapsed(false);
                break;
            }
        }
        parentIndex+=1;

        if(parentIndex < mStoryCommentItems.size())
        {
            mStoryCommentItems.addAll(parentIndex, storyCommentItems);
        }
        else
        {
            mStoryCommentItems.addAll(storyCommentItems);
        }

        mStoryCommentsAdapter.updateData(mStoryCommentItems);
        mRecyclerViewScrollToEndObserver.setLoadingData(mIsLastPageLoaded);
    }


    @Override
    public void onStoryCommentViewItemClicked(StoryCommentBinderModel storyCommentBinderModel, boolean isCollapsed)
    {

        if(storyCommentBinderModel.getKidsIds() == null || storyCommentBinderModel.getKidsIds().size()==0)
        {
            return;
        }

        if(isCollapsed)
        {
            mTagsOfExpandedComments.add(storyCommentBinderModel.getTag());
            mStoryCommentsRecyclerViewModelCallbackHandler.notifyStoryCommentClicked(storyCommentBinderModel);
        }
        else
        {
            ArrayList<StoryCommentItem> storyCommentItemsToRemove = new ArrayList<>();
            for(int lop = 0; lop < mStoryCommentItems.size(); lop++)
            {
                if(storyCommentBinderModel.isChildTag(mStoryCommentItems.get(lop).getStoryCommentBinderModel().getTag()))
                {
                    storyCommentItemsToRemove.add(mStoryCommentItems.get(lop));
                }
            }
            storyCommentBinderModel.collapsed(true);

            mTagsOfExpandedComments.remove(storyCommentBinderModel.getTag());
            mStoryCommentItems.removeAll(storyCommentItemsToRemove);
            mStoryCommentsAdapter.updateData(mStoryCommentItems);
            storyCommentItemsToRemove.clear();

        }
    }

    /********* Load more at end of list  ***********/
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
                    mStoryCommentItems.add(mMoreLoaderStoryCommentsItem);
                    mStoryCommentsAdapter.updateData(mStoryCommentItems);
                    mStoryCommentsRecyclerViewModelCallbackHandler.notifyLoadMoreStoryComments();
                }
            });
        }
    }

    @Override
    public void onActivityDestroyCalled()
    {

        mStoryCommentItems.remove(mMoreLoaderStoryCommentsItem);

        for(int lop=0; lop<mStoryCommentItems.size(); lop++)
        {
            mStoryCommentItems.get(lop).getStoryCommentBinderModel().setOnStoryCommentViewItemClickListener(null);
        }

        mStoryCommentItems.clear();
        mStoryCommentsAdapter.updateData(new ArrayList<StoryCommentItem>());

        mRecyclerViewScrollToEndObserver.setOnLoadMoreRecycleViewDataListner(null);
        mRecyclerViewScrollToEndObserver = null;

        mRecyclerView = null;
        mStoryCommentsRecyclerViewModelCallbackHandler = null;
    }

}

package com.aqif.hackernews.restapi.hackernews.manager;

import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsCallbackBatchProcessorObservable;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchRequest;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchResponse;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.IHackerNewsItemsBatchProcessor;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRequestHandler;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public class HackerNewsApiManager implements IHackerNewsApiManager
        , IHackerNewsTopStoriesCallbackObserver
        , IHackerNewsItemsBatchProcessorCallbackObserver
{

    private IHackerNewsApiManagerTopStoriesCallbackHandler mHackerNewsApiManagerTopStoriesCallbackHandler;
    private IHackerNewsApiManagerItemsBatchProcessorCallbackHandler mHackerNewsApiManagerItemsBatchProcessorCallbackHandler;

    private IHackerNewsTopStoriesRequestHandler mHackerNewsTopStoriesRequestHandler;
    private IHackerNewsItemsBatchProcessor mHackerNewsItemsBatchProcessor;

    ArrayList<HackerNewsItemsBatchRequest> mBatchRequestsSentOut;

    public HackerNewsApiManager(IHackerNewsTopStoriesRequestHandler hackerNewsTopStoriesRequestHandler
            , IHackerNewsItemsBatchProcessor hackerNewsItemsBatchProcessor
            , IHackerNewsApiManagerTopStoriesCallbackHandler hackerNewsApiManagerTopStoriesCallbackHandler
            , IHackerNewsApiManagerItemsBatchProcessorCallbackHandler hackerNewsApiManagerItemsBatchProcessorCallbackHandler)
    {
        this(hackerNewsTopStoriesRequestHandler,
                hackerNewsItemsBatchProcessor,
                hackerNewsApiManagerTopStoriesCallbackHandler,
                hackerNewsApiManagerItemsBatchProcessorCallbackHandler,
                null);
    }

    public HackerNewsApiManager(IHackerNewsTopStoriesRequestHandler hackerNewsTopStoriesRequestHandler
            , IHackerNewsItemsBatchProcessor hackerNewsItemsBatchProcessor
            , IHackerNewsApiManagerTopStoriesCallbackHandler hackerNewsApiManagerTopStoriesCallbackHandler
            , IHackerNewsApiManagerItemsBatchProcessorCallbackHandler hackerNewsApiManagerItemsBatchProcessorCallbackHandler
            , ArrayList<HackerNewsItemsBatchRequest> batchRequestsSentOutArrayList)
    {

        mBatchRequestsSentOut = batchRequestsSentOutArrayList;
        if(mBatchRequestsSentOut==null)
        {
            mBatchRequestsSentOut = new ArrayList<>();
        }

        mHackerNewsTopStoriesRequestHandler = hackerNewsTopStoriesRequestHandler;
        mHackerNewsTopStoriesRequestHandler.getHackerNewsTopStoriesCallbackObservable().registerHackerNewsTopStoriesCallbackObserver(HackerNewsApiManager.this);

        mHackerNewsItemsBatchProcessor = hackerNewsItemsBatchProcessor;
        mHackerNewsItemsBatchProcessor.getHackerNewsItemsBatchProcessorCallbackObservable().registerHackerNewsItemsBatchProcessorCallbackObservable(HackerNewsApiManager.this);

        mHackerNewsApiManagerTopStoriesCallbackHandler = hackerNewsApiManagerTopStoriesCallbackHandler;
        mHackerNewsApiManagerItemsBatchProcessorCallbackHandler = hackerNewsApiManagerItemsBatchProcessorCallbackHandler;

    }

    @Override
    public IHackerNewsApiManagerTopStoriesCallbackObservable getHackerNewsApiManagerTopStoriesObservable()
    {
        return mHackerNewsApiManagerTopStoriesCallbackHandler;
    }

    @Override
    public IHackerNewsApiManagerItemsCallbackBatchProcessorObservable getHackerNewsApiManagerItemsBatchProcessorObservable()
    {
        return mHackerNewsApiManagerItemsBatchProcessorCallbackHandler;
    }

    @Override
    public void fetchTopStories()
    {
        mHackerNewsTopStoriesRequestHandler.performHackerNewsTopStoriesGet();
    }

    @Override
    public void fetchItemsInABatch(int[] itemIds, Object tag)
    {
        HackerNewsItemsBatchRequest hackerNewsItemsBatchRequest = new HackerNewsItemsBatchRequest(itemIds, tag);
        mBatchRequestsSentOut.add(hackerNewsItemsBatchRequest);

        mHackerNewsItemsBatchProcessor.processItemsBatchRequest(hackerNewsItemsBatchRequest);
    }

    @Override
    public void cancelAllRequests()
    {
        mHackerNewsTopStoriesRequestHandler.cancel();
        mHackerNewsItemsBatchProcessor.cancelAllBatches();
    }

    @Override
    public void clear()
    {
        mHackerNewsApiManagerTopStoriesCallbackHandler.clear();
        mHackerNewsApiManagerItemsBatchProcessorCallbackHandler.clear();

        mHackerNewsTopStoriesRequestHandler.getHackerNewsTopStoriesCallbackObservable().unregisterHackerNewsTopStoriesCallbackObserver(HackerNewsApiManager.this);
        mHackerNewsTopStoriesRequestHandler.clear();

        mHackerNewsItemsBatchProcessor.getHackerNewsItemsBatchProcessorCallbackObservable().unregisterHackerNewsItemsBatchProcessorCallbackObservable(HackerNewsApiManager.this);
        mHackerNewsItemsBatchProcessor.cancelAllBatches();
    }

    /********* HackerNews Top Stories callbacks ***********/

    @Override
    public void onHackerNewsTopStoriesSuccess(int[] storiesIds, Object tag)
    {
        mHackerNewsApiManagerTopStoriesCallbackHandler.notifyTopStoriesSuccess(storiesIds);
    }

    @Override
    public void onHackerNewsTopStoriesFailed(String message, Object tag)
    {
        mHackerNewsApiManagerTopStoriesCallbackHandler.notifyTopStoriesFailure(message);
    }

    /********* HackerNews Items Batch Processor callback ***********/

    @Override
    public void onItemsBatchSuccess(HackerNewsItemsBatchResponse hackerNewsItemsBatchResponse)
    {
        mHackerNewsApiManagerItemsBatchProcessorCallbackHandler.notifyItemsBatchSuccess(hackerNewsItemsBatchResponse.getItemsDaos(), hackerNewsItemsBatchResponse.getTag());
    }

    @Override
    public void onItemsBatchFailure(String message, Object tag)
    {
        mHackerNewsApiManagerItemsBatchProcessorCallbackHandler.notifyItemsBatchFailure(message, tag);
    }
}

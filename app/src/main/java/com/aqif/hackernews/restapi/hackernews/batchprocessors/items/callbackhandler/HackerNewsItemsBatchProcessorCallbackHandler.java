package com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchResponse;

import java.util.List;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemsBatchProcessorCallbackHandler implements IHackerNewsItemsBatchProcessorCallbackHandler
{
    protected List<IHackerNewsItemsBatchProcessorCallbackObserver> mHackerNewsItemsBatchProcessorCallbackObservers;

    public HackerNewsItemsBatchProcessorCallbackHandler(List<IHackerNewsItemsBatchProcessorCallbackObserver> hackerNewsItemsBatchProcessorCallbackObservers)
    {
        mHackerNewsItemsBatchProcessorCallbackObservers = hackerNewsItemsBatchProcessorCallbackObservers;
    }

    @Override
    public void registerHackerNewsItemsBatchProcessorCallbackObservable(IHackerNewsItemsBatchProcessorCallbackObserver hackerNewsItemsBatchProcessorCallbackObserver)
    {
        if(hackerNewsItemsBatchProcessorCallbackObserver!=null && !mHackerNewsItemsBatchProcessorCallbackObservers.contains(hackerNewsItemsBatchProcessorCallbackObserver))
        {
            mHackerNewsItemsBatchProcessorCallbackObservers.add(hackerNewsItemsBatchProcessorCallbackObserver);
        }
    }

    @Override
    public void unregisterHackerNewsItemsBatchProcessorCallbackObservable(IHackerNewsItemsBatchProcessorCallbackObserver hackerNewsItemsBatchProcessorCallbackObserver)
    {
        if(hackerNewsItemsBatchProcessorCallbackObserver!=null && mHackerNewsItemsBatchProcessorCallbackObservers.contains(hackerNewsItemsBatchProcessorCallbackObserver))
        {
            mHackerNewsItemsBatchProcessorCallbackObservers.remove(hackerNewsItemsBatchProcessorCallbackObserver);
        }
    }

    @Override
    public void notifyItemsBatchSuccess(HackerNewsItemsBatchResponse hackerNewsItemsBatchResponse)
    {
        for(int lop = 0; lop< mHackerNewsItemsBatchProcessorCallbackObservers.size(); lop++)
        {
            mHackerNewsItemsBatchProcessorCallbackObservers.get(lop).onItemsBatchSuccess(hackerNewsItemsBatchResponse);
        }
    }

    @Override
    public void notifyItemsBatchFailure(String message, Object tag)
    {
        for(int lop = 0; lop< mHackerNewsItemsBatchProcessorCallbackObservers.size(); lop++)
        {
            mHackerNewsItemsBatchProcessorCallbackObservers.get(lop).onItemsBatchFailure(message, tag);
        }
    }

    @Override
    public void clear()
    {
        mHackerNewsItemsBatchProcessorCallbackObservers.clear();
    }
}

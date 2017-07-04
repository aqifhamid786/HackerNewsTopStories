package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackHandler;

import java.util.List;

/**
 * Created by aqifhamid.
 */

public class HackerNewsApiManagerTopStoriesCallbackHandler implements IHackerNewsApiManagerTopStoriesCallbackHandler
{

    protected List<IHackerNewsApiManagerTopStoriesCallbackObserver> mHackerNewsApiManagerTopStoriesCallbackObservers;

    public HackerNewsApiManagerTopStoriesCallbackHandler(List<IHackerNewsApiManagerTopStoriesCallbackObserver> hackerNewsApiManagerTopStoriesCallbackObservers)
    {
        mHackerNewsApiManagerTopStoriesCallbackObservers = hackerNewsApiManagerTopStoriesCallbackObservers;
    }

    @Override
    public void registerHackerNewsApiManagerTopStoriesCallbackObserver(
            IHackerNewsApiManagerTopStoriesCallbackObserver hackerNewsApiManagerTopStoriesCallbackObserver)
    {
        if(hackerNewsApiManagerTopStoriesCallbackObserver!=null &&
                !mHackerNewsApiManagerTopStoriesCallbackObservers.contains(hackerNewsApiManagerTopStoriesCallbackObserver))
        {
            mHackerNewsApiManagerTopStoriesCallbackObservers.add(hackerNewsApiManagerTopStoriesCallbackObserver);
        }
    }

    @Override
    public void unregisterHackerNewsApiManagerTopStoriesCallbackObserver(
            IHackerNewsApiManagerTopStoriesCallbackObserver hackerNewsApiManagerTopStoriesCallbackObserver)
    {
        if(hackerNewsApiManagerTopStoriesCallbackObserver!=null &&
                mHackerNewsApiManagerTopStoriesCallbackObservers.contains(hackerNewsApiManagerTopStoriesCallbackObserver))
        {
            mHackerNewsApiManagerTopStoriesCallbackObservers.remove(hackerNewsApiManagerTopStoriesCallbackObserver);
        }
    }

    @Override
    public void notifyTopStoriesSuccess(int[] storiesIds)
    {
        for(int lop = 0; lop< mHackerNewsApiManagerTopStoriesCallbackObservers.size(); lop++)
        {
            mHackerNewsApiManagerTopStoriesCallbackObservers.get(lop).onTopStoriesSuccess(storiesIds);
        }
    }

    @Override
    public void notifyTopStoriesFailure(String message) 
    {
        for(int lop = 0; lop< mHackerNewsApiManagerTopStoriesCallbackObservers.size(); lop++)
        {
            mHackerNewsApiManagerTopStoriesCallbackObservers.get(lop).onTopStoriesFailure(message);
        }
    }

    @Override
    public void clear()
    {
        mHackerNewsApiManagerTopStoriesCallbackObservers.clear();
    }

}

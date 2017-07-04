package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqifhamid.
 */

public class HackerNewsApiManagerItemsBatchProcessorCallbackHandler implements IHackerNewsApiManagerItemsBatchProcessorCallbackHandler
{
    protected List<IHackerNewsApiManagerItemsBatchProcessorCallbackObserver> mHackerNewsApiManagerItemsBatchProcessorCallbackObservers;

    public HackerNewsApiManagerItemsBatchProcessorCallbackHandler(List<IHackerNewsApiManagerItemsBatchProcessorCallbackObserver> hackerNewsApiManagerItemsBatchProcessorCallbackObservers)
    {
        mHackerNewsApiManagerItemsBatchProcessorCallbackObservers = hackerNewsApiManagerItemsBatchProcessorCallbackObservers;
    }

    @Override
    public void registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(
            IHackerNewsApiManagerItemsBatchProcessorCallbackObserver hackerNewsApiManagerItemsBatchProcessorCallbackObserver)
    {
        if(hackerNewsApiManagerItemsBatchProcessorCallbackObserver!=null &&
                !mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.contains(hackerNewsApiManagerItemsBatchProcessorCallbackObserver))
        {
            mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.add(hackerNewsApiManagerItemsBatchProcessorCallbackObserver);
        }
    }

    @Override
    public void unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(
            IHackerNewsApiManagerItemsBatchProcessorCallbackObserver hackerNewsApiManagerItemsBatchProcessorCallbackObserver)
    {
        if(hackerNewsApiManagerItemsBatchProcessorCallbackObserver!=null &&
                mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.contains(hackerNewsApiManagerItemsBatchProcessorCallbackObserver))
        {
            mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.remove(hackerNewsApiManagerItemsBatchProcessorCallbackObserver);
        }
    }

    @Override
    public void notifyItemsBatchSuccess(ArrayList<ItemDao> items, Object tag)
    {
        for(int lop = 0; lop< mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.size(); lop++)
        {
            mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.get(lop).onItemsBatchSuccess(items, tag);
        }
    }

    @Override
    public void notifyItemsBatchFailure(String message, Object tag)
    {
        for(int lop = 0; lop< mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.size(); lop++)
        {
            mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.get(lop).onItemsBatchFailure(message, tag);
        }
    }


    @Override
    public void clear()
    {
        mHackerNewsApiManagerItemsBatchProcessorCallbackObservers.clear();
    }
}

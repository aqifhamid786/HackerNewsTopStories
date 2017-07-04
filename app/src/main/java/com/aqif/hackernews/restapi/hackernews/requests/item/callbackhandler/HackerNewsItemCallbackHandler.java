package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.HackerNewsConstants;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemCallbackHandler implements IHackerNewsItemCallbackHandler
{

    private Object mTag;
    private List<IHackerNewsItemCallbackObserver> mHackerNewsItemCallbackObservers;

    public HackerNewsItemCallbackHandler(List<IHackerNewsItemCallbackObserver> hackerNewsItemCallbackObservers)
    {
        mHackerNewsItemCallbackObservers = hackerNewsItemCallbackObservers;
    }

    @Override
    public void registerHackerNewsItemCallbackObserver(IHackerNewsItemCallbackObserver hackerNewsItemCallbackObserver)
    {
        if(hackerNewsItemCallbackObserver!=null && !mHackerNewsItemCallbackObservers.contains(hackerNewsItemCallbackObserver))
        {
            mHackerNewsItemCallbackObservers.add(hackerNewsItemCallbackObserver);
        }
    }

    @Override
    public void unregisterHackerNewsItemCallbackObserver(IHackerNewsItemCallbackObserver hackerNewsItemCallbackObserver)
    {
        if(hackerNewsItemCallbackObserver!=null && mHackerNewsItemCallbackObservers.contains(hackerNewsItemCallbackObserver))
        {
            mHackerNewsItemCallbackObservers.remove(hackerNewsItemCallbackObserver);
        }
    }

    @Override
    public void onResponse(Call<ItemDao> call, Response<ItemDao> response)
    {

        if(response.body()!=null)
        {

            for(int lop = 0; lop< mHackerNewsItemCallbackObservers.size(); lop++)
            {
                mHackerNewsItemCallbackObservers.get(lop).onHackerNewsItemSuccess(response.body(), mTag);
            }
        }
        else
        {
            // We received response from server. But there is some internal server error.
            for(int lop = 0; lop< mHackerNewsItemCallbackObservers.size(); lop++)
            {
                mHackerNewsItemCallbackObservers.get(lop).onHackerNewsItemFailed(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage, mTag);
            }
        }
    }

    @Override
    public void onFailure(Call<ItemDao> call, Throwable t)
    {
        for(int lop = 0; lop< mHackerNewsItemCallbackObservers.size(); lop++)
        {
            mHackerNewsItemCallbackObservers.get(lop).onHackerNewsItemFailed(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage, mTag);
        }
    }

    @Override
    public void setTag(Object tag)
    {
        mTag = tag;
    }

    public void clear()
    {
        mHackerNewsItemCallbackObservers.clear();
        mTag = null;
    }
}

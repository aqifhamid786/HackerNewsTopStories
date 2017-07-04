package com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler;

import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemRequestHandler implements IHackerNewsItemRequestHandler
{
    private IHackerNewsItemRetrofitService mHackerNewsItemRetrofitService;
    private IHackerNewsItemCallbackHandler mHackerNewsItemCallbackHandler;
    private Call<ItemDao> mRequest;
    private Object mTag;

    public HackerNewsItemRequestHandler(IHackerNewsItemRetrofitService hackerNewsItemRetrofitService
            , IHackerNewsItemCallbackHandler hackerNewsItemCallbackHandler)
    {
        mHackerNewsItemRetrofitService = hackerNewsItemRetrofitService;
        mHackerNewsItemCallbackHandler = hackerNewsItemCallbackHandler;
    }

    @Override
    public IHackerNewsItemCallbackObservable getHackerNewsItemCallbackObservable()
    {
        return mHackerNewsItemCallbackHandler;
    }

    @Override
    public void performHackerNewsItemGet(int itemId)
    {
        mRequest = mHackerNewsItemRetrofitService.getItem(itemId);
        mRequest.enqueue(mHackerNewsItemCallbackHandler);
    }

    @Override
    public void clear()
    {
        mHackerNewsItemCallbackHandler.clear();
        cancel();
    }

    @Override
    public void cancel()
    {
        if(mRequest!=null && !mRequest.isExecuted())
        {
            mRequest.cancel();
            mRequest = null;
        }
        this.mTag = null;
    }

    @Override
    public Object getTag()
    {
        return mTag;
    }

    @Override
    public void setTag(Object tag)
    {
        this.mTag = tag;
        this.mHackerNewsItemCallbackHandler.setTag(mTag);
    }

}

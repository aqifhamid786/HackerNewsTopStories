package com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler;

import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackObservable;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Created by aqifhamid.
 */

public class HackerNewsTopStoriesRequestHandler implements IHackerNewsTopStoriesRequestHandler
{

    private IHackerNewsTopStoriesRetrofitService mHackerNewsTopStoriesRetrofitService;
    private IHackerNewsTopStoriesCallbackHandler mHackerNewsTopStoriesCallbackHandler;
    private Call<int[]> mRequest;
    private Object mTag;

    public HackerNewsTopStoriesRequestHandler(IHackerNewsTopStoriesRetrofitService hackerNewsTopStoriesRetrofitService
            , IHackerNewsTopStoriesCallbackHandler hackerNewsTopStoriesCallbackHandler)
    {
        mHackerNewsTopStoriesRetrofitService = hackerNewsTopStoriesRetrofitService;
        mHackerNewsTopStoriesCallbackHandler = hackerNewsTopStoriesCallbackHandler;
    }

    @Override
    public IHackerNewsTopStoriesCallbackObservable getHackerNewsTopStoriesCallbackObservable()
    {
        return mHackerNewsTopStoriesCallbackHandler;
    }

    @Override
    public void performHackerNewsTopStoriesGet()
    {
        mRequest = mHackerNewsTopStoriesRetrofitService.getHackerNewsTopStories();
        mRequest.enqueue(mHackerNewsTopStoriesCallbackHandler);
    }
    @Override
    public void clear()
    {
        mHackerNewsTopStoriesCallbackHandler.clear();
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
        mTag = null;
    }

    @Override
    public Object getTag()
    {
        return mTag;
    }

    @Override
    public void setTag(Object tag)
    {
        mTag = tag;
        mHackerNewsTopStoriesCallbackHandler.setTag(mTag);
    }

}

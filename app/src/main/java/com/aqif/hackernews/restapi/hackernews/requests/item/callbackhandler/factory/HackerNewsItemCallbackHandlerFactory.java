package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.factory;

import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.HackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackObserver;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemCallbackHandlerFactory implements IHackerNewsItemCallbackHandlerFactory
{
    @Override
    public IHackerNewsItemCallbackHandler getHackerNewsItemCallbackHandlerInstance()
    {

        return new HackerNewsItemCallbackHandler(new ArrayList<IHackerNewsItemCallbackObserver>());
    }
}

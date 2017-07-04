package com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.HackerNewsConstants;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchRequest;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchResponse;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class HackerNewsItemsBatchProcessorCallbackHandlerTest
{

    @Mock private IHackerNewsItemsBatchProcessorCallbackObserver mObserverA;
    @Mock private IHackerNewsItemsBatchProcessorCallbackObserver mObserverB;
    private ArrayList<IHackerNewsItemsBatchProcessorCallbackObserver> mObservers;
    private HackerNewsItemsBatchProcessorCallbackHandler mCallbackHandler;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new HackerNewsItemsBatchProcessorCallbackHandler(mObservers);
    }

    @After
    public void afterTest()
    {
        mObservers.clear();
        mCallbackHandler = null;
    }

    @Test
    public void registerHackerNewsItemsBatchProcessorCallbackObservable() throws Exception
    {
        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(mObserverA);
        assertEquals(mObservers.size(),2);
    }

    @Test
    public void unregisterHackerNewsItemsBatchProcessorCallbackObservable() throws Exception
    {
        mCallbackHandler.unregisterHackerNewsItemsBatchProcessorCallbackObservable(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(mObserverA);
        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(mObserverB);

        mCallbackHandler.unregisterHackerNewsItemsBatchProcessorCallbackObservable(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsItemsBatchProcessorCallbackObservable(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsItemsBatchProcessorCallbackObservable(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsItemsBatchProcessorCallbackObservable(mObserverB);
        assertEquals(mObservers.size(),0);
    }

    @Test
    public void notifyItemsBatchSuccess() throws Exception
    {

        IHackerNewsItemsBatchProcessorCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(observer);

        HackerNewsItemsBatchRequest request = new HackerNewsItemsBatchRequest(new int[]{}, new Object());
        HackerNewsItemsBatchResponse response = new HackerNewsItemsBatchResponse(request, new ArrayList<ItemDao>());

        mCallbackHandler.notifyItemsBatchSuccess(response);

        Mockito.verify(observer).onItemsBatchSuccess(Mockito.eq(response));
    }

    @Test
    public void notifyItemsBatchFailure() throws Exception
    {
        IHackerNewsItemsBatchProcessorCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(observer);

        Object tag = new Object();
        mCallbackHandler.notifyItemsBatchFailure(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage, tag);

        Mockito.verify(observer).onItemsBatchFailure(Mockito.eq(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage), Mockito.eq(tag));
    }


    @Test
    public void clear() throws Exception
    {
        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(mObserverA);
        mCallbackHandler.registerHackerNewsItemsBatchProcessorCallbackObservable(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }


}
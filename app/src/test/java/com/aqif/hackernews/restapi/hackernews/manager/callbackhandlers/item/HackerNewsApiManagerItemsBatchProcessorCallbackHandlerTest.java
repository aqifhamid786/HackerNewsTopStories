package com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item;

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

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class HackerNewsApiManagerItemsBatchProcessorCallbackHandlerTest {

    @Mock private IHackerNewsApiManagerItemsBatchProcessorCallbackObserver mObserverA;
    @Mock private IHackerNewsApiManagerItemsBatchProcessorCallbackObserver mObserverB;
    private ArrayList<IHackerNewsApiManagerItemsBatchProcessorCallbackObserver> mObservers;
    private HackerNewsApiManagerItemsBatchProcessorCallbackHandler mCallbackHandler;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new HackerNewsApiManagerItemsBatchProcessorCallbackHandler(mObservers);
    }

    @After
    public void afterTest()
    {
        mObservers.clear();
        mCallbackHandler = null;
    }

    @Test
    public void registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver() throws Exception
    {
        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),2);
    }

    @Test
    public void unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver() throws Exception
    {
        mCallbackHandler.unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverB);

        mCallbackHandler.unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),0);
    }

    @Test
    public void notifyItemsBatchSuccess() throws Exception
    {
        IHackerNewsApiManagerItemsBatchProcessorCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(observer);

        ArrayList<ItemDao> items = new ArrayList<>();

        Mockito.verify(observer, Mockito.times(0)).onItemsBatchSuccess(Mockito.eq(items), Mockito.any());
        mCallbackHandler.notifyItemsBatchSuccess(items, null);
        Mockito.verify(observer).onItemsBatchSuccess(Mockito.eq(items), Mockito.any());
    }

    @Test
    public void notifyItemsBatchFailure() throws Exception
    {

        IHackerNewsApiManagerItemsBatchProcessorCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(observer);

        mCallbackHandler.notifyItemsBatchFailure("", null);
        Mockito.verify(observer).onItemsBatchFailure(Mockito.anyString(), Mockito.any());
        mCallbackHandler.notifyItemsBatchFailure("", null);
        Mockito.verify(observer, Mockito.times(2)).onItemsBatchFailure(Mockito.anyString(), Mockito.any());

    }

    @Test
    public void clear()
    {
        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsApiManagerItemsBatchProcessorCallbackObserver(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }

}
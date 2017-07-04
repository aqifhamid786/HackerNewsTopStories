package com.aqif.hackernews.restapi.hackernews.manager;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchRequest;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.HackerNewsItemsBatchResponse;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.IHackerNewsItemsBatchProcessor;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.topstories.IHackerNewsApiManagerTopStoriesCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler.IHackerNewsTopStoriesCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.requests.topstories.requesthandler.IHackerNewsTopStoriesRequestHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class HackerNewsApiManagerTest
{

    private HackerNewsApiManager mHackerNewsApiManager;

    @Mock private IHackerNewsTopStoriesRequestHandler mHackerNewsTopStoriesRequestHandler;
    @Mock private IHackerNewsItemsBatchProcessor mHackerNewsItemsBatchProcessor;
    @Mock private IHackerNewsApiManagerTopStoriesCallbackHandler mHackerNewsApiManagerTopStoriesCallbackHandler;
    @Mock private IHackerNewsApiManagerItemsBatchProcessorCallbackHandler mHackerNewsApiManagerItemsBatchProcessorCallbackHandler;

    ArrayList<HackerNewsItemsBatchRequest> mBatchRequestsSentOut;

    @Before
    public void beforeTest()
    {
        mBatchRequestsSentOut = new ArrayList<>();

        IHackerNewsTopStoriesCallbackObservable hackerNewsTopStoriesCallbackObservable = Mockito.mock(IHackerNewsTopStoriesCallbackObservable.class);
        Mockito.when(mHackerNewsTopStoriesRequestHandler.getHackerNewsTopStoriesCallbackObservable()).thenReturn(hackerNewsTopStoriesCallbackObservable);

        IHackerNewsItemsBatchProcessorCallbackObservable hackerNewsItemsBatchProcessorCallbackObservable = Mockito.mock(IHackerNewsItemsBatchProcessorCallbackObservable.class);
        Mockito.when(mHackerNewsItemsBatchProcessor.getHackerNewsItemsBatchProcessorCallbackObservable()).thenReturn(hackerNewsItemsBatchProcessorCallbackObservable);


        mHackerNewsApiManager = new HackerNewsApiManager(mHackerNewsTopStoriesRequestHandler,
                mHackerNewsItemsBatchProcessor,
                mHackerNewsApiManagerTopStoriesCallbackHandler,
                mHackerNewsApiManagerItemsBatchProcessorCallbackHandler,
                mBatchRequestsSentOut);

    }

    @After
    public void afterTest()
    {
        mHackerNewsApiManager = null;

        mBatchRequestsSentOut.clear();
        mBatchRequestsSentOut = null;
    }

    @Test
    public void getHackerNewsApiManagerTopStoriesObservable() throws Exception
    {
        assertNotNull(mHackerNewsApiManagerTopStoriesCallbackHandler);
        assertEquals(mHackerNewsApiManagerTopStoriesCallbackHandler,mHackerNewsApiManager.getHackerNewsApiManagerTopStoriesObservable());
    }

    @Test
    public void getHackerNewsApiManagerItemsBatchProcessorObservable() throws Exception
    {
        assertNotNull(mHackerNewsApiManagerItemsBatchProcessorCallbackHandler);
        assertEquals(mHackerNewsApiManagerItemsBatchProcessorCallbackHandler, mHackerNewsApiManager.getHackerNewsApiManagerItemsBatchProcessorObservable());
    }

    @Test
    public void fetchTopStories() throws Exception
    {
        mHackerNewsApiManager.fetchTopStories();
        Mockito.verify(mHackerNewsTopStoriesRequestHandler).performHackerNewsTopStoriesGet();
    }

    @Test
    public void fetchItemsInABatch() throws Exception
    {
        int[] testItemsIds = new int[]{};
        Object tag = new Object();
        mHackerNewsApiManager.fetchItemsInABatch(testItemsIds, tag);
        Mockito.verify(mHackerNewsItemsBatchProcessor).processItemsBatchRequest(Mockito.<HackerNewsItemsBatchRequest>any());

        assertEquals(mBatchRequestsSentOut.size(), 1);

    }

    @Test
    public void cancelAllRequests() throws Exception
    {
        mHackerNewsApiManager.cancelAllRequests();

        Mockito.verify(mHackerNewsTopStoriesRequestHandler).cancel();
        Mockito.verify(mHackerNewsItemsBatchProcessor).cancelAllBatches();
    }

    @Test
    public void clear() throws Exception
    {
        mHackerNewsApiManager.clear();
        Mockito.verify(mHackerNewsApiManagerTopStoriesCallbackHandler).clear();
        Mockito.verify(mHackerNewsApiManagerItemsBatchProcessorCallbackHandler).clear();
        Mockito.verify(mHackerNewsTopStoriesRequestHandler).clear();
        Mockito.verify(mHackerNewsItemsBatchProcessor).cancelAllBatches();

    }

    @Test
    public void onHackerNewsTopStoriesSuccess() throws Exception
    {
        int[] testItemsIds = new int[]{};
        Object tag = new Object();
        mHackerNewsApiManager.onHackerNewsTopStoriesSuccess(testItemsIds, tag);
        Mockito.verify(mHackerNewsApiManagerTopStoriesCallbackHandler).notifyTopStoriesSuccess(Mockito.eq(testItemsIds));

    }

    @Test
    public void onHackerNewsTopStoriesFailed() throws Exception
    {

        Object tag = new Object();
        mHackerNewsApiManager.onHackerNewsTopStoriesFailed("", tag);
        Mockito.verify(mHackerNewsApiManagerTopStoriesCallbackHandler).notifyTopStoriesFailure(Mockito.anyString());
    }

    @Test
    public void onItemsBatchSuccess() throws Exception
    {

        Object tag = new Object();
        ArrayList<ItemDao> items = new ArrayList<>();
        HackerNewsItemsBatchResponse hackerNewsItemsBatchResponse = Mockito.mock(HackerNewsItemsBatchResponse.class);
        Mockito.when(hackerNewsItemsBatchResponse.getItemsDaos()).thenReturn(items);
        Mockito.when(hackerNewsItemsBatchResponse.getTag()).thenReturn(tag);

        mHackerNewsApiManager.onItemsBatchSuccess(hackerNewsItemsBatchResponse);
        Mockito.verify(mHackerNewsApiManagerItemsBatchProcessorCallbackHandler).notifyItemsBatchSuccess
                (Mockito.eq(items), Mockito.any());

    }

    @Test
    public void onItemsBatchFailure() throws Exception
    {
        Object tag = new Object();
        mHackerNewsApiManager.onItemsBatchFailure("", tag);

        Mockito.verify(mHackerNewsApiManagerItemsBatchProcessorCallbackHandler).notifyItemsBatchFailure
                (Mockito.anyString(), Mockito.any());
    }

}
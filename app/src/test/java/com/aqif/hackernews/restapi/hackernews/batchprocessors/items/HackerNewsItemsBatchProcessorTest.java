package com.aqif.hackernews.restapi.hackernews.batchprocessors.items;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.HackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory.IHackerNewsItemRequestHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

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
public class HackerNewsItemsBatchProcessorTest
{

    private HackerNewsItemsBatchProcessor mHackerNewsItemsBatchProcessor;

    @Mock private IHackerNewsItemRequestHandlerFactory mHackerNewsItemRequestHandlerFactory;
    @Mock private IHackerNewsItemsBatchProcessorCallbackHandler mHackerNewsItemsBatchProcessorCallbackHandler;
    private ArrayList<HackerNewsItemsBatchRequest> mHackerNewsItemsBatchRequests;
    private ArrayList<IHackerNewsItemRequestHandler> mHackerNewsItemRequestHandlers;
    private ArrayList<ItemDao> mResponseDaoItems;

    @Before
    public void beforeTest()
    {

        IHackerNewsItemCallbackObservable hackerNewsItemCallbackObservable = Mockito.mock(IHackerNewsItemCallbackObservable.class);
        IHackerNewsItemRequestHandler hackerNewsItemRequestHandler = Mockito.mock(IHackerNewsItemRequestHandler.class);

        Mockito.when(hackerNewsItemRequestHandler.getHackerNewsItemCallbackObservable()).thenReturn(hackerNewsItemCallbackObservable);
        Mockito.when(mHackerNewsItemRequestHandlerFactory.getHackerNewsItemRequestHandlerInstance()).thenReturn(hackerNewsItemRequestHandler);


        mHackerNewsItemsBatchRequests = Mockito.spy(new ArrayList<HackerNewsItemsBatchRequest>());
        mHackerNewsItemRequestHandlers = Mockito.spy(new ArrayList<IHackerNewsItemRequestHandler>());
        mResponseDaoItems = Mockito.spy(new ArrayList<ItemDao>());

        mHackerNewsItemsBatchProcessor = new HackerNewsItemsBatchProcessor(mHackerNewsItemsBatchProcessorCallbackHandler,
                mHackerNewsItemRequestHandlerFactory,
                mHackerNewsItemRequestHandlers,
                mHackerNewsItemsBatchRequests,
                mResponseDaoItems);

    }

    @After
    public void afterTest()
    {
        mHackerNewsItemsBatchProcessor = null;

        mHackerNewsItemRequestHandlers.clear();
        mHackerNewsItemRequestHandlers = null;

        mHackerNewsItemsBatchRequests.clear();
        mHackerNewsItemsBatchRequests = null;

        mResponseDaoItems.clear();
        mResponseDaoItems = null;
    }

    @Test
    public void getItemsBatchProcessorObservable() throws Exception
    {
        assertNotNull(mHackerNewsItemsBatchProcessorCallbackHandler);
        assertEquals(mHackerNewsItemsBatchProcessorCallbackHandler, mHackerNewsItemsBatchProcessor.getHackerNewsItemsBatchProcessorCallbackObservable());
    }


    @Test
    public void processItemsBatchRequest()
    {
        Object tag = new Object();
        int[] ids = new int[]{14681561,14681213,14675493,14680457,14678473,14679876,14676505,14670245,14679905,14669709};

        HackerNewsItemsBatchRequest requestA = Mockito.mock(HackerNewsItemsBatchRequest.class);
        Mockito.when(requestA.getItemIds()).thenReturn(ids);

        HackerNewsItemsBatchRequest requestB = Mockito.mock(HackerNewsItemsBatchRequest.class);

        mHackerNewsItemsBatchProcessor.processItemsBatchRequest(null);
        Mockito.verify(mHackerNewsItemsBatchRequests, Mockito.times(0)).add(Mockito.<HackerNewsItemsBatchRequest>any());

        mHackerNewsItemsBatchProcessor.processItemsBatchRequest(requestA);
        Mockito.verify(mHackerNewsItemsBatchRequests).add(Mockito.<HackerNewsItemsBatchRequest>any());

        mHackerNewsItemsBatchProcessor.processItemsBatchRequest(requestB);
        Mockito.verify(mHackerNewsItemsBatchRequests, Mockito.times(2)).add(Mockito.<HackerNewsItemsBatchRequest>any());

    }


    @Test
    public void tryProcessingNextBatch() throws Exception
    {

        mHackerNewsItemsBatchProcessor.tryProcessingNextBatch();
        Mockito.verify(mResponseDaoItems, Mockito.times(0)).clear(); // this means we didn't pass into base if condition because no items are there in queue.

        Object tag = new Object();
        int[] ids = new int[]{14681561,14681213,14675493,14680457,14678473,14679876,14676505,14670245,14679905,14669709};

        HackerNewsItemsBatchRequest requestTemp = new HackerNewsItemsBatchRequest(ids, tag);
        HackerNewsItemsBatchRequest requestA = Mockito.spy(requestTemp);

        mHackerNewsItemsBatchRequests.add(requestA);
        mHackerNewsItemsBatchProcessor.tryProcessingNextBatch();
        Mockito.verify(requestA).getItemIds();    // fetched all ids
        Mockito.verify(mHackerNewsItemRequestHandlerFactory, Mockito.times(ids.length)).getHackerNewsItemRequestHandlerInstance(); // genreated requests against all batches

        HackerNewsItemsBatchRequest requestB = Mockito.mock(HackerNewsItemsBatchRequest.class);
        mHackerNewsItemsBatchRequests.add(requestB);
        mHackerNewsItemsBatchProcessor.tryProcessingNextBatch();
        Mockito.verify(requestB, Mockito.times(0)).getItemIds(); // this means we didn't pass into base if condition because a request is already in process

    }

    @Test
    public void cancelAllBatches() throws Exception
    {
        mHackerNewsItemsBatchProcessor.cancelAllBatches();
        Mockito.verify(mHackerNewsItemsBatchRequests).clear();
    }

    @Test
    public void clear() throws Exception
    {

        mHackerNewsItemsBatchProcessor.clear();
        Mockito.verify(mHackerNewsItemsBatchProcessorCallbackHandler).clear();
    }

    @Test
    public void clearCurrentBatch() throws Exception
    {

        int requestCount = 5;
        for(int lop=0; lop<requestCount; lop++)
        {
            IHackerNewsItemCallbackObservable observable = Mockito.mock(IHackerNewsItemCallbackObservable.class);
            HackerNewsItemRequestHandler request = Mockito.mock(HackerNewsItemRequestHandler.class);
            mHackerNewsItemRequestHandlers.add(request);
        }

        mHackerNewsItemsBatchProcessor.clearCurrentBatch();

        Mockito.verify(mHackerNewsItemRequestHandlers, Mockito.times(requestCount)).get(Mockito.anyInt());
        Mockito.verify(mHackerNewsItemRequestHandlers).clear();

        Mockito.verify(mResponseDaoItems).clear();
    }

    @Test
    public void processBatchIfCompleted() throws Exception
    {

        Object tag = new Object();
        int[] ids = new int[]{14681561,14681213,14675493,14680457,14678473,14679876,14676505,14670245,14679905,14669709};

        // adding a request to be processed.
        HackerNewsItemsBatchRequest request = new HackerNewsItemsBatchRequest(ids, tag);
        mHackerNewsItemsBatchProcessor.processItemsBatchRequest(request);

        int requestCount = 5;
        for(int lop=0; lop<ids.length; lop++)
        {
            assertFalse(mHackerNewsItemsBatchProcessor.processBatchIfCompleted());
            mResponseDaoItems.add(new ItemDao());
        }

        assertTrue(mHackerNewsItemsBatchProcessor.processBatchIfCompleted()); // since we have supplied mock response for each request. thus we should be able to process now.
        assertEquals(mHackerNewsItemsBatchRequests.size(),0);

    }

    @Test
    public void onHackerNewsItemSuccess() throws Exception
    {

        Object tag = new Object();
        int[] ids = new int[]{14681561,14681213,14675493,14680457,14678473,14679876,14676505,14670245,14679905,14669709};
        HackerNewsItemsBatchRequest request = new HackerNewsItemsBatchRequest(ids, tag);
        mHackerNewsItemsBatchProcessor.processItemsBatchRequest(request);

        assertEquals(mResponseDaoItems.size(), 0);
        mHackerNewsItemsBatchProcessor.onHackerNewsItemSuccess(new ItemDao(), null);
        assertEquals(mResponseDaoItems.size(), 1);
        mHackerNewsItemsBatchProcessor.onHackerNewsItemSuccess(new ItemDao(), new Object());
        assertEquals(mResponseDaoItems.size(), 2);
        mHackerNewsItemsBatchProcessor.onHackerNewsItemSuccess(new ItemDao(), null);
        assertEquals(mResponseDaoItems.size(), 3);
    }

    @Test
    public void onHackerNewsItemFailed() throws Exception
    {
        Mockito.verify(mHackerNewsItemsBatchProcessorCallbackHandler, Mockito.times(0)).notifyItemsBatchFailure(Mockito.anyString(), Mockito.any());
        mHackerNewsItemsBatchProcessor.onHackerNewsItemFailed("", null);
        Mockito.verify(mHackerNewsItemsBatchProcessorCallbackHandler).notifyItemsBatchFailure(Mockito.anyString(), Mockito.any());
        mHackerNewsItemsBatchProcessor.onHackerNewsItemFailed("", new Object());
        Mockito.verify(mHackerNewsItemsBatchProcessorCallbackHandler, Mockito.times(2)).notifyItemsBatchFailure(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void clearHackerNewsItemRequestHandlerForTag() throws Exception
    {
        mHackerNewsItemsBatchProcessor.clearHackerNewsItemRequestHandlerForTag(null);
        Mockito.verify(mHackerNewsItemRequestHandlers, Mockito.times(0)).remove(Mockito.any());
        mHackerNewsItemsBatchProcessor.clearHackerNewsItemRequestHandlerForTag(new Object());
        Mockito.verify(mHackerNewsItemRequestHandlers, Mockito.times(0)).remove(Mockito.any());

        Object tag = new Object();
        IHackerNewsItemCallbackObservable observable = Mockito.mock(IHackerNewsItemCallbackObservable.class);

        HackerNewsItemRequestHandler request = Mockito.mock(HackerNewsItemRequestHandler.class);
        Mockito.when(request.getHackerNewsItemCallbackObservable()).thenReturn(observable);
        Mockito.when(request.getTag()).thenReturn(tag);
        mHackerNewsItemRequestHandlers.add(request);

        mHackerNewsItemsBatchProcessor.clearHackerNewsItemRequestHandlerForTag(tag);
        Mockito.verify(mHackerNewsItemRequestHandlers).remove(request);
        assertEquals(mHackerNewsItemRequestHandlers.size(), 0);

    }

}
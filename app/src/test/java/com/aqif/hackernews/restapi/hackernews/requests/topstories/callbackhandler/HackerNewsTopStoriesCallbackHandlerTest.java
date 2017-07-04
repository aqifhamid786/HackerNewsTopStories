package com.aqif.hackernews.restapi.hackernews.requests.topstories.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.HackerNewsConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;


/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class HackerNewsTopStoriesCallbackHandlerTest
{

    @Mock private IHackerNewsTopStoriesCallbackObserver mObserverA;
    @Mock private IHackerNewsTopStoriesCallbackObserver mObserverB;
    private ArrayList<IHackerNewsTopStoriesCallbackObserver> mObservers;
    private HackerNewsTopStoriesCallbackHandler mCallbackHandler;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new HackerNewsTopStoriesCallbackHandler(mObservers);
    }

    @After
    public void afterTest()
    {
        mObservers.clear();
        mCallbackHandler = null;
    }

    @Test
    public void registerHackerNewsTopStoriesCallbackObserver() throws Exception
    {

        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),2);

    }

    @Test
    public void unregisterHackerNewsTopStoriesCallbackObserver() throws Exception
    {

        mCallbackHandler.unregisterHackerNewsTopStoriesCallbackObserver(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(mObserverB);

        mCallbackHandler.unregisterHackerNewsTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsTopStoriesCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsTopStoriesCallbackObserver(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsTopStoriesCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),0);
    }


    @Test
    public void onResponse() throws Exception
    {

        IHackerNewsTopStoriesCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(observer);

        Object tag = new Object();
        mCallbackHandler.setTag(tag);

        int[] mockData = new int[0];
        Call<int[]> call = Mockito.mock(Call.class);

        Response<int[]> responseWithNullBody = Response.success(null); // can not mock Response<T> because it is final class.
        Response<int[]> responseWithBody = Response.success(mockData);

        mCallbackHandler.onResponse(call, responseWithNullBody);
        Mockito.verify(observer).onHackerNewsTopStoriesFailed(Mockito.eq(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage), Mockito.eq(tag));

        mCallbackHandler.onResponse(call, responseWithNullBody);
        Mockito.verify(observer, Mockito.times(2)).onHackerNewsTopStoriesFailed(Mockito.eq(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage), Mockito.eq(tag));

        mCallbackHandler.onResponse(call, responseWithBody);
        Mockito.verify(observer).onHackerNewsTopStoriesSuccess(Mockito.eq(mockData), Mockito.eq(tag));

        mCallbackHandler.onResponse(call, responseWithBody);
        Mockito.verify(observer, Mockito.times(2)).onHackerNewsTopStoriesSuccess(Mockito.eq(mockData), Mockito.eq(tag));

    }

    @Test
    public void onFailure() throws Exception
    {

        IHackerNewsTopStoriesCallbackObserver observer = Mockito.spy(mObserverA);
        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(observer);

        Object tag = new Object();
        mCallbackHandler.setTag(tag);

        Call<int[]> call = Mockito.mock(Call.class);

        mCallbackHandler.onFailure(call, null);
        Mockito.verify(observer).onHackerNewsTopStoriesFailed(Mockito.eq(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage), Mockito.eq(tag));

    }


    @Test
    public void clear()
    {
        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsTopStoriesCallbackObserver(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }


}
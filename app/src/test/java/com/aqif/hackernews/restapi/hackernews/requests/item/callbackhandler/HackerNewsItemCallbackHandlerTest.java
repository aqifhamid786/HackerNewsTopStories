package com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler;

import com.aqif.hackernews.restapi.hackernews.HackerNewsConstants;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

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
public class HackerNewsItemCallbackHandlerTest
{

    @Mock private IHackerNewsItemCallbackObserver mObserverA;
    @Mock private IHackerNewsItemCallbackObserver mObserverB;
    private ArrayList<IHackerNewsItemCallbackObserver> mObservers;
    private HackerNewsItemCallbackHandler mCallbackHandler;

    @Before
    public void beforeTest()
    {
        mObservers = new ArrayList<>();
        mCallbackHandler = new HackerNewsItemCallbackHandler(mObservers);
    }

    @After
    public void afterTest()
    {
        mObservers.clear();
        mCallbackHandler = null;
    }

    @Test
    public void registerHackerNewsItemCallbackObserver() throws Exception
    {
        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsItemCallbackObserver(null);
        assertEquals(mObservers.size(),2);

        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),2);
    }

    @Test
    public void unregisterHackerNewsItemCallbackObserver() throws Exception
    {
        mCallbackHandler.unregisterHackerNewsItemCallbackObserver(null);
        assertEquals(mObservers.size(),0);

        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverB);

        mCallbackHandler.unregisterHackerNewsItemCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsItemCallbackObserver(mObserverA);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsItemCallbackObserver(null);
        assertEquals(mObservers.size(),1);

        mCallbackHandler.unregisterHackerNewsItemCallbackObserver(mObserverB);
        assertEquals(mObservers.size(),0);
    }

    @Test
    public void onResponse() throws Exception
    {
        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverA);

        Object tag = new Object();
        mCallbackHandler.setTag(tag);

        ItemDao item = new ItemDao();
        Call<ItemDao> call = Mockito.mock(Call.class);

        Response<ItemDao> responseWithNullBody = Response.success(null);
        Response<ItemDao> responseWithBody = Response.success(item);

        mCallbackHandler.onResponse(call, responseWithNullBody);
        Mockito.verify(mObserverA).onHackerNewsItemFailed(Mockito.eq(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage), Mockito.eq(tag));

        mCallbackHandler.onResponse(call, responseWithNullBody);
        Mockito.verify(mObserverA, Mockito.times(2)).onHackerNewsItemFailed(Mockito.eq(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage), Mockito.eq(tag));

        mCallbackHandler.onResponse(call, responseWithBody);
        Mockito.verify(mObserverA).onHackerNewsItemSuccess(Mockito.eq(item), Mockito.eq(tag));

        mCallbackHandler.onResponse(call, responseWithBody);
        Mockito.verify(mObserverA, Mockito.times(2)).onHackerNewsItemSuccess(Mockito.eq(item), Mockito.eq(tag));
    }

    @Test
    public void onFailure() throws Exception
    {
        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverA);

        Object tag = new Object();
        mCallbackHandler.setTag(tag);

        Call<ItemDao> call = Mockito.mock(Call.class);

        mCallbackHandler.onFailure(call, null);
        Mockito.verify(mObserverA).onHackerNewsItemFailed(Mockito.eq(HackerNewsConstants.HackerNewsGenericNetworkErrorMessage), Mockito.eq(tag));

    }

    @Test
    public void clear()
    {
        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverA);
        mCallbackHandler.registerHackerNewsItemCallbackObserver(mObserverB);
        assertEquals(mObservers.size(), 2);
        mCallbackHandler.clear();
        assertEquals(mObservers.size(), 0);
    }


}
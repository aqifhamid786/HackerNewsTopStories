package com.aqif.hackernews.screens.topstories.recycler.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by aqifhamid.
 */
@RunWith(MockitoJUnitRunner.class)
public class TopStoryItemTest
{

    @Mock private TopStoryBinderModel mTopStoryBinderModel;
    private TopStoryItem mTopStoryItem;

    @Before
    public void beforeTest() throws Exception
    {
        mTopStoryItem = new TopStoryItem(mTopStoryBinderModel, TopStoryItem.LIST_ITEM_LOADER);
    }

    @After
    public void afterTest() throws Exception
    {
        mTopStoryItem = null;
    }

    @Test
    public void getTopStoryBinderModel() throws Exception
    {
        assertNotNull(mTopStoryBinderModel);
        assertEquals(mTopStoryBinderModel, mTopStoryItem.getTopStoryBinderModel());
    }

    @Test
    public void getType() throws Exception
    {
        assertEquals(TopStoryItem.LIST_ITEM_LOADER, mTopStoryItem.getType());
    }

}
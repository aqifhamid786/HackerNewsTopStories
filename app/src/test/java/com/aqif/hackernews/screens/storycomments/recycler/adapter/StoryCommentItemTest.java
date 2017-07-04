package com.aqif.hackernews.screens.storycomments.recycler.adapter;

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
public class StoryCommentItemTest
{

    @Mock
    private StoryCommentBinderModel mStoryCommentBinderModel;
    private StoryCommentItem mStoryCommentItem;

    @Before
    public void beforeTest() throws Exception
    {
        mStoryCommentItem = new StoryCommentItem(mStoryCommentBinderModel, StoryCommentItem.LIST_ITEM_LOADER);
    }

    @After
    public void afterTest() throws Exception
    {
        mStoryCommentBinderModel = null;
    }

    @Test
    public void getStoryCommentBinderModel() throws Exception
    {
        assertNotNull(mStoryCommentBinderModel);
        assertEquals(mStoryCommentBinderModel, mStoryCommentItem.getStoryCommentBinderModel());
    }

    @Test
    public void getType() throws Exception
    {
        assertEquals(StoryCommentItem.LIST_ITEM_LOADER, mStoryCommentItem.getType());
    }

}
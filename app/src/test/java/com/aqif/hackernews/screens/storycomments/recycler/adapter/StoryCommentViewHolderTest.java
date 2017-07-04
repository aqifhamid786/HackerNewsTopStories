package com.aqif.hackernews.screens.storycomments.recycler.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class StoryCommentViewHolderTest
{

    @Mock
    private View view;
    @Mock private ViewDataBinding mViewDataBinding;
    private StoryCommentViewHolder mStoryCommentViewHolder;

    @Before
    public void beforeTest() throws Exception
    {
        Mockito.when(mViewDataBinding.getRoot()).thenReturn(view);
        mStoryCommentViewHolder = new StoryCommentViewHolder(mViewDataBinding);
    }

    @After
    public void afterTest() throws Exception {}

    @Test
    public void getViewDataBinding() throws Exception
    {
        Mockito.verify(mViewDataBinding).executePendingBindings();
        assertNotNull(mViewDataBinding);
        assertEquals(mStoryCommentViewHolder.getViewDataBinding(), mViewDataBinding);

    }

}
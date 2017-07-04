package com.aqif.hackernews.screens.topstories.recycler.adapter;

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
public class TopStoryViewHolderTest
{

    @Mock private View view;
    @Mock private ViewDataBinding mViewDataBinding;
    private TopStoryViewHolder mTopStoryViewHolder;

    @Before
    public void beforeTest() throws Exception
    {
        Mockito.when(mViewDataBinding.getRoot()).thenReturn(view);
        mTopStoryViewHolder = new TopStoryViewHolder(mViewDataBinding);
    }

    @After
    public void afterTest() throws Exception {}

    @Test
    public void getViewDataBinding() throws Exception
    {
        Mockito.verify(mViewDataBinding).executePendingBindings();
        assertNotNull(mViewDataBinding);
        assertEquals(mTopStoryViewHolder.getViewDataBinding(), mViewDataBinding);

    }

}
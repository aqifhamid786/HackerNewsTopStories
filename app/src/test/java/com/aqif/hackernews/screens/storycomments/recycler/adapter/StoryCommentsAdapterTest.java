package com.aqif.hackernews.screens.storycomments.recycler.adapter;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by aqifhamid.
 */
public class StoryCommentsAdapterTest
{


    @Mock
    private LayoutInflater mLayoutInflater;
    private ArrayList<StoryCommentItem> mStoryCommentItems;
    private StoryCommentsAdapter mStoryCommentsAdapter;

    @Before
    public void beforeTest() throws Exception
    {
        mStoryCommentItems = new ArrayList<>();
        mStoryCommentsAdapter = new StoryCommentsAdapter(mLayoutInflater, mStoryCommentItems);
    }

    @After
    public void afterTest() throws Exception
    {
        mLayoutInflater = null;
        mStoryCommentItems.clear();
    }

    @Test
    public void onCreateViewHolder() throws Exception
    {
    }

    @Test
    public void onBindViewHolder() throws Exception
    {

        StoryCommentItem storyCommentItemA = Mockito.mock(StoryCommentItem.class);
        Mockito.when(storyCommentItemA.getType()).thenReturn(StoryCommentItem.LIST_ITEM_COMMENT);
        mStoryCommentItems.add(storyCommentItemA);

        StoryCommentItem storyCommentItemB = Mockito.mock(StoryCommentItem.class);
        Mockito.when(storyCommentItemB.getType()).thenReturn(StoryCommentItem.LIST_ITEM_LOADER);
        mStoryCommentItems.add(storyCommentItemB);

        StoryCommentViewHolder holder = Mockito.mock(StoryCommentViewHolder.class);
        ViewDataBinding binder = Mockito.mock(ViewDataBinding.class);
        Mockito.when(holder.getViewDataBinding()).thenReturn(binder);

        mStoryCommentsAdapter.onBindViewHolder(holder, 0);
        Mockito.verify(binder).setVariable(Mockito.anyInt(), Mockito.any());

        mStoryCommentsAdapter.onBindViewHolder(holder, 1);
        Mockito.verify(binder).setVariable(Mockito.anyInt(), Mockito.any());
    }

    @Test
    public void getItemViewType() throws Exception
    {
        StoryCommentItem storyItemA = Mockito.mock(StoryCommentItem.class);
        Mockito.when(storyItemA.getType()).thenReturn(StoryCommentItem.LIST_ITEM_COMMENT);
        mStoryCommentItems.add(storyItemA);

        StoryCommentItem storyItemB = Mockito.mock(StoryCommentItem.class);
        Mockito.when(storyItemB.getType()).thenReturn(StoryCommentItem.LIST_ITEM_LOADER);
        mStoryCommentItems.add(storyItemB);

        assertEquals(mStoryCommentsAdapter.getItemViewType(0), StoryCommentItem.LIST_ITEM_COMMENT);
        assertEquals(mStoryCommentsAdapter.getItemViewType(1), StoryCommentItem.LIST_ITEM_LOADER);
    }


    @Test
    public void getItemCount() throws Exception
    {

        StoryCommentItem storyItemA = Mockito.mock(StoryCommentItem.class);
        mStoryCommentItems.add(storyItemA);

        StoryCommentItem storyItemB = Mockito.mock(StoryCommentItem.class);
        mStoryCommentItems.add(storyItemB);

        assertEquals(mStoryCommentsAdapter.getItemCount(), 2);
    }

    @Test
    public void getItemId() throws Exception
    {
        assertEquals(mStoryCommentsAdapter.getItemId(1), 1);
        assertEquals(mStoryCommentsAdapter.getItemId(34), 34);
    }

    @Test
    public void updateData() throws Exception
    {
    }

}
package com.aqif.hackernews.screens.topstories.recycler.adapter;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class TopStoriesAdapterTest
{

    @Mock private LayoutInflater mLayoutInflater;
    private ArrayList<TopStoryItem> mTopStoryItems;
    private TopStoriesAdapter mTopStoriesAdapter;

    @Before
    public void beforeTest() throws Exception
    {
        mTopStoryItems = new ArrayList<>();
        mTopStoriesAdapter = new TopStoriesAdapter(mLayoutInflater, mTopStoryItems);
    }

    @After
    public void afterTest() throws Exception
    {
        mLayoutInflater = null;
        mTopStoryItems.clear();
    }

    @Test
    public void onCreateViewHolder() throws Exception
    {

    }

    @Test
    public void onBindViewHolder() throws Exception
    {
        TopStoryItem storyItemA = Mockito.mock(TopStoryItem.class);
        Mockito.when(storyItemA.getType()).thenReturn(TopStoryItem.LIST_ITEM_TOP_STORY);
        mTopStoryItems.add(storyItemA);

        TopStoryItem storyItemB = Mockito.mock(TopStoryItem.class);
        Mockito.when(storyItemB.getType()).thenReturn(TopStoryItem.LIST_ITEM_LOADER);
        mTopStoryItems.add(storyItemB);

        TopStoryViewHolder holder = Mockito.mock(TopStoryViewHolder.class);
        ViewDataBinding binder = Mockito.mock(ViewDataBinding.class);
        Mockito.when(holder.getViewDataBinding()).thenReturn(binder);

        mTopStoriesAdapter.onBindViewHolder(holder, 0);
        Mockito.verify(binder).setVariable(Mockito.anyInt(), Mockito.any());

        mTopStoriesAdapter.onBindViewHolder(holder, 1);
        Mockito.verify(binder).setVariable(Mockito.anyInt(), Mockito.any());
    }

    @Test
    public void getItemViewType() throws Exception
    {

        TopStoryItem storyItemA = Mockito.mock(TopStoryItem.class);
        Mockito.when(storyItemA.getType()).thenReturn(TopStoryItem.LIST_ITEM_TOP_STORY);
        mTopStoryItems.add(storyItemA);

        TopStoryItem storyItemB = Mockito.mock(TopStoryItem.class);
        Mockito.when(storyItemB.getType()).thenReturn(TopStoryItem.LIST_ITEM_LOADER);
        mTopStoryItems.add(storyItemB);

        assertEquals(mTopStoriesAdapter.getItemViewType(0), TopStoryItem.LIST_ITEM_TOP_STORY);
        assertEquals(mTopStoriesAdapter.getItemViewType(1), TopStoryItem.LIST_ITEM_LOADER);

    }

    @Test
    public void getItemCount() throws Exception
    {

        TopStoryItem storyItemA = Mockito.mock(TopStoryItem.class);
        mTopStoryItems.add(storyItemA);

        TopStoryItem storyItemB = Mockito.mock(TopStoryItem.class);
        mTopStoryItems.add(storyItemB);

        assertEquals(mTopStoriesAdapter.getItemCount(), 2);
    }

    @Test
    public void getItemId() throws Exception
    {
        assertEquals(mTopStoriesAdapter.getItemId(1), 1);
        assertEquals(mTopStoriesAdapter.getItemId(34), 34);
    }

    @Test
    public void updateData() throws Exception
    {

    }

}
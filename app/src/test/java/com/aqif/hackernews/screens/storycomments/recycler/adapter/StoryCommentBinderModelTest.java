package com.aqif.hackernews.screens.storycomments.recycler.adapter;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by aqifhamid.
 */

public class StoryCommentBinderModelTest
{
    private final int ID = 2;
    private final String TEXT = "test_text";
    private final String USER_NAME = "user_name";
    private final int LEVEL = 32;
    private final String TAG = "0_0_";
    private final String CHILD_TAG = "0_0_0_";
    private final boolean DELETED = false;

    private ArrayList<Integer> mKidsIds;

    private StoryCommentBinderModel mStoryCommentBinderModel;

    @Before
    public void beforeTest() throws Exception
    {

        mKidsIds = new ArrayList<>();
        mKidsIds.add(1);
        mKidsIds.add(2);

        mStoryCommentBinderModel = new StoryCommentBinderModel(ID,
                TEXT,
                USER_NAME,
                mKidsIds,
                LEVEL,
                TAG,
                DELETED);
    }

    @After
    public void afterTest() throws Exception
    {
        mKidsIds.clear();
        mKidsIds = null;
    }

    @Test
    public void setOnStoryCommentViewItemClickListener() throws Exception
    {
        StoryCommentBinderModel.IOnStoryCommentViewItemClickListener listener =
                Mockito.mock(StoryCommentBinderModel.IOnStoryCommentViewItemClickListener.class);

        mStoryCommentBinderModel.setOnStoryCommentViewItemClickListener(listener);
        assertEquals(listener, mStoryCommentBinderModel.getOnStoryCommentViewItemClickListener());
    }

    @Test
    public void getId() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.getId(), ID);
    }

    @Test
    public void getLevel() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.getLevel(), LEVEL);
    }

    @Test
    public void getCollapseArrowVisibility() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.getCollapseArrowVisibility(), View.VISIBLE);
        mKidsIds.clear();
        assertEquals(mStoryCommentBinderModel.getCollapseArrowVisibility(), View.INVISIBLE);
    }

    @Test
    public void getSubCommentsCount() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.getSubCommentsCount(), String.format("%d comments", mKidsIds.size()));
    }

    @Test
    public void getBy() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.getBy(), String.format(" by %s", USER_NAME));
    }
    @Test
    public void getKidsIds() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.getKidsIds().size(), mKidsIds.size());
        assertEquals(mStoryCommentBinderModel.getKidsIds().get(0), mKidsIds.get(0));
    }

    @Test
    public void isDeleted() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.isDeleted(), DELETED);
    }

    @Test
    public void getTag() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.getTag(), TAG);
    }

    @Test
    public void isChildTag() throws Exception
    {
        assertEquals(mStoryCommentBinderModel.isChildTag(CHILD_TAG), true);
        assertEquals(mStoryCommentBinderModel.isChildTag(TAG), false);
    }

    @Test
    public void onStoryCommentViewItemClicked() throws Exception
    {
        StoryCommentBinderModel.IOnStoryCommentViewItemClickListener listener =
                Mockito.mock(StoryCommentBinderModel.IOnStoryCommentViewItemClickListener.class);

        mStoryCommentBinderModel.setOnStoryCommentViewItemClickListener(null);
        mStoryCommentBinderModel.onStoryCommentViewItemClicked();
        Mockito.verify(listener, Mockito.times(0)).onStoryCommentViewItemClicked(Mockito.eq(mStoryCommentBinderModel), Mockito.anyBoolean());

        mStoryCommentBinderModel.setOnStoryCommentViewItemClickListener(listener);
        mStoryCommentBinderModel.onStoryCommentViewItemClicked();
        Mockito.verify(listener).onStoryCommentViewItemClicked(Mockito.eq(mStoryCommentBinderModel), Mockito.anyBoolean());
    }

}
package com.aqif.hackernews.screens.topstories.recycler.adapter;

import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class TopStoryBinderModelTest
{

    private final int ID = 2;
    private final String TITLE = "test_title";
    private final int SCORE = 97;
    private final String USER_NAME = "user_name";
    private final int COMMENTS_COUNT = 12;
    private final boolean DELETED = false;

    private ArrayList<Integer> mKidsIds;

    private TopStoryBinderModel mTopStoryBinderModel;

    @Before
    public void beforeTest() throws Exception
    {

        mKidsIds = new ArrayList<>();
        mKidsIds.add(1);
        mKidsIds.add(2);

        mTopStoryBinderModel = new TopStoryBinderModel(ID,
        TITLE,
        SCORE,
        USER_NAME,
        COMMENTS_COUNT,
        mKidsIds,
        DELETED);
    }

    @After
    public void afterTest() throws Exception
    {
        mKidsIds.clear();
    }

    @Test
    public void setOnTopStoryViewItemClickListener() throws Exception
    {
        TopStoryBinderModel.OnTopStoryViewItemClickListener listener =
                Mockito.mock(TopStoryBinderModel.OnTopStoryViewItemClickListener.class);

        mTopStoryBinderModel.setOnTopStoryViewItemClickListener(listener);
        assertEquals(listener, mTopStoryBinderModel.getOnTopStoryViewItemClickListener());
    }

    @Test
    public void getId() throws Exception
    {
        assertEquals(mTopStoryBinderModel.getId(), ID);
    }

    @Test
    public void getTitle() throws Exception
    {
        assertEquals(mTopStoryBinderModel.getTitle(), TITLE);
    }

    @Test
    public void getKidsIds() throws Exception
    {
        assertEquals(mTopStoryBinderModel.getKidsIds().size(), mKidsIds.size());
        assertEquals(mTopStoryBinderModel.getKidsIds().get(0), mKidsIds.get(0));
    }

    @Test
    public void getCommentsCount() throws Exception
    {
        assertEquals(mTopStoryBinderModel.getCommentsCount(), String.format("%d comments", COMMENTS_COUNT));
    }

    @Test
    public void getBy() throws Exception
    {
        assertEquals(mTopStoryBinderModel.getBy(), String.format("%d points by %s", SCORE, USER_NAME));
    }

    @Test
    public void isDeleted() throws Exception
    {
        assertEquals(mTopStoryBinderModel.isDeleted(), DELETED);
    }

    @Test
    public void onTopStoryViewItemClicked() throws Exception
    {
        TopStoryBinderModel.OnTopStoryViewItemClickListener listener =
                Mockito.mock(TopStoryBinderModel.OnTopStoryViewItemClickListener.class);

        mTopStoryBinderModel.setOnTopStoryViewItemClickListener(null);
        mTopStoryBinderModel.onTopStoryViewItemClicked();
        Mockito.verify(listener, Mockito.times(0)).onTopStoryViewItemClicked(mTopStoryBinderModel);

        mTopStoryBinderModel.setOnTopStoryViewItemClickListener(listener);
        mTopStoryBinderModel.onTopStoryViewItemClicked();
        Mockito.verify(listener).onTopStoryViewItemClicked(mTopStoryBinderModel);
    }

}
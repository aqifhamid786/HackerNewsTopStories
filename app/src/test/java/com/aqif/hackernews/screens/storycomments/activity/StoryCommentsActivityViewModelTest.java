package com.aqif.hackernews.screens.storycomments.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aqif.hackernews.restapi.hackernews.manager.IHackerNewsApiManager;
import com.aqif.hackernews.restapi.hackernews.manager.callbackhandlers.item.IHackerNewsApiManagerItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;
import com.aqif.hackernews.screens.constants.ScreensConstants;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.IStoryCommentsRecyclerViewModel;
import com.aqif.hackernews.screens.storycomments.recycler.viewmodel.observer.IStoryCommentsRecyclerViewModelCallbackHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class StoryCommentsActivityViewModelTest
{

    @Mock private Toolbar mToolbar;
    @Mock private ProgressBar mProgressBar;
    @Mock private TextView mEmptyListTextView;
    @Mock private TextView mTopStoryTitleTextView;
    @Mock private IHackerNewsApiManager mHackerNewsApiManager;
    @Mock private IStoryCommentsRecyclerViewModel mStoryCommentsRecyclerViewModel;
    private StoryCommentsActivityViewModel mStoryCommentsActivityViewModel;

    private List<Integer> mCommentsIds;
    private HashMap<String, ArrayList<StoryCommentBinderModel>> mTagToCommentDataHashMap;

    @Before
    public void beforeTest() throws Exception
    {
        IHackerNewsApiManagerItemsBatchProcessorCallbackHandler hackerNewsApiManagerItemsBatchProcessorCallbackHandler =
                Mockito.mock(IHackerNewsApiManagerItemsBatchProcessorCallbackHandler.class);
        Mockito.when(mHackerNewsApiManager.getHackerNewsApiManagerItemsBatchProcessorObservable())
                .thenReturn(hackerNewsApiManagerItemsBatchProcessorCallbackHandler);

        IStoryCommentsRecyclerViewModelCallbackHandler storyCommentsRecyclerViewModelCallbackHandler =
                Mockito.mock(IStoryCommentsRecyclerViewModelCallbackHandler.class);
        Mockito.when(mStoryCommentsRecyclerViewModel.getStoryCommentsRecyclerViewModelObservable())
                .thenReturn(storyCommentsRecyclerViewModelCallbackHandler);

        mCommentsIds = Mockito.spy(new ArrayList<Integer>());
        mTagToCommentDataHashMap = Mockito.spy(new HashMap<String, ArrayList<StoryCommentBinderModel>>());

        mStoryCommentsActivityViewModel = new StoryCommentsActivityViewModel(mToolbar,
                mProgressBar,
                mEmptyListTextView,
                mTopStoryTitleTextView,
                mStoryCommentsRecyclerViewModel,
                mHackerNewsApiManager,
                mCommentsIds,
                mTagToCommentDataHashMap);

    }

    @After
    public void afterTest() throws Exception
    {
        mCommentsIds.clear();
        mCommentsIds = null;
        mTagToCommentDataHashMap.clear();
        mTagToCommentDataHashMap = null;

        mStoryCommentsActivityViewModel = null;
    }

    @Test
    public void onActivityCreateCalled() throws Exception
    {
        ArrayList<Integer> kids = new ArrayList<>();
        kids.add(0);
        kids.add(1);

        Intent intent = Mockito.mock(Intent.class);
        AppCompatActivity activity = Mockito.mock(AppCompatActivity.class);

        Mockito.when(activity.getIntent()).thenReturn(intent);
        Mockito.when(intent.getStringExtra(Mockito.eq(ScreensConstants.Top_Stories__Intnent__Key_Top_Stories_Title))).thenReturn("Top_News");
        Mockito.when(intent.getIntegerArrayListExtra(Mockito.eq(ScreensConstants.Top_Stories__Intnent__Key_Top_Stories_Title))).thenReturn(kids);

        mStoryCommentsActivityViewModel.onActivityCreateCalled(activity);

        Mockito.verify(activity).setSupportActionBar(Mockito.eq(mToolbar));
        Mockito.verify(mTopStoryTitleTextView).setText(Mockito.eq("Top_News"));
        assertEquals(mCommentsIds.size(), 2);

        kids.clear();
        mCommentsIds.clear();
        mStoryCommentsActivityViewModel.onActivityCreateCalled(activity);

        Mockito.verify(mEmptyListTextView).setText(Mockito.eq("This story has no comments."));
        Mockito.verify(mProgressBar).setVisibility(View.INVISIBLE);

        Mockito.verify(mStoryCommentsRecyclerViewModel).stopLoadingDataAtListEnd();

    }

    @Test
    public void onActivityDestroyCalled() throws Exception
    {
        mStoryCommentsActivityViewModel.onActivityDestroyCalled();
        Mockito.verify(mHackerNewsApiManager).clear();
        Mockito.verify(mStoryCommentsRecyclerViewModel).onActivityDestroyCalled();
    }

    @Test
    public void onBackPressed() throws Exception
    {
        assertEquals(mStoryCommentsActivityViewModel.onBackPressed(), false);
    }

    @Test
    public void processNextBatchOfBaseComments()
    {
        mCommentsIds.add(1);
        mCommentsIds.add(2);
        mCommentsIds.add(3);

        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                assertEquals(invocation.<int[]>getArgument(0).length, 3);
                assertEquals(invocation.<int[]>getArgument(0)[0], 1);
                assertEquals(invocation.<int[]>getArgument(0)[1], 2);
                assertEquals(invocation.<int[]>getArgument(0)[2], 3);
                return null;
            }
        }).when(mHackerNewsApiManager).fetchItemsInABatch(Mockito.<int[]>any(), Mockito.any());
        mStoryCommentsActivityViewModel.processNextBatchOfBaseComments();
    }

    @Test
    public void loadMoreStoryComments() throws Exception
    {
        mStoryCommentsActivityViewModel.loadMoreStoryComments();
        Mockito.verify(mCommentsIds).size();

        mCommentsIds.add(1);
        mCommentsIds.add(2);
        mStoryCommentsActivityViewModel.loadMoreStoryComments();
        Mockito.verify(mCommentsIds, Mockito.times(3)).size();
    }

    @Test
    public void storyCommentClicked() throws Exception
    {

    }

    @Test
    public void getCharacterOccurrences() throws Exception
    {
        assertEquals(mStoryCommentsActivityViewModel.getCharacterOccurrences("0_0_",'0'), 2);
        assertEquals(mStoryCommentsActivityViewModel.getCharacterOccurrences("0_0_0_",'0'), 3);
    }

    @Test
    public void onItemsBatchSuccess() throws Exception
    {
        ArrayList<ItemDao> items = new ArrayList<>();
        items.add(new ItemDao());

        Mockito.when(mProgressBar.getVisibility()).thenReturn(View.VISIBLE);
        Mockito.doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                assertEquals(invocation.<ArrayList<ItemDao>>getArgument(0).size(), 1);
                assertEquals(mTagToCommentDataHashMap.get(ScreensConstants.Item_Request_Base_Tag).size(), 1);
                return null;
            }
        }).when(mStoryCommentsRecyclerViewModel)
                .updateStoryCommentsDataForTag(Mockito.<StoryCommentBinderModel>anyList(), Mockito.anyString(), Mockito.anyBoolean());

        mStoryCommentsActivityViewModel.onItemsBatchSuccess(items, ScreensConstants.Item_Request_Base_Tag);

        Mockito.verify(mProgressBar).setVisibility(View.INVISIBLE);
        Mockito.verify(mEmptyListTextView).setVisibility(View.INVISIBLE);


        Mockito.doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                assertEquals(invocation.<ArrayList<ItemDao>>getArgument(0).size(), 1);
                assertEquals(mTagToCommentDataHashMap.get(ScreensConstants.Item_Request_Base_Tag).size(), 2);
                return null;
            }
        }).when(mStoryCommentsRecyclerViewModel)
                .updateStoryCommentsDataForTag(Mockito.<StoryCommentBinderModel>anyList(), Mockito.anyString(), Mockito.anyBoolean());

        mStoryCommentsActivityViewModel.onItemsBatchSuccess(items, ScreensConstants.Item_Request_Base_Tag);
    }

    @Test
    public void onItemsBatchFailure() throws Exception
    {
        mProgressBar.setVisibility(View.VISIBLE);
        mStoryCommentsActivityViewModel.onItemsBatchFailure("message", null);

        Mockito.verify(mEmptyListTextView).setText(Mockito.eq("message"));
        Mockito.verify(mEmptyListTextView).setVisibility(View.VISIBLE);
        Mockito.verify(mProgressBar).setVisibility(View.INVISIBLE);

        mStoryCommentsActivityViewModel.setTotalCommentsLoadedCount(1);
        mStoryCommentsActivityViewModel.onItemsBatchFailure("message", null);

        Mockito.verify(mEmptyListTextView).setVisibility(View.INVISIBLE);
        Mockito.verify(mStoryCommentsRecyclerViewModel).stopLoadingDataAtListEnd();
    }

}
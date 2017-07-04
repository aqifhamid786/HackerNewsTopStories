package com.aqif.hackernews.screens.storycomments.recycler.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;
import com.aqif.hackernews.screens.constants.ScreensConstants;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentBinderModel;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentItem;
import com.aqif.hackernews.screens.storycomments.recycler.adapter.StoryCommentsAdapter;
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
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by aqifhamid.
 */

@RunWith(MockitoJUnitRunner.class)
public class StoryCommentsRecyclerViewModelTest
{

    @Mock private StoryCommentsAdapter mStoryCommentsAdapter;
    @Mock private IStoryCommentsRecyclerViewModelCallbackHandler mStoryCommentsRecyclerViewModelCallbackHandler;
    @Mock private RecyclerViewScrollToEndObserver mRecyclerViewScrollToEndObserver;
    @Mock private RecyclerView mRecyclerView;
    private List<StoryCommentItem> mStoryCommentItems;
    private List<String> mTagsOfExpandedComments;
    private StoryCommentsRecyclerViewModel mStoryCommentsRecyclerViewModel;


    @Before
    public void beforeTest()
    {
        mStoryCommentItems = Mockito.spy(new ArrayList<StoryCommentItem>());
        mTagsOfExpandedComments = Mockito.spy(new ArrayList<String>());

        SimpleItemAnimator animator = Mockito.mock(SimpleItemAnimator.class);
        Mockito.when(mRecyclerView.getItemAnimator()).thenReturn(animator);

        mStoryCommentsRecyclerViewModel = new StoryCommentsRecyclerViewModel(
                mRecyclerView,
                mStoryCommentsAdapter,
                mStoryCommentsRecyclerViewModelCallbackHandler,
                mRecyclerViewScrollToEndObserver,
                mStoryCommentItems,
                mTagsOfExpandedComments);
    }

    @After
    public void afterTest()
    {
        mStoryCommentItems.clear();
        mTagsOfExpandedComments.clear();
        mStoryCommentsRecyclerViewModel = null;
    }

    @Test
    public void getStoryCommentsRecyclerViewModelObservable() throws Exception
    {
        assertNotNull(mStoryCommentsRecyclerViewModelCallbackHandler);
        assertEquals(mStoryCommentsRecyclerViewModelCallbackHandler, mStoryCommentsRecyclerViewModel.getStoryCommentsRecyclerViewModelObservable());
    }

    @Test
    public void stopLoadingDataAtListEnd() throws Exception
    {
        mStoryCommentsRecyclerViewModel.stopLoadingDataAtListEnd();
        Mockito.verify(mStoryCommentItems).remove(Mockito.<StoryCommentItem>any());
        Mockito.verify(mStoryCommentsAdapter).updateData(Mockito.eq(mStoryCommentItems));
        Mockito.verify(mRecyclerViewScrollToEndObserver).setLoadingData(Mockito.anyBoolean());
    }

    @Test
    public void updateStoryCommentsDataForTag() throws Exception
    {
        List<StoryCommentBinderModel> storyCommentBinderModels = new ArrayList<>();

        StoryCommentBinderModel binderModel = Mockito.mock(StoryCommentBinderModel.class);
        Mockito.when(binderModel.isDeleted()).thenReturn(false);
        Mockito.when(binderModel.getTag()).thenReturn("0_");
        storyCommentBinderModels.add(binderModel);

        mStoryCommentsRecyclerViewModel.updateStoryCommentsDataForTag(storyCommentBinderModels, "0_0_", false);
        assertEquals(mStoryCommentItems.size(), 0);

        mStoryCommentsRecyclerViewModel.updateStoryCommentsDataForTag(storyCommentBinderModels, ScreensConstants.Item_Request_Base_Tag, false);
        assertEquals(mStoryCommentItems.size(), 1);

        storyCommentBinderModels.clear();
        binderModel = Mockito.mock(StoryCommentBinderModel.class);
        Mockito.when(binderModel.isDeleted()).thenReturn(false);
        storyCommentBinderModels.add(binderModel);

        binderModel = Mockito.mock(StoryCommentBinderModel.class);
        Mockito.when(binderModel.isDeleted()).thenReturn(false);
        storyCommentBinderModels.add(binderModel);

        mStoryCommentsRecyclerViewModel.updateStoryCommentsDataForTag(storyCommentBinderModels, ScreensConstants.Item_Request_Base_Tag, false);
        assertEquals(mStoryCommentItems.size(), 3);

        storyCommentBinderModels.clear();
        binderModel = Mockito.mock(StoryCommentBinderModel.class);
        Mockito.when(binderModel.isDeleted()).thenReturn(false);
        storyCommentBinderModels.add(binderModel);

        binderModel = Mockito.mock(StoryCommentBinderModel.class);
        Mockito.when(binderModel.isDeleted()).thenReturn(false);
        storyCommentBinderModels.add(binderModel);

        mStoryCommentsRecyclerViewModel.updateStoryCommentsDataForTag(storyCommentBinderModels, ScreensConstants.Item_Request_Base_Tag, false);
        assertEquals(mStoryCommentItems.size(), 5);

        Mockito.verify(mStoryCommentsAdapter, Mockito.times(3)).updateData(Mockito.<List<StoryCommentItem>>any());
    }

   @Test
    public void onStoryCommentViewItemClicked() throws Exception
    {

        ArrayList<Integer> kids = Mockito.spy(new ArrayList<Integer>());
        StoryCommentBinderModel storyCommentBinderModel = Mockito.mock(StoryCommentBinderModel.class);
        Mockito.when(storyCommentBinderModel.getKidsIds()).thenReturn(kids);

        mStoryCommentsRecyclerViewModel.onStoryCommentViewItemClicked(storyCommentBinderModel, false);

        Mockito.verify(mTagsOfExpandedComments, Mockito.times(0)).add(Mockito.anyString());
        Mockito.verify(mTagsOfExpandedComments, Mockito.times(0)).remove(Mockito.anyString());

        kids.add(1);
        Mockito.when(storyCommentBinderModel.getTag()).thenReturn("0_0_");

        mStoryCommentsRecyclerViewModel.onStoryCommentViewItemClicked(storyCommentBinderModel, true);

        Mockito.verify(mTagsOfExpandedComments).add(Mockito.eq("0_0_"));
        Mockito.verify(mStoryCommentsRecyclerViewModelCallbackHandler).notifyStoryCommentClicked(Mockito.eq(storyCommentBinderModel));


        StoryCommentItem storyCommentItem = Mockito.mock(StoryCommentItem.class);
        StoryCommentBinderModel binderModel = Mockito.mock(StoryCommentBinderModel.class);
        Mockito.when(binderModel.getTag()).thenReturn("0_");

        Mockito.when(storyCommentItem.getStoryCommentBinderModel()).thenReturn(binderModel);
        mStoryCommentItems.add(storyCommentItem);

        binderModel = Mockito.mock(StoryCommentBinderModel.class);
        for(int lop=0; lop<4; lop++)
        {
            storyCommentItem = Mockito.mock(StoryCommentItem.class);
            Mockito.when(binderModel.getTag()).thenReturn("0_0_1_0_0_");
            Mockito.when(storyCommentItem.getStoryCommentBinderModel()).thenReturn(binderModel);
            mStoryCommentItems.add(storyCommentItem);
        }

        Mockito.when(storyCommentBinderModel.isChildTag(Mockito.anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArgument(0).equals("0_0_1_0_0_");
            }
        });

        mStoryCommentsRecyclerViewModel.onStoryCommentViewItemClicked(storyCommentBinderModel, false);
        Mockito.verify(mTagsOfExpandedComments).remove(Mockito.eq("0_0_"));
        assertEquals(mStoryCommentItems.size(), 1);
        Mockito.verify(mStoryCommentsAdapter).updateData(Mockito.eq(mStoryCommentItems));

    }

    @Test
    public void onLoadMoreRecycleViewDataListner() throws Exception
    {
        Mockito.when(mRecyclerView.post(Mockito.any(Runnable.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                invocation.<Runnable>getArgument(0).run();
                return null;
            }
        });

        mStoryCommentsRecyclerViewModel.onLoadMoreRecycleViewDataListner(0, 1, mRecyclerView);
        assertEquals(mStoryCommentItems.size(), 1);
        Mockito.verify(mStoryCommentsAdapter).updateData(Mockito.eq(mStoryCommentItems));
        Mockito.verify(mStoryCommentsRecyclerViewModelCallbackHandler).notifyLoadMoreStoryComments();
    }

    @Test
    public void onActivityDestroyCalled() throws Exception
    {
        mStoryCommentsRecyclerViewModel.onActivityDestroyCalled();
        Mockito.verify(mStoryCommentItems).remove(Mockito.any());
        Mockito.verify(mStoryCommentItems).clear();

        Mockito.verify(mStoryCommentsAdapter).updateData(Mockito.<List<StoryCommentItem>>any());
        Mockito.verify(mRecyclerViewScrollToEndObserver).setOnLoadMoreRecycleViewDataListner(null);
    }

}
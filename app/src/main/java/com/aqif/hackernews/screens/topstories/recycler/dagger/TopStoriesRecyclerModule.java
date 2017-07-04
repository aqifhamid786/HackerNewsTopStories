package com.aqif.hackernews.screens.topstories.recycler.dagger;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aqif.hackernews.recycler.observer.RecyclerViewScrollToEndObserver;
import com.aqif.hackernews.screens.topstories.activity.dagger.scope.TopStoriesActivityScope;
import com.aqif.hackernews.screens.topstories.recycler.adapter.TopStoriesAdapter;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.ITopStoriesRecyclerViewModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.TopStoriesRecyclerViewModel;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackHandler;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackObservable;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.ITopStoriesRecyclerViewModelCallbackObserver;
import com.aqif.hackernews.screens.topstories.recycler.viewmodel.observer.TopStoriesRecyclerViewModelCallbackHandler;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aqifhamid.
 */

@Module
public class TopStoriesRecyclerModule
{


    @Provides
    @TopStoriesActivityScope
    public ITopStoriesRecyclerViewModelCallbackHandler provideTopStoriesRecyclerViewModelCallbackHandler()
    {
        return new TopStoriesRecyclerViewModelCallbackHandler(new ArrayList<ITopStoriesRecyclerViewModelCallbackObserver>());
    }

    @Provides
    @TopStoriesActivityScope
    public LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity)
    {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @TopStoriesActivityScope
    public RecyclerViewScrollToEndObserver provideRecyclerViewScrollToEndObserver(LinearLayoutManager linearLayoutManager)
    {
        return new RecyclerViewScrollToEndObserver(linearLayoutManager);
    }

    @Provides
    @TopStoriesActivityScope
    public TopStoriesAdapter provideTopStoriesAdapter(AppCompatActivity activity)
    {
        return new TopStoriesAdapter(activity.getLayoutInflater());
    }

    @Provides
    @TopStoriesActivityScope
    public ITopStoriesRecyclerViewModel provideTopStoriesRecyclerViewModel(LinearLayoutManager linearLayoutManager,
                                                                           RecyclerView recyclerView,
                                                                           TopStoriesAdapter topStoriesAdapter,
                                                                           ITopStoriesRecyclerViewModelCallbackHandler topStoriesRecyclerViewModelCallbackHandler,
                                                                           RecyclerViewScrollToEndObserver recyclerViewScrollToEndObserver)
    {

        recyclerView.setLayoutManager(linearLayoutManager);
        return new TopStoriesRecyclerViewModel(recyclerView,
                topStoriesAdapter,
                topStoriesRecyclerViewModelCallbackHandler,
                recyclerViewScrollToEndObserver);
    }
}

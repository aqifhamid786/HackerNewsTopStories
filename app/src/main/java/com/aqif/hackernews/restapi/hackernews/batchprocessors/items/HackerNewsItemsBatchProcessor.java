package com.aqif.hackernews.restapi.hackernews.batchprocessors.items;

import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackHandler;
import com.aqif.hackernews.restapi.hackernews.batchprocessors.items.callbackhandler.IHackerNewsItemsBatchProcessorCallbackObservable;
import com.aqif.hackernews.restapi.hackernews.requests.item.callbackhandler.IHackerNewsItemCallbackObserver;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.IHackerNewsItemRequestHandler;
import com.aqif.hackernews.restapi.hackernews.requests.item.requesthandler.factory.IHackerNewsItemRequestHandlerFactory;
import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemsBatchProcessor implements IHackerNewsItemsBatchProcessor
    , IHackerNewsItemCallbackObserver
{

    private IHackerNewsItemsBatchProcessorCallbackHandler mHackerNewsItemsBatchProcessorCallbackHandler;
    private IHackerNewsItemRequestHandlerFactory mHackerNewsItemRequestHandlerFactory;

    private HackerNewsItemsBatchRequest mCurrentHackerNewsItemsBatchRequest;

    private ArrayList<IHackerNewsItemRequestHandler> mHackerNewsItemRequestHandlers;
    private ArrayList<HackerNewsItemsBatchRequest> mHackerNewsItemsBatchRequests;
    private ArrayList<ItemDao> mResponseDaoItems;



    public HackerNewsItemsBatchProcessor(IHackerNewsItemsBatchProcessorCallbackHandler hackerNewsItemsBatchProcessorCallbackHandler,
                                         IHackerNewsItemRequestHandlerFactory hackerNewsItemRequestHandlerFactory)
    {
        this(hackerNewsItemsBatchProcessorCallbackHandler, hackerNewsItemRequestHandlerFactory, null, null, null);
    }

    public HackerNewsItemsBatchProcessor(IHackerNewsItemsBatchProcessorCallbackHandler hackerNewsItemsBatchProcessorCallbackHandler,
                                         IHackerNewsItemRequestHandlerFactory hackerNewsItemRequestHandlerFactory,
                                         ArrayList<IHackerNewsItemRequestHandler> hackerNewsItemRequestHandlers,
                                         ArrayList<HackerNewsItemsBatchRequest> hackerNewsItemsBatchRequests,
                                         ArrayList<ItemDao> responseDaoItems)
    {
        mHackerNewsItemsBatchProcessorCallbackHandler = hackerNewsItemsBatchProcessorCallbackHandler;
        mHackerNewsItemRequestHandlerFactory = hackerNewsItemRequestHandlerFactory;

        mHackerNewsItemRequestHandlers = hackerNewsItemRequestHandlers;
        if(mHackerNewsItemRequestHandlers==null)
        {
            mHackerNewsItemRequestHandlers = new ArrayList<>();
        }

        mHackerNewsItemsBatchRequests = hackerNewsItemsBatchRequests;
        if(mHackerNewsItemsBatchRequests==null)
        {
            mHackerNewsItemsBatchRequests = new ArrayList<>();
        }

        mResponseDaoItems = responseDaoItems;
        if(mResponseDaoItems==null)
        {
            mResponseDaoItems = new ArrayList<>();
        }
    }

    @Override
    public IHackerNewsItemsBatchProcessorCallbackObservable getHackerNewsItemsBatchProcessorCallbackObservable()
    {
        return mHackerNewsItemsBatchProcessorCallbackHandler;
    }

    @Override
    public void processItemsBatchRequest(HackerNewsItemsBatchRequest hackerNewsItemsBatchRequest)
    {
        if(hackerNewsItemsBatchRequest!=null && !mHackerNewsItemsBatchRequests.contains(hackerNewsItemsBatchRequest))
        {
            mHackerNewsItemsBatchRequests.add(hackerNewsItemsBatchRequest);
            tryProcessingNextBatch();
        }
    }

    synchronized void tryProcessingNextBatch()
    {
        if(mCurrentHackerNewsItemsBatchRequest == null && mHackerNewsItemsBatchRequests.size()>0)
        {
            mCurrentHackerNewsItemsBatchRequest = mHackerNewsItemsBatchRequests.remove(0);
            mResponseDaoItems.clear();

            int[] itemIds = mCurrentHackerNewsItemsBatchRequest.getItemIds();
            for(int lop=0; lop<itemIds.length; lop++)
            {


                IHackerNewsItemRequestHandler hackerNewsItemRequestHandler = mHackerNewsItemRequestHandlerFactory.getHackerNewsItemRequestHandlerInstance();
                hackerNewsItemRequestHandler.getHackerNewsItemCallbackObservable().registerHackerNewsItemCallbackObserver(HackerNewsItemsBatchProcessor.this);
                mHackerNewsItemRequestHandlers.add(hackerNewsItemRequestHandler);

                Integer tag = itemIds[lop];
                hackerNewsItemRequestHandler.setTag(tag);
                hackerNewsItemRequestHandler.performHackerNewsItemGet(itemIds[lop]);
            }
        }
    }

    @Override
    public void cancelAllBatches()
    {
        mHackerNewsItemsBatchRequests.clear();
        clearCurrentBatch();
    }

    @Override
    public void clear()
    {
        cancelAllBatches();
        mHackerNewsItemsBatchProcessorCallbackHandler.clear();
        mHackerNewsItemRequestHandlerFactory = null;
    }

    synchronized void clearCurrentBatch()
    {
        for(int lop=0; lop<mHackerNewsItemRequestHandlers.size(); lop++)
        {
            mHackerNewsItemRequestHandlers.get(lop).clear();
        }
        mHackerNewsItemRequestHandlers.clear();
        mResponseDaoItems.clear();
        mCurrentHackerNewsItemsBatchRequest = null;

    }

    /********* HackerNews Item callback start ***********/

    @Override
    public void onHackerNewsItemSuccess(ItemDao item, Object tag)
    {

        mResponseDaoItems.add(item);

        if(tag!=null)
        {
            clearHackerNewsItemRequestHandlerForTag(tag);
        }

        if(processBatchIfCompleted())
        {
            tryProcessingNextBatch();
        }
    }

    @Override
    public void onHackerNewsItemFailed(String message, Object tag)
    {
        if(tag!=null)
        {
            clearHackerNewsItemRequestHandlerForTag(tag);
        }

        mHackerNewsItemsBatchProcessorCallbackHandler.notifyItemsBatchFailure(message, tag);
        clearCurrentBatch();
        tryProcessingNextBatch();
    }

    /********* HackerNews Item callback end ***********/

    synchronized boolean processBatchIfCompleted()
    {
        // if number of responses and items are same it means we are done with the batch.
        if(mResponseDaoItems.size() == mCurrentHackerNewsItemsBatchRequest.getItemIds().length)
        {
            HackerNewsItemsBatchResponse hackerNewsItemsBatchResponse = new HackerNewsItemsBatchResponse(
                    mCurrentHackerNewsItemsBatchRequest,
                    mResponseDaoItems
            );
            mHackerNewsItemsBatchProcessorCallbackHandler.notifyItemsBatchSuccess(hackerNewsItemsBatchResponse);
            mHackerNewsItemsBatchRequests.remove(mCurrentHackerNewsItemsBatchRequest);
            mCurrentHackerNewsItemsBatchRequest = null;
            return true;
        }
        return false;
    }

    synchronized void clearHackerNewsItemRequestHandlerForTag(Object tag)
    {
        IHackerNewsItemRequestHandler hackerNewsItemRequestHandler = null;
        for(int lop=0; lop<mHackerNewsItemRequestHandlers.size(); lop++)
        {
            if(mHackerNewsItemRequestHandlers.get(lop).getTag()==tag)
            {
                hackerNewsItemRequestHandler = mHackerNewsItemRequestHandlers.get(lop);
                break;
            }
        }

        if(hackerNewsItemRequestHandler!=null)
        {
            hackerNewsItemRequestHandler.getHackerNewsItemCallbackObservable().unregisterHackerNewsItemCallbackObserver(HackerNewsItemsBatchProcessor.this);
            hackerNewsItemRequestHandler.clear();
            mHackerNewsItemRequestHandlers.remove(hackerNewsItemRequestHandler);
        }
    }
}

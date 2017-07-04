package com.aqif.hackernews.restapi.hackernews.batchprocessors.items;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import java.util.ArrayList;

/**
 * Created by aqifhamid.
 */

public class HackerNewsItemsBatchResponse
{
    private ArrayList<ItemDao> mItemDaos;
    private HackerNewsItemsBatchRequest mHackerNewsItemsBatchRequest;
    private Object mTag;

    public HackerNewsItemsBatchResponse(HackerNewsItemsBatchRequest hackerNewsItemsBatchRequest, ArrayList<ItemDao> itemDaos)
    {
        mHackerNewsItemsBatchRequest = hackerNewsItemsBatchRequest;
        mTag = mHackerNewsItemsBatchRequest.getTag();

        mItemDaos = new ArrayList<>();
        mItemDaos.addAll(itemDaos);
    }

    public HackerNewsItemsBatchRequest getHackerNewstemsBatchRequest()
    {
        return mHackerNewsItemsBatchRequest;
    }

    public Object getTag()
    {
        return mTag;
    }

    public ArrayList<ItemDao> getItemsDaos()
    {
        sortListItems();
        return mItemDaos;
    }

    private void sortListItems()
    {
        int[] ids = mHackerNewsItemsBatchRequest.getItemIds();
        for(int idsIndex=0; idsIndex<ids.length; idsIndex++)
        {
            for(int itemsListIndex = 0; itemsListIndex<mItemDaos.size(); itemsListIndex++)
            {
                if(mItemDaos.get(itemsListIndex).getId()==ids[idsIndex])
                {
                    ItemDao item = mItemDaos.remove(itemsListIndex);
                    mItemDaos.add(idsIndex, item);
                    break;
                }
            }
        }

    }

}

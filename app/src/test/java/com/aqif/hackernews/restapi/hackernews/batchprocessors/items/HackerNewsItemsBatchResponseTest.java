package com.aqif.hackernews.restapi.hackernews.batchprocessors.items;

import com.aqif.hackernews.restapi.hackernews.requests.item.responsedao.ItemDao;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by aqifhamid.
 */
public class HackerNewsItemsBatchResponseTest
{

    @Test
    public void getHackerNewstemsBatchRequest() throws Exception
    {
        HackerNewsItemsBatchRequest request = new HackerNewsItemsBatchRequest(new int[]{}, new Object());
        HackerNewsItemsBatchResponse response = new HackerNewsItemsBatchResponse(request, new ArrayList<ItemDao>());
        assertEquals(request, response.getHackerNewstemsBatchRequest());
    }

    @Test
    public void getTag() throws Exception
    {

        Object tag = new Object();
        HackerNewsItemsBatchRequest request = new HackerNewsItemsBatchRequest(new int[]{}, tag);
        HackerNewsItemsBatchResponse response = new HackerNewsItemsBatchResponse(request, new ArrayList<ItemDao>());

        assertEquals(tag, response.getTag());
    }

    /**** Response data returned should be sorted based on order of ids in request object *****/
    @Test
    public void getItemsDaos() throws Exception
    {

        int[] ids = new int[]{14681561,14681213,14675493,14680457,14678473,14679876,14676505,14670245,14679905,14669709};
        HackerNewsItemsBatchRequest request = new HackerNewsItemsBatchRequest(ids, new Object());

        ArrayList<ItemDao> responseData = new ArrayList<>();

        // create and shuffle responseData
        for(int lop=0; lop < ids.length; lop++)
        {
            ItemDao item  = new ItemDao();
            item.setId(ids[lop]);
            responseData.add(item);
            responseData.add(lop, item);
        }
        Collections.shuffle(responseData, new Random(System.nanoTime()));

        HackerNewsItemsBatchResponse response = new HackerNewsItemsBatchResponse(request, responseData);

        ArrayList<ItemDao> sortedResponseData = response.getItemsDaos();
        for(int lop=0; lop<ids.length; lop++)
        {
            assertEquals(sortedResponseData.get(lop).getId(), ids[lop]);
        }

    }

}
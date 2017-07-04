package com.aqif.hackernews.restapi.interceptors;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *
 * Created by aqifhamid.
 *
 */

public class OkHttpInterceptorMock implements Interceptor
{


    private String mResponseString;
    private HashMap<String, String> mItemResponses;
    private int mStatusCode;

    public OkHttpInterceptorMock(int statusCode)
    {
        mStatusCode = statusCode;
        mItemResponses = new HashMap<>();
        mResponseString = "";

        mItemResponses.put("14681561", "{\"by\":\"sohkamyung\",\"descendants\":7,\"id\":14681561,\"kids\":[14681646,14681646,14681646,14681646,14681646],\"score\":41,\"time\":1498985182,\"title\":\"Minitel: The Online World France Built Before the Web\",\"type\":\"story\",\"url\":\"http://spectrum.ieee.org/computing/networks/minitel-the-online-world-france-built-before-the-web\"}");
        mItemResponses.put("14681646", "{\"by\":\"mrkrab\",\"id\":14681611,\"kids\":[14681646,14681646],\"parent\":14681561,\"text\":\"Don&#x27;t forget about Infovía in Spain, too, even though that was much later.\",\"time\":1498986578,\"type\":\"comment\"}");

    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {

        String requestUrl = chain.request().url().toString();

        if(requestUrl.contains("topstories.json"))
        {
            mResponseString = "[14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561,14681561]";
        }
        else if(requestUrl.contains("/item/"))
        {
            String id = requestUrl.split("/item/")[1].split(".json")[0];
            mResponseString = mItemResponses.get(id);

        }

        Response.Builder builder = new Response.Builder()
                .addHeader("Content-Type", "aapplication/json; charset=utf-8")

                .code(mStatusCode)
                .message("")
                .protocol(Protocol.HTTP_1_0)
                .request(chain.request());

        if(mResponseString!=null && mResponseString.length()>0)
        {
            builder.body(ResponseBody.create(MediaType.parse("application/json"), mResponseString.getBytes()));
        }

        return builder.build();

    }
}

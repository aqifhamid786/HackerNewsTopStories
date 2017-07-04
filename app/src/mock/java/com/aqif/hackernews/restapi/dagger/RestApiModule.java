package com.aqif.hackernews.restapi.dagger;

import com.aqif.hackernews.restapi.dagger.qualifiers.Status400Retrofit;
import com.aqif.hackernews.restapi.dagger.qualifiers.SuccessResponseRetrofit;
import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;
import com.aqif.hackernews.restapi.interceptors.OkHttpInterceptorMock;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aqifhamid.
 */

@Module
public class RestApiModule
{

    private String mHackerNewsBaseURL = "https://hacker-news.firebaseio.com/v0/";

    public RestApiModule(String hackerNewsBaseURL)
    {
        mHackerNewsBaseURL = hackerNewsBaseURL;
    }

    @Provides
    @RestApiScope
    @SuccessResponseRetrofit
    OkHttpClient provideOkHttpClientMock()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new OkHttpInterceptorMock(200));
        return builder.build();
    }

    @Provides
    @RestApiScope
    @Status400Retrofit
    OkHttpClient provideOkHttpClientWith400ErrorInterceptorMock()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new OkHttpInterceptorMock(400));
        return builder.build();
    }

    @Provides
    @RestApiScope
    GsonConverterFactory provideGson()
    {
        return GsonConverterFactory.create();
    }

    @Provides
    @RestApiScope
    @SuccessResponseRetrofit
    Retrofit provideRetrofitForCommentItem(GsonConverterFactory gsonConverterFactory, @SuccessResponseRetrofit OkHttpClient okHttpClient)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mHackerNewsBaseURL)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)

                .build();
        return retrofit;
    }

    @Provides
    @RestApiScope
    @Status400Retrofit
    Retrofit provideRetrofitFor400Status(GsonConverterFactory gsonConverterFactory, @Status400Retrofit OkHttpClient okHttpClient)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mHackerNewsBaseURL)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

}

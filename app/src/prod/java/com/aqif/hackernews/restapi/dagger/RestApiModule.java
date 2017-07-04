package com.aqif.hackernews.restapi.dagger;

import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aqifhamid.
 */

@Module
public class RestApiModule
{
    private final String mApiBaseUrl;

    public RestApiModule(String apiBaseUrl)
    {
        mApiBaseUrl = apiBaseUrl;
    }

    @Provides
    @RestApiScope
    HttpLoggingInterceptor getHttpLoggingInterceptor()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @RestApiScope
    GsonConverterFactory getGson()
    {
        return GsonConverterFactory.create();
    }

    @Provides
    @RestApiScope
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }

    @Provides
    @RestApiScope
    Retrofit getRetrofit(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mApiBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

}

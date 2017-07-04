package com.aqif.hackernews.restapi.dagger;

import com.aqif.hackernews.restapi.dagger.scope.RestApiScope;

import dagger.Component;

/**
 * Created by aqifhamid.
 */

@RestApiScope
@Component(modules = {RestApiModule.class})
public interface RestApiComponent
{

}

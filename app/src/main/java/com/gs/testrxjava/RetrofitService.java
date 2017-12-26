package com.gs.testrxjava;

import retrofit2.http.Body;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by camdora on 17-11-21.
 */

public interface RetrofitService {
    @GET
    Observable<String> login();

}

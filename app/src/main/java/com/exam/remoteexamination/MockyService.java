package com.exam.remoteexamination;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MockyService {
    @POST("https://run.mocky.io/v3/2021c50a-cb01-4811-ae1b-d793c6e1e119")
    Call<DataModel> uploadData(@Body DataModel dataModel);
    @GET("https://run.mocky.io/v3/2021c50a-cb01-4811-ae1b-d793c6e1e119")
    Call<DataModel> getData();
}


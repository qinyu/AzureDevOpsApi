package com.cac.api.source

import com.cac.api.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("{organization}/{project}/_apis/build/builds")
    fun getAllBuilds(@Path("organization") organization: String,
                     @Path("project") project: String,
                     @Query("api-version") version: String ,
                     @Query("branchName") branchName: String?,
                     @Query("repositoryId") repositoryId: String?,
                     @Query("repositoryType") repositoryType: String?,
                     @Query("minTime") minTime: String?,
                     @Query("maxTime") maxTime: String?): Call<Result>
}
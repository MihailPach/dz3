package com.example.n12.retrofit

import com.example.n12.model.GithubUser
import com.example.n12.model.GithubUserDetails

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users")
    fun getUsers(
        @Query("since")since:Int,
        @Query("per_page") perPage:Int
    ): Call<List<GithubUser>>
    @GET("user/{username}")
    fun getUserDetails(
        @Path("username") username: String
    ):Call<GithubUserDetails>
//    @GET("search/users")
//    fun searchUsers(
//        @Query("q")query: String,
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int
//    ):Call<GithubSearchUsers>
}
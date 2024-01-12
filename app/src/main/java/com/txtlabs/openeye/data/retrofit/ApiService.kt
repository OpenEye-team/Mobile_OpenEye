package com.txtlabs.openeye.data.retrofit

import com.txtlabs.openeye.data.post.ArticleBody
import com.txtlabs.openeye.data.post.GlucoseBody
import com.txtlabs.openeye.data.post.LoginBody
import com.txtlabs.openeye.data.post.RegisterBody
import com.txtlabs.openeye.data.response.ArtikelResponse
import com.txtlabs.openeye.data.response.GlucoseResponse
import com.txtlabs.openeye.data.response.ListResponse
import com.txtlabs.openeye.data.response.LoginResponse
import com.txtlabs.openeye.data.response.ObjectResponse
import com.txtlabs.openeye.data.response.PhotoResponse
import com.txtlabs.openeye.data.response.PostArticleResponse
import com.txtlabs.openeye.data.response.RetinopathyResponse
import com.txtlabs.openeye.data.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("/auth/login")
    suspend fun postLogin(
        @Body loginBody: LoginBody
    ): ObjectResponse<LoginResponse>

    @GET("/auth/me")
    suspend fun getUser(): ObjectResponse<UserResponse>

    @POST("/auth/register")
    suspend fun postRegister(
        @Body loginBody: RegisterBody
    ): ObjectResponse<LoginResponse>

    @POST("/auth/logout")
    suspend fun getLogout(): ObjectResponse<Boolean>

    @GET("/glucometer/")
    suspend fun getGlucose(): ObjectResponse<GlucoseResponse>

    @POST("/glucometer/")
    suspend fun getGlucose(
        @Query("date") date: String
    ): ObjectResponse<GlucoseResponse>

    @POST("/glucometer/")
    suspend fun postGlucose(
        @Body glucoseResponse: GlucoseBody
    ): ObjectResponse<GlucoseResponse>

    @GET("/article")
    suspend fun getArticleAll(): ListResponse<ArtikelResponse>

    @POST("/article")
    suspend fun postArticle(
        @Body articleBody: ArticleBody
    ): ObjectResponse<PostArticleResponse>

    @POST("/article/upload")
    suspend fun postArticlePhoto(
        @Part image: MultipartBody.Part
    ): ObjectResponse<PhotoResponse>

    @Multipart
    @POST("/cek")
    suspend fun postRetinopathy(
        @Part file: MultipartBody.Part
    ): RetinopathyResponse
}
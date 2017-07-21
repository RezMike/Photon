package io.github.rezmike.photon.data.network

import io.github.rezmike.photon.data.network.req.AlbumReq
import io.github.rezmike.photon.data.network.req.EditProfileReq
import io.github.rezmike.photon.data.network.req.LoginReq
import io.github.rezmike.photon.data.network.req.RegisterReq
import io.github.rezmike.photon.data.network.res.*
import io.github.rezmike.photon.utils.ConstantManager
import io.github.rezmike.photon.utils.ConstantManager.AUTHORIZATION
import io.github.rezmike.photon.utils.ConstantManager.IF_MODIFIED_SINCE_HEADER
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

interface RestService {


    //region ======================== User ========================

    @PUT("user/{userId}")
    fun updateProfileInfo(@Header(AUTHORIZATION) authToken: String,
                          @Path("userId") userId: String,
                          @Body editProfileReq: EditProfileReq): Observable<Response<UserRes>>

    @POST("user/signIn")
    fun login(@Body loginReq: LoginReq): Observable<Response<UserRes>>

    @POST("user/signUp")
    fun register(@Body registerReq: RegisterReq): Observable<Response<UserRes>>

    @GET("user/{userId}")
    fun getUserInfo(@Header(IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String,
                    @Path("userId") userId: String): Observable<Response<UserRes>>

    //endregion

    //region ======================== Album ========================

    @GET("user/{userId}/album/list")
    fun getAllAlbums(@Header(IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String,
                     @Path("userId") userId: String): Observable<Response<List<AlbumRes>>>

    @POST("user/{userId}/album")
    fun createAlbum(@Header(AUTHORIZATION) authToken: String,
                    @Path("userId") userId: String,
                    @Body albumReq: AlbumReq): Observable<Response<AlbumRes>>

    @DELETE("user/{userId}/album/{id}")
    fun deleteAlbum(@Header(AUTHORIZATION) authToken: String,
                    @Path("userId") userId: String,
                    @Path("id") albumId: String): Observable<Response<AlbumRes>>
    //endregion

    //region ======================== PhotoCard ========================

    @Multipart
    @POST("user/{userId}/image/upload")
    fun uploadImage(@Header(AUTHORIZATION) authToken: String,
                    @Path("userId") userId: String,
                    @Part file: MultipartBody.Part): Observable<Response<ImageUrlRes>>

    @GET
    fun getPhotoFile(@Url fileUrl: String): Observable<Response<ResponseBody>>

    @GET("user/{userId}/favorite/{photoId}")
    fun savePhotoOnFavorite(@Header(AUTHORIZATION) authToken: String,
                            @Path("userId") userId: String,
                            @Path("photoId") photoId: String): Observable<Response<SuccessRes>>

    @GET("photocard/list")
    fun getAllPhotoCards(@Header(IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String): Observable<Response<List<PhotoCardRes>>>

    //endregion
}

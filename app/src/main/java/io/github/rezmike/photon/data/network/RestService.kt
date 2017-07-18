package io.github.rezmike.photon.data.network

import io.github.rezmike.photon.data.network.req.AlbumReq
import io.github.rezmike.photon.data.network.req.LoginReq
import io.github.rezmike.photon.data.network.res.*
import io.github.rezmike.photon.utils.ConstantManager
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

interface RestService {

    @GET("photocard/list")
    fun getAllPhotoCards(@Header(ConstantManager.IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String): Observable<Response<List<PhotoCardRes>>>

    @GET("user/{userId}/album/list")
    fun getAllAlbums(@Header(ConstantManager.IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String,
                     @Path("userId") userId: String): Observable<Response<List<AlbumRes>>>

    @GET("user/{userId}")
    fun getUserInfo(@Header(ConstantManager.IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String,
                    @Path("userId") userId: String): Observable<Response<UserRes>>

    @GET("user/{userId}/favorite/{photoId}")
    fun savePhotoOnFavorite(@Header(ConstantManager.AUTHORIZATION) authToken: String,
                            @Path("userId") userId: String,
                            @Path("photoId") photoId: String): Observable<Response<SuccessRes>>

    @GET
    fun getPhotoFile(@Url fileUrl: String): Observable<Response<ResponseBody>>

    @POST("user/signIn")
    fun login(@Body loginReq: LoginReq): Observable<Response<UserRes>>

    @POST("user/signUp")
    fun register(@Body registerReq: RegisterReq): Observable<Response<UserRes>>

    @Multipart
    @POST("user/{userId}/image/upload")
    fun uploadUserAvatar(@Header(ConstantManager.AUTHORIZATION) authToken: String,
                         @Path("userId") userId: String,
                         @Part file: MultipartBody.Part): Observable<Response<AvatarUrlRes>>

    @POST("user/{userId}/album")
    fun createAlbum(@Header(ConstantManager.AUTHORIZATION) authToken: String,
                    @Path("userId") userId: String,
                    @Body albumReq: AlbumReq): Observable<Response<AlbumRes>>
}

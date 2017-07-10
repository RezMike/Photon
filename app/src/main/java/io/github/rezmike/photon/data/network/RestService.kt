package io.github.rezmike.photon.data.network

import io.github.rezmike.photon.data.network.req.LoginReq
import io.github.rezmike.photon.data.network.res.AlbumRes
import io.github.rezmike.photon.data.network.res.PhotoCardRes
import io.github.rezmike.photon.data.network.res.SuccessRes
import io.github.rezmike.photon.data.network.res.UserRes
import io.github.rezmike.photon.utils.ConstantManager
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
}

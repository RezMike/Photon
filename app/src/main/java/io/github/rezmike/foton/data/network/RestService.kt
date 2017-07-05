package io.github.rezmike.foton.data.network

import io.github.rezmike.foton.data.network.req.SingInReq
import io.github.rezmike.foton.data.network.res.AlbumRes
import io.github.rezmike.foton.data.network.res.PhotoCardRes
import io.github.rezmike.foton.data.network.res.UserRes
import io.github.rezmike.foton.utils.ConstantManager
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

interface RestService {

    @GET("photocard/list")
    fun getAllPhotoCards(@Header(ConstantManager.IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String): Observable<Response<List<PhotoCardRes>>>

    @GET("user/{userId}/album/list")
    fun getAllAlbums(@Header(ConstantManager.IF_MODIFIED_SINCE_HEADER) lastEntityUpdate: String,
                     @Path("userId") userId: String): Observable<Response<List<AlbumRes>>>

    @POST("user/signIn")
    fun signIn(@Body signIn: SingInReq): Observable<Response<UserRes>>
}

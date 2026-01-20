package com.privatemessenger.app.common.data

import com.google.gson.Gson
import com.privatemessenger.app.R
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.common.model.ErrorResponse
import com.privatemessenger.app.retrofit.exception.NotLoggedInException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class AbstractBaseCloudDataSource {
    fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
        try {
            val response: Response<T> = apiToBeCalled()

            if (response.isSuccessful) {
                emit(ApiResponse.Success(data = response.body()!!, code = response.code()))
            } else {
                val errorData = try {
                    Gson().fromJson(
                        response.errorBody()?.string(), ErrorResponse::class.java
                    )
                } catch (e: Exception) {
                    null
                }
                emit(
                    ApiResponse.Error(
                        R.string.something_went_wrong,
                        data = errorData,
                        code = response.code()
                    )
                )
            }
        } catch (e: NotLoggedInException) {
            e.printStackTrace()
            emit(ApiResponse.Error(R.string.need_auth, code = 401))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(ApiResponse.Error(R.string.server_error))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(ApiResponse.Error(R.string.check_internet))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(R.string.something_went_wrong))
        }
    }
}
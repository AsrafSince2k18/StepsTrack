package com.since.core.data.network.utility

import com.since.core.data.BuildConfig
import com.since.core.domain.error.DataError
import com.since.core.domain.error.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.serialization.SerializationException


suspend inline fun <reified Response> HttpClient.get(
    url:String,
    queryParameter:Map<String,Any> = mapOf()
): Result<Response, DataError.NetworkError>{
    return safeCall {
        get(urlString = constraintUrl(url = url)){
            queryParameter.forEach { (key,value) ->
                parameter(key = key,value=value)
            }
        }
    }
}


suspend inline fun <reified Response> HttpClient.delete(
    url:String,
    queryParameter:Map<String,Any> = mapOf()
): Result<Response, DataError.NetworkError>{
    return safeCall {
        delete(urlString = constraintUrl(url = url)){
            queryParameter.forEach { (key,value) ->
                parameter(key = key,value=value)
            }
        }
    }
}


suspend inline fun <reified Request,reified Response> HttpClient.post(
    url:String,
    body:Request
): Result<Response, DataError.NetworkError>{
    return safeCall {
        post(urlString = constraintUrl(url = url)){
            setBody(body = body)
        }
    }
}

suspend inline fun <reified D>safeCall(response : () -> HttpResponse) : Result<D, DataError.NetworkError>{
    val response = try {
        response()
    }catch (e: UnresolvedAddressException){
        e.printStackTrace()
        return Result.Error(DataError.NetworkError.NO_INTERNET)
    }catch (e: SerializationException){
        e.printStackTrace()
        return Result.Error(DataError.NetworkError.KOTLINX_SEREARLIZATION)
    }catch (e: Exception){
        if(e is CoroutineExceptionHandler) throw e
        e.printStackTrace()
        return Result.Error(DataError.NetworkError.UN_KNOW)
    }

    return httpResponse(response = response)

}


suspend  inline fun <reified D> httpResponse(response: HttpResponse): Result<D, DataError.NetworkError>{
    return when(response.status.value){
        in 200..299 ->{
            Result.Success(response.body<D>())
        }
        400 ->{
            Result.Error(DataError.NetworkError.BAD_REQUEST)
        }
        401 ->{
            Result.Error(DataError.NetworkError.UN_AUTHORIZED)
        }
        404 ->{
            Result.Error(DataError.NetworkError.NOT_FOUND)
        }
        409->{
            Result.Error(DataError.NetworkError.CONFLICT)
        }
        in 500..599->{
            Result.Error(DataError.NetworkError.SERVER_ERROR)
        }
        else ->{
            Result.Error(DataError.NetworkError.UN_KNOW)
        }
    }
}

fun constraintUrl(url:String):String{
    return when{
        url.contains(BuildConfig.BASE_URL) -> "/$url"
        url.startsWith("/") -> BuildConfig.BASE_URL+url
        else -> BuildConfig.BASE_URL +"/$url"
    }
}
package com.since.core.data.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.since.core.data.model.AuthInfoSerelizable
import com.since.core.data.model.mapper.toAuthInfoDeseralizable
import com.since.core.data.model.mapper.toAuthInfoSeralizable
import com.since.core.domain.auth_network.SessionRepo
import com.since.core.domain.auth_network.model.AuthInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class SessionRepoImpl(
    private val sharedPreferences: SharedPreferences
) : SessionRepo {

    companion object{
        private const val KEY="user_token"
    }

    override suspend fun setAuthInfo(authInfo: AuthInfo?) {

         withContext(Dispatchers.IO){
            if(authInfo==null){
                sharedPreferences.edit {
                    clear()
                }
                return@withContext
            }
            val auth = Json.encodeToString(authInfo.toAuthInfoSeralizable())
            sharedPreferences.edit {
                putString(KEY,auth)
            }
        }


    }

    override suspend fun getAuthInfo(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val auth = sharedPreferences.getString(KEY, null)
             auth?.let { value ->
                Json.decodeFromString<AuthInfoSerelizable>(value).toAuthInfoDeseralizable()
            }
        }
    }
}
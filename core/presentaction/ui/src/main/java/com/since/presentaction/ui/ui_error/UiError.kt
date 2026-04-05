package com.since.presentaction.ui.ui_error

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiError {


    class StringResource(
        @param:StringRes
        val id:Int,
        val arg : Array<Any> = arrayOf()
    ): UiError


    @Composable
    fun asUiText() : String {
        return when(this){
            is StringResource -> {
                stringResource(id,*arg)
            }
        }
    }


    fun asUiText(context: Context) : String {
        return when(this){
            is StringResource -> {
                context.getString(id,*arg)
            }
        }
    }


}
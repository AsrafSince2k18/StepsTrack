package com.since.presentaction.designsystem.component.run

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.since.presentaction.designsystem.component.RunsBackground

@Composable
fun RunsScaffold(
    topAppBar : (@Composable () -> Unit) ={},
    floatingActionBtn : (@Composable () -> Unit) ={},
    bottomBar : (@Composable () -> Unit) ={},
    snackBar : (@Composable () -> Unit) ={},
    isBackground: Boolean=false,
    content : @Composable (PaddingValues) -> Unit
) {


    Scaffold(
        topBar = {
            topAppBar()
        },
        floatingActionButton = {
            floatingActionBtn()
        },
        snackbarHost = {
            snackBar()
        },
        bottomBar = {
            bottomBar()
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {paddingValues ->
        if(isBackground){
            RunsBackground {
                content(paddingValues)
            }
        }else{
            content(paddingValues)
        }
    }

}
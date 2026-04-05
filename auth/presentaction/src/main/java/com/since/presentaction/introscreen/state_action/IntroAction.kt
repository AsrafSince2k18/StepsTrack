package com.since.presentaction.introscreen.state_action

sealed interface IntroAction {

    data object Register: IntroAction
    data object LogIn : IntroAction

}
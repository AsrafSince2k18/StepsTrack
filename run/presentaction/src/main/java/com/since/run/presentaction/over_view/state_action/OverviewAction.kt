package com.since.run.presentaction.over_view.state_action

sealed interface OverviewAction {

    data object ActiveRunScreen: OverviewAction

}
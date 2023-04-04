package pl.paullettuce.androidtdddisplaylist.display_list.common

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {

    val ui: CoroutineDispatcher

    val background: CoroutineDispatcher
}

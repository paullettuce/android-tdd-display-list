package pl.paullettuce.androidtdddisplaylist.display_list.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestCoroutineDispatchers : CoroutineDispatchers {

    override val ui: CoroutineDispatcher
        get() = Dispatchers.Default
    override val background: CoroutineDispatcher
        get() = Dispatchers.Default
}

package pl.paullettuce.androidtdddisplaylist.display_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.inOrder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.paullettuce.androidtdddisplaylist.display_list.common.TestCoroutineDispatchers
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListResult
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListWrapper
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListApi
import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData
import pl.paullettuce.androidtdddisplaylist.display_list.validation.ApplicationPaginationDataValidator

@RunWith(MockitoJUnitRunner::class)
class DisplayListFeature {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listLiveDataObserver: Observer<ListResult>
    @Mock
    private lateinit var listApi: ListApi
    @Mock
    private lateinit var listWrapperResponse: Deferred<ListWrapper>

    private val paginationData = PaginationData(0, 10)
    private val listWrapper = ListWrapper(listOf("1", "2", "3"))
    private val listResult = ListResult.Success(listWrapper)
    private val enableLoading = ListResult.Loading(true)
    private val disableLoading = ListResult.Loading(false)

    private lateinit var listViewModel : ListViewModel

    @Before
    fun initialize() {
        val listRepository = RemoteListRepository(listApi)
        val validator = ApplicationPaginationDataValidator()
        val dispatchers = TestCoroutineDispatchers()
        listViewModel = ListViewModel(dispatchers, validator, listRepository)
    }

    @Test
    fun perform_list_display() = runBlocking {
        given(listApi.fetch(paginationData)).willReturn(listWrapperResponse)
        given(listWrapperResponse.await()).willReturn(listWrapper)

        listViewModel.listLiveData().observeForever(listLiveDataObserver)
        listViewModel.fetchList(paginationData)

        val inOrder = inOrder(listLiveDataObserver)
        inOrder.verify(listLiveDataObserver).onChanged(enableLoading)
        inOrder.verify(listLiveDataObserver).onChanged(listResult)
        inOrder.verify(listLiveDataObserver).onChanged(disableLoading)
    }
}
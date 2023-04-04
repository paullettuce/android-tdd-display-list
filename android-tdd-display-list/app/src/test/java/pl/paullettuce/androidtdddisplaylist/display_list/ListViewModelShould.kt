package pl.paullettuce.androidtdddisplaylist.display_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.paullettuce.androidtdddisplaylist.display_list.common.TestCoroutineDispatchers
import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData
import pl.paullettuce.androidtdddisplaylist.display_list.validation.PaginationDataValidator

@RunWith(MockitoJUnitRunner::class)
class ListViewModelShould {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var listRepository: ListRepository
    @Mock
    private lateinit var validator: PaginationDataValidator

    private val paginationData = PaginationData(0, 10)

    private lateinit var listViewModel : ListViewModel

    @Before
    fun initialize() {
        val dispatchers = TestCoroutineDispatchers()
        listViewModel = ListViewModel(dispatchers, validator, listRepository)
    }

    @Test
    fun perform_fetch() = runBlocking<Unit> {
        given(validator.validate(paginationData)).willReturn(true)

        listViewModel.fetchList(paginationData)

        verify(listRepository).performFetch(paginationData)
    }

    @Test
    fun not_perform_fetch_with_invalid_pagination_data() = runBlocking<Unit> {
        given(validator.validate(paginationData)).willReturn(false)

        listViewModel.fetchList(paginationData)

        verify(listRepository, never()).performFetch(paginationData)
    }
}
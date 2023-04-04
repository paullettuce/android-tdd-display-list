package pl.paullettuce.androidtdddisplaylist.display_list

import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListResult
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListWrapper
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListApi
import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RemoteListRepositoryShould {

    @Mock
    private lateinit var listApi: ListApi
    @Mock
    private lateinit var listWrapperResponse: Deferred<ListWrapper>

    private val listWrapper = ListWrapper(listOf("1", "2", "3"))
    private val success = ListResult.Success(listWrapper)
    private val failure = ListResult.Failure("Error while fetching")
    private val paginationData = PaginationData(1, 10)

    private lateinit var listRepository: RemoteListRepository

    @Before
    fun initialize() {
        listRepository = RemoteListRepository(listApi)
    }

    @Test
    fun return_list_result_when_fetch_succeeds() = runBlocking {
        given(listApi.fetch(paginationData)).willReturn(listWrapperResponse)
        given(listWrapperResponse.await()).willReturn(listWrapper)

        val result = listRepository.performFetch(paginationData)
        assertEquals(success, result)
    }

    @Test
    fun return_failure_result_when_fails() = runBlocking {
        val response = Response.error<ListWrapper>(500, ResponseBody.create(null, ""))
        given(listApi.fetch(paginationData)).willThrow(HttpException(response))

        val result = listRepository.performFetch(paginationData)
        assertEquals(failure, result)
    }
}
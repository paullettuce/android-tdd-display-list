package pl.paullettuce.androidtdddisplaylist.display_list

import pl.paullettuce.androidtdddisplaylist.display_list.data.ListResult
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListApi
import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData
import retrofit2.HttpException

class RemoteListRepository(private val listApi: ListApi) : ListRepository {

    override suspend fun performFetch(paginationData: PaginationData): ListResult {
        return try {
            val list = listApi.fetch(paginationData).await()
            ListResult.Success(list)
        } catch (e: HttpException) {
            ListResult.Failure("Error while fetching")
        }
    }
}

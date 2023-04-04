package pl.paullettuce.androidtdddisplaylist.display_list

import pl.paullettuce.androidtdddisplaylist.display_list.data.ListResult
import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData

interface ListRepository {

    suspend fun performFetch(paginationData: PaginationData): ListResult
}

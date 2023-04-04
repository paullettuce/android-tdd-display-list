package pl.paullettuce.androidtdddisplaylist.display_list.data

import kotlinx.coroutines.Deferred

interface ListApi {

    fun fetch(paginationData: PaginationData): Deferred<ListWrapper>
}

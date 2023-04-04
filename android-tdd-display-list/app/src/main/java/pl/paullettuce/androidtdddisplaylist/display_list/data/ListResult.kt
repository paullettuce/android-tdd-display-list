package pl.paullettuce.androidtdddisplaylist.display_list.data

sealed class ListResult {

    data class Success(val listWrapper: ListWrapper) : ListResult()
    data class Loading(val value: Boolean) : ListResult()
    data class Failure(val reason: String) : ListResult()
}

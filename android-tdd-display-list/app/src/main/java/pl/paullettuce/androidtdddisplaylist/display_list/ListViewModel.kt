package pl.paullettuce.androidtdddisplaylist.display_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.paullettuce.androidtdddisplaylist.display_list.common.CoroutineDispatchers
import pl.paullettuce.androidtdddisplaylist.display_list.common.CoroutineViewModel
import pl.paullettuce.androidtdddisplaylist.display_list.data.ListResult
import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData
import pl.paullettuce.androidtdddisplaylist.display_list.validation.PaginationDataValidator

class ListViewModel(
    private val dispatcher: CoroutineDispatchers,
    private val validator: PaginationDataValidator,
    private val listRepository: ListRepository
) : CoroutineViewModel(dispatcher){

    private val listLiveData = MutableLiveData<ListResult>()

    fun listLiveData(): LiveData<ListResult> = listLiveData

    fun fetchList(paginationData: PaginationData) {
        listLiveData.value = ListResult.Loading(true)
        if (validator.validate(paginationData)) {
            triggerFetch(paginationData)
        }
    }

    private fun triggerFetch(paginationData: PaginationData) {
        launch(dispatcher.background) {
            val result = listRepository.performFetch(paginationData)
            withContext(dispatcher.ui) {
                listLiveData.value = result
                listLiveData.value = ListResult.Loading(false)
            }
        }
    }
}

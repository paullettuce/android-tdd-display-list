package pl.paullettuce.androidtdddisplaylist.display_list.validation

import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData

interface PaginationDataValidator {

    fun validate(paginationData: PaginationData): Boolean
}

package pl.paullettuce.androidtdddisplaylist.display_list.validation

import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData

class ApplicationPaginationDataValidator : PaginationDataValidator {

    override fun validate(paginationData: PaginationData): Boolean {
        return with(paginationData) { page >= 0 && limit > 0 }
    }
}

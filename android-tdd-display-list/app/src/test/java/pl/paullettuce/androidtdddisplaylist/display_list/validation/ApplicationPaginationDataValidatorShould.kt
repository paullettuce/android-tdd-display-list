package pl.paullettuce.androidtdddisplaylist.display_list.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import pl.paullettuce.androidtdddisplaylist.display_list.data.PaginationData

class ApplicationPaginationDataValidatorShould {

    private val validator: PaginationDataValidator = ApplicationPaginationDataValidator()

    @Test
    fun return_true_for_valid_pagination_data() {
        val validPaginationData = PaginationData(1, 10)
        assertTrue(validator.validate(validPaginationData))
    }
    @Test

    fun return_false_for_invalid_pagination_data() {
        val validPaginationData = PaginationData(-1, 0)
        assertFalse(validator.validate(validPaginationData))
    }
}
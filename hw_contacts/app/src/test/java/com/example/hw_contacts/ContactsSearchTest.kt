package com.example.hw_contacts

import org.junit.Test
import org.junit.Assert.*

class ContactsSearchTest {

    companion object {

        private fun isCorrectSearch(contactSrc: ArrayList<Contact>, str: String, expected: ArrayList<Contact>): Boolean {
            val actual = Contact.search(contactSrc, str)
            if (expected.size != actual.size)
                return false
            for (i in 0 until expected.size)
                if (expected[i] != actual[i])
                    return false
            return true
        }
        private fun assertSearch(contactSrc: ArrayList<Contact>, str: String, expected: ArrayList<Contact>) {
            assertTrue(isCorrectSearch(contactSrc, str, expected))
        }

        val CT_EMPTY_NAME = Contact("", "12345678900")
        val CT_EMPTY_NUMBER = Contact("Empty phone number 1", "")
        val CT_EMPTY_ALL = Contact("", "")
        val CT_CASE_1 = Contact("Not case-sensitive", "10000000000")
        val CT_CASE_2 = Contact("noT CaSe-SENsiTIvE", "10000000000")
        val CT_FORMATTED_NUMBER_1 = Contact("Formatted number", "+7(123)456-78-90")
        val CT_FORMATTED_NUMBER_2 = Contact("Formatted number", "+7(132)465-87-09")
        val CT_1 = Contact("Mama", "12365498709")
        val CT_2 = Contact("Dad", "12365498710")
        val CT_3 = Contact("Bro", "11111111110")

    }



    @Test
    fun findsAllByEmptyString() {
        assertSearch(
            arrayListOf(CT_EMPTY_NAME, CT_EMPTY_NUMBER, CT_EMPTY_ALL),
            "",
            arrayListOf(CT_EMPTY_NAME, CT_EMPTY_NUMBER, CT_EMPTY_ALL)
        )
    }

    @Test
    fun emptyContactList() {
        assertSearch(
            arrayListOf(),
            "Name",
            arrayListOf()
        )
    }

    @Test
    fun searchIsNotCaseSensitive() {
        assertSearch(
            arrayListOf(CT_CASE_1, CT_CASE_2),
            "case-sensitive",
            arrayListOf(CT_CASE_1, CT_CASE_2)
        )
    }

    @Test
    fun correctSearchOfFormattedNumbers1() {
        assertSearch(
            arrayListOf(CT_FORMATTED_NUMBER_1, CT_FORMATTED_NUMBER_2),
            "8709",
            arrayListOf(CT_FORMATTED_NUMBER_2)
        )
    }

    @Test
    fun correctSearchOfFormattedNumbers2() {
        assertSearch(
            arrayListOf(CT_FORMATTED_NUMBER_1, CT_FORMATTED_NUMBER_2),
            "-78-90",
            arrayListOf(CT_FORMATTED_NUMBER_1)
        )
    }

    @Test
    fun justTest1() {
        assertSearch(
            arrayListOf(CT_1, CT_2, CT_3),
            "10",
            arrayListOf(CT_2, CT_3)
        )
    }

    @Test
    fun justTest2() {
        assertSearch(
            arrayListOf(CT_1, CT_2, CT_3),
            "a",
            arrayListOf(CT_1, CT_2)
        )
    }

    @Test
    fun justTest3() {
        assertSearch(
            arrayListOf(CT_1, CT_2, CT_3),
            "Mother",
            arrayListOf()
        )
    }

}

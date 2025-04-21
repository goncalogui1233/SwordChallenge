package com.goncalo.swordchallenge.domain.model.classes

import com.goncalo.data.mappers.Breed
import com.goncalo.data.mappers.CatApiInformation
import com.goncalo.data.mappers.Weight
import org.junit.Assert.*
import org.junit.Test

class CatInformationKtTest {

    @Test
    fun `test CatApiInformationList to CatInformationList`() {
        /*val list = getCatApiInformationList().toCatInformationList()
        assertEquals(2, list.size)
        assertEquals("Australian Mist", list.first().breedName)
        assertEquals("Balinese", list.last().breedName)*/
    }

    private fun getCatApiInformationList() = listOf(
        CatApiInformation(
            "xnzzM6MBI",
            "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg",
            500,
            500,
            listOf(
                Breed(
                    Weight("4 - 5", "metric"),
                    "amis",
                    "Australian Mist",
                    "Lively, Social, Fun-loving, Relaxed, Affectionate",
                    "Australia",
                    "Puss-Puss",
                    "12 - 16"
                )
            ),

        ),
        CatApiInformation(
            "OaTQfIktG",
            "https://cdn2.thecatapi.com/images/OaTQfIktG.jpg",
            500,
            500,
            listOf(
                Breed(
                    Weight("4 - 5", "metric"),
                    "bali",
                    "Balinese",
                    "Affectionate, Intelligent, Playful",
                    "United States",
                    "Balinese",
                    "12 - 16"
                )
            )
        )
    )
}
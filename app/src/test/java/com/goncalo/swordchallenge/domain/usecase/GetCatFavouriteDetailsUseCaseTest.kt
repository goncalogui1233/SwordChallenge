package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.data.mappers.CatDBFavouriteInformation
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.usecase.GetCatFavouriteDetailsUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCatFavouriteDetailsUseCaseTest {

    private lateinit var fakeRepository: CatInformationRepository
    private lateinit var getCatFavouriteDetailsUseCase: GetCatFavouriteDetailsUseCase

    private val item = CatInformation(
        "xnzzM6MBI",
        "abys",
        "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg",
        "Abyssinian",
        "Active, Energetic, Independent, Intelligent, Gentle",
        "Egypt",
        "Good cat",
        "14 - 15",
    )

    @Before
    fun setUp(): Unit = runBlocking {
        fakeRepository = FakeCatInformationRepository()
        getCatFavouriteDetailsUseCase = GetCatFavouriteDetailsUseCase(fakeRepository)

        fakeRepository.insertCatFavourite(item)
    }

    @Test
    fun `test getCatDetailsById`() = runTest {
        val detail = getCatFavouriteDetailsUseCase("xnzzM6MBI")

        assertEquals(true, detail.isSuccess)
        assertEquals("Abyssinian", detail.content?.breedName)
        assertEquals(true, detail.content?.isFavourite)
    }

    @Test
    fun `test getCatDetailsById with wrong id`() = runTest {
        val detail = getCatFavouriteDetailsUseCase("wrongId")

        assertEquals(false, detail.isSuccess)
        assertEquals(null, detail.content)
    }

}
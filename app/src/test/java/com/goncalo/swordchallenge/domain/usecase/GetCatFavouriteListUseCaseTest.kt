package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.data.mappers.CatDBFavouriteInformation
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.usecase.GetCatFavouriteListUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCatFavouriteListUseCaseTest {

    private lateinit var fakeRepository: CatInformationRepository
    private lateinit var getCatFavouriteListUseCase: GetCatFavouriteListUseCase

    private val itemToManipulate = CatInformation(
        "abys",
        "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg",
        "Abyssinian",
        "Active, Energetic, Independent, Intelligent, Gentle",
        "Egypt",
        "Good cat",
        "14 - 15",
    )

    @Before
    fun setUp() {
        fakeRepository = FakeCatInformationRepository()
        getCatFavouriteListUseCase = GetCatFavouriteListUseCase(fakeRepository)
    }

    @Test
    fun `test getEmptyCatFavouriteList`() = runTest {
        getCatFavouriteListUseCase().collect { favoriteList ->
            assertEquals(0, favoriteList.size)
        }
    }

    @Test
    fun `test checkIfListReturnsItemAdded`() = runTest {
        fakeRepository.insertCatFavourite(itemToManipulate)

        getCatFavouriteListUseCase().collect { favoriteList ->
            assertEquals(1, favoriteList.size)
            assertEquals(itemToManipulate.breedName, favoriteList.first().breedName)
        }
    }




}
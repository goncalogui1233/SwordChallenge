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
        "zTiuUpmqf",
        "amis",
        "https://cdn2.thecatapi.com/images/zTiuUpmqf.jpg",
        "Australian Mist",
        "Lively, Social, Fun-loving, Relaxed, Affectionate",
        "Australia",
        "Good cat",
        "12 - 16",
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
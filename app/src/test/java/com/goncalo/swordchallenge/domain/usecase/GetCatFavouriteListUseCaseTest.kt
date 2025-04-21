package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.swordchallenge.domain.model.classes.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.repository.CatInformationRepository
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
        fakeRepository.insertCatFavourite(CatFavouriteInformation(5, itemToManipulate))

        getCatFavouriteListUseCase().collect { favoriteList ->
            assertEquals(1, favoriteList.size)
            assertEquals(itemToManipulate.breedName, favoriteList.first().breedName)
        }
    }




}
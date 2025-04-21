package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.swordchallenge.domain.model.classes.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.repository.CatInformationRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RemoveCatFromFavoriteUseCaseTest {

    private lateinit var fakeRepository: CatInformationRepository
    private lateinit var removeCatFromFavoriteUseCase: RemoveCatFromFavoriteUseCase

    private val itemOneToManipulate = CatInformation(
        "zTiuUpmqf",
        "amis",
        "https://cdn2.thecatapi.com/images/zTiuUpmqf.jpg",
        "Australian Mist",
        "Lively, Social, Fun-loving, Relaxed, Affectionate",
        "Australia",
        "Good cat",
        "12 - 16",
    )

    private val itemTwoToManipulate = CatInformation(
        "zYkcOqNce",
        "amis",
        "https://cdn2.thecatapi.com/images/zTiuUpmqf.jpg",
        "Australian Mist",
        "Lively, Social, Fun-loving, Relaxed, Affectionate",
        "Australia",
        "Good cat",
        "12 - 16",
    )

    @Before
    fun setUp(): Unit = runBlocking {
        fakeRepository = FakeCatInformationRepository()
        removeCatFromFavoriteUseCase = RemoveCatFromFavoriteUseCase(fakeRepository)

        fakeRepository.insertCatFavourite(
            CatFavouriteInformation(
                5, itemOneToManipulate
            )
        )
    }


    @Test
    fun `test removeFirstItemToFavorite`() = runTest {
        val item = fakeRepository.getCatFavouriteList()

        assertEquals(1, item.size)
        val removeStatus = removeCatFromFavoriteUseCase(itemOneToManipulate)
        assertEquals(true, removeStatus.isSuccess)
        assertEquals(0, item.size)
    }

    @Test
    fun `test checkIfIsRemovingItemById`() = runTest {
        val item = fakeRepository.getCatFavouriteList()

        fakeRepository.insertCatFavourite(
            CatFavouriteInformation(
                6, itemTwoToManipulate
            )
        )

        assertEquals(2, item.size)
        val removeStatus = removeCatFromFavoriteUseCase(itemTwoToManipulate)
        assertEquals(true, removeStatus.isSuccess)
        assertEquals(1, item.size)
    }


}
package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.usecase.RemoveCatFromFavoriteUseCase
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

        fakeRepository.insertCatFavourite(itemOneToManipulate)
    }


    @Test
    fun `test removeFirstItemToFavorite`() = runTest {
        var item = fakeRepository.getCatFavouriteList()

        assertEquals(1, item.size)
        val removeStatus = removeCatFromFavoriteUseCase(itemOneToManipulate)

        item = fakeRepository.getCatFavouriteList()
        assertEquals(true, removeStatus.isSuccess)
        assertEquals(0, item.size)
    }

    @Test
    fun `test checkIfIsRemovingItemById`() = runTest {
        fakeRepository.insertCatFavourite(itemTwoToManipulate)

        var item = fakeRepository.getCatFavouriteList()
        assertEquals(2, item.size)

        val removeStatus = removeCatFromFavoriteUseCase(itemTwoToManipulate)
        item = fakeRepository.getCatFavouriteList()
        assertEquals(true, removeStatus.isSuccess)
        assertEquals(1, item.size)
    }


}
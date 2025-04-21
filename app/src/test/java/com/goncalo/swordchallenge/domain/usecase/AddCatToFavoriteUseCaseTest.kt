package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.repository.CatInformationRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddCatToFavoriteUseCaseTest {

    private lateinit var fakeRepository: CatInformationRepository
    private lateinit var addCatToFavoriteUseCase: AddCatToFavoriteUseCase


    @Before
    fun setUp() {
        fakeRepository = FakeCatInformationRepository()
        addCatToFavoriteUseCase = AddCatToFavoriteUseCase(fakeRepository)
    }

    @Test
    fun `test addItemToFavorite`() = runTest {
        val itemList = fakeRepository.getCatFavouriteList()
        assertEquals(0, itemList.size)

        val item = CatInformation(
            "xnzzM6MBI",
            "abys",
            "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg",
            "Abyssinian",
            "Active, Energetic, Independent, Intelligent, Gentle",
            "Egypt",
            "Good cat",
            "14 - 15",
        )

        val addedStatus = addCatToFavoriteUseCase(item)

        assertEquals(true, addedStatus.isSuccess)
        assertEquals(1, itemList.size)
        assertEquals(item.breedName, itemList.last().catInformation.breedName)
    }

    @Test
    fun `test tryAddDuplicateItemToFavorite`() = runTest {
        val itemList = fakeRepository.getCatFavouriteList()
        assertEquals(0, itemList.size)

        val item = CatInformation(
            "xnzzM6MBI",
            "abys",
            "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg",
            "Abyssinian",
            "Active, Energetic, Independent, Intelligent, Gentle",
            "Egypt",
            "Good cat",
            "14 - 15",
        )

        val successAddStatus = addCatToFavoriteUseCase(item)
        assertEquals(true, successAddStatus.isSuccess)
        assertEquals(1, itemList.size)

        val failAddStatus = addCatToFavoriteUseCase(item)
        assertEquals(false, failAddStatus.isSuccess)
        assertEquals(1, itemList.size)
    }



}
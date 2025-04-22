package com.goncalo.domain.usecase

import com.goncalo.data.repository.FakeCatInformationRepository
import com.goncalo.domain.model.classes.CatInformation
import com.goncalo.domain.repository.CatInformationRepository
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

    val item = CatInformation(
        "abys",
        "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg",
        "Abyssinian",
        "Active, Energetic, Independent, Intelligent, Gentle",
        "Egypt",
        "Good cat",
        "14 - 15",
    )

    @Test
    fun `test addItemToFavorite`() = runTest {
        var itemList = fakeRepository.getCatFavouriteList()
        assertEquals(0, itemList.size)

        val addedStatus = addCatToFavoriteUseCase(item)
        itemList = fakeRepository.getCatFavouriteList()

        assertEquals(true, addedStatus.isSuccess)
        assertEquals(1, itemList.size)
        assertEquals(item.breedName, itemList.last().breedName)
    }

    @Test
    fun `test tryAddDuplicateItemToFavorite`() = runTest {
        var itemList = fakeRepository.getCatFavouriteList()
        assertEquals(0, itemList.size)

        val successAddStatus = addCatToFavoriteUseCase(item)
        itemList = fakeRepository.getCatFavouriteList()
        assertEquals(true, successAddStatus.isSuccess)
        assertEquals(1, itemList.size)

        val failAddStatus = addCatToFavoriteUseCase(item)
        itemList = fakeRepository.getCatFavouriteList()
        assertEquals(false, failAddStatus.isSuccess)
        assertEquals(1, itemList.size)
    }



}
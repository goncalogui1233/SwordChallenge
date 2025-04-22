package com.goncalo.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import com.goncalo.data.repository.FakeCatInformationRepository
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.usecase.GetCatListUseCaseTest.MyModelDiffCallback
import com.goncalo.domain.usecase.GetCatListUseCaseTest.NoopListCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetSearchCatListUseCaseTest {

    private lateinit var fakeCatInformationRepository: CatInformationRepository
    private lateinit var getSearchCatListUseCase: GetSearchCatListUseCase

    @Before
    fun setUp() {
        fakeCatInformationRepository = FakeCatInformationRepository()
        getSearchCatListUseCase = GetSearchCatListUseCase(fakeCatInformationRepository)
    }


    @Test
    fun `test getSearchList with filter return one item`() = runTest {
        val itemList = getSearchCatListUseCase("Abyssinian")

        assertEquals(1, itemList.size)
        assertEquals("Abyssinian", itemList.first().breedName)
    }


    @Test
    fun `test getCatList with filter return emptyList`() = runTest {
        val itemList = getSearchCatListUseCase("NoId")

        assertEquals(0, itemList.size)
    }
}
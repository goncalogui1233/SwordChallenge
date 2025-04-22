package com.goncalo.swordchallenge.domain.usecase

import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.domain.repository.CatInformationRepository
import com.goncalo.domain.usecase.GetCatDetailsByIdUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCatDetailsByIdUseCaseTest {

    private lateinit var fakeRepository: CatInformationRepository
    private lateinit var getCatDetailsByIdUseCase: GetCatDetailsByIdUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeCatInformationRepository()
        getCatDetailsByIdUseCase = GetCatDetailsByIdUseCase(fakeRepository)
    }

    @Test
    fun `test getCatDetailsById`() = runTest {
        val detail = getCatDetailsByIdUseCase("abys")

        assertEquals(true, detail.isSuccess)
        assertEquals("Abyssinian", detail.content?.breedName)
    }

    @Test
    fun `test getCatDetailsById with wrong id`() = runTest {
        val detail = getCatDetailsByIdUseCase("wrongId")

        assertEquals(false, detail.isSuccess)
        assertEquals(null, detail.content)
    }
}
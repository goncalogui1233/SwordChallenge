package com.goncalo.swordchallenge.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.goncalo.swordchallenge.data.repository.FakeCatInformationRepository
import com.goncalo.swordchallenge.domain.model.classes.CatFavouriteInformation
import com.goncalo.swordchallenge.domain.model.classes.CatInformation
import com.goncalo.swordchallenge.domain.repository.CatInformationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCatListUseCaseTest {

    private lateinit var repository: CatInformationRepository
    private lateinit var getCatListUseCase: GetCatListUseCase
    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        repository = FakeCatInformationRepository()
        getCatListUseCase = GetCatListUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getCatList and check if returns items`() = runTest {
        getCatListUseCase("").collectLatest { pagingData ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = MyModelDiffCallback(),
                updateCallback = NoopListCallback(),
                mainDispatcher = dispatcher,
                workerDispatcher = dispatcher
            )

            differ.submitData(pagingData)
            advanceUntilIdle()

            //Return item list
            val result = differ.snapshot().items

            assertTrue(result.isNotEmpty())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getCatList and check if all items contains information`() = runTest {
        getCatListUseCase("").collectLatest { pagingData ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = MyModelDiffCallback(),
                updateCallback = NoopListCallback(),
                mainDispatcher = dispatcher,
                workerDispatcher = dispatcher
            )

            differ.submitData(pagingData)
            advanceUntilIdle()

            //Return item list
            val result = differ.snapshot().items

            assertEquals(
                result.size,
                result.filter { it.id.isNotEmpty() && it.breedName?.isNotEmpty() == true }.size
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getCatList with filter return emptyList`() = runTest {
        getCatListUseCase("pokemon").collectLatest { pagingData ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = MyModelDiffCallback(),
                updateCallback = NoopListCallback(),
                mainDispatcher = dispatcher,
                workerDispatcher = dispatcher
            )

            differ.submitData(pagingData)
            advanceUntilIdle()

            //Return item list
            val result = differ.snapshot().items

            assertEquals(0, result.size)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getCatList with filter return one item`() = runTest {
        getCatListUseCase("Bali").collectLatest { pagingData ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = MyModelDiffCallback(),
                updateCallback = NoopListCallback(),
                mainDispatcher = dispatcher,
                workerDispatcher = dispatcher
            )

            differ.submitData(pagingData)
            advanceUntilIdle()

            //Return item list
            val result = differ.snapshot().items

            assertEquals(1, result.size)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test getCatList with one item favourite`() = runTest {
        getCatListUseCase("").collectLatest { pagingData ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = MyModelDiffCallback(),
                updateCallback = NoopListCallback(),
                mainDispatcher = dispatcher,
                workerDispatcher = dispatcher
            )

            differ.submitData(pagingData)
            advanceUntilIdle()

            //Return item list
            val result = differ.snapshot().items

            repository.insertCatFavourite(CatFavouriteInformation(5, result[0]))
            differ.submitData(pagingData)
            advanceUntilIdle()

            val resultFavorite = differ.snapshot().items

            assertEquals(true, resultFavorite.any { it.isFavourite })
        }
    }


    //Helper classes to return the list of items inside PagingData
    class MyModelDiffCallback : DiffUtil.ItemCallback<CatInformation>() {
        override fun areItemsTheSame(oldItem: CatInformation, newItem: CatInformation): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CatInformation, newItem: CatInformation): Boolean =
            oldItem == newItem
    }

    class NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

}
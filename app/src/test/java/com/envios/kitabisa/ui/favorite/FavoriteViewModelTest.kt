package com.envios.kitabisa.ui.favorite

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.transition.Transition
import com.envios.kitabisa.data.local.dao.FavoriteDao
import com.envios.kitabisa.data.local.db.FavoriteDb
import com.envios.kitabisa.data.local.model.Favorite
import com.envios.kitabisa.data.remote.model.MovieDetail
import com.envios.kitabisa.data.repository.MovieRepository
import com.envios.kitabisa.ui.main.MainViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class FavoriteViewModelTest {


    @Mock
    private lateinit var viewModel: FavoriteViewModel

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var context: Context

    private lateinit var favoriteDb:FavoriteDb
    @Mock
    private lateinit var favoriteDao: FavoriteDao

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        viewModel =
            FavoriteViewModel(
                repository
            )
        favoriteDb = Room
            .inMemoryDatabaseBuilder(context, FavoriteDb::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor()) // <-- this makes all the difference
            .build()
        favoriteDao = favoriteDb.favoriteDao()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        favoriteDb.close()
    }

    @Test
    fun getAllFavorites() {

        val favorite = Favorite(
            14,
            false,
            "/DztBnZaqmla2sGUW9s8AyOmskT.jpg",
            "en",
            "American Beauty",
            "Lester Burnham, a depressed suburban father in a mid-life crisis, decides to turn his hectic life around after developing an infatuation with his daughter's attractive friend.",
            24.598,
            "/wby9315QzVKdW9BonAefg8jGTTb.jpg",
            "1999-09-15",
            "American Beauty",
            false
        )


        runBlocking {
            Mockito.`when`(repository.getAllUsersFromLocal()).thenReturn(listOf(favorite))
            viewModel.favorites.postValue(repository.getAllUsersFromLocal())

            assertNotNull(viewModel.favorites)
        }
    }

}
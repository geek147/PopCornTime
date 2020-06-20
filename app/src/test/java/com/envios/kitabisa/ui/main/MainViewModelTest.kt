package com.envios.kitabisa.ui.main

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.envios.kitabisa.data.remote.model.Movie
import com.envios.kitabisa.data.repository.MovieRepository
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
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
import retrofit2.Response

class MainViewModelTest {

    @Mock
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: MovieRepository


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        viewModel =
            MainViewModel(
             repository
            )
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getPopularMovieAsync() {
        val listMovie : MutableList<Movie?> = mutableListOf()
        val movie = Movie(
            false,
            "/naXUDz0VGK7aaPlEpsuYW8kNVsr.jpg",
            514847,
            "Twelve strangers wake up in a clearing. They don't know where they are—or how they got there. In the shadow of a dark internet conspiracy theory, ruthless elitists gather at a remote location to hunt humans for sport. But their master plan is about to be derailed when one of the hunted turns the tables on her pursuers.",
            "/wxPhn4ef1EAo5njxwBkAEVrlJJG.jpg",
            "2020-02-29",
            "Onward",
            false
        )
        listMovie.add(movie)


        val response = listMovie.toList()

        runBlocking {
            Mockito.`when`(repository.getPopularMovies("1")).thenReturn(response)

            viewModel.movies.value = response
            assertNotNull(viewModel.movies.value)
        }
    }

    @Test
    fun getTopRatedMovieAsync() {
        val listMovie : MutableList<Movie?> = mutableListOf()
        val movie = Movie(
            false,
            "/naXUDz0VGK7aaPlEpsuYW8kNVsr.jpg",
            514847,
            "Twelve strangers wake up in a clearing. They don't know where they are—or how they got there. In the shadow of a dark internet conspiracy theory, ruthless elitists gather at a remote location to hunt humans for sport. But their master plan is about to be derailed when one of the hunted turns the tables on her pursuers.",
            "/wxPhn4ef1EAo5njxwBkAEVrlJJG.jpg",
            "2020-02-29",
            "Onward",
            false
        )
        listMovie.add(movie)


        val response = listMovie.toList()

        runBlocking {
            Mockito.`when`(repository.getTopRatedMovie("1")).thenReturn(response)

            viewModel.movies.value = response
            assertNotNull(viewModel.movies.value)
        }
    }

    @Test
    fun getNowPlayingMovieAsync() {
        val listMovie : MutableList<Movie?> = mutableListOf()
        val movie = Movie(
            false,
            "/naXUDz0VGK7aaPlEpsuYW8kNVsr.jpg",
            514847,
            "Twelve strangers wake up in a clearing. They don't know where they are—or how they got there. In the shadow of a dark internet conspiracy theory, ruthless elitists gather at a remote location to hunt humans for sport. But their master plan is about to be derailed when one of the hunted turns the tables on her pursuers.",
            "/wxPhn4ef1EAo5njxwBkAEVrlJJG.jpg",
            "2020-02-29",
            "Onward",
            false
        )
        listMovie.add(movie)


        val response = listMovie.toList()

        runBlocking {
            Mockito.`when`(repository.getNowPlayingMovie("1")).thenReturn(response)

            viewModel.movies.value = response
            assertNotNull(viewModel.movies.value)
        }
    }
}
package com.envios.kitabisa.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.envios.kitabisa.data.remote.model.MovieDetail
import com.envios.kitabisa.data.remote.model.Review
import com.envios.kitabisa.data.repository.MovieRepository
import junit.framework.Assert
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

class DetailViewModelTest {

    @Mock
    private lateinit var viewModel: DetailViewModel

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
            DetailViewModel(
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
    fun getMovieDetailAsync() {
        val response = MovieDetail(
            false,
            "/DztBnZaqmla2sGUW9s8AyOmskT.jpg",
            15000000,
            "",
            14,
            "tt0169547",
            "en",
            "false",
            "Lester Burnham, a depressed suburban father in a mid-life crisis, decides to turn his hectic life around after developing an infatuation with his daughter's attractive friend.",
            24.598,
            "/wby9315QzVKdW9BonAefg8jGTTb.jpg",
            "1999-09-15",
            356296601,
            122,
            "Released",
            "Look closer.",
            "American Beauty",
        false
        )



        runBlocking {
            Mockito.`when`(repository.getMovieDetail("14")).thenReturn(response)

            viewModel.movieDetail.value = DetailViewModel.movieDetailLoaded(response)
            Assert.assertNotNull(viewModel.movieDetail.value)
        }
    }

    @Test
    fun getReviewsMovieAsync() {
        val listReview : MutableList<Review?> = mutableListOf()
        val review = Review(
        "tmdb70752507",
            "The film American Beauty to me is a film about purpose. What is your purpose in life? The film teaches that there is such great beauty in the small things in life and that sometimes you need to take a step back to allow yourself to take in the unfiltered beauty in the small things like a plastic bag dancing in the wind, playing with the leaves. The film tells it's audience that the beauty in the small details is much greater than society's typical image of beauty. The prominent rose in the film is a symbol of false, surface beauty; the expensive and romantic flower is amongst almost every single shot in the film reminding us that we need to look beyond the surface beauty of an entity and see the beauty within. This message surrounding beauty is empathised with the inclusion of the rose on the films poster and the tagline, 'look closer' indicating that to see real beauty one must 'look closer', a task that typically requires more thoughtfulness and less immediate facial value judgements that make up the typical image of beauty.",
            "5e8e1643dd47e100130fc1d0",
        "   https://www.themoviedb.org/review/5e8e1643dd47e100130fc1d0"
        )
        listReview.add(review)


        val response = listReview.toList()

        runBlocking {
            Mockito.`when`(repository.getMovieReviews("14")).thenReturn(response)

            viewModel.reviews.value = DetailViewModel.reviewsLoaded(response)
            Assert.assertNotNull(viewModel.reviews.value)
        }
    }
}
package com.example.weather.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.example.data.local.FavDao
import com.example.data.local.FavDatabase
import com.example.data.local.FavEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class TestFavDb {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    @Named("test_db")
    lateinit var database: FavDatabase
    private lateinit var dao: FavDao
    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.favDao()
    }

    @After
    fun teardown() {
        database.close()
    }
    @Test
    fun insertFavItem() = runBlocking {

        val favItem = FavEntity("suez",30.34,32.29)
        dao.addFav(favItem)
        val allFavItems = dao.getAllFav()
        assertThat(allFavItems).contains(favItem)

    }
    @Test
    fun deleteFavItem() = runBlocking {
        val favItem = FavEntity("ismaillia",30.59 ,32.27 )
        dao.addFav(favItem)
        dao.deleteFav(favItem)
        val allFavItems = dao.getAllFav()
        assertThat(allFavItems).doesNotContain(favItem)
    }

}
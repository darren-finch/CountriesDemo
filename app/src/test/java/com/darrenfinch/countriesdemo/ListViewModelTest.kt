package com.darrenfinch.countriesdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.darrenfinch.countriesdemo.model.CountriesService
import com.darrenfinch.countriesdemo.model.Country
import com.darrenfinch.countriesdemo.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest
{
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup()
    {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSuccess()
    {
        val country = Country("Country Name", "Capital", "Url")
        val countriesList = arrayListOf(country)

        testSingle = Single.just(countriesList)

        `when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        assertEquals(1, listViewModel.countries.value?.size)
        assertEquals(false, listViewModel.loadError.value)
        assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getCountriesFailed()
    {
        testSingle = Single.error(Throwable())

        `when`(countriesService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        assertEquals(null, listViewModel.countries.value?.size)
        assertEquals(true, listViewModel.loadError.value)
        assertEquals(false, listViewModel.loading.value)
    }

    @Before
    fun setupRXSchedulers()
    {
        val immediate = object : Scheduler()
        {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker
            {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}
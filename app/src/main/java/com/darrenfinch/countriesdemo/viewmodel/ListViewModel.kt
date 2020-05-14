package com.darrenfinch.countriesdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darrenfinch.countriesdemo.di.DaggerAPIComponent
import com.darrenfinch.countriesdemo.model.CountriesService
import com.darrenfinch.countriesdemo.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel()
{
    @Inject
    lateinit var countriesService: CountriesService
    private val disposable = CompositeDisposable()

    init
    {
        DaggerAPIComponent.create().inject(this)
    }

    val countries = MutableLiveData<List<Country>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh()
    {
        fetchCountries()
    }

    private fun fetchCountries()
    {
        loading.value = true
        disposable.add(countriesService.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<List<Country>>()
            {
                override fun onSuccess(value: List<Country>?)
                {
                    countries.value = value
                    loadError.value = false
                    loading.value = false
                }
                override fun onError(e: Throwable?)
                {
                    loadError.value = true
                    loading.value = false
                }
            }))
    }

    override fun onCleared()
    {
        super.onCleared()
        disposable.clear()
    }
}
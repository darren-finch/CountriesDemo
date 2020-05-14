package com.darrenfinch.countriesdemo.model

import com.darrenfinch.countriesdemo.di.DaggerAPIComponent
import io.reactivex.Single
import javax.inject.Inject

class CountriesService
{
    @Inject
    lateinit var api: CountriesAPI

    init
    {
        DaggerAPIComponent.create().inject(this)
    }

    fun getCountries() : Single<List<Country>>
    {
        return api.getCountries()
    }
}
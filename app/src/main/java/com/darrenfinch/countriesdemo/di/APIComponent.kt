package com.darrenfinch.countriesdemo.di

import com.darrenfinch.countriesdemo.model.CountriesService
import com.darrenfinch.countriesdemo.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [APIModule::class])
interface APIComponent
{
    fun inject(service: CountriesService)
    fun inject(viewModel: ListViewModel)
}
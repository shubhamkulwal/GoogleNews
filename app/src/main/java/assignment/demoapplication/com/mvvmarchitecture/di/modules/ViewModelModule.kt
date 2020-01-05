package assignment.demoapplication.com.mvvmarchitecture.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import assignment.demoapplication.com.mvvmarchitecture.dashboardmodule.viewmodel.DashboardViewModel
import assignment.demoapplication.com.mvvmarchitecture.di.util.ViewModelKey
import assignment.demoapplication.com.mvvmarchitecture.sample.viewmodel.SampleViewModel
import assignment.demoapplication.com.mvvmarchitecture.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SampleViewModel::class)
    abstract fun bindListViewModel(listViewModel: SampleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

   /* @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory*/
   @Binds
   internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

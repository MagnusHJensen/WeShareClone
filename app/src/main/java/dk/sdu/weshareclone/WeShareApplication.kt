package dk.sdu.weshareclone

import android.app.Application
import dk.sdu.weshareclone.ViewModel.ProfileViewModel
import dk.sdu.weshareclone.repositories.ProfileRepository
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.scope.get
import org.koin.dsl.module

class WeShareApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single {ProfileRepository()}
            viewModel { ProfileViewModel(repository = get()) }
        }

        startKoin {
            androidLogger()
            androidContext(this@WeShareApplication)
            modules ( appModule )
        }
    }
}
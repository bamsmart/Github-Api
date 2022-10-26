package net.shinedev.github

import android.app.Application
import com.amartha.dicoding.mysubmission3.db.DatabaseApplication
import net.shinedev.github.repository.UserRepository
import com.facebook.stetho.Stetho
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class Apps : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { DatabaseApplication.getInstance(this) }
    val repository by lazy { UserRepository(database.getUserDao()) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
    }
}
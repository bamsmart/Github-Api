package net.shinedev.favorite.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.EntryPointAccessors
import net.shinedev.favorite.MyFavoriteActivity
import net.shinedev.github.di.DaggerDynamicFeatureDependencies

@Component(
    dependencies = [DaggerDynamicFeatureDependencies::class],
    modules = [FavoriteFeatureModule::class]
)
interface DynamicFeatureComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            dependencies: DaggerDynamicFeatureDependencies
        ): DynamicFeatureComponent
    }

    fun inject(activity: MyFavoriteActivity)
}

internal fun MyFavoriteActivity.inject() {
    DaggerDynamicFeatureComponent.factory().create(
        applicationContext,
        EntryPointAccessors.fromApplication(
            applicationContext,
            DaggerDynamicFeatureDependencies::class.java
        )
    ).inject(this)
}

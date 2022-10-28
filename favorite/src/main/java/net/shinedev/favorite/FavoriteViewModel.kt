package net.shinedev.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import net.shinedev.core.domain.usecase.UserUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {
    fun getFavoriteUser(queryParams: String) = useCase.getFavoriteUser(queryParams).asLiveData()
}
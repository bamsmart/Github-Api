package net.shinedev.github.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import net.shinedev.core.domain.model.User
import net.shinedev.core.domain.usecase.UserUseCase
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    fun getListUser(queryParams: String) = useCase.getListUser(queryParams).asLiveData()

    fun getDetailUser(username: String) = useCase.getDetailUser(username).asLiveData()

    fun getFollower(username: String) = useCase.getFollower(username).asLiveData()

    fun getFollowing(username: String) = useCase.getFollowing(username).asLiveData()

    fun setFavorite(user: User, isFavorite: Boolean) = useCase.setFavoriteUser(user, isFavorite)


}
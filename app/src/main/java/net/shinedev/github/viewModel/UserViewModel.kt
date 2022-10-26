package net.shinedev.github.viewModel

import androidx.lifecycle.*
import com.amartha.dicoding.mysubmission3.BuildConfig.KEY_AUTH
import net.shinedev.github.entity.User
import net.shinedev.github.network.NetworkModule
import net.shinedev.github.network.UserApi
import net.shinedev.github.repository.UserRepository
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

interface UserContract {
    fun getListUser(username: String?)
    fun setUsernameUser(username: String?)
    fun getFollowing(username: String?)
    fun setUsernameFollowing(username: String?)
    fun getFollower(username: String?)
    fun setUsernameFollowers(username: String?)
    fun getDetailUser(username: String?)
    fun setDetailUser(username: String?)
}

class UserViewModel(private val repository: UserRepository) : ViewModel(), UserContract {

    private var api = NetworkModule.createService(UserApi::class.java)

    private val _usernameList = MutableLiveData<String?>()

    private val _resultListUser = MediatorLiveData<ArrayList<User>?>()

    val resultListUser: LiveData<ArrayList<User>?>
        get() = _resultListUser

    val resultFavoriteUser: LiveData<List<User>> = repository.allFavoriteUser.asLiveData()

    fun getFavoriteById(userId: Long): LiveData<User> {
        return repository.getFavoriteUser(userId).asLiveData()
    }

    private val _usernameFollowing = MutableLiveData<String?>()
    private val _resultFollowing = MediatorLiveData<ArrayList<User>?>()
    val resultFollowing: LiveData<ArrayList<User>?>
        get() = _resultFollowing

    private val _usernameFollower = MutableLiveData<String?>()
    private val _resultFollower = MediatorLiveData<ArrayList<User>?>()
    val resultFollower: LiveData<ArrayList<User>?>
        get() = _resultFollower

    private val _usernameDetail = MutableLiveData<String?>()
    private val _resultDetailUser = MediatorLiveData<User?>()
    val resultDetailUser: LiveData<User?>
        get() = _resultDetailUser

    init {
        _resultListUser.addSource(_usernameList) {
            getListUser(it)
        }

        _resultFollowing.addSource(_usernameFollowing) {
            getFollowing(it)
        }

        _resultFollower.addSource(_usernameFollower) {
            getFollower(it)
        }

        _resultDetailUser.addSource(_usernameDetail) {
            getDetailUser(it)
        }
    }

    override fun getListUser(username: String?) {
        val call = api.getListUser(KEY_AUTH, username)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _resultListUser.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val jsonObject = JSONObject(it.string())
                        if (!jsonObject.isNull("items")) {
                            val jsonArray = jsonObject.getJSONArray("items")
                            val dataList: ArrayList<User> = arrayListOf()
                            for (i in 0 until jsonArray.length()) {
                                dataList.add(
                                    Gson().fromJson(
                                        jsonArray[i].toString(),
                                        User::class.java
                                    )
                                )
                            }
                            _resultListUser.postValue(dataList)
                        } else {
                            _resultListUser.postValue(null)
                        }
                    }
                }
            }
        })
    }

    override fun setUsernameUser(username: String?) {
        _usernameList.value = username
    }


    override fun getFollowing(username: String?) {
        val call = api.getFollowing(keyToken, username)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _resultFollowing.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val jsonArray = JSONArray(it.string())
                        val dataList: ArrayList<User> = arrayListOf()
                        for (i in 0 until jsonArray.length()) {
                            dataList.add(
                                Gson().fromJson(
                                    jsonArray[i].toString(),
                                    User::class.java
                                )
                            )
                        }
                        _resultFollowing.postValue(dataList)
                    }
                }
            }
        })
    }

    override fun setUsernameFollowing(username: String?) {
        _usernameFollowing.value = username
    }

    override fun getFollower(username: String?) {
        val call = api.getFollower(KEY_AUTH, username)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _resultFollower.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val jsonArray = JSONArray(it.string())
                        val dataList: ArrayList<User> = arrayListOf()
                        for (i in 0 until jsonArray.length()) {
                            dataList.add(
                                Gson().fromJson(
                                    jsonArray[i].toString(),
                                    User::class.java
                                )
                            )
                        }
                        _resultFollower.postValue(dataList)
                    }
                }
            }
        })
    }

    override fun setUsernameFollowers(username: String?) {
        _usernameFollower.value = username
    }

    override fun getDetailUser(username: String?) {
        val call = api.getDetailUser(KEY_AUTH, username)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _resultDetailUser.value = null
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val jsonObject = JSONObject(it.string())
                        val data = Gson().fromJson(
                            jsonObject.toString(),
                            User::class.java
                        )
                        _resultDetailUser.postValue(data)
                    }
                }
            }
        })
    }

    override fun setDetailUser(username: String?) {
        _usernameDetail.value = username
    }
}
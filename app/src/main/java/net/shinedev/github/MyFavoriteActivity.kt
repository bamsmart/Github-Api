package net.shinedev.github

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import net.shinedev.github.DetailActivity.Companion.EXTRA_USER
import net.shinedev.github.adapter.UserAdapter
import net.shinedev.github.databinding.ActivityFavoriteBinding
import net.shinedev.github.entity.User
import net.shinedev.github.listener.OnItemClickListener
import net.shinedev.github.viewModel.UserViewModel
import net.shinedev.github.viewModel.UserViewModelFactory
import timber.log.Timber

class MyFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Apps).repository)
    }
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            setDataList()
        }
        setAdapter()
        initView()
        initObserve()
    }

    private fun initView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.etSrc

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                setDataList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        userAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(item: Any) {
                startActivity(
                    Intent(this@MyFavoriteActivity, DetailActivity::class.java).putExtra(
                        EXTRA_USER,
                        item as User,
                        
                    )
                )
            }
        }
    }

    private fun setAdapter() {
        userAdapter = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter
    }

    private fun initObserve() {
        viewModel.resultFavoriteUser.observe(this@MyFavoriteActivity) { users ->
            Timber.d("data user $users")
            users?.let {
                if (it.isNotEmpty()) {
                    showResult()
                    userAdapter.setData(it as ArrayList<User>)
                } else {
                    emptyResult()
                }
            } ?: run {
                emptyResult()
            }
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun setDataList(keySearch: String? = "bams") {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.setUsernameUser(keySearch)
    }

    private fun emptyResult() {
        binding.rvUser.visibility = View.GONE
        binding.tvNoResult.visibility = View.VISIBLE
    }

    private fun showResult() {
        binding.rvUser.visibility = View.VISIBLE
        binding.tvNoResult.visibility = View.GONE
    }


}
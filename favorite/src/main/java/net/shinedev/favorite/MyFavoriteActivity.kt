package net.shinedev.favorite

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.shinedev.core.common.adapter.UserAdapter
import net.shinedev.core.data.Resource
import net.shinedev.core.domain.model.User
import net.shinedev.core.extension.hide
import net.shinedev.core.extension.show
import net.shinedev.favorite.databinding.ActivityFavoriteBinding
import net.shinedev.favorite.di.inject
import net.shinedev.github.DetailActivity.Companion.EXTRA_IS_FAVORITE
import net.shinedev.github.DetailActivity.Companion.EXTRA_USER
import javax.inject.Inject

@AndroidEntryPoint
class MyFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }

    private lateinit var userAdapter: UserAdapter

    override fun onStart() {
        super.onStart()
        observeData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.favorite_user)

        if (savedInstanceState == null) {
            observeData()
        }
        setAdapter()
        initView()
        observeData()
    }

    private fun initView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.etSrc
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    observeData(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        userAdapter.onItemClickListener = object : net.shinedev.core.listener.OnItemClickListener {
            override fun onItemClick(item: Any) {
                startActivity(
                    Intent(
                        this@MyFavoriteActivity,
                        Class.forName("net.shinedev.github.DetailActivity")
                    ).apply {
                        putExtra(
                            EXTRA_USER,
                            item as User
                        )
                        putExtra(EXTRA_IS_FAVORITE, true)
                    }
                )
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out)
            }
        }
    }

    private fun setAdapter() = with(binding) {
        userAdapter = UserAdapter()
        rvUser.apply {
            layoutManager = LinearLayoutManager(this@MyFavoriteActivity)
            adapter = userAdapter
        }
    }

    private fun observeData(queryParams: String = "") {
        viewModel.getFavoriteUser(queryParams).observe(this@MyFavoriteActivity) { result ->
            when (result) {
                is Resource.Success -> {
                    showResult()
                    userAdapter.setData(result.data as ArrayList<User>)
                }
                is Resource.Error -> emptyResult()

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun emptyResult() = with(binding) {
        rvUser.hide()
        tvNoResult.show()
        progressBar.hide()
    }

    private fun showResult() = with(binding) {
        rvUser.show()
        tvNoResult.hide()
        progressBar.hide()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
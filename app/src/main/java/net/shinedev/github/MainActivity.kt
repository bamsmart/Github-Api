package net.shinedev.github

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import net.shinedev.core.common.adapter.UserAdapter
import net.shinedev.core.data.Resource
import net.shinedev.core.domain.model.User
import net.shinedev.core.extension.hide
import net.shinedev.core.extension.show
import net.shinedev.core.listener.OnItemClickListener
import net.shinedev.github.DetailActivity.Companion.EXTRA_USER
import net.shinedev.github.databinding.ActivityMainBinding
import net.shinedev.github.viewModel.UserViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.title_list_user)

        initSearchView()
        setupUserListAdapter()
        if (savedInstanceState == null) {
            observeUserData()
        }
    }

    private fun initSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.etSrc

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(net.shinedev.core.R.string.search_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    observeUserData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupUserListAdapter() = with(binding) {
        userAdapter = UserAdapter()
        rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        userAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(item: Any) {
                val data = item as User
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(EXTRA_USER, data)
                    putExtra(DetailActivity.EXTRA_IS_FAVORITE, data.isFavorite)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeUserData(keySearch: String = "bams") = with(binding) {
        viewModel.getListUser(keySearch).observe(this@MainActivity) { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.show()
                    tvNoResult.hide()
                }
                is Resource.Success -> {
                    userAdapter.setData(result.data as ArrayList<User>)
                    showResult()
                }
                is Resource.Error -> {
                    progressBar.hide()
                    showNoResult()
                }
            }
        }
    }

    private fun showNoResult() = with(binding) {
        rvUser.hide()
        tvNoResult.show()
    }

    private fun showResult() = with(binding) {
        rvUser.show()
        tvNoResult.hide()
        progressBar.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite_user) {
            installFavoriteModule()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleFavorite = FAVORITE_MODULE
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToFavoriteActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        getString(R.string.success_installing_module),
                        Toast.LENGTH_SHORT
                    ).show()
                    moveToFavoriteActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        getString(R.string.failed_installing_module),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun moveToFavoriteActivity() {
        startActivity(Intent(this, Class.forName("net.shinedev.favorite.MyFavoriteActivity")))
    }

    companion object {
        const val FAVORITE_MODULE = "favorite"
    }
}
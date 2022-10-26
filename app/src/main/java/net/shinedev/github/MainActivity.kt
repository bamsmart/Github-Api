package net.shinedev.github

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import net.shinedev.github.DetailActivity.Companion.EXTRA_USER
import net.shinedev.github.adapter.UserAdapter
import net.shinedev.github.databinding.ActivityMainBinding
import net.shinedev.github.entity.User
import net.shinedev.github.listener.OnItemClickListener
import net.shinedev.github.viewModel.UserViewModel
import net.shinedev.github.viewModel.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Apps).repository)
    }
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
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
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_USER, item as User)
                startActivity(intent)
            }
        }
    }

    private fun setAdapter() {
        userAdapter = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter
    }

    private fun initObserve() {
        viewModel.resultListUser.observe(this) { users ->
            users?.let {
                if (it.size > 0) {
                    showResult()
                    userAdapter.setData(it)
                } else {
                    showNoResult()
                }
            } ?: run {
                showNoResult()
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDataList(keySearch: String? = "bams") {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.setUsernameUser(keySearch)
    }

    private fun showNoResult() {
        binding.rvUser.visibility = View.GONE
        binding.tvNoResult.visibility = View.VISIBLE
    }

    private fun showResult() {
        binding.rvUser.visibility = View.VISIBLE
        binding.tvNoResult.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite_user) {
            startActivity(Intent(this, MyFavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
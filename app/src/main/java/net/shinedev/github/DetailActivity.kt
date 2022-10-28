package net.shinedev.github

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.shinedev.core.data.Resource
import net.shinedev.core.domain.model.User
import net.shinedev.core.extension.hide
import net.shinedev.core.extension.parcelable
import net.shinedev.core.extension.show
import net.shinedev.github.adapter.SectionPagerAdapter
import net.shinedev.github.databinding.ActivityDetailBinding
import net.shinedev.github.viewModel.UserViewModel


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null
    private var isFavorite: Boolean = false

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_detail_user)

        if (savedInstanceState == null) {
            user = intent.parcelable(EXTRA_USER)
            isFavorite = intent.getBooleanExtra(EXTRA_IS_FAVORITE, false)
            user?.username?.let {
                observeData(it)
            }
        }
        initFavoriteIcon()
        handleSetFavorite()
    }

    private fun handleSetFavorite() = with(binding) {
        btnAdd.setOnClickListener {
            if (!isFavorite) {
                isFavorite = true
                btnAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                isFavorite = false
                btnAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            user?.let { usr ->
                lifecycleScope.launch {
                    viewModel.setFavorite(usr, isFavorite).collectLatest { row ->
                        if (row > 0) {
                            Snackbar.make(
                                binding.root,
                                if (isFavorite) getString(
                                    R.string.success_favorite,
                                    usr.username
                                ) else getString(R.string.success_un_favorite, usr.username),
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                binding.root,
                                getString(R.string.failed_favorite),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeData(username: String) = with(binding) {
        progressBar.visibility = View.VISIBLE
        viewModel.getDetailUser(username).observe(this@DetailActivity) { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { usr ->
                        user = usr
                        Glide.with(this@DetailActivity).load(usr.avatar).into(imgPhoto)
                        tvName.text = usr.name
                        tvUsername.text = usr.username
                        tvCompany.text = usr.company
                        tvLocation.text = usr.location

                        val sectionPagerAdapter =
                            SectionPagerAdapter(this@DetailActivity, supportFragmentManager)
                        sectionPagerAdapter.username = usr.username
                        sectionPagerAdapter.follower = usr.follower
                        sectionPagerAdapter.following = usr.following

                        val viewPager: ViewPager = viewPager
                        viewPager.adapter = sectionPagerAdapter
                        tabs.setupWithViewPager(viewPager)
                        supportActionBar?.elevation = 0f

                        content.show()
                        tvNoResult.hide()
                    } ?: run {
                        content.hide()
                        tvNoResult.show()
                    }
                    progressBar.hide()
                }
                is Resource.Error -> {
                    progressBar.hide()
                    content.hide()
                    tvNoResult.show()
                }
                is Resource.Loading -> {
                    progressBar.show()
                }
            }
        }
    }

    private fun initFavoriteIcon() = with(binding) {
        if (isFavorite) {
            btnAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            btnAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
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

    companion object {
        const val EXTRA_USER = "user"
        const val EXTRA_IS_FAVORITE = "favorite"
    }
}
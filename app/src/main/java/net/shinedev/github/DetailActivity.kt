package net.shinedev.github

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.shinedev.github.adapter.SectionPagerAdapter
import net.shinedev.github.databinding.ActivityDetailBinding
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.AVATAR
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.COMPANY
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.FOLLOWING
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.IS_FAVORITE
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.LOCATION
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.NAME
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.USERNAME
import net.shinedev.github.db.DatabaseContract.UserColumns.Companion.USER_ID
import net.shinedev.github.entity.User
import net.shinedev.github.viewModel.UserViewModel
import net.shinedev.github.viewModel.UserViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null
    var isFavorite: Boolean = false

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Apps).repository)
    }
    private lateinit var uriWithId: Uri

    companion object {
        const val EXTRA_USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            user = intent.getParcelableExtra(EXTRA_USER)
            initView()
        }

        initObserve()
    }

    private fun initView() {
        viewModel.getFavoriteById(user!!.userId!!.toLong()).observe(this) { user ->
            user?.let {
                isFavorite = it.isFavorite
                if (isFavorite) {
                    binding.btnAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    binding.btnAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }

        user?.username?.let {
            viewModel.setDetailUser(it)
        }

        if (isFavorite) {
            binding.btnAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.btnAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        binding.btnAdd.setOnClickListener {
            if (!isFavorite) {
                isFavorite = true
                binding.btnAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
                addToFavoriteUser()
            } else {
                isFavorite = false
                binding.btnAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                removeFavoriteUser()
            }

        }
    }

    private fun initObserve() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.resultDetailUser.observe(this) { response ->
            response?.let { usr ->
                user = usr
                Glide.with(this).load(usr.avatar).into(binding.imgPhoto)
                binding.tvName.text = usr.name
                binding.tvUsername.text = usr.username
                binding.tvCompany.text = usr.company
                binding.tvLocation.text = usr.location

                val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
                sectionPagerAdapter.username = usr.username
                sectionPagerAdapter.follower = usr.follower
                sectionPagerAdapter.following = usr.following

                val viewPager: ViewPager = binding.viewPager
                viewPager.adapter = sectionPagerAdapter
                binding.tabs.setupWithViewPager(viewPager)
                supportActionBar?.elevation = 0f

                binding.content.visibility = View.VISIBLE
                binding.tvNoResult.visibility = View.GONE
            } ?: run {
                binding.content.visibility = View.GONE
                binding.tvNoResult.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun addToFavoriteUser() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                uriWithId = Uri.parse(CONTENT_URI.toString())
                val values = ContentValues()

                values.put(USER_ID, user?.userId)
                values.put(USERNAME, user?.username)
                values.put(NAME, user?.name)
                values.put(AVATAR, user?.avatar)
                values.put(COMPANY, user?.company)
                values.put(LOCATION, user?.location)
                values.put(FOLLOWERS, user?.follower)
                values.put(FOLLOWING, user?.following)
                values.put(IS_FAVORITE, true)
                contentResolver.insert(uriWithId, values)
            }
        }
    }

    private fun removeFavoriteUser() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user?.userId)
                contentResolver.delete(uriWithId, null, null)

            }
        }
    }
}
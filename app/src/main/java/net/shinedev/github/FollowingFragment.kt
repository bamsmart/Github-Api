package net.shinedev.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.shinedev.core.data.Resource
import net.shinedev.core.domain.model.User
import net.shinedev.core.common.adapter.UserAdapter
import net.shinedev.core.extension.hide
import net.shinedev.core.extension.show
import net.shinedev.github.databinding.FragmentFollowingBinding
import net.shinedev.github.viewModel.UserViewModel

@AndroidEntryPoint
class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private var username: String? = null

    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
            username?.let { user -> observeData(user) }
        }
        setAdapter()
    }

    private fun setAdapter()= with(binding) {
        userAdapter = UserAdapter()
        rvFollowing.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun observeData(username: String) = with(binding) {
        viewModel.getFollowing(username).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { usr ->
                        if (usr.isNotEmpty()) {
                            userAdapter.setData(usr as ArrayList<User>)
                            rvFollowing.show()
                            tvNoResult.hide()
                        } else {
                            showNoResult()
                        }
                        progressBar.hide()
                    } ?: run {
                        showNoResult()
                    }
                }
                is Resource.Error -> {
                    progressBar.hide()
                }
                is Resource.Loading -> {
                    progressBar.show()
                }
            }
        }
    }

    private fun showNoResult() = with(binding) {
        rvFollowing.hide()
        tvNoResult.show()
    }

    companion object {
        private const val ARG_USERNAME = "username"

        @JvmStatic
        fun newInstance(username: String?) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }
}
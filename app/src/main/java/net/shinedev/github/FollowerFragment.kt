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
import net.shinedev.github.databinding.FragmentFollowerBinding
import net.shinedev.github.viewModel.UserViewModel

@AndroidEntryPoint
class FollowerFragment : Fragment() {
    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentFollowerBinding

    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            arguments?.let {
                username = it.getString(ARG_USERNAME)
                username?.let { user ->
                    observeData(user)
                }
            }
        }
        setAdapter()
    }

    private fun setAdapter()= with(binding) {
        userAdapter = UserAdapter()
        rvFollower.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun observeData(username: String) = with(binding) {
        viewModel.getFollower(username).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { usr ->
                        if (usr.isNotEmpty()) {
                            userAdapter.setData(usr as ArrayList<User>)
                            rvFollower.show()
                            tvNoResult.hide()
                        } else {
                            showNoResult()
                        }
                    } ?: run {
                        showNoResult()
                    }
                    progressBar.hide()
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

    private fun showNoResult()= with(binding){
        rvFollower.hide()
        tvNoResult.show()
    }

    companion object {
        private const val ARG_USERNAME = "username"

        @JvmStatic
        fun newInstance(username: String?) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }
}
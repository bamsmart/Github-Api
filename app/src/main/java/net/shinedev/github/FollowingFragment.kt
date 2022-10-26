package net.shinedev.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import net.shinedev.github.adapter.UserAdapter
import net.shinedev.github.databinding.FragmentFollowingBinding
import net.shinedev.github.viewModel.UserViewModel
import net.shinedev.github.viewModel.UserViewModelFactory

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private var username: String? = null

    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory((activity?.application as Apps).repository)
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
        }

        if (savedInstanceState == null) {
            setDataList()
        }
        setAdapter()
        initObserve()
    }

    private fun setAdapter() {
        userAdapter = UserAdapter()
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.adapter = userAdapter
    }

    private fun setDataList() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.setUsernameFollowing(username)
    }

    private fun initObserve() {
        viewModel.resultFollowing.observe(viewLifecycleOwner) { users ->
            users?.let { usr ->
                if (usr.size > 0) {
                    userAdapter.setData(usr)
                    binding.rvFollowing.visibility = View.VISIBLE
                    binding.tvNoResult.visibility = View.GONE
                } else {
                    showNoResult()
                }
                binding.progressBar.visibility = View.GONE
            } ?: run {
                showNoResult()
            }
        }
    }

    private fun showNoResult() {
        binding.rvFollowing.visibility = View.GONE
        binding.tvNoResult.visibility = View.VISIBLE
    }
}
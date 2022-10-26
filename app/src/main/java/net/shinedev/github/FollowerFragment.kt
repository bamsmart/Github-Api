package net.shinedev.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import net.shinedev.github.adapter.UserAdapter
import net.shinedev.github.databinding.FragmentFollowerBinding
import net.shinedev.github.viewModel.UserViewModel
import net.shinedev.github.viewModel.UserViewModelFactory

class FollowerFragment : Fragment() {
    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory((activity?.application as Apps).repository)
    }

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
            }
            setDataList()
        }
        setAdapter()
        initObserve()
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

    private fun setAdapter() {
        userAdapter = UserAdapter()
        binding.rvFollower.layoutManager = LinearLayoutManager(context)
        binding.rvFollower.adapter = userAdapter
    }

    private fun initObserve() {
        viewModel.resultFollower.observe(viewLifecycleOwner) { users ->
            users?.let { usr ->
                if (usr.size > 0) {
                    userAdapter.setData(usr)
                    binding.rvFollower.visibility = View.VISIBLE
                    binding.tvNoResult.visibility = View.GONE
                } else {
                    showNoResult()
                }
            } ?: run {
                showNoResult()
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDataList() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.setUsernameFollowers(username)
    }

    private fun showNoResult() {
        binding.rvFollower.visibility = View.GONE
        binding.tvNoResult.visibility = View.VISIBLE
    }
}
package pcs.technicaltest.robisaputra.ui.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pcs.technicaltest.robisaputra.R
import pcs.technicaltest.robisaputra.databinding.FragmentUserListBinding
import rmtz.lib.baseapplication.BaseFragment
import robi.codingchallenge.networks.NetworkState
import robi.codingchallenge.networks.data.User

@AndroidEntryPoint
class ListUserFragment: BaseFragment<FragmentUserListBinding>() {
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var adapter: Adapter
    private var allData: MutableList<User> = mutableListOf()
    private var displayedData: MutableList<User> = mutableListOf()
    private var pageSize = 10
    private var currentPage = 0

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserListBinding {
        return FragmentUserListBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        adapter = Adapter(mutableListOf())
        adapter.callback = object : Adapter.AdapterCallback {
            override fun onItemClick(user: User) {
                val bundle = Bundle().apply {
                    putParcelable("User", user)
                }
                findNavController().navigate(R.id.detailUserFragment, bundle)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())

        binding.apply {
            swipeRefresh.setOnRefreshListener {
                allData.clear()
                displayedData.clear()
                pageSize = 10
                currentPage = 0
                adapter.updateItems(allData)
                userViewModel.fetchData()
                binding.swipeRefresh.isRefreshing = false
            }
            rvListUsers.layoutManager = layoutManager
            rvListUsers.adapter = adapter
            rvListUsers.isNestedScrollingEnabled = true
            nestedScrollView.setOnScrollChangeListener { _, _, _, _, _ ->
                val bottomReached = !nestedScrollView.canScrollVertically(1) // Check if at the bottom
                if (bottomReached) {
                    binding.footerShimmerLayout.visibility = View.VISIBLE
                    binding.footerShimmerLayout.startShimmer()
                    Log.d(TAG, "Bottom Data")
                    loadNextPage()
                }
            }
        }

        allData.clear()
        displayedData.clear()
        pageSize = 10
        currentPage = 0
        userViewModel.fetchData()
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        lifecycleScope.launch {
            userViewModel.users.collect {
                state ->
                run {
                    when (state) {
                        is NetworkState.Loading -> {
                            binding.rvListUsers.visibility = View.GONE
                            binding.exceptionLayout.visibility = View.GONE
                            binding.shimmerLayout.visibility = View.VISIBLE
                            binding.shimmerLayout.startShimmer()
                        }

                        is NetworkState.Success -> {
                            val userList = state.data
                            binding.shimmerLayout.visibility = View.GONE
                            binding.exceptionLayout.visibility = View.GONE
                            binding.shimmerLayout.stopShimmer()
                            binding.rvListUsers.visibility = View.VISIBLE
                            allData = userList as MutableList<User>
                            withContext(Dispatchers.Main) {
                                loadNextPage() // Load the first page
                            }
                        }

                        is NetworkState.Error -> {
                            Log.e("TAG", "Error: ${state.exception.message}")
                            binding.exceptionLayout.visibility = View.VISIBLE
                            binding.rvListUsers.visibility = View.GONE
                            binding.shimmerLayout.visibility = View.GONE
                            binding.tvTittle.text = "Network Error"
                            binding.tvDescription.text = "${state.exception.message}"
                        }

                        else -> {
                            Log.e("TAG", "Error: $state")
                            binding.exceptionLayout.visibility = View.VISIBLE
                            binding.rvListUsers.visibility = View.GONE
                            binding.shimmerLayout.visibility = View.GONE
                            binding.tvTittle.text = "Unknown Error"
                            binding.tvDescription.text = "Undefined Error Occurred"
                        }
                    }
                }
            }
        }
    }

    private fun loadNextPage() {
        val start = currentPage * pageSize
        val end = minOf(start + pageSize, allData.size)
        Log.d(TAG, "Start: $start, End: $end")
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000)
            if (start < allData.size) {
                displayedData.addAll(allData.subList(start, end))
                adapter.updateItemsInRange(allData.subList(start, end))
                currentPage++
                binding.footerShimmerLayout.stopShimmer()
                binding.footerShimmerLayout.visibility = View.GONE
            } else {
                binding.footerShimmerLayout.stopShimmer()
                binding.footerShimmerLayout.visibility = View.GONE
            }
        }

    }
}
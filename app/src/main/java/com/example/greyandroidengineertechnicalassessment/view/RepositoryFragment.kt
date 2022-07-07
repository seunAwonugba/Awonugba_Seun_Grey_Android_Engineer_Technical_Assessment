package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greyandroidengineertechnicalassessment.adapter.GitHubRepoAdapter
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import com.example.greyandroidengineertechnicalassessment.viewmodel.SearchRepositoryViewModel
import com.example.greyandroidengineertechnicalassessment.view.data.RepositoryResponse
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentRepositoryBinding
import com.example.greyandroidengineertechnicalassessment.utils.Constants.QUERY_PAGE_SIZE
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    lateinit var gitHubRepoAdapter: GitHubRepoAdapter

    private val viewModel : SearchRepositoryViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRepositoryBinding.bind(view)
        setUpRecyclerView()


        //observe state when button is clicked
        binding.button3.setOnClickListener {
            if (binding.editText.text.trim().isEmpty()){
                Snackbar.make(binding.root, "Input field cannot be empty", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                viewModel.searchGitHubRepository(binding.editText.text.trim().toString())
                viewModel.state.observe(viewLifecycleOwner, Observer { response ->
                    when(response){
                        is Resource.Initial -> {
                            showInitialStateRes()
                            hideErrorStateRes()
                            hideProgressBar()
                        }
                        is Resource.Success -> {
                            if (response.data?.items?.isNotEmpty() == true){
                                hideInitialStateRes()
                                hideProgressBar()
                                hideErrorStateRes()
                                response.data.let { data ->
                                    gitHubRepoAdapter.differ.submitList(data.items?.toList()?.map {
                                        RepositoryResponse(
                                            id = it.id ?: 0,
                                            fullName = it.full_name.toString(),
                                            profilePicture = it.owner?.avatar_url ?: "",
                                            star = it.stargazers_count ?: 0,
                                            language = it.language ?: "" ,
                                            description = it.description ?: "",
                                            topics = it.topics ?: emptyList()
                                        )
                                    })
                                    val totalPages = (data.total_count?.div(QUERY_PAGE_SIZE) ?: 0) + 2
                                    isLastPage = viewModel.pageNumber == totalPages

                                }
                            }else{
                                hideInitialStateRes()
                                hideProgressBar()
                                gitHubRepoAdapter.differ.submitList(emptyList())
                                showErrorStateRes()
                            }
                        }
                        is Resource.Error -> {
                            hideInitialStateRes()
                            hideProgressBar()
                            Snackbar.make(
                                binding.root,
                                "Cannot fetch more data at this time: API rate limit exceeded",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is Resource.Loading -> {
                            showProgressBar()
                            hideInitialStateRes()
                            hideErrorStateRes()
                        }
                    }
                })
            }
        }



    }

    private fun setUpRecyclerView(){
        gitHubRepoAdapter = GitHubRepoAdapter()
        binding.searchRepoRv.apply {
            adapter = gitHubRepoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@RepositoryFragment.scrollListener)

        }

    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true

    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        isLoading = false
    }

    private fun hideInitialStateRes(){
        binding.emptyStateImage.visibility = View.GONE
        binding.emptyStateText.visibility = View.GONE
    }

    private fun showInitialStateRes(){
        binding.emptyStateImage.visibility = View.VISIBLE
        binding.emptyStateText.visibility = View.VISIBLE
    }

    private fun showErrorStateRes(){
        binding.emptyStateImage.visibility = View.VISIBLE
        binding.errTxt.visibility = View.VISIBLE
    }

    private fun hideErrorStateRes(){
        binding.emptyStateImage.visibility = View.GONE
        binding.errTxt.visibility = View.GONE
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    var scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate =
                isNotLoadingAndNotLastPage &&
                        isAtLastItem &&
                        isNotAtBeginning &&
                        isTotalMoreThanVisible &&
                        isScrolling


            if(shouldPaginate) {
                viewModel.searchGitHubRepository(binding.editText.text.trim().toString())
                isScrolling = false
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
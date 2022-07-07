package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greyandroidengineertechnicalassessment.adapter.GitHubRepoAdapter
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import com.example.greyandroidengineertechnicalassessment.viewmodel.SearchRepositoryViewModel
import com.example.greyandroidengineertechnicalassessment.view.data.RepositoryResponse
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentRepositoryBinding
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
        binding.editText.addTextChangedListener(buttonState)
        setUpRecyclerView()

        //query backend
//        binding.button3.setOnClickListener {
//
//            if (query.isEmpty()){
//                Snackbar.make(
//                    binding.root, "input field cannot be empty", Snackbar.LENGTH_LONG
//                ).show()
//            }
//
//            if (query.isNotEmpty()){
//                hideEmptyStateRes()
//            }
//        }



        //observe state
        viewModel.state.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideEmptyStateRes()

                    response.data?.let { data ->
                        gitHubRepoAdapter.differ.submitList(data.items?.map {
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
                    }
                }
                is Resource.Error -> {
                    hideEmptyStateRes()
                    response.message?.let {
                        Snackbar.make(binding.root, "An error occurred: Caused by: $it", Snackbar.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showEmptyStateRes()
                }
            }
        })



    }

    private fun setUpRecyclerView(){
        gitHubRepoAdapter = GitHubRepoAdapter()
        binding.searchRepoRv.apply {
            adapter = gitHubRepoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun hideEmptyStateRes(){
        binding.emptyStateImage.visibility = View.GONE
        binding.emptyStateText.visibility = View.GONE
    }

    private fun showEmptyStateRes(){
        binding.emptyStateImage.visibility = View.VISIBLE
        binding.emptyStateText.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val buttonState : TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val query = binding.editText.text.toString().trim()
            binding.button3.isEnabled = query.isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {}

    }
}
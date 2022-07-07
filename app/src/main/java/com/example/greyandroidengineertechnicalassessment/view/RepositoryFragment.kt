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
                            hideInitialStateRes()
                            hideProgressBar()
                            hideErrorStateRes()
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
                            hideInitialStateRes()
                            hideProgressBar()
                            response.message?.let {
                                Snackbar.make(binding.root, "An error occurred: Caused by: $it", Snackbar.LENGTH_LONG).show()
                            }
                            if (response.message == "Repository not found"){
                                showErrorStateRes()
                            }
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
        }

    }

    fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    private val buttonState : TextWatcher = object : TextWatcher{
//        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            val query = binding.editText.text.toString().trim()
//            binding.button3.isEnabled = query.isNotEmpty()
//        }
//
//        override fun afterTextChanged(p0: Editable?) {}
//
//    }
}
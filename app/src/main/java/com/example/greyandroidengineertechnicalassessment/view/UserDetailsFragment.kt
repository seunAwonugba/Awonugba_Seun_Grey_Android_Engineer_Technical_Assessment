package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.adapter.DetailsRepoAdapter
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentUserDetailsBinding
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import com.example.greyandroidengineertechnicalassessment.view.data.DetailsRepoResponse
import com.example.greyandroidengineertechnicalassessment.viewmodel.UserRepositoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailsRepoAdapter: DetailsRepoAdapter

    private val args : UserDetailsFragmentArgs by navArgs()
    private val viewModel : UserRepositoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserDetailsBinding.bind(view)
        setUpRecyclerView()
        binding.imageView.setOnClickListener {
            findNavController().navigateUp()
        }
        val passedArgs = args.user
        //set up user details screen
        binding.detailsProfileImage.load(passedArgs.profilePicture)
        viewModel.getUserDetails(passedArgs.login)
        viewModel.getUsersRepo(passedArgs.login)

        //observe user details response
        viewModel.details.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideDetailsProgressBar()
                    binding.detailsName.text = response.data?.name
                    binding.detailsLogin.text = response.data?.login
                    binding.details.text = response.data?.bio
                    binding.location.text = response.data?.location
                    binding.url.text = response.data?.url
                    binding.followersValue.text = response.data?.followers.toString()
                    binding.followingValue.text = response.data?.following.toString()
                }

                is Resource.Error -> {
                    hideDetailsProgressBar()
                    Snackbar.make(
                        binding.root,
                        "An error occurred: Caused by ${response.message}",
                        Snackbar.LENGTH_LONG
                    ).show()

                }

                is Resource.Loading -> {
                    showDetailsProgressBar()
                }
                else -> {}
            }
        })

        //observe user repositories response
        viewModel.repo.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideRepoProgressBar()
                    response.data?.let { repoResponse ->
                        if (repoResponse.size == 0){
                            shoeEmptyState()
                        }
                        detailsRepoAdapter.differ.submitList(repoResponse.map {
                            DetailsRepoResponse(
                                id = it.id.toString(),
                                firstName = it.full_name?:"",
//                                firstName = it.full_name?:"",
                                secondName = it.name?:"",
                                description = it.description?:"",
                                forkedFrom = it.default_branch?:"",
                                update = it.updated_at?:"",
                                public = it.visibility?:"",
                                star = it.stargazers_count.toString(),
                                language = it.language?:"",
                                fullName = it.full_name?:""
                            )
                        })
                    }
                }

                is Resource.Error -> {
                    hideRepoProgressBar()
                    Snackbar.make(
                        binding.root,
                        "An error occurred: Caused by ${response.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is Resource.Loading -> {
                    showRepoProgressBar()
                }
        }

        })

    }


    private fun setUpRecyclerView(){
        detailsRepoAdapter = DetailsRepoAdapter()
        binding.repoRv.apply {
            adapter = detailsRepoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun showDetailsProgressBar(){
        binding.detailsProgressBar.visibility = View.VISIBLE

    }

    private fun hideDetailsProgressBar(){
        binding.detailsProgressBar.visibility = View.GONE
    }

    private fun showRepoProgressBar(){
        binding.repoProgressBar.visibility = View.VISIBLE

    }

    private fun hideRepoProgressBar(){
        binding.repoProgressBar.visibility = View.GONE
    }

    private fun shoeEmptyState(){
        binding.emptyImage.visibility = View.VISIBLE
        binding.emptyText.visibility = View.VISIBLE

    }

    private fun hideEmptyState(){
        binding.emptyImage.visibility = View.GONE
        binding.emptyText.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
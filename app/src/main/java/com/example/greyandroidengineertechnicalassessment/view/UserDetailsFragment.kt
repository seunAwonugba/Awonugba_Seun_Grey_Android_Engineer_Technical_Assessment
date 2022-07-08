package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
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
import com.example.greyandroidengineertechnicalassessment.viewmodel.UserRepositoryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var detailsRepoAdapter: DetailsRepoAdapter

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
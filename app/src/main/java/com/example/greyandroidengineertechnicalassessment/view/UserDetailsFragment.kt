package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentUserDetailsBinding
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentUsersBinding
import com.example.greyandroidengineertechnicalassessment.viewmodel.SearchUsersViewModel
import com.example.greyandroidengineertechnicalassessment.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : UserViewModel by viewModels()
    private val args : UserDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserDetailsBinding.bind(view)
        binding.imageView.setOnClickListener {
            findNavController().navigateUp()
        }

        val passedArgs = args.user

        //set up user details screen
        binding.detailsProfileImage.load(passedArgs.profilePicture)

        viewModel.getUserDetails(passedArgs.login)
        viewModel.state.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                binding.detailsLogin.text = response.body()?.login
                binding.followingValue.text = response.body()?.following.toString()
                binding.followersValue.text = response.body()?.followers.toString()
                binding.url.text = response.body()?.avatar_url
                binding.detailsName.text = response.body()?.name
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
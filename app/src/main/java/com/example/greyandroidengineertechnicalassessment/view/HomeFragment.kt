package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.cardUser.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_usersFragment)
        }

        binding.cardRepo.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_repositoryFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
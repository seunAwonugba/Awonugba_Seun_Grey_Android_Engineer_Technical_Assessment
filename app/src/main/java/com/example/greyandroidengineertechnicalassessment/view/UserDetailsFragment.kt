package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentUserDetailsBinding
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentUsersBinding


class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserDetailsBinding.bind(view)

        binding.imageView.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
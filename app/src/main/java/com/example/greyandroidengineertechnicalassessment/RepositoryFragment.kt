package com.example.greyandroidengineertechnicalassessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentRepositoryBinding
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentUsersBinding
import com.google.android.material.snackbar.Snackbar

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRepositoryBinding.bind(view)

        binding.button3.setOnClickListener {
            Snackbar.make(binding.root, "Btton clicked", Snackbar.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
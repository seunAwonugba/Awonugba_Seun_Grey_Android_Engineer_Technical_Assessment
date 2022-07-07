package com.example.greyandroidengineertechnicalassessment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greyandroidengineertechnicalassessment.R
import com.example.greyandroidengineertechnicalassessment.adapter.UsersAdapter
import com.example.greyandroidengineertechnicalassessment.databinding.FragmentUsersBinding
import com.example.greyandroidengineertechnicalassessment.utils.Resource
import com.example.greyandroidengineertechnicalassessment.view.data.UsersResponse
import com.example.greyandroidengineertechnicalassessment.viewmodel.SearchUsersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users), UsersAdapter.OnItemClickListener {

    private lateinit var usersAdapter: UsersAdapter
    private val viewModel : SearchUsersViewModel by viewModels()


    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUsersBinding.bind(view)
        setUpRecyclerView()


        //state when user clicks search button
        binding.button.setOnClickListener {
            if (binding.editText2.text.trim().isEmpty()){
                Snackbar.make(binding.root, "Input field cannot be empty", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                viewModel.searchUsers(binding.editText2.text.trim().toString())
                viewModel.state.observe(viewLifecycleOwner, Observer { response ->
                    when(response){
                        is Resource.Initial -> {
                            showInitialStateRes()
                            hideProgressBar()
                            hideErrorRes()
                        }

                        is Resource.Success -> {
                            hideInitialStateRes()
                            hideProgressBar()
                            hideErrorRes()
                            response.data?.let { dtoData ->
                                usersAdapter.usersResponse = dtoData.items.map {
                                    UsersResponse(
                                        id = it.id ?: 0,
                                        login = it.login?: "",
                                        profilePicture = it.avatar_url?:""
                                    )

                                }
                            }
                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            hideInitialStateRes()
                            response.message?.let {
                                Snackbar.make(binding.root, "An error occurred: Caused by: $it", Snackbar.LENGTH_LONG).show()
                            }
                            if (response.message == "User not found"){
                                showErrorRes()
                            }
                        }

                        is Resource.Loading -> {
                            showProgressBar()
                            hideInitialStateRes()
                            hideErrorRes()

                        }

                    }
                })
            }
        }

    }

    private fun setUpRecyclerView(){
        usersAdapter = UsersAdapter(this)
        binding.recyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showProgressBar(){
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar2.visibility = View.GONE
    }

    private fun hideInitialStateRes(){
        binding.initialStateImage.visibility = View.GONE
        binding.initialStateText.visibility = View.GONE
    }
    private fun showInitialStateRes(){
        binding.initialStateImage.visibility = View.VISIBLE
        binding.initialStateText.visibility = View.VISIBLE
    }


    private fun showErrorRes(){
        binding.initialStateImage.visibility = View.VISIBLE
        binding.errorText.visibility = View.VISIBLE
    }
    private fun hideErrorRes(){
        binding.initialStateImage.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

    override fun onNavToUserDetails(position: Int) {
        findNavController().navigate(R.id.action_usersFragment_to_userDetailsFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
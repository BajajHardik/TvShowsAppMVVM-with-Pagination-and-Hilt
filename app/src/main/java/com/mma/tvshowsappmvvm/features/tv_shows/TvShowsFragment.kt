package com.example.tvshowsappmvvm.features.tv_shows

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvshowsappmvvm.ConnectionLivedData
import com.example.tvshowsappmvvm.ConnectivityObserver
import com.mma.tvshowsappmvvm.R
import com.mma.tvshowsappmvvm.databinding.FragmentTvShowsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TvShowsFragment:Fragment(R.layout.fragment_tv_shows) {
    private val viewModel: TvShowViewModel by viewModels()
    private lateinit var tvShowsAdapter:TvShowsAdapter
    private lateinit var connectivityObserver: ConnectivityObserver
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val binding = FragmentTvShowsBinding.bind(view)
        tvShowsAdapter = TvShowsAdapter(
            onItemClick = {article->
                val action = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowDetails(article)
                findNavController().navigate(action)
            }
        )

        //doInit(binding)

        connectivityObserver = ConnectionLivedData(requireContext())

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            connectivityObserver.observer().collect{status->
                when(status){
                    ConnectivityObserver.Status.Available -> doInit(binding)
                    ConnectivityObserver.Status.UnAvailable -> {
                        Toast.makeText(requireContext(),"Internet Connection Lost",Toast.LENGTH_LONG).show()
                    }
                    ConnectivityObserver.Status.Losing -> TODO()
                    ConnectivityObserver.Status.Lost -> Toast.makeText(requireContext(),"Internet Connection Lost",Toast.LENGTH_LONG).show()
                }

            }
        }

        binding.imageSearch.setOnClickListener {
            val action = TvShowsFragmentDirections.actionTvShowsFragmentToSearchTvShowFragment()
            findNavController().navigate(action)
        }
    }

    private fun doInit(binding: FragmentTvShowsBinding){
        binding.apply {
            tvShowRecyclerView.apply {
                adapter = tvShowsAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderAdapter(),
                    footer = LoaderAdapter()
                )
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

//            imageSearch.setOnClickListener {
//                val action = TvShowsFragmentDirections.actionTvShowsFragmentToSearchTvShowFragment()
//                findNavController().navigate(action)
//            }

        }

//        viewModel.list.observe(viewLifecycleOwner, Observer {
//            tvShowsAdapter.submitData(lifecycle,it)
//        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tvShowResults.collectLatest {
                tvShowsAdapter.submitData(it)
            }
        }
    }

}
package com.example.tvshowsappmvvm.features.search_tv_shows

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvshowsappmvvm.ConnectionLivedData
import com.example.tvshowsappmvvm.ConnectivityObserver
import com.example.tvshowsappmvvm.features.tv_shows.LoaderAdapter
import com.example.tvshowsappmvvm.onQueryTextChanged
import com.mma.tvshowsappmvvm.R
import com.mma.tvshowsappmvvm.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchTvShowFragment:Fragment(R.layout.fragment_search) {
    private val viewModel:SearchTvShowViewModel by viewModels()
    private lateinit var searchTvShowsAdapter: SearchTvShowsAdapter
    private lateinit var connectivityObserver: ConnectivityObserver
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        val binding = FragmentSearchBinding.bind(view)

        searchTvShowsAdapter = SearchTvShowsAdapter(onItemClick = {article->
            val action = SearchTvShowFragmentDirections.actionSearchTvShowFragmentToTvShowDetails(article)
            findNavController().navigate(action)
        })

        val menuHost: MenuHost = requireActivity()

        connectivityObserver = ConnectionLivedData(requireContext())

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            connectivityObserver.observer().collectLatest{status->
                when(status){
                    ConnectivityObserver.Status.Available -> doInit(binding)
                    ConnectivityObserver.Status.UnAvailable -> {
                        Toast.makeText(context,"amgkw", Toast.LENGTH_LONG).show()
                    }
                    ConnectivityObserver.Status.Losing -> TODO()
                    ConnectivityObserver.Status.Lost -> Toast.makeText(context,"Internet Connection Lost",
                        Toast.LENGTH_LONG).show()
                }

            }
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search->{
                        val searchView = menuItem.actionView as SearchView
//                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//                            override fun onQueryTextSubmit(query: String?): Boolean {
//                                if(query!=null){
//                                    binding.searchTvShowRecyclerView.scrollToPosition(0)
//                                    viewModel.onSearchQuerySubmit(query)
//                                    searchView.clearFocus()
//                                }
//                                return true
//                            }
//
//                            override fun onQueryTextChange(newText: String?): Boolean {
//                                return true
//                            }
//
//                        })
                        searchView.onQueryTextChanged {
                            viewModel.onSearchQuerySubmit(it)
                        }
                        searchView.clearFocus()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun doInit(binding: FragmentSearchBinding){
        //val menuHost: MenuHost = requireActivity()

        binding.apply {
            searchTvShowRecyclerView.apply {
                adapter= searchTvShowsAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderAdapter(),
                    footer = LoaderAdapter()
                )
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchResults.collectLatest {
                searchTvShowsAdapter.submitData(it)
            }
        }

//        viewModel.searchResults.observe(viewLifecycleOwner){
//            adapter.submitData(viewLifecycleOwner.lifecycle,it)
//        }

    }

}
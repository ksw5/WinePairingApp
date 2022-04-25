package com.example.winepairing.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.winepairing.WineApplication
import com.example.winepairing.databinding.FragmentFavoritesBinding
import com.example.winepairing.view.adapters.WineAdapter
import com.example.winepairing.viewmodel.PairingsViewModel
import com.example.winepairing.viewmodel.PairingsViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    val wineAdapter = WineAdapter()

    val viewModel: PairingsViewModel by activityViewModels() {
        PairingsViewModelFactory((activity?.application as WineApplication).database.wineDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = wineAdapter

        showSavedWines()
    }

    private fun showSavedWines() {
        viewModel.showSavedWines().asLiveData().observe(viewLifecycleOwner) { eventList ->
            wineAdapter.submitList(eventList)
            if (wineAdapter.itemCount == 0) {
                recyclerView.visibility = View.GONE
                binding.empty.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                binding.empty.visibility = View.GONE
                lifecycle.coroutineScope.launch {
                    viewModel.showSavedWines().collect() {
                        wineAdapter.submitList(it)
                    }
                }
            }
        }

    }
}
package com.example.winepairing.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.winepairing.databinding.FragmentPairingBinding
import com.example.winepairing.utils.hideKeyboard
import com.example.winepairing.viewmodel.PairingsViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class PairingFragment : Fragment() {
    private var _binding: FragmentPairingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PairingsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPairingBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        lifecycleScope.launch {
            getResults()
        }



        binding.searchBtn.setOnClickListener {
            hideKeyboard()
            if (binding.userItem.text.isNullOrEmpty()) {
                toastEmpty()
            } else {
                val foodItem = binding.userItem.text.toString()
                // kick off the API request - we don't display anything here, the observing
                // function above handles that when we get the results back
                getWinePairing(foodItem)

            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getWinePairing(foodItem: String) {
        viewModel.getWinePairings(foodItem.lowercase())

    }

    private fun toastEmpty() {
        Toast.makeText(
            this@PairingFragment.requireActivity(),
            "Please enter a food, entree, or cuisine",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun toastError() {
        Toast.makeText(
            this@PairingFragment.requireActivity(),
            "Hmmm...that didn't return any results.  Try a different search term.",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getResults() {
        viewModel.apiResponse.observe(viewLifecycleOwner) { response ->
            val productMatchesList = response.productMatches?.toList()
            val imageUrl = productMatchesList?.get(0)?.imageUrl
            val rating = (productMatchesList?.get(0)?.averageRating)?.times(100)?.roundToInt().toString() + "%"
            val description = productMatchesList?.get(0)?.description
            val price = productMatchesList?.get(0)?.price.toString()
            val title = productMatchesList?.get(0)?.title
            val productString = "\n $description \n Price: $price \n Rating: $rating"
            val wines = response.pairedWines?.joinToString(separator = "\n")
            val emptyWines = "No pairing results."
            val emptyInfo = " "
            if (response.status == "failure") {
                binding.pairingWines.setText(response.message)
                binding.pairingInfo.setText(emptyInfo)
                binding.productTitle.setText(emptyInfo)
                binding.productInfo.setText(emptyInfo)
            } else if (response.pairedWines?.isEmpty() == true) {
                if (response.pairingText?.isNullOrEmpty() == true || productMatchesList?.isEmpty() == true) {
                    binding.pairingInfo.setText(emptyInfo)
                    binding.productInfo.setText(emptyInfo)
                } else {
                    binding.pairingInfo.setText(response.pairingText)
                    binding.pairingWines.setText(emptyWines)
                    binding.productInfo.setText(emptyInfo)
                }
            } else {
                binding.favBtn.visibility = View.VISIBLE
                binding.pairingWines.setText(wines)
                binding.pairingInfo.setText(response.pairingText)
                binding.productTitle.setText(title)
                binding.productInfo.setText(productString)
                Glide.with(this@PairingFragment)
                    .load(imageUrl)
                    .override(500, 600)
                    .centerCrop()
                    .into(binding.wineImage)
            }
        }
    }


}

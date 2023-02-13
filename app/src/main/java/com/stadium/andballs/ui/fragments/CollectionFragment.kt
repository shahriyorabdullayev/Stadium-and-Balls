package com.stadium.andballs.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stadium.andballs.R
import com.stadium.andballs.SharedPref
import com.stadium.andballs.databinding.FragmentCollectionBinding
import com.stadium.andballs.utils.*
import com.stadium.andballs.utils.Constants.KEY_BEST_SCORE


class CollectionFragment : Fragment(R.layout.fragment_collection) {

    private val binding by viewBinding { FragmentCollectionBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bestScore = SharedPref(requireContext()).getBestScore(KEY_BEST_SCORE).toInt()

        binding.apply {
            btnBackCollection.setOnClickListener {
                if (getBoolean(Constants.KEY_VIBR)) {
                    vibrator()
                    requireActivity().onBackPressed()
                } else {
                    requireActivity().onBackPressed()
                }
            }

            if (bestScore >= 50) {
                lock1.gone()
            }
            if (bestScore >= 120) {
                lock2.gone()
            }
            if (bestScore >= 170) {
                lock3.gone()
            }
            if (bestScore >= 200) {
                lock4.gone()
            }
            if (bestScore >= 250) {
                lock5.gone()
            }
            if(bestScore >= 300) {
                lock6.gone()
            }



        }

    }

}
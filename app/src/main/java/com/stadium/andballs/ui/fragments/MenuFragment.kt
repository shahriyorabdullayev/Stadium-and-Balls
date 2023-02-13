package com.stadium.andballs.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stadium.andballs.R
import com.stadium.andballs.SharedPref
import com.stadium.andballs.databinding.FragmentMenuBinding
import com.stadium.andballs.utils.*
import com.stadium.andballs.utils.Constants.EN
import com.stadium.andballs.utils.Constants.KEY_BEST_SCORE
import com.stadium.andballs.utils.Constants.KEY_LANGUAGE
import com.stadium.andballs.utils.Constants.KEY_VIBR


class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding { FragmentMenuBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (SharedPref(requireContext()).getBestScore(KEY_BEST_SCORE).isEmpty()) {
            SharedPref(requireContext()).saveString(KEY_BEST_SCORE, "0")
        }

        with(binding) {
            btnFacts.setOnClickListener {
                if (getBoolean(KEY_VIBR)) {
                    vibrator()
                    findNavController().navigate(R.id.action_menuFragment_to_factsFragment)
                } else {
                    findNavController().navigate(R.id.action_menuFragment_to_factsFragment)
                }

            }

            btnCollection.setOnClickListener {
                if (getBoolean(KEY_VIBR)) {
                    vibrator()
                    findNavController().navigate(R.id.action_menuFragment_to_collectionFragment)
                } else {
                    findNavController().navigate(R.id.action_menuFragment_to_collectionFragment)
                }
            }

            btnPlay.setOnClickListener {
                if (getBoolean(KEY_VIBR)) {
                    vibrator()
                    findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
                } else {
                    findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
                }
            }

            btnSettings.setOnClickListener {
                if (getBoolean(KEY_VIBR)) {
                    vibrator()
                    findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
                } else {
                    findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
                }
            }
        }

    }

}
package com.stadium.andballs.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentFactory
import com.stadium.andballs.R
import com.stadium.andballs.databinding.FragmentFactsBinding
import com.stadium.andballs.utils.*
import com.stadium.andballs.utils.Constants.EN
import com.stadium.andballs.utils.Constants.KEY_LANGUAGE


class FactsFragment : Fragment(R.layout.fragment_facts) {

    private val binding by viewBinding { FragmentFactsBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackFact.setOnClickListener {
            if (getBoolean(Constants.KEY_VIBR)) {
                vibrator()
                requireActivity().onBackPressed()
            } else {
                requireActivity().onBackPressed()
            }
        }
        if (getString(KEY_LANGUAGE) == EN) {
            binding.fact4.setImageResource(R.drawable.img_fact_en_new)
        } else {
            binding.fact4.setImageResource(R.drawable.img_fact_ru_new)
        }
    }

}
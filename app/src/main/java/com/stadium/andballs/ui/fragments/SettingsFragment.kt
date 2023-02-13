package com.stadium.andballs.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.stadium.andballs.R
import com.stadium.andballs.SharedPref
import com.stadium.andballs.databinding.FragmentSettingsBinding
import com.stadium.andballs.service.SoundService
import com.stadium.andballs.utils.Constants.EN
import com.stadium.andballs.utils.Constants.KEY_LANGUAGE
import com.stadium.andballs.utils.Constants.KEY_MUSIC
import com.stadium.andballs.utils.Constants.KEY_VIBR
import com.stadium.andballs.utils.Constants.RU
import com.stadium.andballs.utils.getBoolean
import com.stadium.andballs.utils.vibrator
import com.stadium.andballs.utils.viewBinding
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.reword.Reword
import java.util.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding { FragmentSettingsBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageSoundIcon(SharedPref(requireContext()).getBoolean(KEY_MUSIC))
        manageVibrIcon(SharedPref(requireContext()).getBoolean(KEY_VIBR))
        manageLanIcon(SharedPref(requireContext()).getString(KEY_LANGUAGE)!!)


        with(binding) {
            btnBack.setOnClickListener {
                if (getBoolean(KEY_VIBR)) {
                    vibrator()
                    requireActivity().onBackPressed()
                } else {
                    requireActivity().onBackPressed()
                }
            }

            btnSoundOn.setOnClickListener {
                if (SharedPref(requireContext()).getBoolean(KEY_VIBR)) {
                    if (!SharedPref(requireContext()).getBoolean(KEY_MUSIC)) {
                        vibrator()
                        SharedPref(requireContext()).saveBoolean(KEY_MUSIC, true)
                        manageSoundIcon(true)
                        requireActivity().startService(Intent(requireContext(),
                            SoundService::class.java))
                    }
                } else {
                    if (!SharedPref(requireContext()).getBoolean(KEY_MUSIC)) {
                        SharedPref(requireContext()).saveBoolean(KEY_MUSIC, true)
                        manageSoundIcon(true)
                        requireActivity().startService(Intent(requireContext(),
                            SoundService::class.java))
                    }
                }

            }

            btnSoundOff.setOnClickListener {
                if (SharedPref(requireContext()).getBoolean(KEY_VIBR)) {
                    vibrator()
                    SharedPref(requireContext()).saveBoolean(KEY_MUSIC, false)
                    manageSoundIcon(false)
                    requireActivity().stopService(Intent(requireContext(),
                        SoundService::class.java))
                } else {
                    SharedPref(requireContext()).saveBoolean(KEY_MUSIC, false)
                    manageSoundIcon(false)
                    requireActivity().stopService(Intent(requireContext(),
                        SoundService::class.java))
                }
            }

            btnVibrOn.setOnClickListener {
                SharedPref(requireContext()).saveBoolean(KEY_VIBR, true)
                manageVibrIcon(true)
            }

            btnVibrOff.setOnClickListener {
                SharedPref(requireContext()).saveBoolean(KEY_VIBR, false)
                manageVibrIcon(false)
            }

            btnEn.setOnClickListener {
                if (SharedPref(requireContext()).getBoolean(KEY_VIBR)) {
                    vibrator()
                    SharedPref(requireContext()).saveString(KEY_LANGUAGE, EN)
                    manageLanIcon(EN)
                    AppLocale.desiredLocale = Locale.ENGLISH
                    Reword.reword(binding.root)
                } else {
                    SharedPref(requireContext()).saveString(KEY_LANGUAGE, EN)
                    manageLanIcon(EN)
                    AppLocale.desiredLocale = Locale.ENGLISH
                    Reword.reword(binding.root)

                }

            }

            btnRu.setOnClickListener {
                if (SharedPref(requireContext()).getBoolean(KEY_VIBR)) {
                    vibrator()
                    SharedPref(requireContext()).saveString(KEY_LANGUAGE, RU)
                    manageLanIcon(RU)
                    AppLocale.desiredLocale = Locale(RU)

                } else {
                    SharedPref(requireContext()).saveString(KEY_LANGUAGE, RU)
                    manageLanIcon(RU)
                    AppLocale.desiredLocale = Locale(RU)

                }
            }
        }
    }

    private fun manageSoundIcon(boolean: Boolean) {
        with(binding) {
            if (boolean) {
                btnSoundOn.setBackgroundResource(R.drawable.bg_settings_selected)
                btnSoundOff.setBackgroundResource(R.drawable.bg_settings_non_selected)
            } else {
                btnSoundOff.setBackgroundResource(R.drawable.bg_settings_selected)
                btnSoundOn.setBackgroundResource(R.drawable.bg_settings_non_selected)
            }
        }
    }

    private fun manageVibrIcon(boolean: Boolean) {
        with(binding) {
            if (boolean) {
                btnVibrOn.setBackgroundResource(R.drawable.bg_settings_selected)
                btnVibrOff.setBackgroundResource(R.drawable.bg_settings_non_selected)
            } else {
                btnVibrOff.setBackgroundResource(R.drawable.bg_settings_selected)
                btnVibrOn.setBackgroundResource(R.drawable.bg_settings_non_selected)
            }
        }
    }

    private fun manageLanIcon(str: String) {
        with(binding) {
            if (str == EN) {
                btnEn.setBackgroundResource(R.drawable.bg_settings_selected)
                btnRu.setBackgroundResource(R.drawable.bg_settings_non_selected)
            } else {
                btnRu.setBackgroundResource(R.drawable.bg_settings_selected)
                btnEn.setBackgroundResource(R.drawable.bg_settings_non_selected)
            }
        }

    }

}
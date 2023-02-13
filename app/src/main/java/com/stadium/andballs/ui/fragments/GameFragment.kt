package com.stadium.andballs.ui.fragments

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.stadium.andballs.R
import com.stadium.andballs.SharedPref
import com.stadium.andballs.databinding.FragmentGameBinding
import com.stadium.andballs.utils.*
import com.stadium.andballs.utils.Constants.KEY_BEST_SCORE
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random.Default.nextInt


class GameFragment : Fragment(R.layout.fragment_game) {

    private var width: Int = 0
    private var height: Int = 0
    private val binding by viewBinding { FragmentGameBinding.bind(it) }

    private var score = 0
    private var attempt = 3

    private var jobBalls: Job? = null
    private var jobClickListener: Job? = null

    private lateinit var ballImageViews: ArrayList<ImageView>
    private lateinit var balls: ArrayList<Int>
    private lateinit var ballImageView: ImageView

    private lateinit var ballValueAnimator: ValueAnimator

    private var duration = 6000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calculateDisplayMetrics()
        ballImageViews = ArrayList()
        balls = ArrayList<Int>().apply {
            add(R.drawable.ball_football)
            add(R.drawable.ic_spagetti_1)
            add(R.drawable.ball_basketball)
            add(R.drawable.ball_valeyball)
            add(R.drawable.ic_spagetti_2)
            add(R.drawable.ball_basketball_2)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            Log.d("@@@", "handleOnBackPressed: daa")
            if (score > SharedPref(requireContext()).getBestScore(KEY_BEST_SCORE).toInt()) {
                saveString(KEY_BEST_SCORE, score.toString())
            }
            if (isEnabled) {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }

        jobBalls = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(500)
                if (duration > 3000) {
                    duration-=10
                }
                balls(duration)
            }
        }

        jobClickListener = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (isActive) {
                delay(1)
                for (i in ballImageViews) {
                    i.setOnClickListener {
                        if (i.tag.toString() == "1" || i.tag.toString() == "4") {
                            attempt--
                            vibrator()
                            if (attempt == 2) {
                                binding.tvAttempt.text = "XX"
                            } else if (attempt == 1) {
                                binding.tvAttempt.text = "X"
                            } else if (attempt == 0) {
                                showResultDialog()
                            }
                        } else {
                            score++
                            binding.tvScore.text = score.toString()
                            i.inVisible()
                            ballImageViews.remove(i)
                        }
                    }
                }
            }
        }
    }

    private fun showResultDialog() {
        val dialog = Dialog(requireContext(),
            android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
        dialog.apply {
            dialog.setContentView(R.layout.result_dialog)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            if (score >= SharedPref(requireContext()).getBestScore(KEY_BEST_SCORE).toInt()) saveString(
                KEY_BEST_SCORE, score.toString())
            findViewById<TextView>(R.id.tv_dialog_best_result).text =
                SharedPref(requireContext()).getBestScore(
                    KEY_BEST_SCORE)
            findViewById<TextView>(R.id.tv_dialog_current_result).text = score.toString()
            findViewById<CardView>(R.id.btn_back_dialog).setOnClickListener {
                if (getBoolean(Constants.KEY_VIBR)) {
                    vibrator()
                    jobBalls?.cancel()
                    jobClickListener?.cancel()
                    requireActivity().onBackPressed()
                    dismiss()
                } else {
                    jobBalls?.cancel()
                    jobClickListener?.cancel()
                    requireActivity().onBackPressed()
                    dismiss()
                }

            }
            dialog.show()
        }
    }

    private fun balls(duration:Int) {
        val pos = nextInt(120, width - 150).toFloat()
        val randomBall = nextInt(0, 6)
        ballImageView = ImageView(requireContext())
        binding.parentConstraint.addView(ballImageView)
        ballImageView.apply {
            layoutParams.width = 120
            layoutParams.height = 120
            x = pos
            y = height.toFloat()
            setImageResource(balls[randomBall])
            tag = randomBall
        }
        ballImageViews.add(ballImageView)
        startBallAnimation(ballImageView, duration)
    }

    private fun startBallAnimation(ballImageView: ImageView, ballDuration: Int) {
        ballValueAnimator = ValueAnimator.ofFloat(height.toFloat() - 500f, -200f)
        ballValueAnimator.addUpdateListener {
            val va = it.animatedValue as Float
            ballImageView.translationY = va
        }
        ballValueAnimator.apply {
            duration = ballDuration.toLong()
            interpolator = LinearInterpolator()
            start()
        }

        ballValueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                ballImageViews.remove(ballImageView)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
    }

    private fun calculateDisplayMetrics() {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
    }

    override fun onDestroyView() {
        super.onDestroyView()
        jobBalls?.cancel()
        jobClickListener?.cancel()
    }
}
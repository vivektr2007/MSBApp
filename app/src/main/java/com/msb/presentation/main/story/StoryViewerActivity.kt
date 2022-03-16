package com.msb.presentation.main.story

import android.os.Bundle
import android.os.CountDownTimer
import com.msb.R
import com.msb.databinding.ActivityStoryViewerBinding
import xyz.teknol.utils.base.BaseActivity
import xyz.teknol.utils.extensions.doVisible

class StoryViewerActivity : BaseActivity<ActivityStoryViewerBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_story_viewer

    override fun onViewReady(savedInstanceState: Bundle?) {
        object : CountDownTimer(30000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBar.progress = ((30000 - millisUntilFinished) / 300).toInt()
            }

            override fun onFinish() {
                finish()
            }
        }.start()
        binding.imageView24.setOnClickListener {
            binding.cardView.doVisible()
        }
    }
}
package com.msb.presentation.main.dating.dating_list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.msb.R
import com.msb.databinding.DatingListFragmentBinding
import com.msb.presentation.main.dating.dating_list.adapter.DatingListAdapter
import com.yuyakaido.android.cardstackview.*
import xyz.teknol.utils.base.BaseFragment

class DatingListFragment : BaseFragment<DatingListFragmentBinding>(), CardStackListener {

    private lateinit var viewModel: DatingListViewModel
    private val manager by lazy { CardStackLayoutManager(requireContext(), this) }
    private val adapter by lazy { DatingListAdapter() }
    override fun getLayoutRes(): Int = R.layout.dating_list_fragment

    override fun activityCreated() {

    }

    override fun onFragmentAttach(context: Context) {

    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        initialize()
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.Bottom)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(10.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        binding.cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        if (direction == Direction.Right) {
            findNavController().navigate(R.id.action_datingListFragment_to_matchActivity)
        }
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }
}
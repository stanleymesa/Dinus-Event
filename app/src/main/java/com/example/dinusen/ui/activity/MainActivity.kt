package com.example.dinusen.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.dinusen.R
import com.example.dinusen.databinding.ActivityMainBinding
import com.example.dinusen.helper.*
import com.example.dinusen.repository.RetrofitRepository
import com.example.dinusen.ui.adapter.MainAdapter
import com.example.dinusen.ui.viewmodel.MainViewModel
import com.example.dinusen.ui.viewmodel.RetrofitViewModelFactory
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickCallback, View.OnClickListener {

    private var ratio = 0F
    private val onGoingAdapter = MainAdapter(this)
    private val upcomingOnlineAdapter = MainAdapter(this)
    private val upcomingOfflineAdapter = MainAdapter(this)
    private lateinit var skeletonRvOngoing: Skeleton
    private lateinit var skeletonRvUpcomingOnline: Skeleton
    private lateinit var skeletonRvUpcomingOffline: Skeleton
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel> {
        RetrofitViewModelFactory.getInstance(RetrofitRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setToolbar()
        setAdapter()
        setSkeleton()
        observeData()
        binding.toolbar.etSearch.setOnClickListener(this)
    }

    private fun setToolbar() {
        // Initial Set Toolbar
        binding.toolbar.root.background.alpha = 0
        binding.toolbar.etSearch.isFocusable = false
        setSearchBar(this.getColorStateListSecondaryVariant(),
            this.getColorFromAttr(com.google.android.material.R.attr.colorSecondaryVariant))


        // On Scrolled
        binding.content.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val headerHeight: Int = binding.content.header.height - binding.toolbar.root.height
            ratio = min(max(scrollY, 0), headerHeight).toFloat() / headerHeight
            val newAlpha = (ratio * 255).toInt()

            // Set Toolbar
            binding.toolbar.root.background.alpha = newAlpha

            // Set Toolbar Items
            if (ratio >= 0.65F) {
                setSearchBar(this.getColorStateListPrimary(),
                    this.getColorFromAttr(com.google.android.material.R.attr.colorPrimary))
            } else {
                setSearchBar(this.getColorStateListSecondaryVariant(),
                    this.getColorFromAttr(com.google.android.material.R.attr.colorSecondaryVariant))
            }

        })
    }

    private fun setSearchBar(colorStroke: ColorStateList, colorIcon: Int) {
        binding.toolbar.tiSearch.apply {
            setBoxStrokeColorStateList(colorStroke)
            defaultHintTextColor = colorStroke
            hintTextColor = colorStroke
            editText!!.setTextColor(colorIcon)

            var searchIcon = this@MainActivity.getHelperDrawable(R.drawable.ic_baseline_search_24)
            searchIcon = DrawableCompat.wrap(searchIcon)
            DrawableCompat.setTint(searchIcon, colorIcon)
            DrawableCompat.setTintMode(searchIcon, PorterDuff.Mode.SRC_IN)
            editText!!.setCompoundDrawablesWithIntrinsicBounds(searchIcon, null, null, null)
        }
    }

    private fun setAdapter() {
        binding.content.rvOngoing.apply {
            val widthInDp = 16
            addItemDecoration(MarginItemDecoration(widthInDp.toPixel(this@MainActivity)))
            adapter = onGoingAdapter
        }

        binding.content.rvUpcomingOnline.apply {
            val widthInDp = 16
            addItemDecoration(MarginItemDecoration(widthInDp.toPixel(this@MainActivity)))
            adapter = upcomingOnlineAdapter
        }

        binding.content.rvUpcomingOffline.apply {
            val widthInDp = 16
            addItemDecoration(MarginItemDecoration(widthInDp.toPixel(this@MainActivity)))
            adapter = upcomingOfflineAdapter
        }
    }

    private fun setSkeleton() {
        val radius = 16
        skeletonRvOngoing = binding.content.rvOngoing.applySkeleton(R.layout.item_row_event)
        skeletonRvOngoing.maskCornerRadius = radius.toPixel(this@MainActivity).toFloat()

        skeletonRvUpcomingOnline = binding.content.rvUpcomingOnline.applySkeleton(R.layout.item_row_event)
        skeletonRvUpcomingOnline.maskCornerRadius = radius.toPixel(this@MainActivity).toFloat()

        skeletonRvUpcomingOffline = binding.content.rvUpcomingOffline.applySkeleton(R.layout.item_row_event)
        skeletonRvUpcomingOffline.maskCornerRadius = radius.toPixel(this@MainActivity).toFloat()
    }

    private fun showSkeleton() {
        skeletonRvOngoing.showSkeleton()
        skeletonRvUpcomingOnline.showSkeleton()
        skeletonRvUpcomingOffline.showSkeleton()
    }

    private fun cancelSkeleton() {
        skeletonRvOngoing.showOriginal()
        skeletonRvUpcomingOnline.showOriginal()
        skeletonRvUpcomingOffline.showOriginal()
    }

    private fun observeData() {
        mainViewModel.getAllEvent()

        mainViewModel.events.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                response.data?.let { data ->

                    data.onGoing.apply {
                        if (isNotEmpty()) {
                            binding.content.rvOngoing.apply {
                                scrollToPosition(Integer.MAX_VALUE / 2)
                                LinearSnapHelper().attachToRecyclerView(this)
                            }

                            binding.content.tvOngoingEmpty.visibility = View.INVISIBLE
                            onGoingAdapter.submitList(this)
                        } else {
                            binding.content.tvOngoingEmpty.visibility = View.VISIBLE
                        }
                    }

                    data.onlineUpcoming.apply {
                        if (isNotEmpty()) {
                            binding.content.rvUpcomingOnline.apply {
                                scrollToPosition(Integer.MAX_VALUE / 2)
                                LinearSnapHelper().attachToRecyclerView(this)
                            }
                            binding.content.tvUpcomingOnlineEmpty.visibility = View.INVISIBLE
                            upcomingOnlineAdapter.submitList(this)
                        } else {
                            binding.content.tvUpcomingOnlineEmpty.visibility = View.VISIBLE
                        }
                    }

                    data.offlineUpcoming.apply {
                        if (isNotEmpty()) {
                            binding.content.rvUpcomingOffline.apply {
                                scrollToPosition(Integer.MAX_VALUE / 2)
                                LinearSnapHelper().attachToRecyclerView(this)
                            }
                            binding.content.tvUpcomingOfflineEmpty.visibility = View.INVISIBLE
                            upcomingOfflineAdapter.submitList(this)
                        } else {
                            binding.content.tvUpcomingOfflineEmpty.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        mainViewModel.isLoading.observe(this) { event ->
            event.getContentIfNotHandled()?.let { isLoading ->
                if (isLoading) {
                    showSkeleton()
                } else {
                    cancelSkeleton()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val newAlpha = (ratio * 255).toInt()
        binding.toolbar.root.background.alpha = newAlpha
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(eventId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EVENT_ID, eventId)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_search -> startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}
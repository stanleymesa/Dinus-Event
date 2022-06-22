package com.example.dinusen.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.dinusen.BuildConfig
import com.example.dinusen.R
import com.example.dinusen.data.remote.response.EventItem
import com.example.dinusen.databinding.ActivityDetailBinding
import com.example.dinusen.helper.*
import com.example.dinusen.repository.RetrofitRepository
import com.example.dinusen.ui.viewmodel.DetailViewModel
import com.example.dinusen.ui.viewmodel.RetrofitViewModelFactory
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.google.android.material.appbar.AppBarLayout


class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentSkeleton: Skeleton
    private var linkRegister = ""
    private val detailViewModel by viewModels<DetailViewModel> {
        RetrofitViewModelFactory.getInstance(RetrofitRepository())
    }

    private val getEventId get(): Int {
        return intent.getIntExtra(EVENT_ID, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setCollapsingToolbar()
        setFab()
        observeData()
        binding.content.btnRegisterEvent.setOnClickListener(this)
    }

    private fun setCollapsingToolbar() {
        setSupportActionBar(binding.toolbar)

        var scrollY = -1
        binding.appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            if (scrollY == -1) {
                scrollY = appBarLayout.totalScrollRange
            }

            if (scrollY + verticalOffset == 0) {
                binding.collapseToolbar.title = "Event"
            } else {
                binding.collapseToolbar.title = " "
            }
        })
    }

    private fun observeData() {
        if (getEventId != 0) {
            detailViewModel.getEventById(getEventId)
        }

        detailViewModel.event.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                response.data?.let { data ->
                    setDetail(data.events)
                    linkRegister = data.events.link
                }
            }
        }

        detailViewModel.isLoading.observe(this) { event ->
            event.getContentIfNotHandled()?.let { isLoading ->
                if (isLoading) {
                    showSkeleton()
                } else {
                    cancelSkeleton()
                }
            }
        }
    }

    private fun setDetail(event: EventItem) {
        Glide.with(this)
            .load("${BuildConfig.BASE_URL_IMAGE}${event.image}")
            .into(binding.ivEvent)

        binding.content.apply {
            btnCategory.text = event.category
            tvTitle.text = event.name
            tvDesc.text = event.description
            tvTimeStart.text = event.fromDate
            tvTimeEnd.text = event.untilDate
            tvRoom.text = event.room
            tvContact.text = event.contact
        }
    }

    private fun setFab() {
        var isTimerEnable = false

        binding.fab.setOnClickListener {
            isTimerEnable = !isTimerEnable
            if (!isTimerEnable) {
                binding.fab.setImageDrawable(this.getHelperDrawable(R.drawable.ic_baseline_alarm_add_24))
                binding.fab.imageTintList = this.getColorStateListSecondaryVariant()
            } else {
                binding.fab.setImageDrawable(this.getHelperDrawable(R.drawable.ic_baseline_alarm_off_24))
                binding.fab.imageTintList = this.getColorStateListHelper(R.color.orange)
            }
        }
    }

    private fun showSkeleton() {
        val radius = 16

        contentSkeleton = binding.content.skeleton.createSkeleton()
        contentSkeleton.maskCornerRadius = radius.toPixel(this).toFloat()

        contentSkeleton.showSkeleton()
    }

    private fun cancelSkeleton() {
        contentSkeleton.showOriginal()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register_event -> {
                if (linkRegister.isNotEmpty()) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(linkRegister)
                    startActivity(i)
                }
            }
        }
    }
}
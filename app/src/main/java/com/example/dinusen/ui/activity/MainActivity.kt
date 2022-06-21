package com.example.dinusen.ui.activity

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.NestedScrollView
import com.example.dinusen.R
import com.example.dinusen.databinding.ActivityMainBinding
import com.example.dinusen.helper.getColorFromAttr
import com.example.dinusen.helper.getColorStateListPrimary
import com.example.dinusen.helper.getColorStateListSecondaryVariant
import com.example.dinusen.helper.getHelperDrawable
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private var ratio = 0F
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setToolbar()
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

    override fun onResume() {
        super.onResume()

        val newAlpha = (ratio * 255).toInt()
        binding.toolbar.root.background.alpha = newAlpha
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
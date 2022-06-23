package com.example.dinusen.ui.activity

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import com.example.dinusen.R
import com.example.dinusen.databinding.ActivitySearchBinding
import com.example.dinusen.helper.getColorFromAttr
import com.example.dinusen.helper.getColorStateListPrimary
import com.example.dinusen.helper.getHelperDrawable
import com.google.android.material.R.attr.colorPrimary
import com.google.android.material.chip.Chip

class SearchActivity : AppCompatActivity() {

    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setToolbar()
        addChip()
    }

    private fun setToolbar() {
        setSearchBar(this.getColorStateListPrimary(), this.getColorFromAttr(colorPrimary))
        binding.toolbar.ivBack.isVisible = true
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setSearchBar(colorStroke: ColorStateList, colorIcon: Int) {
        binding.toolbar.tiSearch.apply {
            setBoxStrokeColorStateList(colorStroke)
            defaultHintTextColor = colorStroke
            hintTextColor = colorStroke
            editText!!.setTextColor(colorIcon)

            var searchIcon =
                this@SearchActivity.getHelperDrawable(R.drawable.ic_baseline_search_24)
            searchIcon = DrawableCompat.wrap(searchIcon)
            DrawableCompat.setTint(searchIcon, colorIcon)
            DrawableCompat.setTintMode(searchIcon, PorterDuff.Mode.SRC_IN)
            editText!!.setCompoundDrawablesWithIntrinsicBounds(searchIcon, null, null, null)
        }

        binding.toolbar.root.setOnClickListener {
            binding.toolbar.etSearch.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.toolbar.etSearch.windowToken, 0)
        }

        binding.root.setOnClickListener {
            binding.toolbar.etSearch.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.toolbar.etSearch.windowToken, 0)
        }
    }

    private fun addChip() {
        chipValue.forEach {
            val chip = Chip(this)
            chip.chipBackgroundColor = this.getColorStateListPrimary()
            chip.setTextAppearanceResource(R.style.chipText)
            chip.isFocusable = true
            chip.isClickable = true
            chip.text = it
            chip.isCloseIconVisible = false
            binding.content.chipGroup.addView(chip)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val chipValue = arrayListOf(
            "Seminar",
            "Bootcamp",
            "Web Programming",
            "Mobile Programming",
            "HMTI",
            "DPM",
            "Entrepreneurship",
            "Business",
            "Startup"
        )
    }
}
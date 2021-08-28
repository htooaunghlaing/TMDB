package com.app.tmdb.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.viewpager.widget.PagerAdapter
import android.content.Context
import com.app.tmdb.main.MainActivity
import android.content.Intent
import android.graphics.Color
import android.view.*
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.app.tmdb.R
import com.app.tmdb.databinding.ActivityOnboardingBinding
import com.app.tmdb.util.PrefManager


class OnboardingActivity : AppCompatActivity() {

    private lateinit var layouts: IntArray
    private var prefManager: PrefManager? = null
    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Checking for first time launch - before calling setContentView()
        prefManager = PrefManager(this)
        if (!prefManager!!.getFirstTimeLaunch) {
            launchHomeScreen()
            finish()
        }

        // Making notification bar transparent
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(
            R.layout.welcome_slide_one,
            R.layout.welcome_slide_two,
            R.layout.welcome_slide_three,

        )

        // adding bottom dots
        addBottomDots(0)

        // making notification bar transparent
        changeStatusBarColor()
        val myViewPagerAdapter = MyViewPagerAdapter()
        binding.viewPager.adapter = myViewPagerAdapter
        binding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener)

        binding.btnNext.setOnClickListener{
            val current = (+1).getItem()
            if (current < layouts.size) {
                // move to next screen
                binding.viewPager.currentItem = current
            } else {
                launchHomeScreen()
            }
        }

        binding.btnSkip.setOnClickListener{
            launchHomeScreen()
        }
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(layouts.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        binding.layoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            binding.layoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun Int.getItem(): Int {
        return binding.viewPager.currentItem + this
    }

    private fun launchHomeScreen() {
        prefManager?.getFirstTimeLaunch = false
        startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
        finish()
    }

    //	viewpager change listener
    private var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size - 1) {
                // last page. make button text to GOT IT
                binding.btnNext.text = getString(R.string.start)
                binding.btnSkip.visibility = View.GONE
            } else {
                // still pages are left
                binding.btnNext.text = getString(R.string.next)
                binding.btnSkip.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }



    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layoutInflater =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = layoutInflater.inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view: View = `object` as View
            container.removeView(view)
        }
    }
}
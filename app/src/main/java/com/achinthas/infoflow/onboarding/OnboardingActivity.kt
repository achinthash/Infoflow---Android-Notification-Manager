package com.achinthas.infoflow.onboarding

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.achinthas.infoflow.R

class OnboardingActivity : AppCompatActivity(){

    private lateinit var viewPager: ViewPager2
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        btnPrev = findViewById<Button>(R.id.btn_prev)
        btnNext = findViewById<Button>(R.id.btn_next)

        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position) {
                     0 -> {
                         btnNext.text = "Next"
                         btnPrev.visibility = Button.GONE
                         btnNext.visibility = Button.VISIBLE
                     }
                    1 -> {
                        btnNext.text = "Next"
                        btnPrev.text = "Prev"
                        btnPrev.visibility = Button.VISIBLE
                        btnNext.visibility = Button.VISIBLE
                    }
                    2 -> {

                        btnNext.text = "Next"
                        btnPrev.text = "Prev"
                        btnPrev.visibility = Button.VISIBLE
                        btnNext.visibility = Button.VISIBLE

                    }
                    3 -> {

                        btnNext.text = "Next"
                        btnPrev.text = "Prev"
                        btnPrev.visibility = Button.VISIBLE
                        btnNext.visibility = Button.VISIBLE

                    }
                    4 -> {

                        btnNext.text = "Next"
                        btnPrev.text = "Prev"
                        btnPrev.visibility = Button.VISIBLE
                        btnNext.visibility = Button.VISIBLE

                    }
                    5 -> {
                        btnNext.text = "Finish"
                        btnPrev.text = "Prev"
                        btnPrev.visibility = Button.VISIBLE
                        btnNext.visibility = Button.VISIBLE

                    }
                }
            }
        })

        // next button
        btnNext.setOnClickListener {

            if(viewPager.currentItem +1 < adapter.itemCount) {
                viewPager.currentItem += 1
            } else {
                completeOnboarding()
            }
        }

        // previous button
        btnPrev.setOnClickListener {

            if(viewPager.currentItem > 0){
                viewPager.currentItem -= 1
            }
        }

    }

    // complete the onboarding setup
    private fun completeOnboarding(){
        Toast.makeText(this,"setup is completed", Toast.LENGTH_SHORT).show()
    }


}






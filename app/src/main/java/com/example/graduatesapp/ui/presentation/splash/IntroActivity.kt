package com.example.graduatesapp.ui.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.graduatesapp.R
import com.example.graduatesapp.ui.presentation.auth.AuthActivity
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.model.SliderPagerBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppIntro2() {
    private val introViewModel:IntroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWizardMode = true
        setIndicatorColor(

            selectedIndicatorColor = ContextCompat.getColor(this,R.color.blueColor),
            unselectedIndicatorColor = ContextCompat.getColor(this,android.R.color.darker_gray),
        )
        showIntroSlides()
        introViewModel.skipIntro.observe(this, Observer {
            val intent = Intent(this,AuthActivity::class.java)
            startActivity(intent)
            finish()
        })
    }


    private fun showIntroSlides()
    {

//        setImmersiveMode()
        val pageOne= SliderPagerBuilder()
            .title("Graduates!!")
            .description("Decouvrez les potentiels talents du marché actuel")
            .imageDrawable(R.drawable.ic_intro1)
            .titleColorRes(R.color.blueColor)
            .descriptionColorRes(android.R.color.darker_gray)
            .backgroundColorRes(R.color.white)

            .build()

        val pageTwo=SliderPagerBuilder()
            .title("Tout à porté de main!")
            .titleColorRes(R.color.blueColor)
            .descriptionColorRes(android.R.color.darker_gray)
            .description("Graduates intègre une vue globale des diplômés via les secteurs courants")
            .imageDrawable(R.drawable.ic_intro2)
            .titleColorRes(R.color.blueColor)
            .backgroundColorRes(R.color.white)
            .build()

        val pageThree=SliderPagerBuilder()
            .title("Tout en simplicité !")
            .titleColorRes(R.color.blueColor)
            .descriptionColorRes(android.R.color.darker_gray)
            .description("Son design,  sa prise en main et son mode d'utilisation ont été minutieusement réalisés pour vous permettre une meilleur expérience d'utilisation et une efficacité considérable")
            .imageDrawable(R.drawable.ic_intro3)
            .backgroundColorRes(R.color.white)
            .build()



        addSlide(AppIntroFragment.createInstance(pageOne))
        addSlide(AppIntroFragment.createInstance(pageTwo))
        addSlide(AppIntroFragment.createInstance(pageThree))

    }

    private fun goToHome() {
        introViewModel.skipIntro()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        goToHome()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        goToHome()
    }
}
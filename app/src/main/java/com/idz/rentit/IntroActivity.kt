package com.idz.rentit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.idz.rentIt.R
import com.idz.rentit.repository.Repository

class IntroActivity : AppCompatActivity() {

    companion object {
        private const val TIME_OUT = 3000L
    }

    private lateinit var first: View
    private lateinit var second: View
    private lateinit var third: View
    private lateinit var fourth: View
    private lateinit var fifth: View
    private lateinit var sixth: View
    private lateinit var a: TextView
    private lateinit var slogan: TextView

    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation
    private lateinit var middleAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_intro)

        setDataMembers()
        createAnimation()
        bindAnimation()

        Handler().postDelayed({ isLogin() }, TIME_OUT)
    }

    private fun startGuestsActivity() {
        startActivityFromIntent(GuestsActivity::class.java)
    }

    private fun startUsersActivity() {
        startActivityFromIntent(MainActivity::class.java)
    }

    private fun startActivityFromIntent(activityClass: Class<out AppCompatActivity>) {
        val intent = Intent(this, activityClass).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.removeItem(R.id.userCommentAdditionFragment)
        menu.removeItem(R.id.userProfileFragment)
        menu.removeItem(R.id.logoutMenuItem)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun setDataMembers() {
        first = findViewById(R.id.first_line)
        second = findViewById(R.id.second_line)
        third = findViewById(R.id.third_line)
        fourth = findViewById(R.id.fourth_line)
        fifth = findViewById(R.id.fifth_line)
        sixth = findViewById(R.id.sixth_line)
        a = findViewById(R.id.a)
        slogan = findViewById(R.id.tagLine)
    }

    private fun createAnimation() {
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)
    }

    private fun bindAnimation() {
        first.startAnimation(topAnimation)
        second.startAnimation(topAnimation)
        third.startAnimation(topAnimation)
        fourth.startAnimation(topAnimation)
        fifth.startAnimation(topAnimation)
        sixth.startAnimation(topAnimation)
        a.startAnimation(middleAnimation)
        slogan.startAnimation(bottomAnimation)
    }

    private fun isLogin() {
        Repository.repositoryInstance.executor.execute {
            if (isFinishing || isDestroyed) {
                return@execute
            }
            if (Repository.repositoryInstance.getAuthModel().isSignedIn) {
                Repository.repositoryInstance.mainThreadHandler.post { startUsersActivity() }
            } else {
                Repository.repositoryInstance.mainThreadHandler.post { startGuestsActivity() }
            }
        }
    }
}

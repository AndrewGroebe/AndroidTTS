package com.example.groebe.myapplication

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import android.widget.EditText
import android.view.View
import android.widget.Toast
import java.util.Locale
import java.util.Vector


/**
 * Handles the majority of functionality for the application.
 *
 * @author Andrew Groebe.
 */
class MainActivity : AppCompatActivity(), OnInitListener {

    private var myTTS: TextToSpeech? = null

    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragments = Vector<Fragment>()
        fragments.add(Fragment.instantiate(this, Fragment1::class.java!!.getName()))
        fragments.add(Fragment.instantiate(this, Fragment2::class.java!!.getName()))
        fragments.add(Fragment.instantiate(this, Fragment3::class.java!!.getName()))

        val checkTTSIntent = Intent()
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA)
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE)

        val adapter = PagerAdapter(getSupportFragmentManager(), fragments)
        val pager = findViewById(R.id.viewpager) as ViewPager
        pager.setAdapter(adapter)

        val actionBar = getSupportActionBar()
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS)

        val tabListener = object : ActionBar.TabListener() {
            fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                pager.setCurrentItem(tab.getPosition())
            }

            fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction) {

            }

            fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction) {

            }
        }

        actionBar.addTab(actionBar.newTab().setText("Common Words/Phrases").setTabListener(tabListener))
        actionBar.addTab(actionBar.newTab().setText("Common Words (Continued)").setTabListener(tabListener))
        actionBar.addTab(actionBar.newTab().setText("Food & Restaurants").setTabListener(tabListener))

        pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            fun onPageSelected(position: Int) {
                actionBar.setSelectedNavigationItem(position)
            }
        })
    }

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun speakWords(speech: String) {
        myTTS!!.speak(speech, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun onClick(view: View) {
        val enteredText = findViewById(R.id.enter) as EditText

        when (view.getId()) {
            R.id.speak // If custom string entered, simply speak the string.
            -> {
                val words = enteredText.getText().toString()
                if (words != "") { // If empty string, do nothing.
                    speakWords(words)
                }
            }

            R.id.clear // Clear button, set entered text to an empty string.
            -> enteredText.setText("")

            R.id.subway -> speakWords("Subway order will go here.")
            R.id.whitecastle -> speakWords("White castle order will go here.")
            R.id.mcdonalds -> speakWords("McDonald's order will go here.")

            else -> speakWords(view.getTag().toString())
        }

    }

    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = TextToSpeech(this, this)
            } else {
                val installTTSIntent = Intent()
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
                startActivity(installTTSIntent)
            }
        }
    }

    fun onInit(initStatus: Int) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS!!.isLanguageAvailable(Locale.US) === TextToSpeech.LANG_AVAILABLE) {
                myTTS!!.setLanguage(Locale.US)
            }
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private val MY_DATA_CHECK_CODE = 0
    }

}

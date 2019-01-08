package com.example.groebe.myapplication;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * Handles the majority of functionality for the application.
 *
 * @author Andrew Groebe.
 */
public final class MainActivity extends AppCompatActivity implements OnInitListener {

    private TextToSpeech myTTS;
    private static final int MY_DATA_CHECK_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, Fragment1.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment2.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment3.class.getName()));

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        final ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        actionBar.addTab(actionBar.newTab().setText("Common Words/Phrases").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Common Words (Continued)").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Food & Restaurants").setTabListener(tabListener));

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    private void speakWords(String speech) {
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onClick(View view) {
        EditText enteredText = (EditText)findViewById(R.id.enter);

        switch (view.getId()) {
            case R.id.speak: // If custom string entered, simply speak the string.
                String words = enteredText.getText().toString();
                if (!words.equals("")) { // If empty string, do nothing.
                    speakWords(words);
                }
                break;

            case R.id.clear: // Clear button, set entered text to an empty string.
                enteredText.setText("");
                break;

            case R.id.subway:
                speakWords("Subway order will go here.");
                break;
            case R.id.whitecastle:
                speakWords("White castle order will go here.");
                break;
            case R.id.mcdonalds:
                speakWords("McDonald's order will go here.");
                break;

            default:
                speakWords(view.getTag().toString());
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = new TextToSpeech(this, this);
            } else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    @Override
    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE) {
                myTTS.setLanguage(Locale.US);
            }
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

}
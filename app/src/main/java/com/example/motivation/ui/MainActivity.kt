package com.example.motivation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.motivation.R
import com.example.motivation.databinding.ActivityMainBinding
import com.example.motivation.infra.MotivationConstants
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.repository.Mock

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mSecurityPreference: SecurityPreferences

    private var mPhraseFilter: Int = MotivationConstants.PHRASEFILTER.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mSecurityPreference = SecurityPreferences(this)
        val name = mSecurityPreference.getString(MotivationConstants.KEY.PERSON_NAME)
        binding.textName.text = "Olá, $name!"

        //initial session logic
        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent))
        handleNewPhrase()

        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageMorning.setOnClickListener(this)
        binding.buttonNewPhrase.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        val id = view.id

        val listFilter = listOf(R.id.imageAll, R.id.imageHappy, R.id.imageMorning)

        if (id == R.id.buttonNewPhrase) {
            handleNewPhrase()
        } else if (id in listFilter) {
            handleFilter(id)
        }
    }

    private fun handleFilter(id: Int) {

        val imageAll = binding.imageAll
        val imageHappy = binding.imageHappy
        val imageMorning = binding.imageMorning

        imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
        imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
        imageMorning.setColorFilter(ContextCompat.getColor(this, R.color.white))

        when (id) {
            R.id.imageAll -> {
                imageAll.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent))
                mPhraseFilter = MotivationConstants.PHRASEFILTER.ALL
            }
            R.id.imageHappy -> {
                imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent))
                mPhraseFilter = MotivationConstants.PHRASEFILTER.HAPPY
            }
            R.id.imageMorning -> {
                imageMorning.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent))
                mPhraseFilter = MotivationConstants.PHRASEFILTER.MORNING
            }
        }


    }


    private fun handleNewPhrase() {
        val phrase = Mock().getPhrase(mPhraseFilter)
        binding.textPhrase.text = phrase
    }

}
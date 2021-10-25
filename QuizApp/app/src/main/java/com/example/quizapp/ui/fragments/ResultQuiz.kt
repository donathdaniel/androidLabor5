package com.example.quizapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.shared.MyViewModel
import kotlin.math.roundToInt

class ResultQuiz : Fragment() {

    val sharedView : MyViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result_quiz, container, false)

        val tryAgainButton = view.findViewById<Button>(R.id.tryAgainButton)
        tryAgainButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultQuiz_to_startQuiz)
        }

        if(sharedView.companion.points*10%10 == 0F) {
            val points : Int = sharedView.companion.points.roundToInt()
            view.findViewById<TextView>(R.id.resultTextView).setText("$points / ${sharedView.companion.finalPoints}")
        }
        else{
            view.findViewById<TextView>(R.id.resultTextView).setText("${sharedView.companion.points} / ${sharedView.companion.finalPoints}")
        }

        return view
    }

}
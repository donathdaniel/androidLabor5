package com.example.quizapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.shared.MyViewModel
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.quizapp.shared.Question


class CurrentQuizCheckbox : Fragment() {

    val sharedView : MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_quiz, container, false)


        var question = sharedView.getQuestion()

        val questionText = view.findViewById<TextView>(R.id.questionText)
        questionText.setText(question.text)

        val constraintL = view.findViewById<ConstraintLayout>(R.id.constraint)
        for (i in 0 until constraintL.childCount) {
            val child = constraintL.getChildAt(i) as CheckBox
            child.text = question.answers[i].first
        }


        val nextButton = view.findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {

            if(oneAnswerChecked()){
                var number : MutableList<Int> = mutableListOf()
                for (i in 0 until constraintL.childCount) {
                    val child = constraintL.getChildAt(i) as CheckBox
                    if(child.isChecked) number.add(i)
                }
                sharedView.calculateResult(question, number)
                if(sharedView.endOfQuiz())
                    findNavController().navigate(R.id.action_currentQuizCheckbox_to_resultQuiz)
                else {
                    if(sharedView.typeOfNewxtQuestion() == 1) {
                        findNavController().navigate(R.id.action_currentQuizCheckbox_to_currentQuiz)
                    }
                    else{
                        findNavController().navigate(R.id.action_currentQuizCheckbox_self)
                    }
                }
            }
        }

        return view
    }


    private fun oneAnswerChecked() : Boolean{
        val radioGroup = view?.findViewById<RadioGroup>(R.id.radioGroup)
        if (radioGroup != null) {
            for (i in 0 until radioGroup.childCount) {
                if((radioGroup.getChildAt(i) as RadioButton).isChecked)
                    return true
            }
        }
        return false
    }

}
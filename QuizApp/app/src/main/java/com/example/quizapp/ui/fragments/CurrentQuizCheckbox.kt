package com.example.quizapp.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
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
        val view = inflater.inflate(R.layout.fragment_current_quiz_checkbox, container, false)


        //back button
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialogClickListener =
                    DialogInterface.OnClickListener { dialog, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                findNavController().navigate(R.id.action_currentQuizCheckbox_to_startQuiz)
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {

                            }
                        }
                    }

                val builder = AlertDialog.Builder(context)
                builder.setMessage("Are you sure you want to quit?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show()

            }
        })

        var question = sharedView.getQuestion()

        val questionText = view.findViewById<TextView>(R.id.questionText)
        questionText.setText(question.text)

        val answer1 = view.findViewById<CheckBox>(R.id.answer1)
        val answer2 = view.findViewById<CheckBox>(R.id.answer2)
        val answer3 = view.findViewById<CheckBox>(R.id.answer3)
        val answer4 = view.findViewById<CheckBox>(R.id.answer4)
        val listOfCheckbox : List<CheckBox> = listOf(answer1,answer2,answer3,answer4)
        listOfCheckbox.forEachIndexed { index, item ->
            item.text = question.answers[index].first
        }


        val nextButton = view.findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {

            if(oneAnswerChecked()){
                var number : MutableList<Int> = mutableListOf()
                listOfCheckbox.forEachIndexed { index, item ->
                    if(item.isChecked)
                        number.add(index)
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
        val answer1 = view?.findViewById<CheckBox>(R.id.answer1)
        val answer2 = view?.findViewById<CheckBox>(R.id.answer2)
        val answer3 = view?.findViewById<CheckBox>(R.id.answer3)
        val answer4 = view?.findViewById<CheckBox>(R.id.answer4)
        val listOfCheckbox : List<CheckBox> = listOf(answer1,answer2,answer3,answer4) as List<CheckBox>
        listOfCheckbox.forEach { item ->
            if(item.isChecked)
                return true
        }
        return false
    }

}
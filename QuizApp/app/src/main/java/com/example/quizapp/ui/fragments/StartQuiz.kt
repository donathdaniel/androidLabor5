package com.example.quizapp.ui.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.shared.MyViewModel

class StartQuiz : Fragment() {

    val sharedView : MyViewModel by activityViewModels()
    val REQUEST_SELECT_CONTACT = 1
    lateinit var contactUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_start_quiz, container, false)
        val getStartedButton = view.findViewById<Button>(R.id.getStartedButton)

        val contactsButton = view.findViewById<Button>(R.id.contactsButton)

        contactsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }

        getStartedButton.setOnClickListener {
            val editTextTextPersonName = view.findViewById<EditText>(R.id.editTextTextPersonName)
            if(editTextTextPersonName.text.isEmpty()) {
                Toast.makeText(this.context, "You did not give a name!", Toast.LENGTH_SHORT).show();
            }
            else{
                sharedView.startQuiz()
                if(sharedView.typeOfNewxtQuestion() == 1) {
                    findNavController().navigate(R.id.action_startQuiz_to_currentQuiz)
                }
                else{
                    findNavController().navigate(R.id.action_startQuiz_to_currentQuizCheckbox)
                }
            }
        }


        return view
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_SELECT_CONTACT && resultCode == Activity.RESULT_OK){
            contactUri = data!!.data!!
            getContactName()
        }
    }

    @SuppressLint("Range")
    private fun getContactName() {
        val cursor = requireActivity().contentResolver.query(contactUri,null,null,null,null)

        if(cursor!!.moveToFirst()){
            val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

            val editTextTextPersonName = view?.findViewById<EditText>(R.id.editTextTextPersonName)
            editTextTextPersonName?.setText(contactName)

        }
    }

}

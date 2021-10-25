package com.example.quizapp.shared

import androidx.lifecycle.ViewModel
import java.io.IOException
import java.io.InputStream
import android.app.Application
import android.content.Context


data class Question(var answers : MutableList<Pair<String,Boolean>>, val text : String)
/*

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: MyApplication? = null
            private set
    }
}
*/

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Companion.mContext = this
    }

    companion object {
        private var mContext: MyApplication? = null
        val context: MyApplication
            get() = Companion.mContext!!
    }
}

class MyViewModel : ViewModel() {
    companion object {
        var questions: MutableList<Question> = mutableListOf()
        var questionCounter : Int = 0
        var points : Float = 0F
        var finalPoints : Int = 0
    }

    var companion = Companion

    fun startQuiz() {
        questions = mutableListOf()
        points = 0F
        finalPoints = 0
        questionCounter = 0
        val fileName = "questions.txt"
        var text = ""
        var answers: MutableList<Pair<String,Boolean>> = mutableListOf()
        println("asd")


        // TODO!!!
        /*var myOutput: String = ""
        val myInputStream: InputStream
        try {
            myInputStream = MyApplication.context.assets.open("questions.txt")
            val size: Int = myInputStream.available()
            val buffer = ByteArray(size)
            myInputStream.read(buffer)
            myOutput = String(buffer)

        } catch (e: IOException) {
            // Exception
            e.printStackTrace()
        }
        println(myOutput)*/

        var question1 = Question(mutableListOf(Pair("14",false),Pair("11",false),Pair("8",false),Pair("5",true)), "One rabbit saw 6 elephants while going towards River. Every elephant saw 2 monkeys are going towards river. Every monkey holds one tortoice in their hands. How many animals are going towards the river?")
        var question2 = Question(mutableListOf(Pair("Light",false),Pair("Bats",false),Pair("Stars",true),Pair("Flights",false)), "They come out at night without being called and are lost in the day without being stolen. What are they?")
        var question3 = Question(mutableListOf(Pair("Maggi",false),Pair("Ericson",false),Pair("Sona",false),Pair("Jason",true)), "A man was murdered in his office. The suspects are Ericson, Maggi, Joel, Benny, Sona, Patick. A calendar found near the man has blood written 6, 4, 9, 10, 11. Who is the killer?")
        var question4 = Question(mutableListOf(Pair("Egyszer",false),Pair("Kétszer",false),Pair("Háromszor",true),Pair("Nyolcszor",false)), "Hányszor 8 88-ból 8*8?")
        var question5 = Question(mutableListOf(Pair("Shoe",false),Pair("Charcoal",true),Pair("Belt",false),Pair("All the above",false)), "What is black when you buy it, red when you use it, and gray when you throw it away?")
        var question6 = Question(mutableListOf(Pair("Money",false),Pair("Luxury items",false),Pair("Brain",false),Pair("Nothing",true)), "Poor people have it.Rich people need it.If you eat it you die.")
        var question7 = Question(mutableListOf(Pair("Mirror",false),Pair("Book",true),Pair("Table",false),Pair("None of the above",false)), "What has a spine but no bones?")
        var question8 = Question(mutableListOf(Pair("Son-in-law",true),Pair("Daughter-in-law",false),Pair("Grand mother",false),Pair("Grand Daughter",false)), "If Theresa's daughter is my daughter's mother, what am I to Theresa?")
        questions.add(question1)
        questions.add(question2)
        questions.add(question3)
        questions.add(question4)
        questions.add(question5)
        questions.add(question6)
        questions.add(question7)
        questions.add(question8)

        randomizeQuestions()
    }

    fun typeOfNewxtQuestion() : Int{
        var counter = 0
        for(q in questions[questionCounter].answers){
            if(q.second == true){
                counter++
            }
        }
        return counter
    }

    fun randomizeQuestions(){
        questions.shuffle()
        randomizeAnswers()
    }

    fun randomizeAnswers(){
        for(q in questions)
            q.answers.shuffle()
    }

    fun endOfQuiz() : Boolean {
        if (questionCounter == questions.size) {
            return true
        }
        return false
    }

    fun getQuestion() : Question{
        val question = Question(questions[questionCounter].answers, questions[questionCounter].text)
        questionCounter++
        return question
    }

    fun calculateResult(question: Question, numbers : MutableList<Int>) {
        finalPoints++

        var correctAnswers = 0F
        for(number in numbers)
            if(question.answers[number].second == true){
                correctAnswers++
            }

        var questionCorrectAnswer = 0
        for(q in question.answers){
            if(q.second == true)
                questionCorrectAnswer++
        }

        points += correctAnswers/questionCorrectAnswer
    }

}
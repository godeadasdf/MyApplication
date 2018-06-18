package com.kangning.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kangning.myapplication.recovery.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    @BindRelation(type = TYPE.SINGLE, clazz = String::class)
    var json: String = ""

    @BindRelation(type = TYPE.LIST, clazz = Bean::class)
    var beans: ArrayList<Bean> = ArrayList()

    data class Bean(val name: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
            json += edit_text.text.toString() + '\n'
            beans.add(Bean(edit_text.text.toString()))
            edit_text.setText("")

            RecoveryWorker.save(this, "OP")
        }

        RecoveryWorker.check(this, this::class.qualifiedName!!+"OP") {
            if (!it) {
                Toast.makeText(this, "nono", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "yeye", Toast.LENGTH_LONG).show()
            }
        }

        RecoveryWorker.recover(this, "OP") {
            text.text = json + beans.toString()
        }

    }
}


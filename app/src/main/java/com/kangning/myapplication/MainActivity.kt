package com.kangning.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.kangning.myapplication.recovery.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    @BindRelation(type = TYPE.SINGLE, clazz = String::class)
    var json: String = ""

    @BindRelation(type = TYPE.LIST, clazz = Bean::class)
    var beans: List<Bean> = Collections.emptyList()

    data class Bean(val name: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*launch(CommonPool) {
            val json = RecoveryRepository(application).getRecoveryItem("s").jsonString
            withContext(UI) {
                text.text = json
            }
        }*/

        RecoveryWorker.recover(this) {
            Log.d("json", beans.toString())
        }
        RecoveryWorker.save(this)
    }
}


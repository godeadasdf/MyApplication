package com.kangning.myapplication.recovery

import android.app.Activity
import android.util.Log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*

object RecoveryWorker {

    fun recover(activity: Activity) {

    }

    fun save(activity: Activity) {
        launch(CommonPool) {
            RecoveryRepository(activity.application).insert(
                    RecoveryEntity(activity::class.qualifiedName!!,
                            generateJson(activity)
                    )
            )
        }
    }

    private fun generateJson(activity: Activity): String {

        val map = HashMap<String, String?>()
        val clazz = activity::class
        clazz.declaredMemberProperties.forEach { prop ->
            val mutableProp = try {
                prop as KMutableProperty<*>
            } catch (e: Exception) {
                null
            } ?: return@forEach

            val annotation = mutableProp.findAnnotation<BindRelation>()
            if (annotation != null) {
                when (annotation.clazz) {
                    String::class -> {
                        map[prop.name] = prop.getter.call(activity) as String
                    }

                    List::class -> {

//                        annotation.clazz.isInstance(prop.getter.call(activity))
                    }

//                    List::class ->
//                        map[prop.name] =

                }

            }
        }
        map
        return ""

    }
}

data class RecoverCell(
        val type: RecoveryType,
        val gson: String,
        val clazz: KClass<Any>
)

sealed class RecoveryType {
    class JustRecord : RecoveryType()
    class ForRecovery : RecoveryType()
}
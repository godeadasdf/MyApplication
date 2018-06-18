package com.kangning.myapplication.recovery

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible

object RecoveryWorker {

    fun recover(activity: Activity, func: () -> Unit) {
        launch(CommonPool) {
            val content = RecoveryRepository(activity.application).getRecoveryItem(
                    activity::class.qualifiedName!!
            )
            if (content != null) {
                val clazz = activity::class
                val map = Gson().fromJson<HashMap<String, Any?>>(content.jsonString,
                        object : TypeToken<HashMap<String, Any?>>() {}.type)
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
                                mutableProp.isAccessible = true
                                mutableProp.setter.call(activity, map[mutableProp.name])
                            }

                            else -> {
//                            map[prop.name] = List::class.cast(prop.getter.call(activity))
                                mutableProp.isAccessible = true
                                mutableProp.setter.call(activity, List::class.cast(map[mutableProp.name]))
                            }

                        }

                    }
                }

                withContext(UI) {
                    func()
                }

                RecoveryRepository(activity.application).deleteRecoveryItem(
                        activity::class.qualifiedName!!)

            }

        }
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

    fun check(activity: Activity, className: String, func: (Boolean) -> Unit) {
        launch(CommonPool) {
            val recoverable = RecoveryRepository(activity.application).getRecoveryItem(className) != null
            withContext(UI) {
                func(recoverable)
            }
        }
    }

    private fun generateJson(activity: Activity): String {

        val map = HashMap<String, Any?>()
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

                    else -> {
                        map[prop.name] = List::class.cast(prop.getter.call(activity))
                    }

                }

            }
        }
        return Gson().toJson(map)
    }
}

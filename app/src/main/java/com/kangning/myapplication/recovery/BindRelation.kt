package com.kangning.myapplication.recovery

import android.view.View
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindRelation(
//        val widgetName: String
//        val widgetClass: KClass<out View>
        val type: TYPE,
        val clazz: KClass<*>
)

enum class TYPE {
    SINGLE, LIST
}


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME) //这一行也可以省略
annotation class Value(val value: String)
package com.kangning.myapplication.recovery

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

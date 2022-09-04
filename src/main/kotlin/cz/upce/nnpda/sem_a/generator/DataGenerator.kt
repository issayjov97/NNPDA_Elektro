package cz.upce.nnpda.sem_a.generator

import org.springframework.stereotype.Component
import java.util.function.BiConsumer
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class DataGenerator<T : Any>(
    private var type: KClass<T>
) {
    private val setters: MutableMap<BiConsumer<T, *>, DataType<*>> = mutableMapOf()

    fun <F> setData(setter: BiConsumer<T, F>, dataType: DataType<F>) {
        setters[setter] = dataType
    }

    fun createBean(): T {
        return try {
            val bean = type.createInstance()
            val var3: Iterator<*> = this.setters.entries.iterator()
            while (var3.hasNext()) {
                val (key, value) = var3.next() as Map.Entry<T, *>
                this.assignValue(bean, key as BiConsumer<T, in Any>, value as DataType<*>)
            }
            bean
        } catch (var5: Exception) {
            throw RuntimeException("Unable to create bean instance of type " + type.simpleName, var5)
        }
    }

    fun create(count: Int): List<T> {
        val beans = mutableListOf<T>()

        (0..count).forEach {
            beans.add(createBean())
        }
        return beans
    }

    private fun assignValue(bean: T, setter: BiConsumer<T, in Any>, dataType: DataType<*>) {
        setter.accept(bean, dataType.getValue()!!)
        DataType.MEASUREMENT
    }

}

abstract class DataType<F> {
    companion object {
        val MEASUREMENT = Measurement()
        val ID = ID()
    }

    abstract fun getValue(): F
}

class Measurement : DataType<Double>() {
    override fun getValue(): Double = Math.random()
}

class ID : DataType<Long>() {
    override fun getValue(): Long = 0L
}

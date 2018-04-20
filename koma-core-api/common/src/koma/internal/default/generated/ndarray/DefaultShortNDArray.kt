/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.internal.default.generated.ndarray

import koma.ndarray.*
import koma.internal.KomaJsName
import koma.internal.default.utils.*


/**
 * An (unoptimized) implementation of [NDArray] in pure Kotlin, for portability between the
 * different platforms koma supports.
 *
 * @param shape A vararg specifying the size of each dimension, e.g. a 3D array with size 4x6x8 would pass in 4,6,8)
 * @param init A function that takes a location in the new array and returns its initial value.
 */
open class DefaultShortNDArray(@KomaJsName("shape_private") vararg protected val shape: Int,
                             init: ((IntArray)->Short)? = null): NDArray<Short> {

    /**
     * Underlying storage. PureKt backend uses a simple array.
     */
    private val storage: ShortArray

    init {
        @Suppress("UNCHECKED_CAST")
        storage = if (init!=null) 
            ShortArray(shape.reduce{ a, b-> a * b}, {init.invoke(linearToNIdx(it))}) 
        else
            ShortArray(shape.reduce{ a, b-> a * b})
    }

    override fun getGeneric(vararg indices: Int): Short {
        checkIndices(indices)
        return storage[nIdxToLinear(indices)]
    }
    override fun getGeneric(i: Int): Short = storage[i]
    override fun setGeneric(i: Int, value: Short) { storage[i] = value }

    override fun setGeneric(vararg indices: Int, value: Short) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value
    }
    // TODO: cache this
    override val size get() = storage.size
    override fun shape(): List<Int> = shape.toList()
    override fun copy(): NDArray<Short> = DefaultShortNDArray(*shape, init = { this.getGeneric(*it) })
    override fun getBaseArray(): Any = storage

    private val wrongType = "Double methods not implemented for generic NDArray"
    override fun getDouble(i: Int): Double {
        val ele = storage[checkLinearIndex(i)]
        return ele.toDouble()
    }
    override fun setDouble(i: Int, value: Double) {
        storage[checkLinearIndex(i)] = value.toShort()
    }

    override fun getByte(i: Int): Byte {
        val ele = storage[checkLinearIndex(i)]
        return ele.toByte()
    }
    override fun setByte(i: Int, value: Byte) {
        storage[checkLinearIndex(i)] = value.toShort()
    }

    override fun getInt(i: Int): Int {
        val ele = storage[checkLinearIndex(i)]
        return ele.toInt()
    }
    override fun setInt(i: Int, value: Int) {
        storage[checkLinearIndex(i)] = value.toShort()
    }

    override fun getFloat(i: Int): Float {
        val ele = storage[checkLinearIndex(i)]
        return ele.toFloat()
    }
    override fun setFloat(i: Int, value: Float) {
        storage[checkLinearIndex(i)] = value.toShort()
    }

    override fun getLong(i: Int): Long {
        val ele = storage[checkLinearIndex(i)]
        return ele.toLong()
    }
    override fun setLong(i: Int, value: Long) {
        storage[checkLinearIndex(i)] = value.toShort()
    }

    override fun getShort(i: Int): Short {
        val ele = storage[checkLinearIndex(i)]
        return ele.toShort()
    }
    override fun setShort(i: Int, value: Short) {
        storage[checkLinearIndex(i)] = value.toShort()
    }



}


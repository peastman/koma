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
open class DefaultLongNDArray(@KomaJsName("shape_private") vararg protected val shape: Int,
                             init: ((IntArray)->Long)? = null): NDArray<Long> {

    /**
     * Underlying storage. PureKt backend uses a simple array.
     */
    private val storage: LongArray

    init {
        @Suppress("UNCHECKED_CAST")
        storage = if (init!=null) 
            LongArray(shape.reduce{ a, b-> a * b}, {init.invoke(linearToNIdx(it))}) 
        else
            LongArray(shape.reduce{ a, b-> a * b})
    }

    override fun getGeneric(vararg indices: Int): Long {
        checkIndices(indices)
        return storage[nIdxToLinear(indices)]
    }
    override fun getLinear(index: Int): Long = storage[index]
    override fun setLinear(index: Int, value: Long) { storage[index] = value }

    override fun setGeneric(vararg indices: Int, value: Long) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value
    }
    // TODO: cache this
    override fun shape(): List<Int> = shape.toList()
    override fun copy(): NDArray<Long> = DefaultLongNDArray(*shape, init = { this.getGeneric(*it) })
    override fun getBaseArray(): Any = storage

    private val wrongType = "Double methods not implemented for generic NDArray"
    override fun getDouble(vararg indices: Int): Double {
        checkIndices(indices)
        val ele = storage[nIdxToLinear(indices)]
        return ele.toDouble()
    }
    override fun setDouble(vararg indices: Int, value: Double) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value.toLong()
    }
    override fun getByte(vararg indices: Int): Byte {
        checkIndices(indices)
        val ele = storage[nIdxToLinear(indices)]
        return ele.toByte()
    }
    override fun setByte(vararg indices: Int, value: Byte) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value.toLong()
    }
    override fun getInt(vararg indices: Int): Int {
        checkIndices(indices)
        val ele = storage[nIdxToLinear(indices)]
        return ele.toInt()
    }
    override fun setInt(vararg indices: Int, value: Int) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value.toLong()
    }
    override fun getFloat(vararg indices: Int): Float {
        checkIndices(indices)
        val ele = storage[nIdxToLinear(indices)]
        return ele.toFloat()
    }
    override fun setFloat(vararg indices: Int, value: Float) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value.toLong()
    }
    override fun getLong(vararg indices: Int): Long {
        checkIndices(indices)
        val ele = storage[nIdxToLinear(indices)]
        return ele.toLong()
    }
    override fun setLong(vararg indices: Int, value: Long) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value.toLong()
    }
    override fun getShort(vararg indices: Int): Short {
        checkIndices(indices)
        val ele = storage[nIdxToLinear(indices)]
        return ele.toShort()
    }
    override fun setShort(vararg indices: Int, value: Short) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value.toLong()
    }


}


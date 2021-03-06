/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.ndarray

import koma.extensions.create
import koma.extensions.fill
import koma.extensions.fillLinear
import koma.internal.*
import koma.internal.default.generated.ndarray.DefaultGenericNDArrayFactory
import koma.internal.default.utils.safeNIdxToLinear
import koma.matrix.*
import kotlin.reflect.KClass
import koma.util.IndexIterator

// TODO: broadcasting, iteration by selected dims, views, reshape
/**
 * A general N-dimensional container for arbitrary types. For this container to be
 * useful, you'll probably want to import koma.extensions.*, which includes e.g.
 * element getter/setters which are non boxed for primitives.
 *
 * If you are looking for a 2D container supporting linear algebra, please look at
 * [Matrix].
 */
interface NDArray<T> {
    companion object {

        // TODO: Ideally these properties are expect/actual with implementations. However, there's currently
        // a generation issue with kotlin/native that breaks this approach, so as a workaround we'll define
        // getXFactory methods in koma.internal as expect actual and proxy them here. These properties have
        // to be lazily evaluated to avoid a race on startup in js, so we use private nullable fields and
        // initialize on first use

        var doubleFactory: NumericalNDArrayFactory<Double>
            get() = _doubleFactory ?: getDoubleNDArrayFactory().also { _doubleFactory = it }
            set(value) { _doubleFactory = value }
        private var _doubleFactory: NumericalNDArrayFactory<Double>? = null

        var floatFactory: NumericalNDArrayFactory<Float>
            get() = _floatFactory ?: getFloatNDArrayFactory().also { _floatFactory = it }
            set(value) { _floatFactory = value }
        private var _floatFactory: NumericalNDArrayFactory<Float>? = null

        var longFactory: NumericalNDArrayFactory<Long>
            get() = _longFactory ?: getLongNDArrayFactory().also { _longFactory = it }
            set(value) { _longFactory = value }
        private var _longFactory: NumericalNDArrayFactory<Long>? = null

        var intFactory: NumericalNDArrayFactory<Int>
            get() = _intFactory ?: getIntNDArrayFactory().also { _intFactory = it }
            set(value) { _intFactory = value }
        private var _intFactory: NumericalNDArrayFactory<Int>? = null

        var shortFactory: NumericalNDArrayFactory<Short>
            get() = _shortFactory ?: getShortNDArrayFactory().also { _shortFactory = it }
            set(value) { _shortFactory = value }
        private var _shortFactory: NumericalNDArrayFactory<Short>? = null

        var byteFactory: NumericalNDArrayFactory<Byte>
            get() = _byteFactory ?: getByteNDArrayFactory().also { _byteFactory = it }
            set(value) { _byteFactory = value }
        private var _byteFactory: NumericalNDArrayFactory<Byte>? = null

        fun <T> createGeneric(vararg dims: Int, filler: (IntArray) -> T) =
            DefaultGenericNDArrayFactory<T>().createGeneric(*dims, filler = filler)

        fun <T> createGenericNulls(vararg dims: Int) =
                DefaultGenericNDArrayFactory<T?>().createGeneric(*dims, filler = {null})

        @Suppress("UNCHECKED_CAST")
        inline operator fun <reified T> invoke(vararg dims: Int,
                                               crossinline filler: (IntArray) -> T) =
            when(T::class) {
                Double::class -> doubleFactory.zeros(*dims).fill { filler(it) as Double }
                Float::class  -> floatFactory.zeros(*dims).fill { filler(it) as Float }
                Long::class   -> longFactory.zeros(*dims).fill { filler(it) as Long }
                Int::class    -> intFactory.zeros(*dims).fill { filler(it) as Int }
                Short::class  -> shortFactory.zeros(*dims).fill { filler(it) as Short }
                Byte::class   -> byteFactory.zeros(*dims).fill { filler(it) as Byte }
                else          -> createGeneric(*dims) { filler(it) }
            } as NDArray<T>

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> createLinear(vararg dims: Int,
                                            crossinline filler: (Int) -> T) =
            when(T::class) {
                Double::class -> doubleFactory.zeros(*dims).fillLinear { filler(it) as Double }
                Float::class  -> floatFactory.zeros(*dims).fillLinear { filler(it) as Float }
                Long::class   -> longFactory.zeros(*dims).fillLinear { filler(it) as Long }
                Int::class    -> intFactory.zeros(*dims).fillLinear { filler(it) as Int }
                Short::class  -> shortFactory.zeros(*dims).fillLinear { filler(it) as Short }
                Byte::class   -> byteFactory.zeros(*dims).fillLinear { filler(it) as Byte }
                else          -> createGenericNulls<T>(*dims).fillLinear { filler(it) }
            } as NDArray<T>
    }

    val size: Int get() = shape().reduce { a, b -> a * b }
    fun shape(): List<Int>
    fun copy(): NDArray<T>

    fun getBaseArray(): Any

    fun toIterable(): Iterable<T> {
        return object: Iterable<T> {
            override fun iterator(): Iterator<T> = object: Iterator<T> {
                private var cursor = 0
                private val size = this@NDArray.size
                override fun next(): T {
                    cursor += 1
                    // TODO: Either make 1D access work like Matrix or fix this
                    // to not use the largest dimension.
                    return this@NDArray.getGeneric(cursor - 1)
                }
                override fun hasNext() = cursor < size
            }
        }
    }

    /**
     * Converts this NDArray into a one-dimensional List in row-major order.
     */
    fun toList() = List(size) { getGeneric(it) }

    /**
     * Converts this NDArray into a one-dimensional MutableList in row-major order.
     */
    fun toMutableList() = MutableList(size) { getGeneric(it) }

    /**
     * Find the linear index of the minimum element in this array.
     * If the array contains non-comparable values, this throws an exception.
     * This is intended for internal use.  Call argMin() instead.
     */
    fun argMinInternal(): Int

    /**
     * Find the linear index of the maximum element in this array.
     * If the array contains non-comparable values, this throws an exception.
     * This is intended for internal use.  Call argMax() instead.
     */
    fun argMaxInternal(): Int

    /**
     * Find the value of the minimum element in this array.
     * If the array contains non-comparable values, this throws an exception.
     * This is intended for internal use.  Call min() instead.
     */
    fun minInternal(): T

    /**
     * Find the value of the maximum element in this array.
     * If the array contains non-comparable values, this throws an exception.
     * This is intended for internal use.  Call max() instead.
     */
    fun maxInternal(): T

    // Iterator over the indices of this NDArray, simultaneously in array and linear form.
    // Not intended to be used directly, but instead used by ext funcs in `koma.extensions`
    fun iterateIndices() = IndexIterator { shape().toIntArray() }


    // Primitive optimized getter/setters to avoid boxing. Not intended
    // to be used directly, but instead are used by ext funcs in `koma.extensions`.

    @KomaJsName("getGenericND")
    fun getGeneric(vararg indices: Int) = getGeneric(safeNIdxToLinear(indices))
    @KomaJsName("getGeneric1D")
    fun getGeneric(i: Int): T
    @KomaJsName("setGenericND")
    fun setGeneric(vararg indices: Int, v: T) = setGeneric(safeNIdxToLinear(indices), v)
    @KomaJsName("setGeneric1D")
    fun setGeneric(i: Int, v: T)

    @KomaJsName("getDoubleND")
    fun getDouble(vararg indices: Int) = getDouble(safeNIdxToLinear(indices))
    @KomaJsName("getDouble1D")
    fun getDouble(i: Int): Double = (getGeneric(i) as Number).toDouble()
    @KomaJsName("setDoubleND")
    fun setDouble(vararg indices: Int, v: Double) = setDouble(safeNIdxToLinear(indices), v)
    @KomaJsName("setDouble1D")
    @Suppress("UNCHECKED_CAST")
    fun setDouble(i: Int, v: Double) { setGeneric(i, v as T) }


    @KomaJsName("getFloatND")
    fun getFloat(vararg indices: Int) = getFloat(safeNIdxToLinear(indices))
    @KomaJsName("getFloat1D")
    fun getFloat(i: Int): Float = (getGeneric(i) as Number).toFloat()
    @KomaJsName("setFloatND")
    fun setFloat(vararg indices: Int, v: Float) = setFloat(safeNIdxToLinear(indices), v)
    @KomaJsName("setFloat1D")
    @Suppress("UNCHECKED_CAST")
    fun setFloat(i: Int, v: Float) { setGeneric(i, v as T) }


    @KomaJsName("getLongND")
    fun getLong(vararg indices: Int) = getLong(safeNIdxToLinear(indices))
    @KomaJsName("getLong1D")
    fun getLong(i: Int): Long = (getGeneric(i) as Number).toLong()
    @KomaJsName("setLongND")
    fun setLong(vararg indices: Int, v: Long) = setLong(safeNIdxToLinear(indices), v)
    @KomaJsName("setLong1D")
    @Suppress("UNCHECKED_CAST")
    fun setLong(i: Int, v: Long) { setGeneric(i, v as T) }


    @KomaJsName("getIntND")
    fun getInt(vararg indices: Int) = getInt(safeNIdxToLinear(indices))
    @KomaJsName("getInt1D")
    fun getInt(i: Int): Int = (getGeneric(i) as Number).toInt()
    @KomaJsName("setIntND")
    fun setInt(vararg indices: Int, v: Int) = setInt(safeNIdxToLinear(indices), v)
    @KomaJsName("setInt1D")
    @Suppress("UNCHECKED_CAST")
    fun setInt(i: Int, v: Int) { setGeneric(i, v as T) }


    @KomaJsName("getShortND")
    fun getShort(vararg indices: Int) = getShort(safeNIdxToLinear(indices))
    @KomaJsName("getShort1D")
    fun getShort(i: Int): Short = (getGeneric(i) as Number).toShort()
    @KomaJsName("setShortND")
    fun setShort(vararg indices: Int, v: Short) = setShort(safeNIdxToLinear(indices), v)
    @KomaJsName("setShort1D")
    @Suppress("UNCHECKED_CAST")
    fun setShort(i: Int, v: Short) { setGeneric(i, v as T) }


    @KomaJsName("getByteND")
    fun getByte(vararg indices: Int) = getByte(safeNIdxToLinear(indices))
    @KomaJsName("getByte1D")
    fun getByte(i: Int): Byte = (getGeneric(i) as Number).toByte()
    @KomaJsName("setByteND")
    fun setByte(vararg indices: Int, v: Byte) = setByte(safeNIdxToLinear(indices), v)
    @KomaJsName("setByte1D")
    @Suppress("UNCHECKED_CAST")
    fun setByte(i: Int, v: Byte) { setGeneric(i, v as T) }
}

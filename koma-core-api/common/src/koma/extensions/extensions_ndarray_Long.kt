@file:koma.internal.JvmName("NDArray")
@file:koma.internal.JvmMultifileClass

/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.extensions

import koma.internal.default.generated.ndarray.DefaultGenericNDArray
import koma.internal.default.utils.checkIndices
import koma.internal.default.utils.linearToNIdx
import koma.matrix.doubleFactory
import koma.ndarray.NDArray
import koma.pow
import koma.matrix.Matrix



@koma.internal.JvmName("fillLong")
inline fun  NDArray<Long>.fill(f: (idx: IntArray) -> Long): NDArray<Long> {
    this.forEachIndexedN { idx, ele ->
        this.set(indices=*idx, value = f(idx))
    }
    return this
}


/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray.
 *
 * @param f A function that takes in an element and returns an element
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapLong")
inline fun  NDArray<Long>.map(f: (Long) -> Long): NDArray<Long> {
    // TODO: Something better than copy here
    val out = this.copy()
    for ((idx, ele) in this.toIterable().withIndex())
        out.setLinear(idx, f(ele))
    return out
}
/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is a linear index, depending on the underlying storage
 * major dimension.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the linear index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapIndexedLong")
inline fun  NDArray<Long>.mapIndexed(f: (idx: Int, ele: Long) -> Long): NDArray<Long> {
    // TODO: Something better than copy here
    val out = this.copy()
    for ((idx, ele) in this.toIterable().withIndex())
        out.setLinear(idx, f(idx, ele))
    return out
}
/**
 * Takes each element in a NDArray and passes them through f.
 *
 * @param f A function that takes in an element
 *
 */
@koma.internal.JvmName("forEachLong")
inline fun  NDArray<Long>.forEach(f: (ele: Long) -> Unit) {
    for (ele in this.toIterable())
        f(ele)
}
/**
 * Takes each element in a NDArray and passes them through f. Index given to f is a linear
 * index, depending on the underlying storage major dimension.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the linear index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexedLong")
inline fun  NDArray<Long>.forEachIndexed(f: (idx: Int, ele: Long) -> Unit) {
    for ((idx, ele) in this.toIterable().withIndex())
        f(idx, ele)
}

// TODO: for both of these, batch compute [linearToNIdx] instead of computing for every ele

/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is the full ND index of the element.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the ND index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
@koma.internal.JvmName("mapIndexedNLong")
inline fun  NDArray<Long>.mapIndexedN(f: (idx: IntArray, ele: Long) -> Long): NDArray<Long>
        = this.mapIndexed { idx, ele -> f(linearToNIdx(idx), ele) }

/**
 * Takes each element in a NDArray and passes them through f. Index given to f is the full
 * ND index of the element.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the ND index of the element's location.
 *
 */
@koma.internal.JvmName("forEachIndexedNLong")
inline fun  NDArray<Long>.forEachIndexedN(f: (idx: IntArray, ele: Long) -> Unit)
        = this.forEachIndexed { idx, ele -> f(linearToNIdx(idx), ele) }


@koma.internal.JvmName("getRangesLong")
operator fun  NDArray<Long>.get(vararg indices: IntRange): NDArray<Long> {
    checkIndices(indices.map { it.last }.toIntArray())
    return DefaultGenericNDArray<Long>(shape = *indices
            .map { it.last - it.first + 1 }
            .toIntArray()) { newIdxs ->
        val offsets = indices.map { it.first }
        val oldIdxs = newIdxs.zip(offsets).map { it.first + it.second }
        this.getGeneric(*oldIdxs.toIntArray())
    }
}

@koma.internal.JvmName("setLong")
operator fun  NDArray<Long>.set(vararg indices: Int, value: NDArray<Long>) {
    val shape = shape()
    val lastIndex = indices.mapIndexed { i, range -> range + value.shape()[i] }
    val outOfBounds = lastIndex.withIndex().any { it.value > shape()[it.index] }
    if (outOfBounds)
        throw IllegalArgumentException("NDArray with shape ${shape()} cannot be " +
                "set at ${indices.toList()} by a ${value.shape()} array " +
                "(out of bounds)")

    val offset = indices.map { it }.toIntArray()
    value.forEachIndexedN { idx, ele ->
        val newIdx = offset.zip(idx).map { it.first + it.second }.toIntArray()
        this.setGeneric(indices=*newIdx, value=ele)
    }
}


operator fun  NDArray<Long>.get(vararg indices: Int) = getLong(*indices)
operator fun  NDArray<Long>.set(vararg indices: Int, value: Long) = setLong(indices=*indices, value=value)


@koma.internal.JvmName("divLong")
operator fun NDArray<Long>.div(other: Long) = map { (it/other).toLong() }
@koma.internal.JvmName("timesArrLong")
operator fun NDArray<Long>.times(other: NDArray<Long>) = mapIndexedN { idx, ele -> (ele*other.get(*idx)).toLong() }
@koma.internal.JvmName("timesLong")
operator fun NDArray<Long>.times(other: Long) = map { (it * other).toLong() }
@koma.internal.JvmName("unaryMinusLong")
operator fun NDArray<Long>.unaryMinus() = map { (-it).toLong() }
@koma.internal.JvmName("minusLong")
operator fun NDArray<Long>.minus(other: Long) = map { (it - other).toLong() }
@koma.internal.JvmName("minusArrLong")
operator fun NDArray<Long>.minus(other: NDArray<Long>) = mapIndexedN { idx, ele -> (ele - other.get(*idx)).toLong() }
@koma.internal.JvmName("plusLong")
operator fun NDArray<Long>.plus(other: Long) = map { (it + other).toLong() }
@koma.internal.JvmName("plusArrLong")
operator fun NDArray<Long>.plus(other: NDArray<Long>) = mapIndexedN { idx, ele -> (ele + other.get(*idx)).toLong() }
@koma.internal.JvmName("powLong")
infix fun NDArray<Long>.pow(exponent: Int) = map { pow(it.toDouble(), exponent).toLong() }


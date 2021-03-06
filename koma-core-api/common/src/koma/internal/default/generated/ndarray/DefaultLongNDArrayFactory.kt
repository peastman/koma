/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.internal.default.generated.ndarray

import koma.ndarray.*
import koma.internal.getRng
import koma.internal.syncNotNative

class DefaultLongNDArrayFactory: NumericalNDArrayFactory<Long> {
    override fun createGeneric(lengths: IntArray, filler: (IntArray)->Long) = DefaultLongNDArray(*lengths, init=filler)

    override fun zeros(vararg lengths: Int) = DefaultLongNDArray(*lengths) { 0L }

    override fun ones(vararg lengths: Int) = DefaultLongNDArray(*lengths) { 1L }

    override fun rand(vararg lengths: Int): NDArray<Long> {
        val rng = getRng()
        return syncNotNative(rng) {
            DefaultLongNDArray(*lengths) {
               rng.nextDoubleUnsafe().toLong()
            }
        }
    }

    override fun randn(vararg lengths: Int): NDArray<Long> {
        val rng = getRng()
        return syncNotNative(rng) {
            DefaultLongNDArray(*lengths) {
                rng.nextGaussianUnsafe().toLong()
            }
        }
    }
}

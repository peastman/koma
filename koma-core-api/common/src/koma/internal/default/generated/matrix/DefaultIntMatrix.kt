/**
 * THIS FILE IS AUTOGENERATED, DO NOT MODIFY. EDIT THE FILES IN templates/
 * AND RUN ./gradlew :codegen INSTEAD!
 */

package koma.internal.default.generated.matrix

import koma.*
import koma.extensions.*
import koma.matrix.*
import koma.internal.notImplemented


class DefaultIntMatrix (val rows: Int, 
                          val cols: Int): Matrix<Int> {
    val storage = IntArray(rows*cols)

    
    
    override fun div(other: Int): Matrix<Int>
            = this.mapIndexed { _, _, ele -> ele/other}


    override fun times(other: Matrix<Int>): Matrix<Int> {
        val out = getFactory().zeros(this.numRows(), other.numCols())
        out.forEachIndexed { row, col, _ ->
            for (cursor in 0 until this.numCols())
                out[row,col] += this[row, cursor]*other[cursor, col]
        }
        return out
    }
    
    override fun times(other: Int): Matrix<Int> 
            = this.map { it*other }

    override fun unaryMinus(): Matrix<Int> 
            = this.map { it*-1 }

    override fun minus(other: Int): Matrix<Int>
            = this.map { it - other }
    
    override fun minus(other: Matrix<Int>): Matrix<Int> 
            = this.mapIndexed { row, col, ele -> ele - other[row,col] }

    override fun plus(other: Int): Matrix<Int> 
            = this.map { it + other }

    override fun plus(other: Matrix<Int>): Matrix<Int> 
            = this.mapIndexed { row, col, ele -> ele + other[row,col] }

    override fun transpose(): Matrix<Int> 
            = getFactory()
            .zeros(numCols(),numRows())
            .fill { row, col -> this[col,row] }

    override fun elementTimes(other: Matrix<Int>): Matrix<Int> 
            = this.mapIndexed { row, col, ele -> ele*other[row,col] }

    
    
    override fun epow(other: Int): Matrix<Int>
            = this.mapIndexed { _, _, ele -> pow(ele.toDouble(), other.toDouble()).toInt() }

    override fun numRows(): Int = this.rows
    override fun numCols(): Int = this.cols

    private fun setStorage(i: Int, v: Int) {
        storage[i] = v
    }
    private fun setStorage(i: Int, j: Int, v: Int) {
        checkBounds(i,j)
        storage[this.cols*i+j] = v
    }

    private fun getStorage(i: Int, j: Int): Int {
        checkBounds(i,j)
        return storage[this.cols*i+j]
    }

    private fun getStorage(i: Int): Int 
            = storage[i]
    
    override fun copy(): Matrix<Int> 
            = this.map { it }
    
    
    override fun getInt(i: Int, j: Int): Int = this.getStorage(i,j).toInt()
    override fun getDouble(i: Int, j: Int): Double = this.getStorage(i,j).toDouble()
    override fun getFloat(i: Int, j: Int): Float = this.getStorage(i,j).toFloat()
    override fun getGeneric(i: Int, j: Int): Int = this.getStorage(i,j)
    override fun getInt(i: Int): Int = this.getStorage(i).toInt()
    override fun getDouble(i: Int): Double = this.getStorage(i).toDouble()
    override fun getFloat(i: Int): Float = this.getStorage(i).toFloat()
    override fun getGeneric(i: Int): Int = this.getStorage(i)
    override fun setInt(i: Int, v: Int) { this.setStorage(i, v.toInt())}
    override fun setDouble(i: Int, v: Double) { this.setStorage(i, v.toInt())}
    override fun setFloat(i: Int, v: Float) { this.setStorage(i, v.toInt())}
    override fun setGeneric(i: Int, v: Int) { this.setStorage(i, v)}
    override fun setInt(i: Int, j: Int, v: Int) { this.setStorage(i, j, v.toInt())}
    override fun setDouble(i: Int, j: Int, v: Double) { this.setStorage(i, j, v.toInt())}
    override fun setFloat(i: Int, j: Int, v: Float) { this.setStorage(i, j, v.toInt())}
    override fun setGeneric(i: Int, j: Int, v: Int) { this.setStorage(i, j, v)}
    override fun getDoubleData(): DoubleArray = storage.map { it.toDouble() }.toDoubleArray()
    override fun getRow(row: Int): Matrix<Int> {
        checkBounds(row, 0)
        val out = getFactory().zeros(1,cols)
        for (i in 0 until cols)
            out[i] = this[row, i]
        return out
    }
    override fun getCol(col: Int): Matrix<Int> {
        checkBounds(0,col)
        val out = getFactory().zeros(rows,1)
        for (i in 0 until rows)
            out[i] = this[i, col]
        return out
    }

    override fun setCol(index: Int, col: Matrix<Int>) {
        checkBounds(0,index)
        for (i in 0 until rows)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<Int>) {
        checkBounds(index, 0)
        for (i in 0 until cols)
            this[index, i] = row[i]
    }

    override fun chol(): Matrix<Int> {
        error(notImplemented)
    }

    override fun LU(): Triple<Matrix<Int>, Matrix<Int>, Matrix<Int>> {
        error(notImplemented)
    }

    override fun QR(): Pair<Matrix<Int>, Matrix<Int>> {
        error(notImplemented)
    }

    override fun SVD(): Triple<Matrix<Int>, Matrix<Int>, Matrix<Int>> {
        error(notImplemented)
    }

	override fun expm(): Matrix<Int> {
        error(notImplemented)
    }

    override fun solve(other: Matrix<Int>): Matrix<Int> {
        error(notImplemented)
    }

    override fun inv(): Matrix<Int> {
        error(notImplemented)
    }

    override fun det(): Int {
        error(notImplemented)
    }

    override fun pinv(): Matrix<Int> {
        error(notImplemented)
    }

    override fun normF(): Int {
        error(notImplemented)
    }

    override fun normIndP1(): Int {
        error(notImplemented)
    }

    override fun elementSum(): Int 
            = this.toIterable().reduce { a, b -> a + b }

    override fun diag(): Matrix<Int> 
            = getFactory()
            .zeros(numRows(),1)
            .fill{ row, _ -> this[row,row] }

    override fun maxInternal(): Int = this[argMax()]
    override fun mean(): Int = elementSum()/(numRows()*numCols())
    override fun minInternal(): Int = this[argMin()]

    override fun argMaxInternal(): Int {
        var highest= Int.MIN_VALUE
        var highestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] > highest) {
                highest = this[i]
                highestIdx = i
            }
        return highestIdx
    }

    override fun argMinInternal(): Int {
        var lowest = Int.MAX_VALUE
        var lowestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] < lowest) {
                lowest = this[i]
                lowestIdx = i
            }
        return lowestIdx
    }

    override fun trace(): Int {
        error(notImplemented)
    }

    override fun T(): Matrix<Int> = this.transpose()

    override fun getBaseMatrix(): Any 
            = storage
    override fun getFactory(): MatrixFactory<Matrix<Int>> 
            = DefaultIntMatrixFactory()
    
    private fun checkBounds(row: Int, col: Int) {
        if (row >= rows || col >= cols)
            throw IllegalArgumentException("row/col index out of bounds")
    }
}

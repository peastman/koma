package koma.matrix.default

import koma.*
import koma.extensions.*
import koma.matrix.*

class DefaultFloatMatrix (val rows: Int, 
                          val cols: Int): Matrix<Float> {
    val storage = FloatArray(rows*cols)

    
    override fun div(other: Float): Matrix<Float>
            = this.mapIndexed { _, _, ele -> ele/other}

    
    override fun div(other: Int): Matrix<Float>
            = this.mapIndexed { _, _, ele -> ele/other}


    override fun times(other: Matrix<Float>): Matrix<Float> {
        val out = getFactory().zeros(this.numRows(), other.numCols())
        out.forEachIndexed { row, col, _ ->
            for (cursor in 0 until this.numCols())
                out[row,col] += this[row, cursor]*other[cursor, col]
        }
        return out
    }
    
    override fun times(other: Float): Matrix<Float> 
            = this.map { it*other }

    override fun unaryMinus(): Matrix<Float> 
            = this.map { it*-1 }

    override fun minus(other: Float): Matrix<Float>
            = this.map { it - other }
    
    override fun minus(other: Matrix<Float>): Matrix<Float> 
            = this.mapIndexed { row, col, ele -> ele - other[row,col] }

    override fun plus(other: Float): Matrix<Float> 
            = this.map { it + other }

    override fun plus(other: Matrix<Float>): Matrix<Float> 
            = this.mapIndexed { row, col, ele -> ele + other[row,col] }

    override fun transpose(): Matrix<Float> 
            = getFactory()
            .zeros(numCols(),numRows())
            .fill { row, col -> this[col,row] }

    override fun elementTimes(other: Matrix<Float>): Matrix<Float> 
            = this.mapIndexed { row, col, ele -> ele*other[row,col] }

    
    override fun epow(other: Float): Matrix<Float> 
            = this.mapIndexed { _, _, ele -> pow(ele.toDouble(), other.toDouble()).toFloat() }

    
    override fun epow(other: Int): Matrix<Float>
            = this.mapIndexed { _, _, ele -> pow(ele.toDouble(), other.toDouble()).toFloat() }

    override fun numRows(): Int = this.rows
    override fun numCols(): Int = this.cols

    private fun setStorage(i: Int, v: Float) {
        storage[i] = v
    }
    private fun setStorage(i: Int, j: Int, v: Float) {
        checkBounds(i,j)
        storage[this.cols*i+j] = v
    }

    private fun getStorage(i: Int, j: Int): Float {
        checkBounds(i,j)
        return storage[this.cols*i+j]
    }

    private fun getStorage(i: Int): Float 
            = storage[i]
    
    override fun copy(): Matrix<Float> 
            = this.map { it }
    
    
    override fun getInt(i: Int, j: Int): Int = this.getStorage(i,j).toInt()
    override fun getDouble(i: Int, j: Int): Double = this.getStorage(i,j).toDouble()
    override fun getFloat(i: Int, j: Int): Float = this.getStorage(i,j).toFloat()
    override fun getGeneric(i: Int, j: Int): Float = this.getStorage(i,j)
    override fun getInt(i: Int): Int = this.getStorage(i).toInt()
    override fun getDouble(i: Int): Double = this.getStorage(i).toDouble()
    override fun getFloat(i: Int): Float = this.getStorage(i).toFloat()
    override fun getGeneric(i: Int): Float = this.getStorage(i)
    override fun setInt(i: Int, v: Int) { this.setStorage(i, v.toFloat())}
    override fun setDouble(i: Int, v: Double) { this.setStorage(i, v.toFloat())}
    override fun setFloat(i: Int, v: Float) { this.setStorage(i, v.toFloat())}
    override fun setGeneric(i: Int, v: Float) { this.setStorage(i, v)}
    override fun setInt(i: Int, j: Int, v: Int) { this.setStorage(i, j, v.toFloat())}
    override fun setDouble(i: Int, j: Int, v: Double) { this.setStorage(i, j, v.toFloat())}
    override fun setFloat(i: Int, j: Int, v: Float) { this.setStorage(i, j, v.toFloat())}
    override fun setGeneric(i: Int, j: Int, v: Float) { this.setStorage(i, j, v)}
    override fun getDoubleData(): DoubleArray = storage.map { it.toDouble() }.toDoubleArray()
    override fun getRow(row: Int): Matrix<Float> {
        checkBounds(row, 0)
        val out = getFactory().zeros(1,cols)
        for (i in 0 until cols)
            out[i] = this[row, i]
        return out
    }
    override fun getCol(col: Int): Matrix<Float> {
        checkBounds(0,col)
        val out = getFactory().zeros(rows,1)
        for (i in 0 until rows)
            out[i] = this[i, col]
        return out
    }

    override fun setCol(index: Int, col: Matrix<Float>) {
        checkBounds(0,index)
        for (i in 0 until rows)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<Float>) {
        checkBounds(index, 0)
        for (i in 0 until cols)
            this[index, i] = row[i]
    }

    override fun chol(): Matrix<Float> {
        TODO("not implemented")
    }

    override fun LU(): Triple<Matrix<Float>, Matrix<Float>, Matrix<Float>> {
        TODO("not implemented")
    }

    override fun QR(): Pair<Matrix<Float>, Matrix<Float>> {
        TODO("not implemented")
    }

    override fun SVD(): Triple<Matrix<Float>, Matrix<Float>, Matrix<Float>> {
        TODO("not implemented")
    }

	override fun expm(): Matrix<Float> {
        TODO("not implemented")
    }

    override fun solve(other: Matrix<Float>): Matrix<Float> {
        TODO("not implemented")
    }

    override fun inv(): Matrix<Float> {
        TODO("not implemented")
    }

    override fun det(): Float {
        TODO("not implemented")
    }

    override fun pinv(): Matrix<Float> {
        TODO("not implemented")
    }

    override fun normF(): Float {
        TODO("not implemented")
    }

    override fun normIndP1(): Float {
        TODO("not implemented")
    }

    override fun elementSum(): Float 
            = this.toIterable().reduce { a, b -> a + b }

    override fun diag(): Matrix<Float> 
            = getFactory()
            .zeros(numRows(),1)
            .fill{ row, _ -> this[row,row] }

    override fun max(): Float = this[argMax()]
    override fun mean(): Float = elementSum()/(numRows()*numCols())
    override fun min(): Float = this[argMin()]

    override fun argMax(): Int {
        var highest= Float.MIN_VALUE
        var highestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] > highest) {
                highest = this[i]
                highestIdx = i
            }
        return highestIdx
    }

    override fun argMin(): Int {
        var lowest = Float.MAX_VALUE
        var lowestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] < lowest) {
                lowest = this[i]
                lowestIdx = i
            }
        return lowestIdx
    }

    override fun trace(): Float {
        TODO("not implemented")
    }

    override fun T(): Matrix<Float> = this.transpose()

    override fun getBaseMatrix(): Any 
            = storage
    override fun getFactory(): MatrixFactory<Matrix<Float>> 
            = DefaultFloatMatrixFactory()
    
    private fun checkBounds(row: Int, col: Int) {
        if (row >= rows || col >= cols)
            throw IllegalArgumentException("row/col index out of bounds")
    }
}

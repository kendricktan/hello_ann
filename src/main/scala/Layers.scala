package main.scala

import scala.util.Random
/**
  * Created by kendricktan on 10/10/16.
  */
class Layers (rows: Int, var neurons: Array[Neurons]){
  // Multi dimensional array containing weight matrix
  var weights : Array[Array[Double]] = null

  // Auxiliary constructor
  // Random double between -1.25 and 1.25
  // (formula : min + (Random * (max-min)))
  def this(rows: Int) = {
    this(rows, Array.fill(rows)(new Neurons(-1.25 + (Random.nextDouble() * 2.5))))
  }

  def length : Int = this.neurons.length
}

package main.scala

import scala.util.Random
/**
  * Created by kendricktan on 10/10/16.
  */
class Layers (rows: Int, neurons: Array[Neurons]){
  var outputs = new Array[Double](neurons.length)

  // Auxiliary constructor
  // Random double between -1.25 and 1.25
  // (formula : min + (Random * (max-min)))
  def this(rows: Int) = {
    this(rows, Array.fill(rows)(new Neurons(-1.25 + (Random.nextDouble() * 2.5))))
  }

  def neuron(row: Int) : Neurons = {
    this.neurons(row)
  }
}

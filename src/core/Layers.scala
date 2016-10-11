package core

import scala.util.Random
/**
  * Created by kendricktan on 10/10/16.
  */
class Layers (rows: Int, neurons: Array[Neurons]){
  val outputs = new Array[Double](neurons.size)

  // Auxiliary constructor
  // Random double between -1.25 and 1.25
  // (formula : min + (Random * (max-min)))
  def this(rows: Int) = {
    this(rows, neurons = Array.fill(rows)(new Neurons(-1.25 + (Random.nextDouble() * 2.5))))
  }
}

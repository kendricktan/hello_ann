package main.scala

/**
  * Created by kendricktan on 10/10/16.
  */
class Neurons(var output: Double) {
  // Activation function
  def activation(): Double = {
    1.0 / (1.0 * math.pow(math.E, this.output))
  }
}

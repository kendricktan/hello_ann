package main.scala

import scala.util.Random
/**
  * Created by kendricktan on 11/10/16.
  */
class Network (inputSize: Int, var layers: Seq[Layers]){
  // Auxiliary constructor
  def this(inputSize: Int) = {
    this(inputSize, Seq(new Layers(inputSize)))
  }

  // Adds an additional layer to the existing network
  // Everything you add a layer, you also add a multi
  // dimensional array known as 'weight' to the array
  def addLayer(inputSize: Int, addBias: Boolean) : Unit = {
    // If bias just + 1 to input size
    if (addBias) {
      this.addLayer(inputSize + 1, !addBias)
    }

    // + 1 for bias layer
    else {
      // Construct a new layer and add a weight matrix to it
      // Randomize weight valies to be between -1.25 and 2.5
      val new_layer = new Layers(inputSize)
      new_layer.weights = Array.ofDim[Double](layers.last.length, inputSize)
      new_layer.weights = new_layer.weights.map(x => x.map(y => -1.25 + (Random.nextDouble() * 2.5)))

      // Create new layer and ammend it to network's layers
      layers = layers ++ Seq(new_layer)
    }
  }

  // Will try and implement in matrix format if have time
  // Try and fit model to training data
  def fit(inputs: Array[Neurons], outputs: Array[Neurons]): Unit ={
    // Set the first layer to be input
    this.layers.head.neurons = inputs

    // Forward pass to store the outputs
    // Go through each layer, except for input
    for (i <- 1 until this.layers.length-1) {
      // for each layer for through each of
      // its neurons
      for (x <- 0 until this.layers(i).neurons.length-1) {
        // Variable to calculate the outputs
        var c_output: Double = 0.0

        // Iterate through the output neurons
        for (y <- 0 until this.layers(i-1).neurons.length-1) {
          // Get output neuron
          val o_neurons = this.layers(i-1).neurons(y)

          // Current weight
          val c_weight = this.layers(i).weights(y)(x)

          // If i = 0 then we're getting the output,
          // not the activation from neuron
          // since it's the input
          if (i - 1 == 0) {
            c_output = c_output + (o_neurons.output * c_weight)
          }
          else {
            c_output = c_output + (o_neurons.activation() * c_weight)
          }
        }

        // Got our output, now make that the input neuron's output
        this.layers(i).neurons(x).output = c_output
        println(this.layers(i).neurons(x).output)
      }
    }

    // Backwards propagation
    // Calculate the rate of change of each output with respect to
    // the error
  }
}

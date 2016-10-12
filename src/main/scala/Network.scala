package main.scala

import scala.util.Random

/**
  * Created by kendricktan on 11/10/16.
  */
class Network(inputSize: Int, var layers: Seq[Layers]) {
  // Out learning rate
  val learning_rate: Double = 0.1

  // Auxiliary constructor
  def this(inputSize: Int) = {
    this(inputSize, Seq(new Layers(inputSize)))
  }

  // Adds an additional layer to the existing network
  // Everything you add a layer, you also add a multi
  // dimensional array known as 'weight' to the array
  def addLayer(inputSize: Int): Unit = {
    // Construct a new layer and add a weight matrix to it
    // Randomize weight valies to be between -1.25 and 2.5
    val new_layer = new Layers(inputSize)
    new_layer.weights = Array.ofDim[Double](layers.last.length, inputSize)
    new_layer.weights = new_layer.weights.map(x => x.map(y => -1.25 + (Random.nextDouble() * 2.5)))

    // Create new layer and ammend it to network's layers
    layers = layers ++ Seq(new_layer)
  }

  // Will try and implement in matrix format if have time
  // Try and fit model to training data
  def fit(inputs: Array[Neurons], outputs: Array[Neurons]): Unit = {
    // Set the first layer to be input
    this.layers.head.neurons = inputs

    // Forward pass to store the outputs
    // Go through each layer, except for input
    for (i <- 1 until this.layers.length) {
      // for each layer for through each of
      // its neurons
      for (y <- 0 until this.layers(i).neurons.length) {
        // Variable to calculate the outputs
        var c_output: Double = 0.0

        // Iterate through the output neurons
        for (x <- 0 until this.layers(i - 1).neurons.length - 1) {
          // Get output neuron
          val o_neurons = this.layers(i - 1).neurons(x)

          // Current weight
          val c_weight = this.layers(i).weights(x)(y)

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
        println(c_output)
        this.layers(i).neurons(y).output = c_output
      }
    }

    // Backwards propagation
    // Calculate the rate of change of each output with respect to
    // the error

    // To 1 instead of 0 because input layer doesn't have any
    // weight matrix
    for (i <- this.layers.length-1 to 1 by -1) {
      // Back propagate through each neuron
      for (x <- 0 until this.layers(i - 1).neurons.length) {
        for (y <- 0 until this.layers(i).neurons.length) {
          println(x, y, i)
          // Back propagation has a different algorithm for
          // the output layer weightings

          // output layer algorithm: where l = last, and t_l = target
          // o_l = o_l-1 * delta_l
          // where delta_l = o_l*(1-o_l)*(o_l-t_l)

          // if cur layer is output layer
          if (this.layers(i) == this.layers.last) {
            // Output neuron
            val o_n = this.layers(i).neurons(y)

            // Neuron gradient
            val theta_n = o_n.activation() * (1.0 - o_n.activation()) * (o_n.activation() - outputs(y).activation())

            this.layers(i).weights(x)(y) = this.layers(i).weights(x)(y) - (this.learning_rate * this.layers(i).neurons(y).activation() * theta_n)
          }

          // hidden layer algorithm:
          // o_h = o_h * delta_h
          // where delta_h = o_h*(1-o_h)*sum(delta_h+1*w_h_h+1)
          // where w_h_h+1 is the weight matrix from layer h to layer h+1
          // if its an input layer  
          else {
            // Sum of the weights from the previous layer
            // and their delta, used to compute current layer's gradient
            val o_n = this.layers(i + 1).neurons
            val o_n_a = o_n.map(a => a.activation())
            val theta_n = (o_n_a, o_n_a.map(q => 1.0 - q), (o_n_a, outputs.map(a => a.activation())).zipped.map(_ - _)).zipped.map(_ * _ * _)
            val sum_theta_n = (theta_n, this.layers(i + 1).weights(y)).zipped.map(_ * _).sum

            // Current layer neurons and gradients
            val c_n = this.layers(i).neurons(x)
            val theta_c_n = c_n.activation() * (1.0 - c_n.activation()) * sum_theta_n
            this.layers(i).weights(x)(y) = this.layers(i).weights(x)(y) - (this.learning_rate * this.layers(i).neurons(y).activation() * theta_c_n)
          }
        }
      }
    }
  }
}

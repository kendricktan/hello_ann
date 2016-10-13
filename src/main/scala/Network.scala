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
    // Randomize weight valies to be between -1.25 and 1.25
    this.layers.last.weights = Array.ofDim[Double](this.layers.last.length, inputSize)
    this.layers.last.weights = this.layers.last.weights.map(x => x.map(y => 0.25 ))
    //this.layers.last.weights = this.layers.last.weights.map(x => x.map(y => -1.25 + (Random.nextDouble() * 2.5)))

    // Create new layer and ammend it to network's layers
    this.layers = this.layers ++ Seq(new Layers(inputSize))
  }

  // Will try and implement in matrix format if have time
  // Try and fit model to training data
  def fit(inputs: Array[Neurons], outputs: Array[Neurons]): Unit = {
    if (inputs.length != this.layers.head.neurons.length){
      throw new IllegalArgumentException("Inputs must be of the same size!")
    }

    this.layers.head.neurons = inputs

    // Propagate forward
    for (i <- 0 until this.layers.length - 1) {

      // Loop through next layer
      for (y <- this.layers(i + 1).neurons.indices) {
        // Neuron outputs to be stored
        var c_outputs = 0.0

        // Loop through current layer
        for (x <- this.layers(i).neurons.indices) {
          // If input layer we just want the outputs, not the activation
          if (i == 0) {
            c_outputs = c_outputs + (this.layers(i).weights(x)(y) * this.layers(i).neurons(x).output)
          }

          // Else we want the activation function
          else{
            c_outputs = c_outputs + (this.layers(i).weights(x)(y) * this.layers(i).neurons(x).activation())
          }
        }

        // Store it in the next layer's neuron
        this.layers(i+1).neurons(y).output = c_outputs
      }
    }


    // Backward propagation
    for (i <- this.layers.length-1 to 1 by -1){
      for (y <- this.layers(i-1).neurons.indices){
        for (x <- this.layers(i).neurons.indices){
          // If its a output layer we need to use a different algorithm
          if (i == this.layers.length-1){
            // Neuron activation
            val n_a = this.layers(i).neurons(x).activation()
            val theta_a = n_a * (1.0 - n_a) * (n_a - outputs(x).output)

            // Change weighting of neuron
            val theta_weight = -this.learning_rate*theta_a*this.layers(i-1).weights(y)(x)
            this.layers(i-1).weights(y)(x) += theta_weight
          }

          else{
            // We need data from previous layer (reversed) to
            // compute the gradient of the current layer
            // Neurons from the previous layer
            val n_p = this.layers(i+1).neurons
            val n_p_a = n_p.map(a => a.activation())
            val theta_p_a = (n_p_a, n_p_a.map(a => 1.0 - a), (n_p_a, outputs.map(a=>a.output)).zipped.map(_-_)).zipped.map(_*_*_)
            val theta_p_a_sum = theta_p_a.sum

            // Got values from previous layer,
            // Now compute for current layer
            val n_a = this.layers(i).neurons(x).activation()
            val theta_a = n_a * (1.0 - n_a) * theta_p_a_sum

            // Change weighting of neuron
            val theta_weight = -this.learning_rate*theta_a*this.layers(i-1).weights(y)(x)
            this.layers(i-1).weights(y)(x) += theta_weight
          }
        }
      }
    }

  }

  def predict(inputs: Array[Neurons]) : Array[Double] = {
    this.layers.head.neurons = inputs

    // Propagate forward
    for (i <- 0 until this.layers.length - 1) {

      // Loop through next layer
      for (y <- this.layers(i + 1).neurons.indices) {
        // Neuron outputs to be stored
        var c_outputs = 0.0

        // Loop through current layer
        for (x <- this.layers(i).neurons.indices) {

          // If input layer we just want the outputs, not the activation
          if (i == 0) {
            c_outputs = c_outputs + (this.layers(i).weights(x)(y) * this.layers(i).neurons(x).output)
          }

          // Else we want the activation function
          else{
            c_outputs = c_outputs + (this.layers(i).weights(x)(y) * this.layers(i).neurons(x).activation())
          }
        }

        // Store it in the next layer's neuron
        this.layers(i+1).neurons(y).output = c_outputs
      }
    }

    this.layers.last.neurons.map(a => a.activation())
  }

}

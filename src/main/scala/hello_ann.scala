package main.scala

/**
  * Created by kendricktan on 11/10/16.
  */

object hello_ann {
  def main(args: Array[String]) : Unit = {
    // Simple 3 layered ANN
    // With a hidden layer

    // Initialize our network
    // Input layer has 3 nodes
    var network = new Network(3)

    // Hidden layer consists of 5 nodes
    network.addLayer(5)

    // 1 node for output layer
    network.addLayer(1)

    println(network.layers(1).neuron(1).weight)
  }
}

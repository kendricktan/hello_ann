package main.scala

/**
  * Created by kendricktan on 11/10/16.
  */
object hello_ann {
  def main(args: Array[String]): Unit = {
    // Basic binary calculator
    // Do note that the input layers and output layers
    // defined here must be the same size
    val inputs = Array[Double](1.0, 1.0, 1.0)
    val outputs = Array[Double](7.0)

    // Simple 3 layered ANN
    // With a hidden layer
    // Initialize our network
    // Input layer has 3 nodes
    var network = new Network(3)

    // Hidden layer consists of 5 nodes
    network.addLayer(5)

    // 1 node for output layer
    network.addLayer(1)

    // Fit network to this output
    for (x <- 0 until 100) {
      println("Epoch: " + x)
      network.fit(inputs.map(x => new Neurons(x)), outputs.map(x => new Neurons(x)))
    }
  }
}

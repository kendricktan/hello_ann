package main.scala

/**
  * Created by kendricktan on 11/10/16.
  */
object hello_ann {
  def main(args: Array[String]): Unit = {
    // Basic NOT Neural Network
    val inputs = Array[Double](1.0, 0.0).map(x => new Neurons(x))
    val outputs = Array[Double](0.0, 1.0).map(x => new Neurons(x))

    // Simple 3 layered ANN
    // Input -> Hidden -> Output
    // Initialize our network
    // Input layer has 3 nodes
    var network = new Network(2)

    // Hidden layer consists of 5 nodes
    network.addLayer(5)

    // 2 node for output layer (same as input as we're just NOT'ing them)
    network.addLayer(2)

    // Fit network to this output
    for (x <- 0 to 10000) {
      network.fit(inputs, outputs)

      // Output
      if (x % 1000 == 0) {
        println("Epoch: " + x)
        println("Prediction: " + network.predict(inputs).mkString(" "))
        println("Loss: " + network.loss(inputs, outputs))
      }
    }
  }
}

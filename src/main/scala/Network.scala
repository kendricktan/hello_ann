package main.scala

/**
  * Created by kendricktan on 11/10/16.
  */
class Network (inputSize: Int, var layers: Seq[Layers]){
  // Auxiliary constructor
  def this(inputSize: Int) = {
    this(inputSize, Seq(new Layers(inputSize)))
  }

  // Adds an additional layer to the existing network
  def addLayer(inputSize: Int) : Unit = {
    // + 1 for bias layer
    layers = layers ++ Seq(new Layers(inputSize + 1))
  }
}

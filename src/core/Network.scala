package core

/**
  * Created by kendricktan on 11/10/16.
  */
class Network (inputSize: Int, var layers: Seq[Any]){
  // Auxiliary constructor
  def this(inputSize: Int) = {
    this(inputSize, Seq(new Layers(inputSize)))
  }

  // Adds an additional layer to the existing network
  def addLayer(inputSize: Int) : Unit = {
    layers = layers ++ Seq(new Layers(inputSize))
  }
}

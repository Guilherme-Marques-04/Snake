object Snake extends App {

  val apple: Int = 1
  val snakeHead: Int = 2
  val snake: Int = 3

  def createArray(x:Int, y:Int): Array[Array[Int]] ={
    val array: Array[Array[Int]] = Array.ofDim(x,y)
    array
  }
}
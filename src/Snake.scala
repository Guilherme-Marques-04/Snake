object Snake extends App {
  def generateSnake(board : Array[Array[Int]]) : Array[Array[Int]] = {

    var snakeSize : Int = 2
    var out : Array[Array[Int]] = board.clone

    // put the snake in the middle left of the board
    // body of the snake
    out(1)(out(0).length / 2) = 3

    // Head of the snake
    out(2)(out(0).length / 2) = 2

    return out
  }

  def moveSnake() : Unit = {

  }

  def createArray(x:Int, y:Int): Array[Array[Int]] ={
    val array: Array[Array[Int]] = Array.ofDim(x,y)
    array
  }

  // variables
  var snakeDirection = null // toDo
}
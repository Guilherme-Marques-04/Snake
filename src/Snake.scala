object Snake extends App {
  def generateSnake(board : Array[Array[Int]]) : Array[Array[Int]] = {

    var snakeSize : Int = 2
    var out : Array[Array[Int]] = board.clone

    // put the snake in the middle left of the board
    out(1)(out(0).length) = 3
    out(2)(out(0).length) = 2

    return out
  }
}
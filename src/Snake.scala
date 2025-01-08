import scala.util.Random
import hevs.graphics.FunGraphics
import java.awt.event.{KeyAdapter, KeyEvent}

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

  // Choose length of board
  var x: Int = Input.readInt()
  var y: Int = Input.readInt()
  var length: Int = x*y

  // Create the array
  def createArray(): Array[Array[Int]] = {
    if (x < 10 || y < 10) {
      x = 10
      y = 10
    } else if (x > 50 || y > 50) {
      x = 50
      y = 50
    }

    val board: Array[Array[Int]] = Array.ofDim(x, y)
    board
  }

  // variables
  var snakeDirection: Int = 0x27

  // generate random position of apple
  def generateApple(): Array[Array[Int]] = {
    var board: Array[Array[Int]] = generateSnake(createArray())
    while(x == 1) {
      var x: Int = 0
      var randomX: Int = Random.between(0, length)
      var randomY: Int = Random.between(0, length)
      if (board(randomX)(randomY) == 0) {
        board(randomX)(randomY) = -1
        x = 1
      } else {
        board(randomX)(randomY) = 0
      }
    }
    board
  }

  // test print board
  var b: Array[Array[Int]] = createArray()
  for(i <- b.indices){
    println("")
    for(j <- b(i).indices){
      print(b(i)(j))
    }
  }
}
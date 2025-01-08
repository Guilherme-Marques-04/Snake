import hevs.graphics.FunGraphics
import java.awt.Color
import scala.util.Random
import hevs.graphics.FunGraphics
import java.awt.event.{KeyAdapter, KeyEvent}

object Snake extends App {
  def generateSnake(board : Array[Array[Int]]) : Array[Array[Int]] = {

    var snakeSize : Int = 2
    var out : Array[Array[Int]] = board.clone
    // put the snake in the middle left of the board
    // body of the snake
    out(out(0).length / 2)(1) = 1
    // Head of the snake
    out(out(0).length / 2)(2) = 2

    return out
  }

  def moveSnake() : Unit = {

  }

  // length of board
  var x: Int = 25
  var y: Int = 25
  var length: Int = x*y

  // Create the array
  def createArray(): Array[Array[Int]] = {
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

  // display graphic
  def displayGame(): Unit = {
    val land: FunGraphics = new FunGraphics(650, 650)

    var posX: Int = -25
    var posY: Int = -25
    val a: Array[Array[Int]] = generateSnake(createArray())

    for(i <- a.indices){
      posY += 25
      posX = -25
      for(j <- a(i).indices){
        posX += 25
        if(a(i)(j) == 0){
          land.setColor(Color.blue)
          land.drawFillRect(posX, posY, posX+25, posY+25)
        } else if(a(i)(j) >= 1) {
          land.setColor(Color.green)
          land.drawFillRect(posX, posY, posX + 25, posY + 25)
        }
      }
    }
  }
  displayGame()
}
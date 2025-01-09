import hevs.graphics.FunGraphics
import java.awt.Color
import scala.util.Random
import hevs.graphics.FunGraphics
import java.awt.event.{KeyAdapter, KeyEvent}

object Snake extends App {
  // variables
  var snakeDirection: Int = 0x27
  var snakeHeadLocationX : Int = 0
  var snakeHeadLocationY : Int = 0

  def generateSnake(board : Array[Array[Int]]) : Array[Array[Int]] = {

    var snakeSize : Int = 2
    var out : Array[Array[Int]] = board.clone

    // put the snake in the middle left of the board
    // body of the snake
    out(out(0).length / 2)(1) = 1
    out(out(0).length / 2)(2) = 2
    // Head of the snake
    out(out(0).length / 2)(3) = 3

    return out
  }

  def moveSnake(land : FunGraphics, board : Array[Array[Int]]) : Unit = {
    val timer = new java.util.Timer()

    val task = new java.util.TimerTask {

      def run() = {
        snakeDirection match {
          case 0x25 // left
          => board(snakeHeadLocationY - 1)(snakeHeadLocationX)

          case 0x26 // up
          => board(snakeHeadLocationY)(snakeHeadLocationX - 1)

          case 0x27 // right
          => board(snakeHeadLocationY + 1)(snakeHeadLocationX)

          case 0x28 // down
          => board(snakeHeadLocationY)(snakeHeadLocationX + 1)
        }

        land.setKeyManager(new KeyAdapter(){
          override def keyPressed(e: KeyEvent): Unit = {
            if (e.getKeyCode == KeyEvent.VK_LEFT && snakeDirection != KeyEvent.VK_RIGHT) snakeDirection = 0x25
            if (e.getKeyCode == KeyEvent.VK_UP && snakeDirection != KeyEvent.VK_DOWN) snakeDirection  = 0x26
            if (e.getKeyCode == KeyEvent.VK_RIGHT && snakeDirection != KeyEvent.VK_LEFT) snakeDirection = 0x27
            if (e.getKeyCode == KeyEvent.VK_DOWN && snakeDirection != KeyEvent.VK_UP) snakeDirection = 0x28
          }
        })
      }
    }
    timer.schedule(task, 1000L, 1000L)
  }

  // length of board
  var x: Int = 25
  var y: Int = 25

  // Create the array
  def createArray(): Array[Array[Int]] = {
    val board: Array[Array[Int]] = Array.ofDim(x, y)
    board
  }

  // generate random position of apple
  def generateApple(): Array[Array[Int]] = {
    var board: Array[Array[Int]] = generateSnake(createArray())
    var x: Int = 1
    while(x == 1) {
      var randomX: Int = Random.between(0, x)
      var randomY: Int = Random.between(0, y)
      if (board(randomX)(randomY) == 0) {
        board(randomX)(randomY) = -1
        x = 0
      }
    }
    board
  }

  // display graphic
  def displayGame(): Unit = {
    val land: FunGraphics = new FunGraphics(650, 650, "Snake")
    var table : Array[Array[Int]] = createArray()
    table = generateSnake(table)

    var posX: Int = -25
    var posY: Int = -25
    var a: Array[Array[Int]] = generateApple()

    for(i <- a.indices){
      println("")
      posY += 25
      posX = -25
      for(j <- a(i).indices){
        print(a(i)(j))
        posX += 25
        if(a(i)(j) == 0){
          land.setColor(Color.blue)
          land.drawFillRect(posX, posY, posX+25, posY+25)
        } else if(a(i)(j) >= 1) {
          land.setColor(Color.green)
          land.drawFillRect(posX, posY, posX + 25, posY + 25)
        } else if(a(i)(j) == -1){
          land.setColor(Color.red)
          land.drawFillRect(posX, posY, posX + 25, posY + 25)
        }
      }
    }
    // variables
    snakeHeadLocationX = table(0).length / 2
    snakeHeadLocationY = 2
  }

  displayGame()
}
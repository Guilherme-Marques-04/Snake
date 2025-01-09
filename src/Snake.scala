import hevs.graphics.FunGraphics
import java.awt.Color
import scala.util.Random
import hevs.graphics.FunGraphics
import java.awt.event.{KeyAdapter, KeyEvent}

object Snake extends App {
  // variables
  val windowSizeX: Int = 650
  val windowSizeY: Int = 650
  val window: FunGraphics = new FunGraphics(windowSizeX, windowSizeY, "Snake")

  // Create the array
  val boardSizeX: Int = 25
  val boardSizeY: Int = 25
  var board: Array[Array[Int]] = Array.ofDim(boardSizeX, boardSizeY)

  //generate snake
  board = generateSnake(board)

  var snakeSize: Int = 2

  var snakeDirection: Int = 0x27
  var snakeHeadLocationX: Int = board(0).length / 2
  var snakeHeadLocationY: Int = 2

  def generateSnake(board: Array[Array[Int]]): Array[Array[Int]] = {

    snakeSize = 2
    var out: Array[Array[Int]] = board.clone

    // put the snake in the middle left of the board
    // body of the snake
    out(out(0).length / 2)(1) = 1
    out(out(0).length / 2)(2) = 2
    // Head of the snake
    out(out(0).length / 2)(3) = 3

    return out
  }

  def moveSnake(land: FunGraphics, board: Array[Array[Int]]): Unit = {
    val timer = new java.util.Timer()

    val task = new java.util.TimerTask {

      def run() = {
        snakeDirection match {
          case 0x25 // left
          => snakeHeadLocationY -= 1

          case 0x26 // up
          => snakeHeadLocationX -= 1

          case 0x27 // right
          => snakeHeadLocationY += 1

          case 0x28 // down
          => snakeHeadLocationX += 1
        }

        checkSnakeEat()

        board(snakeHeadLocationX)(snakeHeadLocationY) += 1

        land.setKeyManager(new KeyAdapter() {
          override def keyPressed(e: KeyEvent): Unit = {
            if (e.getKeyCode == KeyEvent.VK_LEFT && snakeDirection != KeyEvent.VK_RIGHT) snakeDirection = 0x25
            if (e.getKeyCode == KeyEvent.VK_UP && snakeDirection != KeyEvent.VK_DOWN) snakeDirection = 0x26
            if (e.getKeyCode == KeyEvent.VK_RIGHT && snakeDirection != KeyEvent.VK_LEFT) snakeDirection = 0x27
            if (e.getKeyCode == KeyEvent.VK_DOWN && snakeDirection != KeyEvent.VK_UP) snakeDirection = 0x28
          }
        })

        // If the snake eat the apple
        if (snakeHeadLocationX )
      }
    }
    timer.schedule(task, 1000L, 1000L)
  }

  def checkSnakeEat() : Unit = {

  }

  // generate random position of apple
  def generateApple(): Unit = {
    // break while statement if apple isn't generate in the snake
    var isAppleGood: Boolean = true
    while (isAppleGood) {
      var randomX: Int = Random.between(0, boardSizeX)
      var randomY: Int = Random.between(0, boardSizeY)
      if (board(randomX)(randomY) == 0) {
        board(randomX)(randomY) = -1
        isAppleGood = false
      }
    }
  }

  // display graphic
  def displayGame(): Unit = {

    var posX: Int = -25
    var posY: Int = -25

    for (i <- board.indices) {
      println("")
      posY += 25
      posX = -25
      for (j <- board(i).indices) {
        print(board(i)(j))
        posX += 25
        if (board(i)(j) == 0) {
          window.setColor(Color.blue)
          window.drawFillRect(posX, posY, posX + 25, posY + 25)
        } else if (board(i)(j) >= 1) {
          window.setColor(Color.green)
          window.drawFillRect(posX, posY, posX + 25, posY + 25)
        } else if (board(i)(j) == -1) {
          window.setColor(Color.red)
          window.drawFillRect(posX, posY, posX + 25, posY + 25)
        }
      }
    }
  }
  displayGame()
}
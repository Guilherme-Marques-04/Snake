import hevs.graphics.FunGraphics
import java.awt.Color
import scala.util.Random
import java.awt.event.{KeyAdapter, KeyEvent}

object Snake extends App {
  // variables
  val windowSizeX: Int = 600
  val windowSizeY: Int = 650
  val window: FunGraphics = new FunGraphics(windowSizeX, windowSizeY, "Snake")

  // Create the array
  val boardSizeX: Int = 25
  val boardSizeY: Int = 25
  var board: Array[Array[Int]] = Array.ofDim(boardSizeX, boardSizeY)

  // score
  var score: Int = -1

  var snakeSize: Int = 2

  var snakeDirection: Int = 0x27
  var snakeHeadLocationX: Int = board(0).length / 2
  var snakeHeadLocationY: Int = 3

  // Location of the apple
  var randomX: Int = 0
  var randomY: Int = 0

  var gameOver: Boolean = false

  val timer = new java.util.Timer()

  def generateSnake(): Unit = {

    snakeSize = 3

    // put the snake in the middle left of the board
    // body of the snake
    board(board(0).length / 2)(1) = 1
    board(board(0).length / 2)(2) = 2
    board(board(0).length / 2)(3) = 3
  }

  def moveSnake(): Unit = {
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

        checkSnakeInteraction()

        if (gameOver == false) {

          board(snakeHeadLocationX)(snakeHeadLocationY) = snakeSize + 1

          window.setKeyManager(new KeyAdapter() {
            override def keyPressed(e: KeyEvent): Unit = {
              if (e.getKeyCode == KeyEvent.VK_LEFT && snakeDirection != KeyEvent.VK_RIGHT) snakeDirection = 0x25
              else if (e.getKeyCode == KeyEvent.VK_UP && snakeDirection != KeyEvent.VK_DOWN) snakeDirection = 0x26
              else if (e.getKeyCode == KeyEvent.VK_RIGHT && snakeDirection != KeyEvent.VK_LEFT) snakeDirection = 0x27
              else if (e.getKeyCode == KeyEvent.VK_DOWN && snakeDirection != KeyEvent.VK_UP) snakeDirection = 0x28
            }
          })

          for (i <- board.indices) {
            for (j <- board.indices) {
              if (board(i)(j) > 0) {
                board(i)(j) -= 1
              }
            }
          }
          displayGame()
        }
      }
    }
    timer.schedule(task, 100L, 100L)
  }

  def checkSnakeInteraction(): Unit = {
    // Check if the snake eat the apple
    if (snakeHeadLocationX == randomX && snakeHeadLocationY == randomY) {
      generateApple()
      snakeSize += 1
    }

    if (snakeHeadLocationX >= 0 && snakeHeadLocationY >= 0) {
      // Check if the snake hit himself
      if (board(snakeHeadLocationX)(snakeHeadLocationY) > 0) {
        gameOver = true
        timer.cancel()
        window.drawString(160, 275, "Game over !", fontSize = 50)
      }
    }

    // Check if the snake hit the walls
    if (snakeHeadLocationX >= boardSizeX - 1 || snakeHeadLocationX < 0 || snakeHeadLocationY >= boardSizeY - 1 || snakeHeadLocationY < 0) {
      gameOver = true
      timer.cancel()
      window.drawString(160, 275, "Game over !",fontSize = 50)
    }
  }

  // generate random position of apple
  def generateApple(): Unit = {
    // break while statement if apple isn't generate in the snake
    var isAppleGood: Boolean = true
    while(isAppleGood) {
      randomX = Random.between(0, boardSizeX - 1)
      randomY = Random.between(0, boardSizeY - 1)
      if (board(randomX)(randomY) == 0) {
        board(randomX)(randomY) = -1
        isAppleGood = false
        score += 1
      }
    }
  }

  // display graphic
  def displayGame(): Unit = {

    var posX: Int = -25
    var posY: Int = 25

    window.frontBuffer.synchronized {
      window.setColor(Color.white)
      window.drawFillRect(0,0,600,50)
      window.drawString(25, 25, "Score: " + score)

      for (i <- board.indices) {
        posY += 25
        posX = -25
        for (j <- board(i).indices) {
          posX += 25
          if (board(i)(j) == 0) {
            window.setColor(Color.lightGray)
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
  }
  generateSnake() // Generate the snake
  generateApple() // Generate for the first time
  moveSnake()
}
import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap

import java.awt.Color
import scala.util.Random
import java.awt.event.{KeyAdapter, KeyEvent, MouseAdapter, MouseEvent}

object Snake extends App {
  // variables
  val windowSizeX: Int = 600
  val windowSizeY: Int = 650
  val window: FunGraphics = new FunGraphics(windowSizeX, windowSizeY, "Retro Snake")

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

  var timer = new java.util.Timer()

  def showMenu() : Unit = {

    // Show image
    window.drawTransformedPicture(windowSizeX / 2,windowSizeY / 4,0,0.60,"/res/img/SnakeTitle.png")

    // Draw buttons
    window.setColor(Color.gray)
    window.drawFillRect(625 / 2 - 160, windowSizeY / 10 * 5,300,75)
    window.setColor(Color.WHITE)
    window.drawString(625 / 2 - 160 + 100, windowSizeY / 10 * 5 + 52, "Play", fontSize = 50)
    window.setColor(Color.red)
    window.drawFillRect(625 / 2 - 160, windowSizeY / 8 * 5,300,75)
    window.setColor(Color.WHITE)
    window.drawString(625 / 2 - 160 + 100, windowSizeY / 8 * 5 + 52, "Quit", fontSize = 50)

    // This will handle the mouse

    window.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {
        val event = e

        // Get the mouse position from the event
        val posClickX = event.getX
        val posClickY = event.getY

        // Check if play button is clicked
        if (posClickX >= 625 / 2 - 160
          && posClickX <= 625 / 2 - 160 + 300
          && posClickY >= windowSizeY / 10 * 5
          && posClickY <= windowSizeY / 10 * 5 + 75) {
          generateSnake() // Generate the snake
          generateApple() // Generate for the first time
          moveSnake()
        }

        // Check if quit button is clicked
        if (posClickX >= 625 / 2 - 160
          && posClickX <= 625 / 2 - 160 + 300
          && posClickY >= windowSizeY / 8 * 5
          && posClickY <= windowSizeY / 8 * 5 + 75) {
          System.exit(0)
        }
      }
    })

    window.setKeyManager(new KeyAdapter() {
      override def keyPressed(e: KeyEvent): Unit = {
        if (gameOver) {
          e.getKeyCode match {
            case KeyEvent.VK_R => resetGame() // Redémarrer le jeu
            case KeyEvent.VK_Q => System.exit(0) // Quitter le jeu
            case _ => // Rien faire pour d'autres touches
          }
        }
      }
    })

    window.setKeyManager(new KeyAdapter() {
      override def keyPressed(e: KeyEvent): Unit = {
        if (!gameOver) {
          e.getKeyCode match {
            case KeyEvent.VK_LEFT if snakeDirection != KeyEvent.VK_RIGHT => snakeDirection = 0x25
            case KeyEvent.VK_UP if snakeDirection != KeyEvent.VK_DOWN => snakeDirection = 0x26
            case KeyEvent.VK_RIGHT if snakeDirection != KeyEvent.VK_LEFT => snakeDirection = 0x27
            case KeyEvent.VK_DOWN if snakeDirection != KeyEvent.VK_UP => snakeDirection = 0x28
            case _ => // Rien faire pour d'autres touches
          }
        }
      }
    })
  }

  def resetGame(): Unit = {
    // Réinitialisez toutes les variables de jeu
    board = Array.ofDim[Int](boardSizeX, boardSizeY)
    snakeSize = 3
    snakeDirection = 0x27
    snakeHeadLocationX = board(0).length / 2
    snakeHeadLocationY = 3
    score = -1
    gameOver = false

    // Annulez l'ancien timer et créez-en un nouveau
    timer.cancel()
    timer = new java.util.Timer()

    if (!gameOver) {
      generateSnake() // Générer le serpent
      generateApple() // Générer la première pomme
      moveSnake() // Démarrer le jeu
    }
  }



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
          case 0x25 => snakeHeadLocationY -= 1
          case 0x26 => snakeHeadLocationX -= 1
          case 0x27 => snakeHeadLocationY += 1
          case 0x28 => snakeHeadLocationX += 1
        }

        checkSnakeInteraction()

        if (!gameOver) {
          board(snakeHeadLocationX)(snakeHeadLocationY) = snakeSize + 1

          for (i <- board.indices) {
            for (j <- board(i).indices) {
              if (board(i)(j) > 0) {
                board(i)(j) -= 1
              }
            }
          }
          displayGame()
        }
      }
    }

    // Planifiez la tâche si le jeu n'est pas déjà en cours
    timer.schedule(task, 100L, 100L)
  }


  def checkSnakeInteraction(): Unit = {
    // Vérifiez si le serpent est en dehors des limites
    if (snakeHeadLocationX < 0 || snakeHeadLocationX >= boardSizeX - 1 ||
      snakeHeadLocationY < 0 || snakeHeadLocationY >= boardSizeY - 1) {
      gameOver = true
      timer.cancel()
      window.drawString(160, 275, "Game over!", fontSize = 50)
      window.drawString(160, 325, "Press 'R' to Restart or 'Q' to Quit", fontSize = 20)
      return
    }

    // Vérifiez si le serpent mange la pomme
    if (snakeHeadLocationX == randomX && snakeHeadLocationY == randomY) {
      generateApple()
      snakeSize += 1
    }

    // Vérifiez si le serpent se mord lui-même
    if (board(snakeHeadLocationX)(snakeHeadLocationY) > 0) {
      gameOver = true
      timer.cancel()
      window.drawString(160, 275, "Game over!", fontSize = 50)
      window.drawString(160, 325, "Press 'R' to Restart or 'Q' to Quit", fontSize = 20)
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

  showMenu()
}
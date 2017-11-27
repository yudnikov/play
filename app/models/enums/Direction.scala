package models.enums

abstract class Direction(val code: String) {
  Direction.register(this)
}

object Direction {
  private var values: Map[String, Direction] = Map()
  def register(direction: Direction): Unit = values = values + (direction.code -> direction)
  def of(code: String): Direction = values(code)
  case object Plus extends Direction("+")
  case object Minus extends Direction("-")
}
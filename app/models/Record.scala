package models

import enums.Direction
import forms.RecordForm.RecordData
import models.core.{Manager, Model}
import org.joda.time.DateTime

case class Record(direction: Direction, date: DateTime, amount: BigDecimal)(val id: Long = Record.count) extends Model[Long](Record) {
  lazy val isPlus: Boolean = direction == Direction.Plus
  lazy val isMinus: Boolean = direction == Direction.Minus
}

object Record extends Manager[Long, Record] {
  /*Record(Direction.Plus, new DateTime() minusDays 2, 10000)()
  Record(Direction.Minus, new DateTime() minusDays 1, 250)()
  Record(Direction.Minus, new DateTime(), 120)()*/
  private var counter: Long = 0L
  def count: Long = {
    counter = counter + 1
    counter
  }
  def apply(data: RecordData)(id: Long): Record = {
    val direction = Direction.of(data.direction)
    val date = new DateTime(data.date)
    new Record(direction, date, data.amount)(id)
  }
}
package forms

import java.util.Date

import play.api.data.Form
import play.api.data.Forms._

object RecordForm {

  case class RecordData(direction: String, date: Date, amount: BigDecimal)

  val form = Form(
    mapping(
      "direction" -> text,
      "date" -> date,
      "amount" -> bigDecimal(10, 2)
    )(RecordData.apply)(RecordData.unapply)
  )

}

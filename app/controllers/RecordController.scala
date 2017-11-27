package controllers

import javax.inject.Inject

import forms.RecordForm
import forms.RecordForm.RecordData
import models.Record
import models.enums._
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import play.api.data.Form
import play.api.mvc.{AnyContent, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}

class RecordController @Inject()(
  cc: MessagesControllerComponents
) extends MessagesAbstractController(cc) {

  implicit val formatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")

  def list = Action {
    Ok(views.html.index("")(views.html.record.list(Record.list.sortBy(_.date.getMillis))))
  }

  def edit(id: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    val record = Record.get(id)
    val form = RecordForm.form.bind(
      Map(
        "direction" -> record.direction.code,
        "date" -> formatter.print(record.date),
        "amount" -> record.amount.toString
      )
    )
    Ok(views.html.index(""){
      views.html.record.recordForm(form, routes.RecordController.update(record.id), allowDirection = true)
    })
  }

  def remove(id: Long) = Action {
    Ok(s"remove record $id")
  }

  def createProfit = Action { implicit request: MessagesRequest[AnyContent] =>
    val id = Record.count
    val form = RecordForm.form.bind(
      Map(
        "id" -> id.toString,
        "direction" -> Direction.Plus.code,
        "date" -> formatter.print(new DateTime()),
      )
    )
    Ok(views.html.index("")(views.html.record.recordForm(form, routes.RecordController.update(id))))
  }

  def createConsume = Action { implicit request: MessagesRequest[AnyContent] =>
    val id = Record.count
    val form = RecordForm.form.bind(
      Map(
        "id" -> id.toString,
        "direction" -> Direction.Minus.code,
        "date" -> formatter.print(new DateTime()),
      )
    )
    Ok(views.html.index("")(views.html.record.recordForm(form, routes.RecordController.update(id))))
  }

  def view(id: Long) = Action {
    Ok(s"view record $id")
  }

  def update(id: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[RecordForm.RecordData] =>
      BadRequest("error!")
    }
    val successFunction = { data: RecordForm.RecordData =>
      Record(data)(id)
      Redirect(routes.RecordController.list())
    }
    val formValidationResult = RecordForm.form.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }

}

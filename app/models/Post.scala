package models

import models.core.{Manager, Model}

case class Post(header: String, content: String)(val id: Long) extends Model[Long](Post)

object Post extends Manager[Long, Post] {
  Post("Internal jew has woke up", "From now we are starting to count our money! It will be funny, I think!")(1)
}

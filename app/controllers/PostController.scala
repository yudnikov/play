package controllers

import javax.inject.Inject

import models.Post
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents}

class PostController @Inject()(
  cc: MessagesControllerComponents
) extends MessagesAbstractController(cc) {

  def list = Action {
    Ok(views.html.index("Posts"){views.html.post.list(Post.list)})
  }

}

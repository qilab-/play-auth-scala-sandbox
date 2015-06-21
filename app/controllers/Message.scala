package controllers

import jp.t2v.lab.play2.auth.AuthElement
import play.api.mvc._
import controllers.auth.AuthConfigImpl
import models.auth.Role
object Message
  extends Controller
  with AuthElement
  with AuthConfigImpl {

  def show() = StackAction(AuthorityKey -> Role.NormalUser) { implicit request =>
    val user = loggedIn
    Ok(views.html.message.show(user.name))
  }
}

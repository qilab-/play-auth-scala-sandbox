package controllers

import controllers.auth.AuthConfigImpl
import jp.t2v.lab.play2.auth.{AuthenticationElement, LoginLogout}
import models.auth.Account
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Sessions
  extends Controller
  with LoginLogout
  with AuthConfigImpl {

  implicit val auto = scalikejdbc.AutoSession

  val loginForm = Form {
    mapping(
      "email" -> email,
      "password" -> text
    )(Account.authenticate)(_.map(u => (u.email, "")))
      .verifying("invalid email or password", _.isDefined)
  }

  def login() = Action {
    Ok(views.html.session.login())
  }

  def authenticate() = Action.async { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest),
      user => gotoLoginSucceeded(user.get.id)
    )
  }

  def logout() = Action.async { implicit request =>
    gotoLogoutSucceeded
  }

}

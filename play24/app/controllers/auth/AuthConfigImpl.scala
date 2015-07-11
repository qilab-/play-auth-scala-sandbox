package controllers.auth

import jp.t2v.lab.play2.auth._
import models.auth.{Account, Role}
import play.api.mvc.{RequestHeader, Result}
import play.api.mvc.Results.{Forbidden, Ok, Unauthorized, MovedPermanently}
import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.classTag

trait AuthConfigImpl extends AuthConfig {

  override type Id = Long

  override type User = Account

  override type Authority = Role

  override val idTag = classTag[Id]

  override val sessionTimeoutInSeconds: Int = 3600

  override def resolveUser(id: Id)(implicit context: ExecutionContext): Future[Option[User]] = Future {
    Account.findById(id)
  }

  override def loginSucceeded(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] =
    Future.successful(MovedPermanently("/"))

  override def logoutSucceeded(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] =
    Future.successful(Ok(views.html.session.logout()))

  override def authenticationFailed(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] = {
    Future.successful {
      Unauthorized(views.html.error.unauthorized())
    }
  }

  def authorizationFailed(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] = ???

  override def authorizationFailed(request: RequestHeader, user: User, authority: Option[Authority])(implicit context: ExecutionContext): Future[Result] =
    Future.successful(Forbidden(views.html.error.forbidden()))

  override def authorize(user: User, authority: Authority)(implicit context: ExecutionContext): Future[Boolean] =
    Future.successful {
      (user.role, authority) match {
        case (Role.Administrator, _) => true
        case (Role.NormalUser, Role.NormalUser) => true
        case _ => false
      }
    }

  override lazy val tokenAccessor: TokenAccessor = new CookieTokenAccessor(
    cookieName = "pascn",
    cookieSecureOption = false,
    cookieHttpOnlyOption = true,
    cookieDomainOption = None,
    cookiePathOption = "/",
    cookieMaxAge = Some(sessionTimeoutInSeconds)
  )
}

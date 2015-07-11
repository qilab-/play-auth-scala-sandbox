package models.auth

import skinny.orm.SkinnyCRUDMapper
import scalikejdbc._

case class Account(
    id: Long,
    email: String,
    password: String,
    name: String,
    role: Role) {
  def isValidPassword(plainPassword: String): Boolean = {
    // TODO password.exists(BCrypt.checkpw(plainPassword, _))
    password == plainPassword
  }
}

object Account extends SkinnyCRUDMapper[Account] {
  override lazy val tableName = "accounts"
  override lazy val defaultAlias = createAlias("a")
  override def extract(rs: WrappedResultSet, n: ResultName[Account]): Account =
    autoConstruct(rs, n)

  def authenticate(email: String, password: String)(implicit session: DBSession): Option[Account] = {
    findByEmail(email)(session).filter { account =>
      account.isValidPassword(password)
    }
  }

  def findByEmail(email: String)(implicit session: DBSession): Option[Account] = {
    findBy(sqls.eq(defaultAlias.email, email))(session)
  }
}

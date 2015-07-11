package models.auth

import scalikejdbc.TypeBinder

sealed trait Role

object Role {

  case object Administrator extends Role
  case object NormalUser extends Role

  def valueOf(value: String): Role = value match {
    case "Administrator" => Administrator
    case "NormalUser" => NormalUser
  }

  implicit val typeBinder: TypeBinder[Role] = TypeBinder.string.map(valueOf)

}

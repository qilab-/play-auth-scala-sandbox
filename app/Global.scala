import play.api.mvc.WithFilters
import play.api.{Application, GlobalSettings}
import play.Play

object Global extends WithFilters() with GlobalSettings {

  override def onStart(app: Application) {
    super.onStart(app)

    skinny.DBSettings.initialize()
    if (Play.isDev()) {
      skinny.dbmigration.DBMigration.migrate()
    }
  }
}

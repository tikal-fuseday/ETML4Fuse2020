package controllers

import java.sql.DriverManager

import com.github.tototoshi.play2.json4s.Json4s
import com.typesafe.scalalogging.LazyLogging
import org.json4s.DefaultFormats

import scala.collection.mutable.ArrayBuffer

//import play.api.libs.json.Json
import javax.inject._
import org.json4s._

import play.api.libs.json._
import play.api.mvc._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
case class EtlDetails(sourceCatalog: String, destCatalog : String , sql : String, destTable : String)

@Singleton
class UserController @Inject()( json4s: Json4s,cc: ControllerComponents) extends AbstractController(cc) with LazyLogging{
  implicit val formats = new DefaultFormats {}
  var tokens = ArrayBuffer.empty[String]

  val url = "jdbc:presto://18.194.75.75:8080/"

  val connection = DriverManager.getConnection(url,"test",null)

  def etl() = Action(json4s.json) { implicit request =>

    val data = request.body.extract[EtlDetails]
    val url = s"jdbc:presto://18.194.75.75:8080/${data.sourceCatalog.replace('.','/')}"

    val connection = DriverManager.getConnection(url,"test",null)

    val query = s"create table  ${data.destCatalog}.${data.destTable} as  ${data.sql}"
    val stmt = connection.createStatement
    val rs = stmt.execute(query)
    Ok(Json.obj("ok"->"ok")).withHeaders(headers: _*)
  }


  def headers = List(
    "Access-Control-Allow-Origin" -> "*",
    "Access-Control-Allow-Methods" -> "GET, POST, OPTIONS, DELETE, PUT",
    "Access-Control-Max-Age" -> "3600",
    "Access-Control-Allow-Headers" -> "Origin, Content-Type, Accept, Authorization, token, otp",
    "Access-Control-Allow-Credentials" -> "true"
  )

  def options(p: String) = Action { request =>
    NoContent.withHeaders(headers: _*)
  }


}

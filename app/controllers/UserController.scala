package controllers

import java.sql.{DriverManager, ResultSet, SQLException}

import com.github.tototoshi.play2.json4s.Json4s
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime
import org.json4s.DefaultFormats
import java.sql.ResultSet

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Try

//import play.api.libs.json.Json
import javax.inject._
import org.json4s._
//import persistence._
import play.api.libs.json._
import play.api.mvc._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
case class EtlDetails(sourceCatalog: String, destCatalog : String , sql : String, destTable : String)

@Singleton
class UserController @Inject()( json4s: Json4s,cc: ControllerComponents) extends AbstractController(cc) with LazyLogging{


  import json4s.implicits._
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
    Ok(Json.obj("count"->1)).withHeaders(headers: _*)
  }

  def test() = Action { implicit request =>


    import java.sql.Connection
    import java.sql.DriverManager


    val query = "create table  postgres.public.test2 as  select a,b from postgres.public.test"

      val stmt = connection.createStatement
      val rs = stmt.execute(query)
//      var count = 0;
//      while ( {
//        rs.next
//      }) {
//        val a = rs.getString("a")
//       count +=1
//        System.out.println(a)
//      }
    if (stmt != null) stmt.close
    // properties
    //val url = "jdbc:presto://example.net:8080/hive/sales?user=test&password=secret&SSL=true"
    //val connection = DriverManager.getConnection(url)
    Ok(Json.obj("count"->1)).withHeaders(headers: _*)
   // val data = request.body.extract[LoginDetails]
//    if (Set("Loginworks","nab123").contains(data.password)){
//      val t = UUID.randomUUID().toString
//      tokens+=t
//      Ok(Json.obj("token"->t)).withHeaders(headers: _*)
//    }else {
//      BadRequest(Json.obj("error"-> "invalid credentials")).withHeaders(headers : _*)
//    }

  }

  def headers = List(
    "Access-Control-Allow-Origin" -> "*",
    "Access-Control-Allow-Methods" -> "GET, POST, OPTIONS, DELETE, PUT",
    "Access-Control-Max-Age" -> "3600",
    "Access-Control-Allow-Headers" -> "Origin, Content-Type, Accept, Authorization, token, otp",
    "Access-Control-Allow-Credentials" -> "true"
  )

  def services = List(
    "AC Service and Repair", "Refrigerator Repair", "Washing Machine Repair", "RO or Water Purifier Repair", "Geyser / Water Heater Repair", "Microwave Repair", "Chimney and Hob Servicing", "TV Repair", "Mobile Repair", "Laptop Repair", "iPhone, iPad, Mac Repair" ,
     "Salon at Home", "Spa at Home for Women", "Party Makeup Artist", "Bridal Makeup Artist", "Pre Bridal Beauty Packages", "Mehendi Artists" ,
      "Carpenter", "Plumber", "Electrician", "Pest Control", "Home Deep Cleaning", "Bathroom Deep Cleaning", "Sofa Cleaning", "Kitchen Deep Cleaning", "Carpet Cleaning", "Geyser / Water Heater Repair", "Washing Machine Repair", "AC Service and Repair", "Microwave Repair", "Refrigerator Repair", "Laptop Repair",  "RO or Water Purifier Repair", "TV Repair", "Chimney and Hob Servicing", "iPhone, iPad, Mac Repair" ,
      "CA for Small Business", "Web Designer & Developer", "Packers & Movers", "CA/CS for Company Registration", "CCTV Cameras and Installation", "Graphics Designer", "Lawyer", "Outstation Taxi", "CA for Income Tax Filing", "Visa Agency", "Real Estate Lawyer", "Corporate Event Planner", "GST Registration & Migration Services", "Vastu Shastra Consultants" ,
       "Astrologer", "Baby Portfolio Photographer", "Packers & Movers", "Monthly Tiffin Service", "Passport Agent", "Home Tutor", "Mathematics Tutor", "Commerce Home Tutor", "Outstation Taxi" ,
     "Birthday Party Planner", "Bridal Makeup Artist", "Wedding Planner", "Wedding Photographer", "Party Makeup Artist", "Pre-Wedding Shoot", "Event Photographer", "Mehendi Artists", "Astrologer", "Wedding Choreographe", "Party Caterer", "DJ", "Wedding Caterers", "Corporate Event Planner", "Pre Bridal Beauty Packages"

    )
  def options(p: String) = Action { request =>
    NoContent.withHeaders(headers: _*)
  }

  def safe(req : Request[JValue], c : => Result)  ={
    try {

        val token = req.headers.toMap("token")(0)
        if (!(tokens.contains(token))){
          throw new RuntimeException("authentication error")
        }
      c
    }
    catch {
      case e: Throwable  =>
        e.printStackTrace();
        BadRequest(Json.obj("error"-> e.getMessage)).withHeaders(headers : _*)
    }
  }
  def safeAny(req : Request[AnyContent], c : => Result)  ={
    try {

      val token = req.headers.toMap("token")(0)
      if (!(tokens.contains(token))){
        throw new RuntimeException("authentication error")
      }
      c
    }
    catch {
      case e: Throwable  =>
        e.printStackTrace();
        BadRequest(Json.obj("error"-> e.getMessage)).withHeaders(headers : _*)
    }
  }
}

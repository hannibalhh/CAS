
import java.util.Date
import play.api.libs.json.JsObject
import com.googlecode.protobuf.format.JsonFormat
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage._
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage.GeoCoordinates
import org.haw.cas.web.model.needs.NeedsComponent
import play.api.libs.json.Json

object NeedsDataTest extends App {
  val geo1 = GeoCoordinates.newBuilder().setLatitude(9.993333f).setLongitude(9.993333f).build()
  val geo2 = GeoCoordinates.newBuilder().setLatitude(10.993333f).setLongitude(5.993333f).build()
  val acco1 = Accomodation.newBuilder.setAuthor("ich").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht").setGeo(geo1).build()
  val acco2 = Accomodation.newBuilder.setAuthor("ich2").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht2").setGeo(geo2).build()
  val helper1 = Helper.newBuilder.setAuthor("ich3").setCreationTime(System.currentTimeMillis()).setMessage("meine nachricht3").setGeo(geo2).build()

  val needs = Needs.newBuilder.addAccomodation(acco1).addAccomodation(acco2).addHelper(helper1).build()
  
//  println(needs)
//  println(JsonFormat.printToString(needs))
//  println(JsObject(Seq("needs" -> Json.parse(JsonFormat.printToString(needs)))))
//  println(NeedsComponent.converts(needs))
}
package blueeyes.persistence.mongo

import org.spex.Specification
import MongoQueryBuilder._
import MongoImplicits._
import blueeyes.json.JPathImplicits._
import org.mockito.Mockito.{times, when}
import org.mockito.Mockito
import blueeyes.persistence.mongo.json.MongoJson._
import blueeyes.json.JsonAST._

class SelectOneQueryBehaviourSpec extends Specification {
  private val collection  = mock[DatabaseCollection]

  private val keys     = jObject2MongoObject(JObject(JField("foo", JInt(1)) :: JField("bar", JInt(1)) :: Nil))
  private val filter   = jObject2MongoObject(JObject(JField("name", JString("Joe")) :: Nil))
  private val sort     = Some(jObject2MongoObject(JObject(JField("name", JInt(-1)) :: Nil)))
  private val jObject  = JObject(JField("address", JObject( JField("city", JString("London")) :: JField("street", JString("Regents Park Road")) ::  Nil)) :: Nil)
  private val jObject1 = JObject(JField("address", JObject( JField("city", JString("London")) :: JField("street", JString("Regents Park Road 1")) ::  Nil)) :: Nil)

  "Call collection method" in{
    import MongoFilterImplicits._

    when(collection.select(keys, filter, sort, None, Some(1))).thenReturn(jObject2MongoObject(jObject) :: jObject2MongoObject(jObject1) :: Nil)

    val query  = selectOne("foo", "bar").from("collection").where("name" === "Joe").sortBy("name" <<)
    val result = query(collection)

    Mockito.verify(collection, times(1)).select(keys, filter, sort, None, Some(1))

    result mustEqual(Some(jObject))
  }

  "Call collection method with dummy JObject when filter is not specified" in{
    import MongoFilterImplicits._
    
    when(collection.select(keys, jObject2MongoObject(JObject(Nil)), None, None, Some(1))).thenReturn(jObject2MongoObject(jObject1) :: Nil)

    val query  = selectOne("foo", "bar").from("collection")
    val result = query(collection)

    Mockito.verify(collection, times(1)).select(keys, jObject2MongoObject(JObject(Nil)), None, None, Some(1))

    result mustEqual(Some(jObject1))
  }

}
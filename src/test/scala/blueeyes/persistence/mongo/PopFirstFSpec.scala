package blueeyes.persistence.mongo

import org.specs2.mutable.Specification
import UpdateFieldFunctions._

class PopFirstFSpec extends Specification{
  "fuse applies pop to set update" in {
    PopFirstF("n").fuseWith(SetF("n", MongoPrimitiveArray(MongoPrimitiveString("foo") :: MongoPrimitiveString("bar") :: Nil))) mustEqual(Some(SetF("n", MongoPrimitiveArray(MongoPrimitiveString("bar") :: Nil))))
  }
  "fuse with popLast leaves popFirst" in {
    PopFirstF("n").fuseWith(PopLastF("n")) mustEqual(Some(PopFirstF("n")))
  }
  "fuse with popFirst leaves popFirst" in {
    PopFirstF("n").fuseWith(PopFirstF("n")) mustEqual(Some(PopFirstF("n")))
  }
}
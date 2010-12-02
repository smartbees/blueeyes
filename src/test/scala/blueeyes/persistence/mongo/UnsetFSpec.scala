package blueeyes.persistence.mongo

import org.spex.Specification

class UnsetFSpec extends Specification{
  "fuse does not change update" in {
    import MongoImplicits._

    UnsetF("n").fuseWith(SetF("n", 2)) mustEqual(Some(UnsetF("n")))
  }
}
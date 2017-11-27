package models.core

abstract class Model[K](manager: Manager[K, Model[K]]) {
  val id: K
  manager.update(this)
}

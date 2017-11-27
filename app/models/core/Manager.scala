package models.core

trait Manager[K, +T <: Model[K]] {
  private[this] var models: Map[K, T] = Map()
  def get(id: K): T = models(id)
  def update(model: Model[K]): Unit = models = models + (model.id -> model.asInstanceOf[T])
  def list: List[T] = models.values.toList
}

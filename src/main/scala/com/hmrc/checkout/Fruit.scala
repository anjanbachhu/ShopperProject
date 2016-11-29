package com.hmrc.checkout


trait Fruit {
  def SellingPrice: Double
}

object Orange extends Fruit {
  override def SellingPrice: Double = 0.25
}

object Apple extends Fruit {
  override def SellingPrice: Double = 0.60
}
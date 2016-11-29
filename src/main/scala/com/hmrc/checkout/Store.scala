package com.hmrc.checkout


object Store {
  def cartAmount(cart: List[Fruit]): Double = cart.foldLeft(0.0)((x, fruit) => x + fruit.SellingPrice)
}

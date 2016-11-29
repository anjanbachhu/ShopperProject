package com.hmrc.checkout

object OffersList {

  type Offer = List[Fruit] => Double

  val OrangesOffer: Offer = (cart) => {
    val oranges = cart.collect{case o @ Orange => o}.size
    oranges / 3 * Orange.SellingPrice
  }

  val ApplesOffer: Offer = (cart) => {
    val apples = cart.collect{case a @ Apple => a}.size
    apples / 2 * Apple.SellingPrice
  }
}

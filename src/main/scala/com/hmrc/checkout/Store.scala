package com.hmrc.checkout

import com.hmrc.checkout.OffersList.Offer

object Store {
  def cartAmount(cart: List[Fruit])(offers: List[Offer] = List.empty[Offer]): Double = {
    val fullPrice = cart.foldLeft(0.0)((noOfFts, fruit) => noOfFts +  fruit.SellingPrice)
    if(offers.isEmpty)
      fullPrice
    else
      offers.foldLeft(fullPrice)((noOfFts, offer) => noOfFts - offer(cart))
  }
}

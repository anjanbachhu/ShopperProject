package com.hmrc.checkout

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class StoreTest extends FlatSpec with Matchers with BeforeAndAfter {

  import ExecuteTests._
  import Store.cartAmount

  //Apples
  it should "pass when cart contain 5 apples and retun the correct price" in {
    val totAmount = cartAmount(_Apples_5)
    totAmount shouldBe _Apples_5_Price
  }
  //Oranges
  it should "pass when cart contain 5 oranges and retun the correct price" in {
    val totAmount = cartAmount(_Oranges_5)
    totAmount shouldBe _Oranges_5_Price
  }
  //Cart contain mix fruits
  it should "pass when cart contain 5 apples, 5 oranges" in {
    val totAmount = cartAmount(_Apples_5_Oranges_5)
    ExecuteTests.bigDecimalFormatter(totAmount) shouldBe _Apples_5_Oranges_5_Price
  }
  //empty cart
  it should "return zero when the cart is empty" in {
    val totAmount = cartAmount(List.empty[Fruit])
    totAmount shouldBe 0.0
  }
}

object ExecuteTests {

  def bigDecimalFormatter(x: Double) = {
    BigDecimal(x).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  val _Apples_5 = List.fill(5)(Apple)
  val _Apples_5_Price = bigDecimalFormatter(5 * Apple.SellingPrice)
  val _Oranges_5 = List.fill(5)(Orange)
  val _Oranges_5_Price = bigDecimalFormatter(5 * Orange.SellingPrice)
  val _Apples_5_Oranges_5 = Apple :: Apple :: Apple :: Apple :: Apple :: Orange :: Orange :: Orange :: Orange :: Orange :: Nil
  val _Apples_5_Oranges_5_Price = bigDecimalFormatter(5 * Apple.SellingPrice + 5 * Orange.SellingPrice)
  val EmptyCart = List.empty[Fruit]
}
package com.hmrc.checkout

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import com.hmrc.checkout.OffersList._

class StoreTest extends FlatSpec with Matchers with BeforeAndAfter {

  import ExecuteTests._
  import Store.cartAmount

  //Apples
  it should "pass when cart contain 5 apples and retun the correct price" in {
    val totAmount = cartAmount(_Apples_5)()
    totAmount shouldBe _Apples_5_Price
  }
  //Oranges
  it should "pass when cart contain 5 oranges and retun the correct price" in {
    val totAmount = cartAmount(_Oranges_5)()
    totAmount shouldBe _Oranges_5_Price
  }
  //Cart contain mix fruits
  it should "pass when cart contain 5 apples, 5 oranges" in {
    val totAmount = cartAmount(_Apples_5_Oranges_5)()
    ExecuteTests.bigDecimalFormatter(totAmount) shouldBe _Apples_5_Oranges_5_Price
  }
  //empty cart
  it should "return zero when the cart is empty" in {
    val totAmount = cartAmount(List.empty[Fruit])()
    totAmount shouldBe 0.0
  }

  //Apples Offers
  it should "pass when cart contain 2 apples and offers - 'Buy one, get one free offer'" in {
    val totAmount = cartAmount(_Apples_2)(ApplesOffer :: Nil)
    totAmount shouldBe _Apples_1_Price
  }

  it should "pass when cart contain 3 apples and offers - 'Buy one, get one free offer'" in {
    val totAmount = cartAmount(_Apples_3)(ApplesOffer :: Nil)
    ExecuteTests.bigDecimalFormatter(totAmount) shouldBe _Apples_2_Price
  }

  //Oranges - Offers
  it should "pass when cart contain 3 oranges and offers - offer '3 for the price of 2 offer'" in {
    val totAmount = cartAmount(_Oranges_3)(OrangesOffer :: Nil)
    totAmount shouldBe _Oranges_2_Price
  }

  it should "pass when cart contain 4 oranges and offers - offer '3 for the price of 2 offer'" in {
    val totAmount = cartAmount(_Oranges_4)(OrangesOffer :: Nil)
    ExecuteTests.bigDecimalFormatter(totAmount) shouldBe _Oranges_3_Price
  }

  //Cart contain mix fruits - offers
  it should "pass when cart contain 2 apples, 3 oranges and offers - both offers" in {
    val totAmount = cartAmount(_Apples_2_Oranges_3)(ApplesOffer :: OrangesOffer :: Nil)
    ExecuteTests.bigDecimalFormatter(totAmount) shouldBe _Apples_1_Oranges_2_Price
  }

  //empty cart and offer applied
  it should "return zero when cart is empty and offers is applied" in {
    val totAmount = cartAmount(List.empty[Fruit]) (OrangesOffer :: ApplesOffer :: Nil)
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

  //Apples offers testing
  val _Apples_2 = List.fill(2)(Apple)
  val _Apples_3 = List.fill(3)(Apple)
  val _Apples_1_Price = bigDecimalFormatter(Apple.SellingPrice)
  val _Apples_2_Price = bigDecimalFormatter(2 * Apple.SellingPrice)

  //Oranges offers testing
  val _Oranges_3 = List.fill(3)(Orange)
  val _Oranges_4 = List.fill(4)(Orange)
  val _Oranges_2_Price = bigDecimalFormatter(2 * Orange.SellingPrice)
  val _Oranges_3_Price = bigDecimalFormatter(3 * Orange.SellingPrice)

  //Mix fruits
  val _Apples_2_Oranges_3 = Apple :: Apple :: Orange :: Orange :: Orange :: Nil
  val _Apples_1_Oranges_2_Price = bigDecimalFormatter(1 * Apple.SellingPrice + 2 * Orange.SellingPrice)
  val _Apples_2_Oranges_3_Price = bigDecimalFormatter(2 * Apple.SellingPrice + 3 * Orange.SellingPrice)

}
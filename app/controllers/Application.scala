package controllers

import play.api._
import cache.Cache
import play.api.mvc._
import play.api.Play.current

object Application extends Controller {

  def index = Action {

    // set
    try {
      Cache.set("item.key", "item.value")
    } catch {
      case _ => println("Cacheをsetするとこでエラー。redisを起動していない場合などに発生する。")
    }

    // setにキャッシュの有効期限を秒で指定できる。
    try {
      Cache.set("item.key2", "item.value2", 30)
    } catch {
      case _ => println("Cacheをsetするとこでエラー。redisを起動していない場合などに発生する。")
    }

    // get 方法1
    //   方法1と2のどちらのように値を決めても良い。
    //   ただしkeyが無い場合playには警告が出る。（[warn] application - could not deserialize key:item.key ex:java.lang.NullPointerException）
    val valueFromCache1: Option[String] = Cache.getAs[String]("item.key")
    println(valueFromCache1.getOrElse("item.keyがなかった"))

    // get 方法2
    val valueFromCache2: String = Cache.getOrElse[String]("item.key") {
      "item.keyがなかった"
    }
    println(valueFromCache2)


    Ok(views.html.index("Your new application is ready."))
  }

}
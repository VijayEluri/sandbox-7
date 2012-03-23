package com.example.example


import scala.util.continuations._
import scala.actors.Actor.{self, actor}
import scala.annotation.tailrec

object Controller {

  def retry[T](limit: Int, catchClass: Class[_])(f: => T) = {
    reset {
      val params = shift {
        ctx: (Int => T) =>
         // このへんに事前処理
          val counter = 0
          def process(l: Int): T = {
            try {
              ctx(l)
            } catch {
              case e: Throwable =>
                if (l < limit - 1 && catchClass.isAssignableFrom(e.getClass())) {
                  Thread.sleep(100);
                  process(l + 1)
                } else {
                  throw e
                }
            }
          }
          process(counter)
         // このへんに事後処理
      }
      println("counter = " + params)
      // 繰り返し処理
      f
    }
  }

}


object App {
  def main(args: Array[String]) {
    var i = 0;
    Controller.retry(3,classOf[Exception]){
      println(i)
      i+=1
      throw new Exception(i.toString)
    } 
  }
}

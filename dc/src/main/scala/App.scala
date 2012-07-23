package com.example.example


import scala.util.continuations._
import scala.actors.Actor.{self, actor}
import scala.annotation.tailrec

object Controller {

  def retry[T](limit: Int, catchClass: Class[_])(func: => T):T = {
    reset {
      val params = shift {
        ctx: (Int => T) =>
          var counter = 0
          while(counter < limit - 1){
            try{
              return ctx(counter)
            } catch {
              case e: Throwable =>
                if (catchClass.isAssignableFrom(e.getClass())) {
                  counter += 1
                }else{
                  throw e
                }
            }
          }
          throw new RuntimeException
      }
      func
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

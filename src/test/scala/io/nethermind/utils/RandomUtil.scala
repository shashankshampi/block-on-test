package io.nethermind.utils

import java.security.SecureRandom

object RandomUtil {
  private val random = new SecureRandom

  def generateRandomString(length: Int): String = {
    val text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val sb = new StringBuilder(length)
    for (i <- 0 until length) {
      sb.append(text.charAt(random.nextInt(text.length)))
    }
    sb.toString
  }
  def generateRandomStringStartingWithLoad_(length: Int): String = {
    val text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val sb = new StringBuilder(length).append("Load_")
    for (i <- 0 until length) {
      sb.append(text.charAt(random.nextInt(text.length)))
    }
    sb.toString
  }

  def generateRandomAlphaNumericString(length: Int): String = {
    val text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val sb = new StringBuilder(length)
    for (i <- 0 until length) {
      sb.append(text.charAt(random.nextInt(text.length)))
    }
    sb.toString
  }

  def generateRandomNumericString(length: Int): String = {
    val textnumber = "123"
    val sb = new StringBuilder(length)
    for (i <- 0 until length) {
      sb.append(textnumber.charAt(random.nextInt(textnumber.length)))
    }
    sb.toString
  }

  def generateRandomMobileNumberString(length: Int, startsWith: Int): String = {
    val textnumber = "0123456789"
    val sb = new StringBuilder(length)
    for (i <- 0 until length) {
      sb.append(textnumber.charAt(random.nextInt(textnumber.length)))
    }
    startsWith + sb.toString
  }


  def generateRandomEmail(length: Int): String = {
    val text = "abcdefghijklmnopqrstuvwxyz";
    val sb = new StringBuilder(length)

    for (i <- 0 until length) {
      sb.append(text.charAt(random.nextInt(text.length())));
    }
      sb + "@" + "loadtest" + ".com"
  }

  def main(args: Array[String]): Unit = {
    for (a <- 1 to 100000) {
      println(generateRandomEmail(6));
    }
  }
}

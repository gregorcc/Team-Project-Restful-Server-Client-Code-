package edu.uiowa.cs.tornadoFX.testingFeature

import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.HtmlEmail
import java.net.URL
import java.util.*
import kotlin.streams.asSequence

//Gradle
//compile group: 'org.apache.commons', name: 'commons-email', version: '1.1'

fun main(args: Array<String>) {
    //Send Email.
    val senderEmail = args[0] // your email address
    val password = args[1] // your password
    val toMail = args[2] // user email address, Check is user exist or not then, send email

    val email = HtmlEmail()
    email.hostName = "smtp.googlemail.com"
    email.setSmtpPort(465)
    email.setAuthenticator(DefaultAuthenticator(senderEmail, password))
    email.isSSL = true
    //email.isSSLOnConnect = true
    email.setFrom(senderEmail)
    email.addTo(toMail)
    email.subject = "Test email with inline image sent using Kotlin"

    // Generate Random String.
    val outputStrLength = 10L
    val source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val generatedString = Random().ints(outputStrLength, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
    println(generatedString)

    email.setTextMsg("Your temporary password is:  $generatedString")
    val kotlinLogoURL = URL("https://kotlinlang.org/assets/images/twitter-card/kotlin_800x320.png")
    val cid = email.embed(kotlinLogoURL, "Kotlin logo")
    email.setHtmlMsg("<html><h1>Kotlin logo</h1><img src=\"cid:$cid\"></html>")
    email.send()

}
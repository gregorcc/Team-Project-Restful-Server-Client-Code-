/*
@Copied from an Internet Source...
 */

package edu.uiowa

import com.sun.org.apache.xpath.internal.operations.Bool
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.HtmlEmail
import java.net.URL
import java.util.*
import kotlin.streams.asSequence

//Gradle
//compile group: 'org.apache.commons', name: 'commons-email', version: '1.1'



fun forgotPasswordAsBackend(emailBackend : String, birthday: String, profileList: MutableList<UserProfile>, userList: MutableMap<String, String>): List<String> {
    for(i in profileList) {
        if (i.birthday == birthday && i.name == emailBackend) {
            //Send Email.
            val senderEmail = "teamoop06@gmail.com"  // your email address
            val password = "watermelonOOP" // your password
            val toMail = emailBackend // user email address, Check is user exist or not then, send email

            val email = HtmlEmail()
            email.hostName = "smtp.googlemail.com"
            email.setSmtpPort(465)
            email.setAuthenticator(DefaultAuthenticator(senderEmail, password))
            email.isSSL = true
            //email.isSSLOnConnect = true
            email.setFrom(senderEmail)
            email.addTo(toMail)
            email.subject = "Recover password for your Survey Account"

            // Generate Random String.
            val outputStrLength = 10L
            val source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            val generatedString = Random().ints(outputStrLength, 0, source.length)
                    .asSequence()
                    .map(source::get)
                    .joinToString("")
            println("new passwoed for $email="+generatedString)
            changePasswordAsBackend(emailBackend, userList[emailBackend]!!, generatedString, mapOf(emailBackend to generatedString), userList)

            val kotlinLogoURL = URL("https://kotlinlang.org/assets/images/twitter-card/kotlin_800x320.png")
            val cid = email.embed(kotlinLogoURL, "Kotlin logo")
            email.setHtmlMsg("<html><h1>Kotlin logo</h1><img src=\"cid:$cid\"><p>Your temporary password is:  $generatedString</p></html>")
            email.send()
            return listOf("true", generatedString)
        }
    }
    return listOf("false", "ERROR")
}

fun changePasswordAsBackend(email: String, old: String, new: String, recoverlist: Map<String, String>, userList: MutableMap<String,String>):Boolean{
    if(recoverlist.containsKey(email)){
        if(recoverlist[email]==old){
            if(userList.containsKey(email)){
                userList[email]=new
                saveUserFile(userList)
            }
        }
    }
    return false
}

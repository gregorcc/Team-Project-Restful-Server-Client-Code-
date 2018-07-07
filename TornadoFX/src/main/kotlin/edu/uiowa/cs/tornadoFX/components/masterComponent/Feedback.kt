package edu.uiowa.cs.tornadoFX.components.masterComponent

import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.HtmlEmail
import tornadofx.*
import java.net.URL

/*
*   @@author Layton Gao
*
*   In this is the feedback page; It will send email to my account.
*
* */

class Feedback: Fragment(){
    override val root = borderpane {
        center = vbox {
            label("Leave Your Feedback Here:")
            val feedbacktext = textarea()
            add(feedbacktext)
            button("Submit") {
                action {
                    if (feedbacktext.text.isNotEmpty()){
                        sendFeedback(feedbacktext.text)
                    }
                }

            }
        }
    }
    private fun sendFeedback(feedback:String) {
        //Send Email.
        val senderEmail = "teamoop06@gmail.com" // your email address
        val password = "watermelonOOP" // your password
        val toMail = "teamoop06@gmail.com" // user email address, Check is user exist or not then, send email

        val email = HtmlEmail()
        email.hostName = "smtp.googlemail.com"
        email.setSmtpPort(465)
        email.setAuthenticator(DefaultAuthenticator(senderEmail, password))
        email.isSSL = true
        email.setFrom(senderEmail)
        email.addTo(toMail)
        email.subject = "Email Feedback on Survey App"

        email.setTextMsg("Your temporary password is:  $feedback")

        val kotlinLogoURL = URL("https://kotlinlang.org/assets/images/twitter-card/kotlin_800x320.png")
        val cid = email.embed(kotlinLogoURL, "Kotlin logo")
        email.setHtmlMsg("<html><h1>Kotlin logo</h1><img src=\"cid:$cid\"><p>User feedback:\n  $feedback</p></html>")
        email.send()

    }
}
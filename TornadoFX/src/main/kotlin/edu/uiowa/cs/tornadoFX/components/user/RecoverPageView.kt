package edu.uiowa.cs.tornadoFX.components.user

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.Popup
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterController
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterView
import edu.uiowa.cs.tornadoFX.components.user.loginPage.UserLoginView
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate

/*
*   @@author Layton Gao
*
*   class @RecoverPageView:
*       This page will allow user to recover their password. It will ask for email address and data of birth to
*       identify user. Server will send email to user with a random generated password.
*
* */


class RecoverPageView: View() {
    private val recoverController: MasterController by inject()

    private val model = object : ViewModel() {
        var userName = bind { SimpleStringProperty() }
        var dateOfBirth = bind { SimpleObjectProperty<LocalDate>() }
    }

    override val root = borderpane()

    init {
        with(root) {
            addClass(Styles.wrapper)

            left = vbox(10) {

                form {
                    fieldset {
                        field {
                            label("Email Address: Which will be the username.")
                        }
                        field("Email Address") {
                            textfield(model.userName) {
                                promptText = "This will be your Username"
                                required()
                            }
                        }
                        field("Date of Birth") {
                            datepicker(model.dateOfBirth) {
                                value = LocalDate.of(2000, 1, 1)
                                addClass(Styles.noStyles)
                            }
                        }

                        // send email address and data of birth, let server to verify, and send email to user's email
                        button("Commit") {
                            action {
                                println(
                                        """
TornadoFX: ----------------Saved Value-----------------------
| Send recover information
| ${model.dateOfBirth}
| ${model.userName}
|
             """
                                )
                                model.commit{
                                    recoverController.networkPortal.recoverPassword(model.userName.value,model.dateOfBirth.value.toString())
                                    Popup.message = "Your temporary password is sending to email, Please change your password as soon as possible."
                                    find(Popup::class).openModal()
                                }

                            }
                        }

                    }
                }
            }
            bottom = button("Return"){
                action {
                    replaceWith(UserLoginView::class)
                }
            }
        }
    }
}

// Here is an idea I adopted from internet for sending email and generate random password.

//Gradle
//compile group: 'org.apache.commons', name: 'commons-email', version: '1.1'
//
//fun main(args: Array<String>) {
//    //Send Email.
//    val senderEmail = args[0] // your email address
//    val password = args[1] // your password
//    val toMail = args[2] // user email address, Check is user exist or not then, send email
//
//    val email = HtmlEmail()
//    email.hostName = "smtp.googlemail.com"
//    email.setSmtpPort(465)
//    email.setAuthenticator(DefaultAuthenticator(senderEmail, password))
//    email.isSSL = true
//    //email.isSSLOnConnect = true
//    email.setFrom(senderEmail)
//    email.addTo(toMail)
//    email.subject = "Test email with inline image sent using Kotlin"
//
//    // Generate Random String.
//    val outputStrLength = 10L
//    val source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
//    val generatedString = Random().ints(outputStrLength, 0, source.length)
//            .asSequence()
//            .map(source::get)
//            .joinToString("")
//    println(generatedString)
//
//    email.setTextMsg("Your temporary password is:  $generatedString")
//    val kotlinLogoURL = URL("https://kotlinlang.org/assets/images/twitter-card/kotlin_800x320.png")
//    val cid = email.embed(kotlinLogoURL, "Kotlin logo")
//    email.setHtmlMsg("<html><h1>Kotlin logo</h1><img src=\"cid:$cid\"></html>")
//    email.send()
//
//}
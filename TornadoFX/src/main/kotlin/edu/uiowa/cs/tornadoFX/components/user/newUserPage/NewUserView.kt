package edu.uiowa.cs.tornadoFX.components.user.newUserPage

import edu.uiowa.cs.tornadoFX.app.Styles
import edu.uiowa.cs.tornadoFX.components.dataStructure.User
import edu.uiowa.cs.tornadoFX.components.masterComponent.MasterView
import edu.uiowa.cs.tornadoFX.components.user.loginPage.UserLoginView
import edu.uiowa.cs.tornadoFX.components.masterComponent.Shakeable
import edu.uiowa.cs.tornadoFX.components.user.welcomePage.UserWelcomePageView
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate

/*
*   @@author Layton Gao
*
*   class @NewUserView
*       This class is used for UI of create a page that allowed user to create a new account.
*       It contains @userName, @password, @firstName, @lastName, @confirmPassword, @dateOfBirth
*       six different text field.
*       Commit button will do some checking for illegal input.
*
*   private val @model
*       contains @userName, @password, @firstName, @lastName, @confirmPassword, @dateOfBirth
*       six model for store data.
*
*   fun @returnButton
*       A open function allows different user return to different view class.
*
*   class @ChangeUserProfileView
*       This is for people already logged in to change they profile.
* */


open class NewUserView : MasterView("User Profiles"), Shakeable {
    val newUserController: NewUserController by inject()

    //override val root: BorderPane = BorderPane()

    // This is the private model for newUserPage, all the data will store inside it.
    // new user View and Controller will read, display, modify its data.
    private val model = object: ViewModel(){
        var userName = bind{ SimpleStringProperty() }
        var password = bind{ SimpleStringProperty() }
        var firstName = bind{ SimpleStringProperty() }
        var lastName = bind{ SimpleStringProperty() }
        var confirmPassword = bind { SimpleStringProperty() }
        var dateOfBirth = bind { SimpleObjectProperty<LocalDate>() }

    }

    // I leave this Button as a open function, Because It can play two role.
    // It can be the newUserClass Button, Which is no logged in. It will return to Login page.
    // IT can be the profileClass Button, Which is already logged in, It will return to Welcome page.
    open fun returnButton() = button("Exit") {
        action{
            replaceWith(UserLoginView())
        }
    }

    // This is the main UI body of NewUserView class
    init{
        with(root){
            center = borderpane {
                addClass(Styles.wrapper)
                left = vbox(10){

                    form {
                        fieldset {
                            field("First Name") {
                                textfield(model.firstName){
                                    promptText = "Type Your First Name Here"
                                }.required()
                            }

                            field("Last Name"){
                                textfield(model.lastName){
                                    promptText = "Type Your Last Name Here"
                                }.required()
                            }

                            field("Email Address") {
                                textfield(model.userName){
                                    promptText = "This will be your Username"
                                    required()
                                }
                            }
                            field("Date of Birth") {
                                datepicker(model.dateOfBirth) {
                                    value = LocalDate.of(2000, 1, 1)
                                    addClass(Styles.noStyles)
                                    validator{
                                        when{
                                            it?.year ?:0 > LocalDate.now().year -> error("Your Are Not Born Yet")
                                            (it?.year ?:0 == LocalDate.now().year) && (it?.dayOfYear ?:0 > LocalDate.now().dayOfYear)
                                                -> error("Your Are Not Born Yet")
                                            else -> null
                                        }
                                    }
                                }
                            }
                            field("Password"){
                                passwordfield(model.password){
                                    promptText = "8 to 16 Digits Long, No Special Character,\\ No Space "
                                    requestFocus()
                                    required()

                                    // Check valid input
                                    validator {
                                        when {
                                            it.isNullOrBlank() -> error("Please Inter Your Password")
                                            it!!.contains("\\") -> error("Password Contains Special Characters")
                                            it.length < 8 -> error("Password Too Short")
                                            it.length > 16 -> error("Password Too Long")
                                            else -> null
                                        }
                                    }
                                }
                            }

                            // check the whether Password matches.
                            field("Confirm Password") {
                                passwordfield(model.confirmPassword){
                                    promptText = "Need to match The Password Above"
                                    requestFocus()
                                    required()

                                    // Check valid input
                                    validator {
                                        if (it.isNullOrBlank()) error("Please Inter Your Password") else
                                            if (it!!.length > model.password.value.length && it != model.password.value)
                                                error("Password Not Match Yet") else null
                                    }
                                }
                            }
                        }
                    }
                    // when all required input satiated, it will save the data.
                    button("Commit") {
                        isDefaultButton = true
                        action {
                            model.commit{

                                // check the completion or username and password, if everything is ready, then send it to server.
                                newUserController.checkCompletion(User(model.userName.value, model.firstName.value, model.lastName.value,
                                        model.password.value, model.dateOfBirth.value),model.confirmPassword.value)

                            }
                        }
                    }
                }
                bottom = vbox {
                    add(returnButton())
                }
            }
        }
    }

    override fun onDock() {
        model.validate(decorateErrors = false)
    }
}

// this is the class for logged in user to change they profile
class ChangeUserProfileView: NewUserView(){
    override fun returnButton() = button("Return"){
        action {
            replaceWith(UserWelcomePageView::class)
        }
    }
}


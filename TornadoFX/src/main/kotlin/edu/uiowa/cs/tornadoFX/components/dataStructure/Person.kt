package edu.uiowa.cs.tornadoFX.components.dataStructure

import tornadofx.*
import java.time.LocalDate
import java.time.Period

/*
*   @@author Layton Gao
*
*   Class @Person is the fundamental structure for User and Admin, I think divide Person to different group will be
*   beneficial for later programming. But none of my group mates has time to contribute effort on those additional ideas, and
*   the progress of our group project was no every fast, So I stopped to develop other function on Users and Admins.
*
* */

open class Person(userName:String, firstName:String,lastName:String,password:String,dateOfBirth:LocalDate){
    init {

    }
    var userName by property(userName)
    fun userNameProperty() = getProperty(Person::userName)

    var firstName by property(firstName)
    fun firstNameProperty() = getProperty(Person::firstName)

    var lastName by property(lastName)
    fun lastNameProperty() = getProperty(Person::lastName)

    var password by property(password)
    fun passwordProperty() = getProperty(Person::password)

    var dateOfBirth by property(dateOfBirth)
    fun dateOfBirthProperty() = getProperty(Person::dateOfBirth)

    val age: Int get() = Period.between(dateOfBirth, LocalDate.now()).years
    var isLogin: Boolean = false   //todo

    val name: String get() = firstName + lastName

    override fun toString(): String {
        return "$userName $firstName $lastName $password ${dateOfBirth}"
    }
    fun toStringUser():edu.uiowa.cs.networkController.User{
        return edu.uiowa.cs.networkController.User(userName,firstName,lastName,password,dateOfBirth.toString())
    }
}

class User(userName:String, firstName:String,lastName:String,password:String, dateOfBirth:LocalDate): Person(
        userName, firstName,lastName,password, dateOfBirth){
}

class Administrator(userName:String, firstName:String, lastName:String, password:String, dateOfBirth:LocalDate): Person(
        userName, firstName,lastName,password, dateOfBirth){
}



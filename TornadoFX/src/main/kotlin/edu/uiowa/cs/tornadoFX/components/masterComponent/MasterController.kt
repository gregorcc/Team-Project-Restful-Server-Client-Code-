package edu.uiowa.cs.tornadoFX.components.masterComponent

import edu.uiowa.cs.networkController.FrontEndNetworkAPI
import edu.uiowa.cs.tornadoFX.components.dataStructure.Person
import edu.uiowa.cs.tornadoFX.components.dataStructure.User
import tornadofx.*

/*
*   @@author Layton Gao
*
*   MasterController contains the an object of @FrontEndNetworkAPI which will send and get all the packages
*   It also contains @PASSWORD and @USERNAME, which will be use for networking, like get logout, sending response of
*   Survey, Change profile and other things.
*
* */

open class MasterController: Controller(){
    private lateinit var person: Person

    private companion object {
        var PASSWORD: String = ""
        var USERNAME: String = ""
    }

    val networkPortal = FrontEndNetworkAPI("http://localhost:8080/")
    fun getUser(): Person {
        return person
    }

    fun setUser(user: User){
        this.person = user
    }

    fun setUserPASSWORD(string:String){
        PASSWORD = string
    }

    fun setUserNAME(string:String){
        USERNAME = string
    }

    fun getUserPASSWORD():String{
        return PASSWORD
    }

    fun getUserNAME():String{
        return USERNAME
    }
}
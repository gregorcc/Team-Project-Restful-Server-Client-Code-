package edu.uiowa.cs.networkController
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientRequestFilter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javax.ws.rs.ext.ContextResolver

/**
 * @author Greg Mich
 * Implementation of the FrontEndNetworking Interface for use by the GUI specialist
 * see edu.uiowa.cs.networkController.FrontEndNetworking for documentation and more
 * information on how to use the interface
 */
class FrontEndNetworkAPI : FrontEndNetworking{
    private val serverIPWithPort: String
    constructor(serverIPWithPort: String = "http://localhost:8080"){
        this.serverIPWithPort = serverIPWithPort
    }

    override fun frontEndLogin(username: String, password: String): Boolean {
        println("CONTROLLER CLIENT: Attempting login for $username")
        val userLoginTemp = User(username, " ", " ", password, " ")
        val mapper = ObjectMapper()
        val jsonObj = mapper.writeValueAsString(userLoginTemp)
        val response = client
                .target(serverIPWithPort)
                .path("client/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonObj, MediaType.APPLICATION_JSON))
        println(response)
        val responseMessage = cleanUpTag(response.entityTag.toString())
        if(responseMessage != "${userLoginTemp.userName} successfully logged in"){
            return false
        }
        return true
    }

    override fun createNewUser(newUser: User): Boolean {
        println("CONTROLLER CLIENT: Attempting to create new user ${newUser.userName}")
        val mapper = ObjectMapper()
        val jsonObj = mapper.writeValueAsString(newUser)
        val response = client
                .target(serverIPWithPort)
                .path("client/createnewuser")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonObj, MediaType.APPLICATION_JSON))
        println(response)
        val responseMessage = cleanUpTag(response.entityTag.toString())
        if(responseMessage != "Successfully created user : ${newUser.userName}"){
            return false
        }
        return true
    }

    override fun frontEndGetSurvey(surveyType: String): List<Any> {
        println("CONTROLLER CLIENT: Attempting to get survey $surveyType")
        val response =  client
                .target(serverIPWithPort)
                .path("client/getsurvey/$surveyType")
                .request(MediaType.APPLICATION_JSON)
                .get()
        val jsonString = response.readEntity(String::class.java)
        val mapper = ObjectMapper()
        val surveyObject = mapper.readValue(jsonString,List::class.java)
        return surveyObject as List<Any>
    }

    override fun frontEndSubmitAnswers(username: String, surveyType:String, answers: List<String>): Boolean {
        println("CONTROLLER CLIENT: Attempting to submit answers")
        val frontEndSubmitAnswerMessage = NetworkMessageSubmitAnswers(username,surveyType, answers)
        val mapper = ObjectMapper()
        val jsonObj = mapper.writeValueAsString(frontEndSubmitAnswerMessage)
        val response = client
                .target(serverIPWithPort)
                .path("/client/submit")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonObj, MediaType.APPLICATION_JSON))
        println(response)
        if(response.status != 200){
            return false
        }
        return true
    }

    override fun frontEndLogOutUser(userName: String): Boolean {
        println("CONTROLLER CLIENT: Attempting to logout $userName")
        val user = User(userName, "null","null","null","00/00/0000")
        val mapper = ObjectMapper()
        val jsonObj = mapper.writeValueAsString(user)
        val response = client
                .target(serverIPWithPort)
                .path("/client/logout")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonObj, MediaType.APPLICATION_JSON))
        val responseMessage = cleanUpTag(response.entityTag.toString())
        println(responseMessage)
        if(responseMessage != "logged out"){
            return false
        }
        return true
    }


    override fun frontEndGetSurveyList(): List<String> {
        println("CONTROLLER CLIENT: Attempting to get survey list")
        val response = client
                .target(serverIPWithPort)
                .path("client/list")
                .request(MediaType.APPLICATION_JSON)
                .get()
        val jsonString = response.readEntity(String::class.java)
        val mapper = ObjectMapper()
        val surveyObject = mapper.readValue(jsonString,List::class.java)
        return surveyObject as List<String>
    }

    override fun adminAddSurvey(userName: String, password: String, surveyType: String, questions: List<String>): Boolean{
        println("CONTROLLER CLIENT: Attempting to create a new survey")
        println("CONTROLLER CLIENT: $userName + $password + $surveyType + $questions")

        val adminUser = User(userName, "null","null",password,"00/00/0000")
        val networkPayload = NetworkMessageAddSurvey(adminUser,surveyType,questions)
        val mapper = ObjectMapper()
        val jsonObj = mapper.writeValueAsString(networkPayload)
        val response = client
                .target(serverIPWithPort)
                .path("/admin/addSurvey")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonObj, MediaType.APPLICATION_JSON))
        val responseMessage = cleanUpTag(response.entityTag.toString())
        println("CLIENT CONTROLLER: $responseMessage")
        if(response.status != 200){
            return false
        }
        return true
    }

    override fun adminAddSurveyMC(userName: String, password: String, surveyType: String, questionsWithOptions: Map<String, List<String>>): Boolean {
        println("CONTROLLER CLIENT: Attempting to create a new multiple choice survey")
        println("CONTROLLER CLIENT: $userName + $password + $surveyType + $questionsWithOptions")

        val adminUser = User(userName, "null","null",password,"00/00/0000")
        val networkPayload = NetworkMessageAddMCSurvey(adminUser,surveyType,questionsWithOptions)
        val mapper = ObjectMapper()
        val jsonObj = mapper.writeValueAsString(networkPayload)
        val response = client
                .target(serverIPWithPort)
                .path("admin/addSurveyMC")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonObj, MediaType.APPLICATION_JSON))
        val responseMessage = cleanUpTag(response.entityTag.toString())
        println("CLIENT CONTROLLER: $responseMessage")
        if(response.status != 200){
            return false
        }
        return true
    }

    override fun recoverPassword(email: String, birthday: String): Boolean {
        println("CONTROLLER CLIENT: Attempting to recover password")
        val networkPayload = NetworkMessageForgotPassword(email, birthday)
        val mapper = ObjectMapper()
        val jsonObj = mapper.writeValueAsString(networkPayload)
        val response = client
                .target(serverIPWithPort)
                .path("/client/forgotpassword")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonObj, MediaType.APPLICATION_JSON))
        if(response.status != 200){
            return false
        }
        return true
    }

    private val client = ClientBuilder.newClient()
    init {
        client.register(LoggingFilter()) // for debugging, prints info
        client.register(  // need this for conversion from JSON to Kotlin objects
                ContextResolver<ObjectMapper> {
                    ObjectMapper().registerModule(KotlinModule()) }
        )
    }

}

//utility functions
class LoggingFilter: ClientRequestFilter {
    override fun filter(requestContext: ClientRequestContext) {
        println("URI: " + requestContext.uri)
    }
}

fun cleanUpTag(input: String): String{
    return input.filter{it != '"'}
}

//some simple tests for TornadoFX clients
fun main(args: Array<String>) {
    val networkPortal = FrontEndNetworkAPI("http://localhost:8080/")
    val me = User("gregmich95@gmail.com", "Greg","Mich","123456789","9/15/1995")
    println("Attempting to login")
    var response = networkPortal.frontEndLogin("gmich", "123456")
    println(response)
    println("Attempting to create a new user: ${me.userName}")
    response = networkPortal.createNewUser(me)
    println(response)
    println("Attempting to log user out")
    response = networkPortal.frontEndLogOutUser(me.userName)
    println(response)
    val response2 = networkPortal.frontEndGetSurveyList()
    println(response2)
    val response3 = networkPortal.frontEndSubmitAnswers("gmich","sports",listOf("","",""))
    println(response3)
    val response4 = networkPortal.adminAddSurvey("Admin","Watermelon","GMsurvey",listOf("How are you doing?","What are you thinking about today?"))
    println(response4)
    val myQuestions = HashMap<String, List<String>>()
    myQuestions.put("Question One",listOf("Answer1","Answer2","Answer3"))
    myQuestions.put("Question Two",listOf("Answer1","Answer2","Answer3"))
    myQuestions.put("Question Three",listOf("Answer1","Answer2","Answer3"))
    val response5 = networkPortal.adminAddSurveyMC("Admin","Watermelon","GMMC",myQuestions)
    println(response5)
    val response6 = networkPortal.recoverPassword(me.userName,me.birthday)
    println(response6)
}
package edu.uiowa
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientRequestFilter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javax.ws.rs.ext.ContextResolver

class LoggingFilter: ClientRequestFilter {
    override fun filter(requestContext: ClientRequestContext) {
        println("URI: " + requestContext.uri)
    }
}

class RestTesterClient {

    private val client = ClientBuilder.newClient()
    init {
        client.register(LoggingFilter()) // for debugging, prints info
        client.register(  // need this for conversion from JSON to Kotlin objects
                ContextResolver<ObjectMapper> {
                    ObjectMapper().registerModule(KotlinModule()) }
        )
    }

    fun attemptLoginTest(user: User): Response{
        return client
                .target(REST_URI)
                .path("client/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON))
    }

    fun attemptCreateNewUser(user: User): Response{
        return client
                .target(REST_URI)
                .path("test/createnewuser")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON))
    }

    fun attemptLogoutUser(user: User): Response {
        return client
                .target(REST_URI)
                .path("client/logout")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON))
    }

        fun attemptGetSurvey(surveyType: String): List<Any> {
        return client
                .target(REST_URI)
                .path("client/getsurvey/$surveyType")
                .request(MediaType.APPLICATION_JSON)
                .get(List::class.java) as List<Any>
    }

    fun attemptGetSurveysList(): List<String>{
        return client
                .target(REST_URI)
                .path("/client/list")
                .request(MediaType.APPLICATION_JSON)
                .get(List::class.java) as List<String>
    }

    fun attemptSubmitAnswers(userName:String, surveyType: String, answers:List<String>): Response{
        val networkPayload = NetworkMessageSubmitAnswers(userName,surveyType,answers)
        return client.
                target(REST_URI)
                .path("/client/submit")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(networkPayload, MediaType.APPLICATION_JSON))
    }

    fun attemptAddSurvey(userName: String,password: String, surveyType: String, questions: List<String>): Response{
        //only relevant fields are non-null
        val adminUser = User(userName, "null","null",password,"00/00/0000")
        val networkPayload = NetworkMessageAddSurvey(adminUser,surveyType,questions)
        return client
                .target(REST_URI)
                .path("/admin/addSurvey")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(networkPayload, MediaType.APPLICATION_JSON))
    }

    fun attemptAddSurveyMC(userName: String,password: String, surveyType: String, questionsWithOptions: Map<String, List<String>>): Response{
        //only relevant fields are non-null
        val adminUser = User(userName, "null","null",password,"00/00/0000")
        val networkPayload = NetworkMessageAddMCSurvey(adminUser,surveyType,questionsWithOptions)
        return client
                .target(REST_URI)
                .path("/admin/addSurveyMC")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(networkPayload, MediaType.APPLICATION_JSON))
    }

    fun attemptForgotPassword(email: String, birthday: String): Response{
        val networkPayload = NetworkMessageForgotPassword(email, birthday)
        return client
                .target(REST_URI)
                .path("/client/forgotpassword")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(networkPayload, MediaType.APPLICATION_JSON))
    }

    companion object {
        const val REST_URI = "http://localhost:8080/"
    }
}

// This is the main() for the client. There needs to be
// a run configuration for edu.uiowa.cs.TestClient
object TestClient {
    @JvmStatic
    fun main(args: Array<String>) {
        val client = RestTesterClient();  // the object for REST communication
        val me = User("gregmich95@gmail.com", "Greg", "Mich", "passw3rd", "9/15/1995")
        val response = client.attemptCreateNewUser(me)
        println(response)
        println(response.entityTag.toString())

        val response3 = client.attemptLoginTest(me)
        println(response3)
        println(response3.entityTag.toString())

        val response2 = client.attemptLogoutUser(me)
        println(response2)
        println(response2.entityTag.toString())

        val response4 = client.attemptLogoutUser(me)
        println(response4)
        println(response4.entityTag.toString())

        val response5 = client.attemptGetSurvey("Sports")
        println(response5)
        println(response5.javaClass)
        if(!response5.isEmpty())
        println(response5[0])

        val response6 = client.attemptGetSurveysList()
        println(response6)

        //user not logged in
        val myAnswers = listOf<String>("test","test","test","test")
        val response7 = client.attemptSubmitAnswers(me.userName,"sports",myAnswers)
        println(response7)

        client.attemptLoginTest(me)
        //user logged in
        val response8 = client.attemptSubmitAnswers(me.userName, "sports",myAnswers)
        println(response8)


        //addsurvey using default ADMIN, Username: Admin pass: Watermelon
        val response9 = client.attemptAddSurvey("Admin","Watermelon","GMsurvey",listOf("How are you doing?","What are you thinking about today?"))
        println(response9)

        val myQuestions = HashMap<String, List<String>>()
        myQuestions.put("Question One",listOf("Answer1","Answer2","Answer3"))
        myQuestions.put("Question Two",listOf("Answer1","Answer2","Answer3"))
        myQuestions.put("Question Three",listOf("Answer1","Answer2","Answer3"))
        val response10 = client.attemptAddSurveyMC("Admin","Watermelon","GMMC",myQuestions)
        println(response10)

        val response11 = client.attemptForgotPassword(me.userName,me.birthday)
        println(response11)
    }
}

fun cleanUpTag(input: String): String{
    return input.filter{it != '"'}
}
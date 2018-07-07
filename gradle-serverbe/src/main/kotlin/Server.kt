/**
 * @author Greg Mich
 */
package edu.uiowa
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Application
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ContextResolver

//class used for server testing only
@Path("test")
@Produces(APPLICATION_JSON)
class TestResource {

    //back end portal allows the server to use the interface
    private val backEndPortal = backendInterface

    //this method works to return a default survey that is using the back-end
    //implementation of the survey class
    @GET @Path("defaultsurvey")
    fun getDefaultSurvey(): SurveyQuestions{
        val defaultQuestionsList = ArrayList<String>()
        defaultQuestionsList.add("How old are you?")
        defaultQuestionsList.add("How many A's have you gotten this semester?")
        defaultQuestionsList.add("How many years of school do you have left?")
        val defaultSurvey = SurveyQuestions("DEFAULT", defaultQuestionsList)
        println("Fetching default survey")
        return defaultSurvey
    }

    //attempts to login from frontend
    @POST @Path("login")
    @Consumes(APPLICATION_JSON)
    fun attemptLogin(user: User): Response{
        println("received request for: ${user.userName} to login")
        val portalResponse = backEndPortal.logIn(user.userName, user.password)
        println(portalResponse)
        if(portalResponse.toLowerCase().equals("no such user exists")){
            return Response.status(404).tag(portalResponse).build()
        }
        if(portalResponse.toLowerCase().equals("incorrect password")){
            return Response.status(403).tag(portalResponse).build()
        }
        if(portalResponse.equals("${user.userName} successfully logged in")){
            return Response.status(200).tag(portalResponse).build()
        }
        if(portalResponse.equals("${user.userName} is already loggedIn")){
            return Response.status(500).tag(portalResponse).build()
        }
        return Response.status(500).tag("other error").build()
    }

    @POST @Path("createnewuser")
    @Consumes(APPLICATION_JSON)
    fun createNewUser(user: User): Response{
        println("Attempting to create new user\nUsername: ${user.userName}")
        val portalResponse = backEndPortal.createUserProfile(user)
        return Response.status(200).tag(portalResponse).build()
    }

    @POST @Path("logout")
    @Consumes(APPLICATION_JSON)
    fun logOutUser(user: User): Response{
        println("logging out ${user.userName}")
        val portalResponse = backEndPortal.logOut(user.userName)
        return Response.status(200).tag(portalResponse).build()
    }
}

//a class used for front-end client usage. Android and TornadoFX
@Path("client")
@Produces(APPLICATION_JSON)
class ClientResource{

    //this will return the "default survey" to the client.
    @GET @Path("defaultsurvey")
    fun getDefaultSurvey(): SurveyQuestions{
        val defaultQuestionsList = ArrayList<String>()
        defaultQuestionsList.add("How old are you?")
        defaultQuestionsList.add("How many A's have you gotten this semester?")
        defaultQuestionsList.add("How many years of school do you have left?")
        val defaultSurvey = SurveyQuestions("DEFAULT", defaultQuestionsList)
        println("SERVER CONTROLLER: Fetching default survey")
        return defaultSurvey
    }


    @POST @Path("login")
    @Consumes(APPLICATION_JSON)
    fun attemptLogin(user: User): Response{
        val backEndPortal = backendInterface
        println("SERVER CONTROLLER: received request for: ${user.userName} to login")
        val portalResponse = backEndPortal.logIn(user.userName, user.password)
        println("SERVER CONTROLLER: $portalResponse")
        if(portalResponse.toLowerCase().equals("no such user exists")){
            return Response.status(404).tag(portalResponse).build()
        }
        if(portalResponse.toLowerCase().equals("incorrect password")){
            return Response.status(403).tag(portalResponse).build()
        }
        if(portalResponse.equals("${user.userName} successfully logged in")){
            return Response.status(200).tag(portalResponse).build()
        }
        if(portalResponse.equals("${user.userName} is already loggedIn")){
            return Response.status(200).tag(portalResponse).build()
        }
        return Response.status(500).tag("other error").build()
    }

    @POST @Path("createnewuser")
    @Consumes(APPLICATION_JSON)
    fun createNewUser(user: User): Response{
        val backEndPortal = backendInterface
        println("SERVER CONTROLLER: Attempting to create new user. Username: ${user.userName}")
        val portalResponse = backEndPortal.createUserProfile(user)
        println("SERVER CONTROLLER: $portalResponse")
        if(portalResponse == "Successfully created user : ${user.userName}"){
            return Response.status(200).tag(portalResponse).build()
        }
        else{
            return Response.status(500).tag(portalResponse).build()
        }
    }

    @POST @Path("logout")
    @Consumes(APPLICATION_JSON)
    fun logOutUser(user: User): Response{
        val backEndPortal = backendInterface
        println("SERVER CONTROLLER: logging out ${user.userName}")
        val portalResponse = backEndPortal.logOut(user.userName)
        println("SERVER CONTROLLER: $portalResponse")
        if(portalResponse == "logged out") {
            return Response.status(200).tag(portalResponse).build()
        }
        else return Response.status(500).tag(portalResponse).build()
    }

    @GET @Path("getsurvey/{surveyType}")
    fun clientGetSurvey(@PathParam("surveyType")surveyType: String): List<Any> {
        val backEndPortal = backendInterface
        println("SERVER CONTROLLER: Client request + $surveyType")
        return backEndPortal.getSurvey(surveyType)
    }

    @POST @Path("/submit")
    @Consumes(APPLICATION_JSON)
    fun submitAnswersToBackend(answerSubmission: NetworkMessageSubmitAnswers): Response{
        println("SERVER CONTROLLER: Client attempting to submit answers")
        val backEndPortal = backendInterface
       val backendResponse=  backEndPortal.saveResponse(answerSubmission.username,
                answerSubmission.surveyName,
                answerSubmission.responseList)
        println("SERVER CONTROLLER: $backendResponse")
        if(backendResponse == "No such Survey Type exists" ||
                backendResponse == "Survey Size and Response size doesn't match!"){
            return Response.status(500).tag(backendResponse).build()
        }
        else if(backendResponse == "User is not logged in."){
            return Response.status(403).tag(backendResponse).build()
        }
        else return Response.status(200).tag(backendResponse).build()
    }

    @GET @Path("/list")
    fun getListOfSurveys(): List<String>{
        println("SERVER CONTROLLER: Client requesting list of surveys")
        val backEndPortal = backendInterface
        return backEndPortal.getSurveyList()
    }

    @POST @Path("forgotpassword")
    @Consumes(APPLICATION_JSON)
    fun recoverPassword(forgotPasswordPayload: NetworkMessageForgotPassword): Response{
        println("SERVER CONTROLLER: Attempting to recover password")
        val backEndPortal = backendInterface
        val backEndResponse = backEndPortal.forgotPassword(forgotPasswordPayload.email,
                forgotPasswordPayload.birthday)
        if(backEndResponse){
           return Response.status(200).entity("OK").build()
        }
        return Response.status(403).entity("Something went wrong").build()
    }

}

//Admin only functions, requires admin login details
@Path("admin")
@Produces(APPLICATION_JSON)
class AdminResource{

    @POST @Path("/addSurvey")
    @Consumes(APPLICATION_JSON)
    fun addSurvey(addSurveySubmission: NetworkMessageAddSurvey): Response{
        println("SERVER CONTROLLER: Attempting to add/update survey")
        val backEndPortal = backendInterface
        val backEndResponse = backEndPortal.addSurvey(addSurveySubmission.user,
                addSurveySubmission.surveyType,
                addSurveySubmission.questions)
        println("SERVER CONTROLLER: $backEndResponse")
        if(backEndResponse == "You are not Authorised to modify the survey"){
            return Response.status(403).tag(backEndResponse).build()
        }
        return Response.status(200).tag(backEndResponse).build()
    }

    @POST @Path("/addSurveyMC")
    @Consumes(APPLICATION_JSON)
    fun addMCSurvey(addSurveySubmission: NetworkMessageAddMCSurvey): Response?{
        println("SERVER CONTROLLER: Attempting to add/update multiple choice survey")
        val backEndPortal = backendInterface
        val backEndResponse = backEndPortal.addMCsurvey(addSurveySubmission.user,
                addSurveySubmission.surveyType,
                addSurveySubmission.questionsWithOptions)
        println("SERVER CONTROLLER: $backEndResponse")
        if(backEndResponse == "You are not Authorised to modify the survey"){
            return Response.status(403).tag(backEndResponse).build()
        }
        return Response.status(200).tag(backEndResponse).build()
    }
}

class JerseyApplication: Application() {
    override fun getSingletons(): MutableSet<Any> {
        return mutableSetOf(TestResource(), ClientResource(), AdminResource())
    }
}

object NettyServer {
    @JvmStatic
    fun main(args: Array<String>) {
        // val resourceConfig = ResourceConfig.forApplication(JerseyApplication())
        val resourceConfig = ResourceConfig.forApplication(JerseyApplication())
                .register(
                        ContextResolver<ObjectMapper> {
                            ObjectMapper().registerModule(KotlinModule()) }
                )
        val server = NettyHttpContainerProvider.createHttp2Server(URI.create("http://localhost:8080/"), resourceConfig, null)
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { server.close() }))
    }
}



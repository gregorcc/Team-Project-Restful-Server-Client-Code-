package kathiresh.pandian.uiowa.surveys

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.request.JsonObjectRequest
import com.android.volley.toolbox.VolleyTickle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
/**
 * @author Greg Mich
 * Implementation of the FrontEndNetworking Interface for use by the GUI specialist
 * see app.java.kathiresh.pandian.uiowa.surveys.FrontEndNetworkingAndroid for documentation and more
 * information on how to use the interface
 */
class FrontEndNetworkAndroidAPI: FrontEndNetworkingAndroid{
    private val URL: String
    //default network address
    constructor(url: String = "http://10.0.2.2:8080/"){
        this.URL = url
    }

    override fun frontEndLogin(username: String, password: String, P: Context): Boolean {
        //only username and password are important, rest are dummy fields
        val loginUser = User(username,"null","null",password,"00/00/0000")
        val tickleRequest = VolleyTickle.newRequestTickle(P)
        val jsonMapper = Gson()
        val jsonString = jsonMapper.toJson(loginUser)
        val sendJson = JSONObject(jsonString)
        val stringRequest = JsonObjectRequest(Request.Method.POST, URL + "client/login", sendJson,
                Response.Listener { },
                Response.ErrorListener { })
        tickleRequest.add(stringRequest)
        val response = tickleRequest.start()
        Log.v(null,response.statusCode.toString())
        val networkResponseCode = response.statusCode
        //http status code 200 = ok
        if(networkResponseCode == 200){
            return true
        }
        return false
    }

    override fun createNewUser(newUser: User, P: Context): Boolean {
        val newRequestTickle = VolleyTickle.newRequestTickle(P)
        val jsonMapper = Gson()
        val jsonString = jsonMapper.toJson(newUser)
        val sendJson = JSONObject(jsonString)
        val stringRequest = JsonObjectRequest(Request.Method.POST, URL + "client/createnewuser", sendJson,
                Response.Listener {  },
                Response.ErrorListener {  })
        newRequestTickle.add(stringRequest)
        val response = newRequestTickle.start()
        Log.v(null,response.statusCode.toString())
        val networkResponseCode = response.statusCode
        if(networkResponseCode == 200){
            return true
        }
        return false
    }

    override fun frontEndLogOutUser(userName: String, P: Context): Boolean {
        //only username is important, rest are dummy fields
        val logoutUser = User(userName,"null","null","null","00/00/0000")
        val newRequestTickle = VolleyTickle.newRequestTickle(P)
        val jsonMapper = Gson()
        val jsonString = jsonMapper.toJson(logoutUser)
        val sendJson = JSONObject(jsonString)
        val stringRequest = JsonObjectRequest(Request.Method.POST, URL + "client/logout", sendJson,
                Response.Listener {  },
                Response.ErrorListener {  })
        newRequestTickle.add(stringRequest)
        val response = newRequestTickle.start()
        Log.v(null,response.statusCode.toString())
        val networkResponseCode = response.statusCode
        if(networkResponseCode == 200){
            return true
        }
        return false
    }

    override fun frontEndSubmitAnswers(username: String,surveyType:String, answers: List<String>,P: Context): Boolean {
        val answerSubmission = NetworkMessageSubmitAnswers(username, surveyType, answers)
        val newRequestTickle = VolleyTickle.newRequestTickle(P)
        val jsonMapper = Gson()
        val jsonString = jsonMapper.toJson(answerSubmission)
        val sendJson = JSONObject(jsonString)
        val stringRequest = JsonObjectRequest(Request.Method.POST, URL + "client/submit", sendJson,
                Response.Listener{ },
                Response.ErrorListener {  })
        newRequestTickle.add(stringRequest)
        val response = newRequestTickle.start()
        val networkResponseCode = response.statusCode
        if(networkResponseCode == 200){
            return true
        }
        return false
    }

    override fun frontEndGetSurveyList(P: Context): List<String> {
        val newRequestTickle = VolleyTickle.newRequestTickle(P)
        val sendJson = JSONObject()
        val stringRequest = JsonObjectRequest(Request.Method.GET, URL + "client/list", sendJson,
                Response.Listener { },
                Response.ErrorListener { })
        newRequestTickle.add(stringRequest)
        val response = newRequestTickle.start()
        Log.v("STATUS CODE:", response.statusCode.toString())
            val data = VolleyTickle.parseResponse(response)
            Log.v("DATA:", data)
        val mapper = Gson()
        val conversionType = object: TypeToken<List<String>>(){ }.type
        return mapper.fromJson(data,conversionType)
    }

    override fun frontEndGetSurvey(surveyType: String, P: Context): List<Any> {
        val newRequestTickle = VolleyTickle.newRequestTickle(P)
        val sendJson = JSONObject()
        val stringRequest = JsonObjectRequest(Request.Method.GET, URL + "client/getsurvey/$surveyType", sendJson,
                Response.Listener { },
                Response.ErrorListener { })
        newRequestTickle.add(stringRequest)
        val response = newRequestTickle.start()
        Log.v("STATUS CODE:", response.statusCode.toString())
        val data = VolleyTickle.parseResponse(response)
        Log.v("DATA:", data)
        val mapper = Gson()
        val conversionType = object: TypeToken<List<Any>>(){ }.type
        return mapper.fromJson(data,conversionType)
    }

    override fun recoverPassword(email: String, birthday: String, P: Context): Boolean {
        val answerSubmission = NetworkMessageForgotPassword(email, birthday)
        val newRequestTickle = VolleyTickle.newRequestTickle(P)
        val jsonMapper = Gson()
        val jsonString = jsonMapper.toJson(answerSubmission)
        val sendJson = JSONObject(jsonString)
        val stringRequest = JsonObjectRequest(Request.Method.POST, URL + "client/forgotpassword", sendJson,
                Response.Listener{ },
                Response.ErrorListener {  })
        newRequestTickle.add(stringRequest)
        val response = newRequestTickle.start()
        val networkResponseCode = response.statusCode
        if(networkResponseCode == 200){
            return true
        }
        return false
    }

}
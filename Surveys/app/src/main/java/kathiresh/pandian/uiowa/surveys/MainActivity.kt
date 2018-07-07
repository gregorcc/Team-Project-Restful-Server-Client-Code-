package kathiresh.pandian.uiowa.surveys

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    lateinit var error_msg: TextView
    lateinit var log_in: Button
    lateinit var username: EditText
    lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        setContentView(R.layout.activity_main)
        //get necessary user inputs and validate before
        log_in = findViewById(R.id.button_sign_in);
        username= findViewById(R.id.editTextusername)
        password= findViewById(R.id.editTextpassword)
        error_msg= findViewById(R.id.textViewerrormsg)
        log_in.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View){
                validate(username.getText().toString(), password.getText().toString())

            }
        })

    }

    fun newUser(view: View) {
        val newUser = Intent(this, newuser::class.java)
        startActivity(newUser)
    }
    //either returns error message or successfully allows user to login
    fun validate( username: String , password: String){
        val networkPortal = FrontEndNetworkAndroidAPI()
        val right_user: Boolean = networkPortal.frontEndLogin(username, password, this@MainActivity)
        val senduser =Intent(this,surveymenu::class.java)

        if (right_user){
            senduser.putExtra("Username",username)
            startActivity(senduser)


            finish()

        }
        else{
            error_msg.text= ("Wrong Username and/or Password")
        }
    }

    override fun onBackPressed() {

    }
}

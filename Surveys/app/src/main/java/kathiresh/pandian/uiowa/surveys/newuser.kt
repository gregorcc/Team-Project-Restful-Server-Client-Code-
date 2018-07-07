package kathiresh.pandian.uiowa.surveys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class newuser : AppCompatActivity() {
    lateinit var confirmPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newuser)


        val email = findViewById<EditText>(R.id.editemailaddress)
        val password = findViewById<EditText>(R.id.editpassword)
        confirmPassword = findViewById(R.id.editconfirmpassword)
        val d_o_b = findViewById<EditText>(R.id.editdob)
        val first_name = findViewById<EditText>(R.id.editfirstname)
        val last_name = findViewById<EditText>(R.id.editlastname)
        val buttonsubmit = findViewById<Button>(R.id.buttonnewuser)
        val error_msg = findViewById<TextView>(R.id.error_msgtextView)

        fun checkUser(user: User): Boolean {
            //checkUser info to make sure it is passable
            var flag=false

            when {
                (user.birthday.isBlank() || user.firstName.isBlank() ||
                        user.lastName.isBlank() || user.password.isBlank() || user.userName.isBlank())-> error_msg.text = ("Completely Fill out Form")
                user.password != confirmPassword.text.toString() -> error_msg.text= ("Confirm password does not match password")
                !user.userName.matches("^[a-z0-9](\\.?[a-z0-9_-]){0,}@[a-z0-9-]+\\.([a-z]{1,6}\\.)?[a-z]{2,6}\$".toRegex()) -> error_msg.text=("Username is not an email address")

                else -> flag=true
            }
           return flag
        }

        fun goto_login_page(view: View) {
            val goto_login_page = Intent(this, MainActivity::class.java)
            startActivity(goto_login_page)
        }

        fun create_new_user(email: EditText, password: EditText, d_o_b: EditText, first_name: EditText, last_name: EditText, view: View) {

            val networkPortal = FrontEndNetworkAndroidAPI()
            val new_user = User(email.text.toString(), first_name.text.toString(), last_name.text.toString(), password.text.toString(), d_o_b.text.toString())

            if (checkUser(new_user)) {
                Log.d("NewUser", "Successful User Creation")
                val success = networkPortal.createNewUser(new_user, this@newuser)
                goto_login_page(view)
            }
            Log.d("NewUser", "Creation Unsuccessful")

        }

        buttonsubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                create_new_user(email, password, d_o_b, first_name, last_name, view)

            }
        })

    }

    }


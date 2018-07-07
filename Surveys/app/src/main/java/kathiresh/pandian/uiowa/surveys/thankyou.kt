package kathiresh.pandian.uiowa.surveys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class thankyou : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)
        val backtosurvey=findViewById<Button>(R.id.buttonmenu)

        backtosurvey.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View) {
               surveys(view)
            }
        })
    }
    fun surveys(view: View) {

        val surveylist = Intent(this, surveymenu::class.java)
        startActivity(surveylist)
    }

}

package com.example.bookhub.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class DescriptionActivity : AppCompatActivity() {

    lateinit var txtBookName : TextView
    lateinit var txtBookAuthor : TextView
    lateinit var txtBookPrice : TextView
    lateinit var txtBookRatings : TextView
    lateinit var txtBookDesc : TextView
    lateinit var imgBookImage : ImageView
    lateinit var btnAddToFavorites : Button
    lateinit var progressLayout : RelativeLayout
    lateinit var progressBar : ProgressBar

    var bookId : String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        txtBookName = findViewById(R.id.txtBookName)
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookRatings = findViewById(R.id.txtBookRating)
        imgBookImage = findViewById(R.id.imgBookImage)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        progressLayout = findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        btnAddToFavorites = findViewById(R.id.btnAddToFavorites)

        if (intent != null){
            bookId = intent.getStringExtra("book_id")
        }else{
            finish()
            Toast.makeText(this@DescriptionActivity, "Unexpected error", Toast.LENGTH_SHORT).show()
        }
        if (bookId == "100"){
            finish()
            Toast.makeText(this@DescriptionActivity, "Unexpected error", Toast.LENGTH_SHORT).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        val jsonRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
            /*handling the response*/
            try {
                val success = it.getBoolean("success")
                if (success){
                    val bookJsonObject = it.getJSONObject("book_data")
                    progressLayout.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    txtBookName.text = bookJsonObject.getString("name")
                    txtBookAuthor.text = bookJsonObject.getString("author")
                    txtBookPrice.text = bookJsonObject.getString("price")
                    txtBookRatings.text = bookJsonObject.getString("rating")
                    txtBookDesc.text = bookJsonObject.getString("description")
                    Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.default_book_cover).into(imgBookImage)
                }else{
                    Toast.makeText(this@DescriptionActivity, "Some error occurred!!!", Toast.LENGTH_SHORT).show()
                }

            }catch (e : JSONException){
                Toast.makeText(this@DescriptionActivity, "Some error occurred!!!", Toast.LENGTH_SHORT).show()
            }
        }, Response.ErrorListener {
            /*handling the error*/
            Toast.makeText(this@DescriptionActivity, "Volley error $it", Toast.LENGTH_SHORT).show()
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] =  "090874971084d4"
                return headers
            }
        }
        queue.add(jsonRequest)
    }
}
package com.example.bookhub.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.adapter.DashboardRecyclerAdapter
import com.example.bookhub.model.Book

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard : RecyclerView
    lateinit var layoutManager : RecyclerView.LayoutManager
    val bookList = arrayListOf("P.S. I love You","The Great Gatsby","Anna Karenina","Madame Bovary","War and Peace","Lolita","Middlemarch"
        ,"The Adventures of Huckleberry Finn","Moby-Dick","The Lord of the Rings")
    lateinit var recyclerAdapter : DashboardRecyclerAdapter
    lateinit var progressLayout : RelativeLayout
    lateinit var progressBar : ProgressBar

    val bookInfoList = ArrayList<Book>()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        progressLayout = view.findViewById(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        progressBar = view.findViewById(R.id.progressBar)

        /*
        btnCheckInternet = view.findViewById(R.id.btnCheckInternet)
        btnCheckInternet.setOnClickListener {
            if (ConnectionManager().checkConnectivity(activity as Context)){
                //internet is available
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("success")
                dialog.setMessage("internet connection found")
                dialog.setPositiveButton("ok"){
                    text, listener ->
                }

                dialog.setNegativeButton("cancel"){
                    text, listener ->
                }
                dialog.create()
                dialog.show()

            }else{ //internet is not available
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("error")
                dialog.setMessage("internet connection not found")
                dialog.setPositiveButton("ok"){
                        text, listener ->
                }

                dialog.setNegativeButton("cancel"){
                        text, listener ->
                }
                dialog.create()
                dialog.show()
            }
        }
        */

        /*---------------------------------JSON Parsing-------------------------------------------*/
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books/"

        if (ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                //handling the response
                try {
                    progressLayout.visibility = View.GONE
                    println("response is $it")
                    val success = it.getBoolean("success")

                    if (success){
                        val data = it.getJSONArray("data")

                        for (i in 0 until data.length()){
                            val bookJsonObject = data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")
                            )
                            bookInfoList.add(bookObject)

                            recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
                            layoutManager = LinearLayoutManager(activity)
                            recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)
                            recyclerDashboard.adapter = recyclerAdapter
                            recyclerDashboard.layoutManager = layoutManager

                            /*
                            recyclerDashboard.addItemDecoration(
                                DividerItemDecoration(
                                    recyclerDashboard.context,
                                    (layoutManager as LinearLayoutManager).orientation
                                )
                            )
                             */

                        }
                    }else{
                        Toast.makeText(activity as Context, "Some error occurred!!!", Toast.LENGTH_SHORT).show()
                    }
                }catch (e : JSONException){
                    Toast.makeText(activity as Context,"Some JSON exception has occurred", Toast.LENGTH_SHORT).show()
                }

                }, Response.ErrorListener {
                //handling the errors
                Toast.makeText(activity as Context, "Volley error has occurred", Toast.LENGTH_SHORT).show()
                println("error is $it")
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "090874971084d4"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("error")
            dialog.setMessage("internet connection not found")
            dialog.setPositiveButton("Open Settings"){
                    text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }

            dialog.setNegativeButton("Exit App"){
                    text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
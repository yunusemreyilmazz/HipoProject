package com.example.hipoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        run_app()
    }

    fun run_app(){
        var array= arrayListOf<String>()
        val search=findViewById<SearchView>(R.id.search_view)
        var json: String?=null
        try {
            val inputStream: InputStream =assets.open("Hipo.json")
            json=inputStream.bufferedReader().use{it.readText()}

            val root= JSONObject(json)
            val jsonarr=root.getJSONArray("members")



            for(i in 0..jsonarr.length()-1){
                var jsonobj=jsonarr.getJSONObject(i)
                var obj=jsonobj.getString("name")
                array.add(obj)

            }
            var adpt= ArrayAdapter(this,android.R.layout.simple_list_item_1,array)
            val listV = findViewById<ListView>(R.id.list_v) as ListView
            listV.adapter=adpt

            search.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if(array.contains(p0)){
                        adpt.filter.filter(p0)
                    }else{
                        Toast.makeText(this@MainActivity,"not found",Toast.LENGTH_LONG).show()
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    adpt.filter.filter(p0)
                    return false
                }

            })
            var button:Button
            var editText:EditText
            button=findViewById(R.id.button1)
            editText=findViewById(R.id.editText)

            button.setOnClickListener{
                array.add(editText.text.toString())
                editText.setText("")
                adpt.notifyDataSetChanged()
                listV.adapter=adpt
            }

        }
        catch (e: IOException){

        }
    }
}
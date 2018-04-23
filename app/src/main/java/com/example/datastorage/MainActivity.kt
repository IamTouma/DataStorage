package com.example.datastorage

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val save: Button = findViewById(R.id.button_save)
        save.setOnClickListener({
            val editText: EditText = findViewById(R.id.text_save)
            var text: String = editText.text.toString()
            saveFile("testfile.txt", text)
        })

        val load: Button = findViewById(R.id.button_load)
        load.setOnClickListener({
            var text: String? = readFile("testfile.txt")
            val editText: EditText = findViewById(R.id.text_load)
            editText.setText(text)
        })
    }

    private fun saveFile(file: String, value: String) {
        try  {
            openFileOutput(file, Context.MODE_PRIVATE).use {
                it.write(value.toByteArray())
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFile(file: String): String {
        var text = ""

        try  {
            openFileInput(file).use { fileInputStream ->
                InputStreamReader(fileInputStream, "UTF-8").use { inputStreamReader ->
                    BufferedReader(inputStreamReader).use { bufferedReader ->
                        do {
                            var lineBuffer: String? = bufferedReader.readLine()
                            if (lineBuffer != null) {
                                if (text.isNotEmpty())
                                    text += '\n'
                                text += lineBuffer.toString()
                            }
                        } while (lineBuffer != null)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return text
    }
}

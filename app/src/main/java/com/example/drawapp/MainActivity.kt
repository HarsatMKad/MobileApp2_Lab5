package com.example.drawapp

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.get
import com.squareup.picasso.Picasso
import java.io.File


val REQUEST_IMAGE = 1

class MainActivity : AppCompatActivity() {
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE){
            val uri: Uri? = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

            val dv = findViewById<DrawingView>(R.id.drawingView)
            dv.loadBackground(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dv = findViewById<DrawingView>(R.id.drawingView)
        val blackBnt = findViewById<Button>(R.id.blackBtn)
        val redBtn = findViewById<Button>(R.id.redBtn)
        val greenBtn = findViewById<Button>(R.id.greenBtn)
        val blueBtn = findViewById<Button>(R.id.blueBtn)
        val numberText = findViewById<EditText>(R.id.editTextNumber)
        val thicBtn = findViewById<Button>(R.id.thicBtn)
        val loadBtn = findViewById<Button>(R.id.LoadButton)
        val uploadBtn = findViewById<Button>(R.id.uploadButton)

        blackBnt.setOnClickListener{
            dv.changeColor(Color.BLACK)
        }

        redBtn.setOnClickListener{
            dv.changeColor(Color.RED)
        }

        greenBtn.setOnClickListener{
            dv.changeColor(Color.GREEN)
        }

        blueBtn.setOnClickListener{
            dv.changeColor(Color.BLUE)
        }

        thicBtn.setOnClickListener{
            val text = numberText.text.toString().toFloat()
            try {
                dv.changeThickness(text)
            }
            catch (e: Exception){
                Log.v("thic error", e.message!!)
            }
        }

        loadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE)
        }

        uploadBtn.setOnClickListener {
            val popupMenu = PopupMenu(this@MainActivity, uploadBtn)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->

                if(menuItem.title == "Save"){
                    Toast.makeText(this, menuItem.title, Toast.LENGTH_SHORT).show()
                }
                if(menuItem.title == "Share"){

                }
                true
            }
            popupMenu.show()
        }
    }
}
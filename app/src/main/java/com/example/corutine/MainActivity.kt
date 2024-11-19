package com.example.corutine
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        gridLayout.rowCount = 3
        gridLayout.columnCount = 3

        CoroutineScope(Dispatchers.IO).launch {
            val imageUrls = getImageUrls()

            withContext(Dispatchers.Main) {
                for (url in imageUrls) {
                    val imageView = ImageView(this@MainActivity)
                    val params = GridLayout.LayoutParams()
                    params.width = GridLayout.LayoutParams.WRAP_CONTENT
                    params.height = GridLayout.LayoutParams.WRAP_CONTENT
                    imageView.layoutParams = params

                    Picasso.get().load(url).into(imageView)

                    gridLayout.addView(imageView)
                }
            }
        }
    }

    private suspend fun getImageUrls(): List<String> {
        return withContext(Dispatchers.IO) {
            val baseUrl = "http://cti.ubm.ro/cmo/digits/"
            (0..9).map { "$baseUrl/img$it.jpg" }
        }
    }
}
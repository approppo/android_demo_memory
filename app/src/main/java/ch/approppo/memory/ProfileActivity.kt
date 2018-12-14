package ch.approppo.memory

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File


class ProfileActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1

        private const val LOG_TAG = "ProfileActivity"

        private const val PROFILE_IMAGE_FILE_NAME = "profile_image.png"
        fun newIntent(ctx: Context) = Intent(ctx, ProfileActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bt_take_photo.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        val imageBitmap = BitmapFactory.decodeFile(File(filesDir, PROFILE_IMAGE_FILE_NAME).absolutePath)
        imageBitmap?.let {
            iv_profile_image.setImageBitmap(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.onboarding_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.next) {
            startActivity(MainActivity.newIntent(this))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            val file = File(filesDir, PROFILE_IMAGE_FILE_NAME)
            if (imageBitmap != null) {
                file.outputStream().use {
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                }
                iv_profile_image.setImageBitmap(imageBitmap)
            } else {
                Log.w(LOG_TAG, "ImageBitmap from intent was null")
            }
        }
    }
}

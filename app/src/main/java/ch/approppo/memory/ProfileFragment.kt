package ch.approppo.memory

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import java.io.File

class ProfileFragment : Fragment() {

    companion object {
        fun newFragment(): Fragment = ProfileFragment()
    }

    private lateinit var callback: OnboardingFlowCallback

    private lateinit var btTakeFoto: Button
    private lateinit var ivImage: ImageView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlowCallback ?: throw IllegalStateException("Host must implement OnboardingFlowCallback")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        btTakeFoto = view.findViewById(R.id.bt_take_foto)
        btTakeFoto.setOnClickListener { takeFoto() }
        ivImage = view.findViewById(R.id.iv_profile_image)
        val file = File(requireContext().filesDir, "profile_image.png")
        if (file.exists()) {
            file.inputStream().use {
                val bitmap = BitmapFactory.decodeStream(it)
                ivImage.setImageBitmap(bitmap)
            }
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.next) {
            callback.nextFromProfile()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun takeFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data") as? Bitmap
            image?.let { bitmap ->
                ivImage.setImageBitmap(bitmap)
                val file = File(requireContext().filesDir, "profile_image.png")
                file.outputStream().use { os ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
                }
            }
        }
    }
}

package space.pal.sig.view.apod

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.ActivityApodBinding
import space.pal.sig.model.entity.AstronomyPictureOfTheDay
import space.pal.sig.util.DateExtensions.displayDate
import java.util.*

class ApodActivity : AppCompatActivity() {

    companion object {
        const val APOD_DATE = "apod_date"
    }

    private lateinit var binding: ActivityApodBinding
    private var apod: AstronomyPictureOfTheDay? = null
    private var date: Date? = null
    private val viewModel: ApodViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apod)
        title = ""
        setSupportActionBar(binding.apodToolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        date = intent.getSerializableExtra(APOD_DATE) as Date?
        viewModel.submitDate(date)
        viewModel.getAstronomyPictureOfTheDay()
                .observe(this) { showApod(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_image, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.description -> {
                if (apod != null) {
                    val dialog: Dialog = AlertDialog
                            .Builder(this, R.style.SpaceNow_Dialog)
                            .setTitle(apod?.title)
                            .setMessage(apod?.explanation?.let { HtmlCompat.fromHtml(it, 0) })
                            .setPositiveButton(R.string.ok) { dialogInterface, _ ->
                                dialogInterface.dismiss()
                            }.create()
                    dialog.show()
                } else {
                    Toast.makeText(
                            this,
                            R.string.no_apod_label,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.download -> {
                if (apod != null) {
                    val writePermission = ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED
                    if (writePermission) {
                        viewModel.downloadImage(apod?.hdUrl)
                    } else {
                        ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                1)
                    }
                } else {
                    Toast.makeText(
                            this,
                            R.string.no_apod_label,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.select_another -> {
                date?.let {
                    val calendar = Calendar.getInstance()
                    calendar.time = it
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val dialog = DatePickerDialog(this, R.style.SpaceNow_Dialog,
                            { _: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                                val date = Calendar.getInstance()
                                date[Calendar.YEAR] = selectedYear
                                date[Calendar.MONTH] = selectedMonth
                                date[Calendar.DAY_OF_MONTH] = selectedDayOfMonth
                                this.date = date.time
                                viewModel.submitDate(this.date)
                            }, year, month, day)
                    dialog.show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String?>,
                                            grantResults: IntArray) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            apod?.let { viewModel.downloadImage(it.hdUrl) }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showApod(apod: AstronomyPictureOfTheDay?) {
        this.apod = apod
        title = date?.displayDate()
        if (apod != null) {
            binding.apodLoadingLabel.isVisible = true
            binding.apodImage.contentDescription = apod.title
            Picasso.get().load(apod.url).centerCrop().fit()
                    .into(binding.apodImage, object : Callback {
                        override fun onSuccess() {
                            binding.apodLoadingLabel.isVisible = false
                        }

                        override fun onError(e: Exception?) {}
                    })
            binding.apodImage.isVisible = true
            binding.apodLabel.isVisible = false
        } else {
            binding.apodImage.isVisible = false
            binding.apodLabel.isVisible = true
        }
    }
}
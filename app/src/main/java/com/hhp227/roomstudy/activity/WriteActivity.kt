package com.hhp227.roomstudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import com.hhp227.roomstudy.R
import com.hhp227.roomstudy.databinding.ActivityWriteBinding
import androidx.databinding.DataBindingUtil.setContentView
import com.hhp227.roomstudy.activity.MainActivity.Companion.ID_MEMO
import com.hhp227.roomstudy.activity.MainActivity.Companion.REQUEST_CODE
import com.hhp227.roomstudy.activity.MainActivity.Companion.REQUEST_CODE_ADD
import com.hhp227.roomstudy.activity.MainActivity.Companion.REQUEST_CODE_SET
import com.hhp227.roomstudy.activity.MainActivity.Companion.TEXT_MEMO
import com.hhp227.roomstudy.activity.MainActivity.Companion.TITLE_MEMO

class WriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityWriteBinding>(this, R.layout.activity_write).apply {
            when (intent.getIntExtra(REQUEST_CODE, -1)) {
                REQUEST_CODE_ADD -> {
                    textButton1 = getString(R.string.text_add)
                    textButton2 = getString(R.string.text_close)
                }
                REQUEST_CODE_SET -> {
                    textButton1 = getString(R.string.text_set)
                    textButton2 = getString(R.string.text_remove)
                }
            }
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            button1.setOnClickListener {
                val title = etInputTitle.text
                val text = etInputText.text

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(text)) {
                    Toast.makeText(
                        baseContext,
                        if (title.isEmpty()) getString(R.string.title_hint) else getString(R.string.text_hint),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Intent(applicationContext, MainActivity::class.java).putExtra(TITLE_MEMO, title.toString())
                        .putExtra(TEXT_MEMO, text.toString())
                        .also { intent ->
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                }
            }
            button2.setOnClickListener {
                if (intent.getIntExtra(REQUEST_CODE, -1) == REQUEST_CODE_SET) {
                    setResult(RESULT_REMOVE, Intent(baseContext, MainActivity::class.java).putExtra(ID_MEMO, intent.getIntExtra(ID_MEMO, -1)))
                }
                finish()
            }
            Toast.makeText(applicationContext, "id: ${intent.getIntExtra(ID_MEMO, -1)}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val RESULT_REMOVE = 0
    }
}
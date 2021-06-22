package com.hhp227.roomstudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.hhp227.roomstudy.R
import com.hhp227.roomstudy.databinding.ActivityWriteBinding
import androidx.databinding.DataBindingUtil.setContentView
import com.hhp227.roomstudy.activity.MainActivity.Companion.ID_MEMO
import com.hhp227.roomstudy.activity.MainActivity.Companion.REQUEST_CODE
import com.hhp227.roomstudy.activity.MainActivity.Companion.REQUEST_ADD
import com.hhp227.roomstudy.activity.MainActivity.Companion.REQUEST_SET
import com.hhp227.roomstudy.activity.MainActivity.Companion.TEXT_MEMO
import com.hhp227.roomstudy.activity.MainActivity.Companion.TITLE_MEMO

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView<ActivityWriteBinding>(this, R.layout.activity_write).apply {
            when (intent.getIntExtra(REQUEST_CODE, -1)) {
                REQUEST_ADD -> {
                    textButton1 = getString(R.string.text_add)
                    textButton2 = getString(R.string.text_close)
                }
                REQUEST_SET -> {
                    textButton1 = getString(R.string.text_set)
                    textButton2 = getString(R.string.text_remove)
                }
            }
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setOnClickListener(::onClick)
            etInputTitle.setText(intent.getStringExtra(TITLE_MEMO))
            etInputText.setText(intent.getStringExtra(TEXT_MEMO))
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

    private fun onClick(v: View) {
        when (v.id) {
            R.id.button1 -> {
                val title = binding.etInputTitle.text
                val text = binding.etInputText.text

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(text)) {
                    Toast.makeText(
                        baseContext,
                        if (title.isEmpty()) getString(R.string.title_hint) else getString(R.string.text_hint),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Intent(applicationContext, MainActivity::class.java).putExtra(REQUEST_CODE, intent.getIntExtra(REQUEST_CODE, -1))
                        .putExtra(ID_MEMO, intent.getIntExtra(ID_MEMO, -1))
                        .putExtra(TITLE_MEMO, title.toString())
                        .putExtra(TEXT_MEMO, text.toString())
                        .also { intent ->
                            setResult(
                                when (intent.getIntExtra(REQUEST_CODE, -1)) {
                                    REQUEST_ADD -> RESULT_ADD
                                    REQUEST_SET -> RESULT_SET
                                    else -> -1
                                },
                                intent
                            )
                            finish()
                        }
                }
            }
            R.id.button2 -> {
                if (intent.getIntExtra(REQUEST_CODE, -1) == REQUEST_SET) {
                    setResult(RESULT_REMOVE, Intent(baseContext, MainActivity::class.java).putExtra(ID_MEMO, intent.getIntExtra(ID_MEMO, -1)))
                }
                finish()
            }
        }
    }

    companion object {
        const val RESULT_ADD = 0
        const val RESULT_SET = 1
        const val RESULT_REMOVE = 2
    }
}
package com.hhp227.roomstudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.hhp227.roomstudy.R
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hhp227.roomstudy.activity.WriteActivity.Companion.RESULT_ADD
import com.hhp227.roomstudy.activity.WriteActivity.Companion.RESULT_REMOVE
import com.hhp227.roomstudy.activity.WriteActivity.Companion.RESULT_SET
import com.hhp227.roomstudy.adapter.MemoAdapter
import com.hhp227.roomstudy.data.AppDatabase
import com.hhp227.roomstudy.data.MainRepository
import com.hhp227.roomstudy.databinding.ActivityMainBinding
import com.hhp227.roomstudy.model.MemoDto
import com.hhp227.roomstudy.viewmodel.MainViewModel
import com.hhp227.roomstudy.viewmodel.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository.getInstance(AppDatabase.getInstance(this).memoDao()))
        ).get(MainViewModel::class.java)
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.let { intent ->
            when (result.resultCode) {
                RESULT_ADD -> mainViewModel.insertMemo(
                    MemoDto(
                        title = intent.getStringExtra(TITLE_MEMO) ?: "",
                        text = intent.getStringExtra(TEXT_MEMO) ?: ""
                    )
                )
                RESULT_SET -> getMemo(intent.getIntExtra(ID_MEMO, -1)) { memoDto ->
                    memoDto.title = intent.getStringExtra(TITLE_MEMO) ?: ""
                    memoDto.text = intent.getStringExtra(TEXT_MEMO) ?: ""

                    mainViewModel.updateMemo(memoDto)
                }
                RESULT_REMOVE -> getMemo(intent.getIntExtra(ID_MEMO, -1)) { memoDto ->
                    mainViewModel.deleteMemo(memoDto)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            setSupportActionBar(toolbar)
            recyclerView.apply {
                adapter = MemoAdapter().apply {
                    mainViewModel.memoList.observe(this@MainActivity) { list ->
                        submitList(list)
                    }
                    setOnItemClickListener { _, p ->
                        Intent(baseContext, WriteActivity::class.java).putExtra(REQUEST_CODE, REQUEST_SET)
                            .putExtra(ID_MEMO, currentList[p].id)
                            .putExtra(TITLE_MEMO, currentList[p].title)
                            .putExtra(TEXT_MEMO, currentList[p].text)
                            .also { intent ->
                                activityResultLauncher.launch(intent)
                            }
                    }
                }
            }
            fab.setOnClickListener {
                activityResultLauncher.launch(Intent(baseContext, WriteActivity::class.java).putExtra(REQUEST_CODE, REQUEST_ADD))
            }
        }
    }

    private fun getMemo(id: Int, result: (MemoDto) -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {
            result(mainViewModel.selectMemo(id))
        }
    }

    companion object {
        const val REQUEST_ADD = 0
        const val REQUEST_SET = 1
        const val REQUEST_CODE = "code"
        const val ID_MEMO = "id"
        const val TITLE_MEMO = "title"
        const val TEXT_MEMO = "text"
    }
}
package com.hhp227.roomstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hhp227.roomstudy.data.MainRepository
import com.hhp227.roomstudy.model.MemoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val memoList = repository.getMemoList()

    val test = "테스트"

    fun selectMemo(id: Int, result: (MemoDto) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            result(repository.getMemo(id))
        }
    }

    fun insertMemo(memoDto: MemoDto) {
        viewModelScope.launch {
            repository.addMemo(memoDto)
        }
    }

    fun updateMemo(memoDto: MemoDto) {
        viewModelScope.launch {
            repository.setMemo(memoDto)
        }
    }

    fun deleteMemo(memoDto: MemoDto) {
        viewModelScope.launch {
            repository.removeMemo(memoDto)
        }
    }
}
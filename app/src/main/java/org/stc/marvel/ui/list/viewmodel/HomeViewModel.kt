package org.stc.marvel.ui.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.stc.marvel.data.model.CharacterItem
import org.stc.marvel.data.repo.MarvelRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {

    var isFirstLoading = true
    private val _characterDetails = MutableStateFlow<CharacterItem?>(null)
    val characterDetails: StateFlow<CharacterItem?> get() = _characterDetails

    private val _characterList = MutableStateFlow<PagingData<CharacterItem>>(PagingData.empty())
    val characterList: StateFlow<PagingData<CharacterItem>> get() = _characterList

    init {
        loadCharacters()
    }
     fun loadCharacters() {
        viewModelScope.launch {
            repository.searchCharacters(10, 0)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _characterList.value = pagingData
                }
        }
    }
}

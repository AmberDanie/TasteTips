package com.example.tastetips.model

data class TasteTipsState(
    val loginShellIsOpen: Boolean = false,
    val registerShellIsOpen: Boolean = false,
    val isLoading: Boolean = true,
    val isChoosable: Boolean = false,
    val searchText: String = ""
)
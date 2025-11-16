package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.factory.PurchaseTaxiFactory
import com.example.myapplication.data.model.PurchaseTaxi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UseDetailViewModel @Inject constructor(
    private val purchaseTaxiFactory: PurchaseTaxiFactory
): ViewModel(){
    private val _purchaseList = MutableStateFlow<List<PurchaseTaxi>>(emptyList())
    val taxiList = _purchaseList.asStateFlow()


    init {
        loadPurchaseList()
    }

    private fun loadPurchaseList(){
        _purchaseList.value = purchaseTaxiFactory.getPurchaseTaxiList()
    }
}
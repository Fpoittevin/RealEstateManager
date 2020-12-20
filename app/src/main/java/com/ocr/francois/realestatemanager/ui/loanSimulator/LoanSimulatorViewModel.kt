package com.ocr.francois.realestatemanager.ui.loanSimulator

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.ui.base.BaseCurrencyViewModel
import com.ocr.francois.realestatemanager.utils.Utils
import kotlin.math.pow

class LoanSimulatorViewModel(currencyRepository: CurrencyRepository) : BaseCurrencyViewModel(
    currencyRepository
), View.OnClickListener {
    val priceLiveData = MutableLiveData<String>()
    private var price: Double? = null
    val contributionLiveData = MutableLiveData<String>()
    private var contribution: Double? = null
    val durationInYearsLiveData = MutableLiveData<String>()
    private var durationInYears: Double? = null
    val rateLiveData = MutableLiveData<String>()
    private var rate: Double? = null
    private var loanAmount: Double? = null
    val currencyLiveData = currencyRepository.getCurrencyLiveData()

    private val monthlyPaymentLiveData = MutableLiveData<Double>()
    private val loanPriceLiveData = MutableLiveData<Double>()

    val haveResultToDisplay = MutableLiveData<Boolean>().apply { value = false }

    val monthlyPaymentFormattedLiveData = MediatorLiveData<String>().apply {
        addSource(currencyLiveData) {
            this@LoanSimulatorViewModel.currency = it
        }
        addSource(monthlyPaymentLiveData) {
            value = Utils.getFormattedPriceWithCurrency(
                currency,
                it.toInt()
            )
        }
    }

    val loanPriceFormattedLiveData = MediatorLiveData<String>().apply {
        addSource(currencyLiveData) {
            this@LoanSimulatorViewModel.currency = it
        }
        addSource(loanPriceLiveData) {
            value = Utils.getFormattedPriceWithCurrency(
                currency,
                it.toInt()
            )
        }
    }

    val isFormCompleted = MediatorLiveData<Boolean>().apply {
        addSource(priceLiveData) {
            price = it.toDoubleOrNull()
            checkFormIsCompleted()
        }
        addSource(contributionLiveData) {
            contribution = it.toDoubleOrNull()
            checkFormIsCompleted()
        }
        addSource(durationInYearsLiveData) {
            durationInYears = it.toDoubleOrNull()
            checkFormIsCompleted()
        }
        addSource(rateLiveData) {
            rate = it.toDoubleOrNull()
            rate?.let { rateValue ->
                rate = rateValue.div(100)
            }
            checkFormIsCompleted()
        }
    }

    private fun checkFormIsCompleted() {
        isFormCompleted.value =
            price != null && contribution != null && durationInYears != null && rate != null
    }

    override fun onClick(p0: View?) {
        calculate()
    }

    private fun calculate() {
        loanAmount = price!!.minus(contribution!!)
        monthlyPaymentLiveData.value = loanAmount!!.times(
            rate!!.div(12)
        ).div(
            1.minus(
                1.plus(
                    rate!!.div(12)
                ).pow((-12).times(durationInYears!!))
            )
        )

        loanPriceLiveData.value = 12.times(
            durationInYears!!
        ).times(monthlyPaymentLiveData.value!!).minus(loanAmount!!)

        haveResultToDisplay.value = true
    }
}
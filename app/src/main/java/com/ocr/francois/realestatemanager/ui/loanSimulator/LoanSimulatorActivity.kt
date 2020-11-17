package com.ocr.francois.realestatemanager.ui.loanSimulator

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityLoanSimulatorBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.utils.Currency
import kotlin.math.pow

class LoanSimulatorActivity : BaseActivity() {

    private var price = 0.0
    private var contribution = 0.0
    private var durationInYears = 0.0
    private var rate = 0.0
    private var loanAmount = 0.0
    private var monthlyPayment = 0.0
    private var loanPrice = 0.0

    private var currency = Currency.DOLLAR

    private lateinit var binding: ActivityLoanSimulatorBinding

    private val loanSimulatorViewModel: LoanSimulatorViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoanSimulatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar(binding.activityLoanSimulatorToolbar, true)
        configureCalculateButton()
        binding.activityLoanSimulatorResultsContainer.visibility = View.INVISIBLE

        observeCurrency()
    }

    private fun observeCurrency() {
        loanSimulatorViewModel.getCurrencyLiveData().observe(this, {
            this.currency = it
        })
    }

    private fun configureCalculateButton() {
        binding.activityLoanSimulatorCalculateButton.setOnClickListener {
            hideKeyboard()
            if (formIsCompleted()) {
                calculateMonthlyPaymentAndLoanPrice()
            }
        }
    }

    private fun formIsCompleted(): Boolean {
        var isCompleted = true

        if (binding.activityLoanSimulatorPriceTextInput.text.isNullOrEmpty()) {
            isCompleted = false
            binding.activityLoanSimulatorPriceLayout.error =
                getString(R.string.required_field)
        } else {
            price = binding.activityLoanSimulatorPriceTextInput.text.toString().toDouble()
        }

        if (binding.activityLoanSimulatorContributionTextInput.text.isNullOrEmpty()) {
            isCompleted = false
            binding.activityLoanSimulatorContributionLayout.error =
                getString(R.string.required_field)
        } else {
            contribution =
                binding.activityLoanSimulatorContributionTextInput.text.toString().toDouble()
        }

        if (binding.activityLoanSimulatorDurationTextInput.text.isNullOrEmpty()) {
            isCompleted = false
            binding.activityLoanSimulatorDurationLayout.error =
                getString(R.string.required_field)
        } else {
            durationInYears =
                binding.activityLoanSimulatorDurationTextInput.text.toString().toDouble()
        }

        if (binding.activityLoanSimulatorRateTextInput.text.isNullOrEmpty()) {
            isCompleted = false
            binding.activityLoanSimulatorRateLayout.error =
                getString(R.string.required_field)
        } else {
            rate = binding.activityLoanSimulatorRateTextInput.text.toString().toDouble().div(100)
        }

        return isCompleted
    }

    private fun calculateMonthlyPaymentAndLoanPrice() {

        loanAmount = price - contribution

        monthlyPayment = loanAmount.times(
            rate.div(12)
        ).div(
            1.minus(
                1.plus(
                    rate.div(12)
                ).pow((-12).times(durationInYears))
            )
        )

        loanPrice = 12.times(
            durationInYears
        ).times(monthlyPayment).minus(loanAmount)

        binding.apply {

            var loanPriceText = ""
            var monthlyPaymentText = ""
            activityLoanSimulatorResultsContainer.visibility = View.VISIBLE
            when (currency) {
                Currency.DOLLAR -> {
                    loanPriceText =
                        "$ " + String.format("%.2f", loanPrice)
                    monthlyPaymentText =
                        "$ " + String.format("%.2f", monthlyPayment)
                }
                Currency.EURO -> {
                    loanPriceText =
                        String.format("%.2f", loanPrice) + " €"
                    monthlyPaymentText =
                        String.format("%.2f", monthlyPayment) + " €"
                }
            }
            activityLoanSimulatorLoanPriceTextView.text = loanPriceText
            activityLoanSimulatorMonthlyPaymentTextView.text = monthlyPaymentText
        }
    }
}
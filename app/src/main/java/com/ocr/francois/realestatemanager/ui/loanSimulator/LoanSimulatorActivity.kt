package com.ocr.francois.realestatemanager.ui.loanSimulator

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityLoanSimulatorBinding
import kotlinx.android.synthetic.main.activity_loan_simulator.*
import kotlin.math.pow

class LoanSimulatorActivity : AppCompatActivity() {

    private var price = 0.0
    private var contribution = 0.0
    private var durationInYears = 0.0
    private var rate = 0.0
    private var loanAmount = 0.0
    private var monthlyPayment = 0.0
    private var loanPrice = 0.0

    private lateinit var binding: ActivityLoanSimulatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoanSimulatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar()
        configureCalculateButton()
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_loan_simulator_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureCalculateButton() {
        binding.activityLoanSimulatorCalculateButton.setOnClickListener {
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
            activityLoanSimulatorLoanPriceTextView.text = String.format("%.2f", loanPrice)
            activityLoanSimulatorMonthlyPaymentTextView.text = String.format("%.2f", monthlyPayment)
        }
    }
}
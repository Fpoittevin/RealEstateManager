package com.ocr.francois.realestatemanager.ui.loanSimulator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityLoanSimulatorBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity

class LoanSimulatorActivity : BaseActivity() {

    lateinit var binding: ActivityLoanSimulatorBinding

    private val loanSimulatorViewModel: LoanSimulatorViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_loan_simulator)
        binding.apply {
            viewModel = loanSimulatorViewModel
            lifecycleOwner = this@LoanSimulatorActivity
        }

        configureToolbar(binding.activityLoanSimulatorToolbar, true)

        loanSimulatorViewModel.haveResultToDisplay.observe(this, {
            hideKeyboard()
        })
    }
}
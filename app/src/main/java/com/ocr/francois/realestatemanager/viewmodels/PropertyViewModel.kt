package com.ocr.francois.realestatemanager.viewmodels

import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import java.util.concurrent.Executor

class PropertyViewModel(
    val propertyRepository: PropertyRepository,
    val executor: Executor
) {

}
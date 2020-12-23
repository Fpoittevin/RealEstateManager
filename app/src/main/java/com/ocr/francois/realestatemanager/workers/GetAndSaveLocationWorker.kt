package com.ocr.francois.realestatemanager.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ocr.francois.realestatemanager.api.GeocoderService
import com.ocr.francois.realestatemanager.events.FailureEvent
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.GeocodeResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAndSaveLocationWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    companion object {
        const val PROPERTY_ID_KEY = "PROPERTY_ID"
        const val PROPERTY_ADDRESS_KEY = "PROPERTY_ADDRESS"
    }

    override fun doWork(): Result {

        val propertyId = inputData.getLong(PROPERTY_ID_KEY, 0)
        inputData.getString(PROPERTY_ADDRESS_KEY)?.let { address ->
            if (propertyId.compareTo(0) != 0 && address.isNotEmpty()) {
                GeocoderService
                    .retrofit
                    .create(
                        GeocoderService::class.java
                    ).getLocation(address)
                    .enqueue(object : Callback<GeocodeResponse> {

                        override fun onResponse(
                            call: Call<GeocodeResponse>,
                            response: Response<GeocodeResponse>
                        ) {
                            response.body()?.let { body ->
                                body.results?.let { results ->
                                    if (results.isNotEmpty()) {
                                        val result = results[0]

                                        result.geometry?.let { geometry ->
                                            geometry.location?.let { location ->
                                                location.lat?.let { lat ->
                                                    location.lng?.let { lng ->
                                                        val propertyRepository =
                                                            Injection
                                                                .providePropertyRepository(
                                                                    applicationContext
                                                                )
                                                        GlobalScope.launch {
                                                            propertyRepository.saveLocation(
                                                                propertyId,
                                                                lat,
                                                                lng
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }

                        override fun onFailure(call: Call<GeocodeResponse>, t: Throwable) {
                            EventBus.getDefault()
                                .post(FailureEvent(t.message.toString()))
                        }
                    })
            }
        }
        return Result.success()
    }
}
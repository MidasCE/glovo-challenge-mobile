package com.example.glovochallenge.glovochallenge.data.provider

import com.example.glovochallenge.glovochallenge.data.model.CoordinateEntity
import io.reactivex.Single

interface LocationProvider {

    fun isPermissionGranted() : Boolean

    fun getCurrentLocation() : Single<CoordinateEntity>
}

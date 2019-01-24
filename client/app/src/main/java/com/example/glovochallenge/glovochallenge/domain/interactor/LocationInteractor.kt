package com.example.glovochallenge.glovochallenge.domain.interactor

import com.example.glovochallenge.glovochallenge.data.model.CoordinateEntity
import io.reactivex.Single

interface LocationInteractor {
    fun isPermissionGranted() : Boolean

    fun getCurrentLocation() : Single<CoordinateEntity>
}

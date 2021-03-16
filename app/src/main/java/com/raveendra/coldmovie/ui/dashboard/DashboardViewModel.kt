package com.raveendra.coldmovie.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.coldmovie.connection.getApi
import com.raveendra.coldmovie.model.discover.DiscoverMovieResponse
import com.raveendra.coldmovie.model.discover.DiscoverResultsItem
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

class DashboardViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()
    private val api: String = com.raveendra.coldmovie.BuildConfig.API_KEY
    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<DiscoverResultsItem>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<DiscoverResultsItem>>
        get() = _properties

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MarsProperty] [List] [LiveData]. The Retrofit service returns a coroutine Deferred, which we
     * await to get the result of the transaction.
     */
    private fun getMarsRealEstateProperties() {

        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                val result = getApi.retrofitService.getDiscover(api, 1)
                _properties.value = result.results!!
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
            }
        }
    }
}
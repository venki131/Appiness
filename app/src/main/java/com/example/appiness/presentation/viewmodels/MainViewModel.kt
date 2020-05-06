package com.example.appiness.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appiness.core.application.ResponseApi
import com.example.appiness.core.others.Status
import com.example.appiness.domain.remoteusecase.BakersRemoteDataUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val remoteDataUseCase: BakersRemoteDataUseCase
) : ViewModel() {
    private var compositeDisposable: CompositeDisposable? = null
    var bakersLiveDataResponse: MutableLiveData<ResponseApi> = MutableLiveData()
    var showProgress : MutableLiveData<Boolean> = MutableLiveData()

    private fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        return compositeDisposable as CompositeDisposable
    }

    fun bakersResponseLiveData(): LiveData<ResponseApi> {
        return bakersLiveDataResponse
    }

    fun getBakersData() {
        getCompositeDisposable().add(
            remoteDataUseCase.getBakersData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        setCommonCallBack(Status.HideProgress, false)
                        onResponseCode(it)
                    }, {
                        setCommonCallBack(
                            Status.Fail,
                            "Unable to connect. Please try after sometime."
                        )
                    }
                )
        )
    }

    private fun onResponseCode(responseData: ResponseApi) {
        when (responseData.status) {
            Status.Success -> setCommonCallBack(Status.Success, responseData.data!!)
            else -> setCommonCallBack(
                Status.Failed,
                "Unable to connect. Please try after sometime."
            )
        }
    }

    private fun setCommonCallBack(status: Status, obj: Any) {
        bakersLiveDataResponse.value = ResponseApi.genericCallBack(status, obj)
    }
}
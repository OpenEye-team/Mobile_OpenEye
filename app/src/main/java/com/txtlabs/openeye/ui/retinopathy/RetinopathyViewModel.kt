package com.txtlabs.openeye.ui.retinopathy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.txtlabs.openeye.data.ResultState
import com.txtlabs.openeye.data.response.RetinopathyResponse
import com.txtlabs.openeye.data.retrofit.ApiConfig
import com.txtlabs.openeye.utils.Utils
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class RetinopathyViewModel : ViewModel() {

    private val _postRetinopathy = MutableLiveData<ResultState<RetinopathyResponse>>()
    val postRetinopathy: MutableLiveData<ResultState<RetinopathyResponse>> = _postRetinopathy

    private val _forceLogout = MutableLiveData(false)
    val forceLogout: MutableLiveData<Boolean> = _forceLogout

    var getFile: File? = null

    fun postRetinopathy() {
        _postRetinopathy.value = ResultState.Loading
        viewModelScope.launch {
            getFile?.let { Log.d("filename", it.name) }

            try {
                if (getFile != null) {
                    val tempFile = Utils.reduceImageSize(getFile as File)
                    val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val photoPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "file",
                        tempFile.name,
                        requestFile
                    )
                    val response = ApiConfig.getApiServiceWithoutToken().postRetinopathy(photoPart)
                    val data = response
                    Log.d("ProfileViewModel", "postFoto: ${response.label}")
                    if (data != null) {
                        Log.d("ProfileViewModel", "postFoto: ${data.label}")
                        if (data.kumilcintabh != null) {
                            _postRetinopathy.postValue(ResultState.Success(data))
                            Log.d("ProfileViewModel", "postFoto: ${data.label}")
                        } else {
                            _postRetinopathy.postValue(ResultState.Failure(Throwable("Gagal Upload")))
                            Log.d("ProfileViewModel", "postFoto gagal: ${data.label}")
                        }
                    }
                }
            } catch (e: HttpException) {
                Log.d("ProfileViewModel", "postFoto err: ${e.message.toString()} ")
                _postRetinopathy.value = ResultState.Failure(e)
                if (e.code() == 401) {
                    _forceLogout.value = true
                }
            } catch (e: Throwable) {
                Log.d("ProfileViewModel", "postFoto err: ${e.message.toString()} ")
                _postRetinopathy.value = ResultState.Failure(e)
            }
        }
    }
}
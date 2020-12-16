package id.putraprima.mygoldtracker.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.*
import id.putraprima.mygoldtracker.data.GoldRepository
import id.putraprima.mygoldtracker.data.model.Profile
import id.putraprima.mygoldtracker.util.ImageHelper
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: GoldRepository) : ViewModel() {
    private var _profileNew: MutableLiveData<Profile> = MutableLiveData()
    val profileNew: LiveData<Profile>
        get() = _profileNew
    private var _profileTmpUri: MutableLiveData<Uri> = MutableLiveData()
    val profileTmpUri: LiveData<Uri>
        get() = _profileTmpUri
    val profile: LiveData<List<Profile>> = repository.profile.asLiveData()

    fun insert(profile: Profile) = viewModelScope.launch {
        repository.insertProfile(profile)
    }

    fun update(profile: Profile) = viewModelScope.launch {
        repository.updateProfile(profile)
    }

    fun save(nama: String, email: String, image: Drawable) {
        viewModelScope.launch {
            val bitmap: Bitmap = image.toBitmap()
            val path = ImageHelper.saveToInternalStorage(bitmap)
            _profileNew.value = Profile(nama = nama, email = email, path = path)
        }
    }

    fun tmpUri(uri: Uri) {
        _profileTmpUri.value = uri
    }
}

class ProfileViewModelFactory(private val repository: GoldRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
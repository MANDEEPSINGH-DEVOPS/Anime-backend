package com.example.animeg6.ui.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilViewModel extends ViewModel {

    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> phone = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();

    public void setUserData(String userName, String userEmail, String userPassword,String userPhone) {
        name.setValue(userName);
        email.setValue(userEmail);
        password.setValue(userPassword);

        phone.setValue(userPhone);
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPhone() {
        return phone;
    }
}

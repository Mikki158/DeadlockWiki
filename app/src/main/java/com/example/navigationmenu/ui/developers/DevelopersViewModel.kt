package com.example.navigationmenu.ui.developers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DevelopersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Приложение разработано в рамках курсового проекта по дисциплине “Программирование мобильных устройств”.\n" +
                "\n" +
                "Команда:\n" +
                "Башков Александр\n" +
                "Гринькин Михаил\n" +
                "Пудова Анастасия\n" +
                "\n" +
                "Для обратной связи:\n" +
                "noname@gmail.com"
    }
    val text: LiveData<String> = _text
}
package com.example.navigationmenu.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Deadlock — это геройский шутер в формате 6v6, разрабатываемая и издаваемая компанией Valve. \n" +
                "\n" +
                "Игроки управляют мощными героями и сопровождают NPC на нескольких «линиях», уничтожая стационарные защитные сооружения вражеской команды. Это делает игровой процесс похожим на игры жанра MOBA. \n" +
                "Когда оборона противника уничтожена, он призывает своего «Покровителя» — огромного голема, которого требуется одолеть, чтобы выиграть матч.\n" +
                "\n" +
                "Игроки могут открывать разные навыки и способности для своего персонажа, а также использовать зиплайны для перемещения по арене. В игре представлены разные герои, но неизвестно, есть ли у них какая-то биография или предыстория."
    }
    val text: LiveData<String> = _text
}
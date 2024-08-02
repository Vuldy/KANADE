import com.projects.kanade.ui.common.ErrorState
import com.projects.kanade.ui.reusables.GetDate

data class PilihJadwalState(
    var tanggal: String = GetDate(),
    var waktu: Int = 0,
    var keluhan: String = "",
    var hari: String = "",
    var valid: Boolean = false,
)

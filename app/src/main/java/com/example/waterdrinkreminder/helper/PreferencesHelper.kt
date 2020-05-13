import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper private constructor() {

    companion object {
        private val sharePref = PreferencesHelper()
        private lateinit var sharedPreferences: SharedPreferences

        private val CURRENT_DATE_STRING = "current_date"
        private val CURRENT_PERCENTAGE_VOLUME = "current_percentage_volume"
        private val CURRENT_VOLUME = "current_volume"

        fun getInstance(context: Context): PreferencesHelper {
            if (!::sharedPreferences.isInitialized) {
                synchronized(PreferencesHelper::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return sharePref
        }
    }

    val getCurrentDate: String?
        get() = sharedPreferences.getString(CURRENT_DATE_STRING, "")

    val getCurrentPercentageVolume: Int?
        get() = sharedPreferences.getInt(CURRENT_PERCENTAGE_VOLUME, 0)

    val getCurrentVolume: Int?
        get() = sharedPreferences.getInt(CURRENT_VOLUME, 0)

    fun saveCurrentDate(date: String) {
        sharedPreferences.edit()
            .putString(CURRENT_DATE_STRING, date)
            .apply()
    }

    fun saveCurrentPercentageVolume(percentageVolume: Int) {
        sharedPreferences.edit()
            .putInt(CURRENT_PERCENTAGE_VOLUME, percentageVolume)
            .apply()
    }

    fun saveCurrentVolume(volume: Int) {
        sharedPreferences.edit()
            .putInt(CURRENT_VOLUME, volume)
            .apply()
    }

//    fun removePlaceObj() {
//        sharedPreferences.edit().remove(PLACE_OBJ).apply()
//    }
//
//    fun clearAll() {
//        sharedPreferences.edit().clear().apply()
//    }

}
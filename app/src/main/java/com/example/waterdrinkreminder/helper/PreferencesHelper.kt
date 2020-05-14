import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper private constructor() {

    companion object {
        private val sharePref = PreferencesHelper()
        private lateinit var sharedPreferences: SharedPreferences

        private val CURRENT_DATE_STRING = "current_date"
        private val CURRENT_TARGET_VOLUME = "current_target_volume"
        private val CURRENT_REMAINNG_VOLUME = "current_remaining_volume"

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

    val getCurrentTargetVolume: Int?
        get() = sharedPreferences.getInt(CURRENT_TARGET_VOLUME, 0)

    val getCurrentRemainingVolume: Int?
        get() = sharedPreferences.getInt(CURRENT_REMAINNG_VOLUME, 0)

    fun saveCurrentDate(date: String) {
        sharedPreferences.edit()
            .putString(CURRENT_DATE_STRING, date)
            .apply()
    }

    fun saveCurrentTargetVolume(target: Int) {
        sharedPreferences.edit()
            .putInt(CURRENT_TARGET_VOLUME, target)
            .apply()
    }

    fun saveCurrentRemainingVolume(remaining: Int) {
        sharedPreferences.edit()
            .putInt(CURRENT_REMAINNG_VOLUME, remaining)
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
package io.santisme.ahoy.domain.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListConverter {

    var gSon = Gson()

    @TypeConverter
    fun fromPosterJson(data: String?): List<PosterEntity>? {

        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return gSon.fromJson(data, listType)

    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<PosterEntity>?): String? {
        return gSon.toJson(someObjects)
    }

}
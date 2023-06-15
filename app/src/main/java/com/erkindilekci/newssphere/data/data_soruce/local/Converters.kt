package com.erkindilekci.newssphere.data.data_soruce.local

import androidx.room.TypeConverter
import com.erkindilekci.newssphere.domain.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}
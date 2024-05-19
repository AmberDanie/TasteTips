package pet.project.tastetips.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RefrigeratorItem::class],
    version = 1,
    exportSchema = false
)
abstract class RefrigeratorDatabase: RoomDatabase() {
    abstract fun itemDao() : RefrigeratorItemDao

    companion object {
        @Volatile
        private var Instance: RefrigeratorDatabase? = null
        fun getDataBase(context: Context) : RefrigeratorDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RefrigeratorDatabase::class.java, "refrigerator_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}
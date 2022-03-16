package xyz.teknol.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(
//    entities = [UserEntity::class, RoleEntity::class, RaceEntity::class, EthnicityEntity::class],
//    version = 1
//)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun createDataBase(applicationContext: Context, name: String): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, name
            ).build()
        }
    }

//    public abstract fun userDao(): UserDao
//    abstract fun roleDao(): RoleDao
//    abstract fun raceDao(): RaceDao
//    abstract fun ethnicityDao(): EthnicityDao
}
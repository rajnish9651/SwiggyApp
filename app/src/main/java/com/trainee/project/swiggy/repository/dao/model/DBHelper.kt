package com.trainee.project.swiggy.repository.dao.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trainee.project.swiggy.convertor.Converters
import com.trainee.project.swiggy.repository.dao.model.dao.UserDao
import com.trainee.project.swiggy.repository.dao.model.dao.UserSavedLocationDao
import com.trainee.project.swiggy.repository.dao.model.model.FoodTypeData
import com.trainee.project.swiggy.repository.dao.model.model.UserDeatailsData
import com.trainee.project.swiggy.repository.dao.model.model.UserSavedLocationData

@Database(entities = [FoodTypeData::class,UserDeatailsData::class,UserSavedLocationData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)  // Register the TypeConverters class here
abstract class DBHelper : RoomDatabase() {

    abstract fun foodDao(): FoodDao;

    abstract fun userDao(): UserDao;

    abstract fun userSavedLocationDao(): UserSavedLocationDao;

    companion object {
        private var INSTANCE: DBHelper? = null



        fun getDatabase(context: Context): DBHelper {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DBHelper::class.java,
                        "FOOD_DB2"
                    ).build()

                    INSTANCE = instance

                }
                return instance

            }


        }


    }

}
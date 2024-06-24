package com.example.newsify.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsify.models.Article

@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao() : ArticleDAO

    companion object{
        //Volatile annotation ensures that changes in one thread immediately reflects on another thread
        @Volatile
        private var instance: ArticleDatabase? = null // instance variable holds the singleton instance of ArticleDatabase
        private val LOCK = Any()//LOCK object used to synchronize the database creation process
        //*** Basically this block ensures that only one thread can execute the code in this code block at a time

        /**
         Then this is custom invoke operator for the companion object, which allows you to create an instance of ArticleDatabase
         by calling ArticleDatabase context. This function follows singleTon pattern ensures that single instance is being created.
         If the instance is not created then it enters into the synchronised block object to ensure that only one thread can create
         create the database at a time.
         **/
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }
        //This method is responsible for creating the database.
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}
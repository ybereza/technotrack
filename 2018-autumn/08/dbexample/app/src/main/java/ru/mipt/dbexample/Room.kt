package ru.mipt.dbexample

import android.arch.persistence.room.*
import android.content.Context

@Entity(tableName = "test")
data class Value(
        @PrimaryKey(autoGenerate = true) val id : Int,
        @ColumnInfo(name = "value") val text : String
)

@Dao
interface ValuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertValue(value: Value)

    @Update
    fun updateValue(value: Value)

    @Delete
    fun deleteValue(value: Value)

    @Query("SELECT * FROM test WHERE value == :text")
    fun getValueByName(text: String): List<Value>

    @Query("SELECT * FROM test")
    fun getValues(): List<Value>

    @Query("SELECT Count(*) FROM test")
    fun size() : Long
}


@Database(entities = [Value::class], version = 2)
abstract class ValuesDatabase : RoomDatabase() {
    abstract fun valuesDao(): ValuesDao

    companion object {
        var INSTANCE: ValuesDatabase? = null
        fun getAppDataBase(context: Context): ValuesDatabase? {
            if (INSTANCE == null){
                synchronized(ValuesDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ValuesDatabase::class.java, "values")
                            .allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun closeDatabase(){
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}
package com.unibuzz.campusapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteDatabase
import com.unibuzz.campusapp.data.model.Event
import com.unibuzz.campusapp.data.model.Item
import com.unibuzz.campusapp.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE campusTag = :campusTag ORDER BY createdAt DESC")
    suspend fun getAll(campusTag: String = "unibuzz"): List<Item>

    @Query("SELECT * FROM items WHERE campusTag = :campusTag ORDER BY createdAt DESC")
    fun getAllAsFlow(campusTag: String = "unibuzz"): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getById(id: Long): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item): Long

    @Update
    suspend fun update(item: Item)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface EventDao {
    @Query("SELECT * FROM events WHERE campusTag = :campusTag ORDER BY dateTime DESC")
    suspend fun getAll(campusTag: String = "unibuzz"): List<Event>

    @Query("SELECT * FROM events WHERE campusTag = :campusTag ORDER BY dateTime DESC")
    fun getAllAsFlow(campusTag: String = "unibuzz"): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getById(id: Long): Event?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    @Update
    suspend fun update(event: Event)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email OR handle = :email LIMIT 1")
    suspend fun findByEmailOrHandle(email: String): User?

    @Query("SELECT * FROM users WHERE (email = :emailOrHandle OR handle = :emailOrHandle) AND password = :password LIMIT 1")
    suspend fun authenticate(emailOrHandle: String, password: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)
}

@Database(
    entities = [Item::class, Event::class, User::class],
    version = 3,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun eventDao(): EventDao
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "unibuzz_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME,
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { populateInitialData(it) }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialData(db: AppDatabase) {
            val sampleItems = listOf(
                Item(
                    title = "Fresh Homemade Chocolate Chip Cookies (6-pack)",
                    description = "Warm homemade chocolate chip cookies baked fresh daily in student dorms. Perfect study snack!",
                    priceText = "$5.00",
                    condition = "Brand New",
                    category = "Food & Snacks",
                    locationHint = "Student Union / Dorm Kitchen",
                    contact = "@cookie_baker",
                    imageUris = "",
                    status = "AVAILABLE",
                    posterHandle = "dorms_bakery",
                ),
                Item(
                    title = "Late-Night Study Snack Box & Energy Drinks",
                    description = "Includes 2 energy drinks, chips, chocolate bars, and instant ramen pack for late-night exam prep.",
                    priceText = "$7.50",
                    condition = "Brand New",
                    category = "Food & Snacks",
                    locationHint = "Campus Library Hall",
                    contact = "@snack_hub",
                    imageUris = "",
                    status = "AVAILABLE",
                    posterHandle = "snack_hub",
                ),
                Item(
                    title = "Artisanal Campus Iced Latte & Croissant Combo",
                    description = "Fresh croissant and cold brew iced latte prepared fresh for campus morning classes.",
                    priceText = "$4.50",
                    condition = "Brand New",
                    category = "Food & Snacks",
                    locationHint = "Quad Courtyard / Library Cafe",
                    contact = "@campus_brew",
                    imageUris = "",
                    status = "AVAILABLE",
                    posterHandle = "barista_student",
                ),
                Item(
                    title = "Calculus & Analytical Geometry (14th Ed)",
                    description = "Gently used textbook with highlighted notes and study guide included.",
                    priceText = "$35.00",
                    condition = "Good",
                    category = "Books",
                    locationHint = "Science Hall 102",
                    contact = "@alex_textbooks",
                    imageUris = "",
                    status = "AVAILABLE",
                    posterHandle = "alex_t",
                ),
                Item(
                    title = "Wireless Noise-Canceling Headphones",
                    description = "Great sound quality and long battery life. Comes with charging cable.",
                    priceText = "$45.00",
                    condition = "Like New",
                    category = "Electronics",
                    locationHint = "Engineering Lab B",
                    contact = "@tech_deals",
                    imageUris = "",
                    status = "AVAILABLE",
                    posterHandle = "tech_guy",
                ),
            )
            sampleItems.forEach { db.itemDao().insert(it) }
        }
    }
}

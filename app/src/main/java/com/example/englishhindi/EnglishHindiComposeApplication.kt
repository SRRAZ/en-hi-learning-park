package com.example.englishhindi

import android.app.Application
import android.os.StrictMode
import androidx.room.Room
import com.example.englishhindi.data.db.AppDatabase
import com.example.englishhindi.data.repository.WordRepository
import com.example.englishhindi.data.repository.WordRepositoryImpl
import com.example.englishhindi.util.LanguageManager
import com.example.englishhindi.util.PreferenceManager
import java.util.concurrent.Executors

/**
 * Application class with Compose implementation
 */
class EnglishHindiComposeApplication : Application() {

    // Repositories
    lateinit var wordRepository: WordRepository
    
    // Database
    lateinit var database: AppDatabase
    
    // Language manager
    lateinit var languageManager: LanguageManager
    
    // Preference manager
    lateinit var preferenceManager: PreferenceManager
    
    // Background executor
    private val executor = Executors.newFixedThreadPool(4)

    override fun onCreate() {
        super.onCreate()
        
        // Initialize StrictMode for debug builds
        if (BuildConfig.DEBUG) {
            initializeStrictMode()
        }
        
        // Initialize database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "english_hindi_learning_db"
        )
            .fallbackToDestructiveMigration()
            .build()
            
        // Initialize repositories
        wordRepository = WordRepositoryImpl(database.wordDao())
        
        // Initialize language manager
        languageManager = LanguageManager(this)
        
        // Initialize preference manager
        preferenceManager = PreferenceManager(this)
        
        // Preload essential data in background
        preloadData()
    }
    
    /**
     * Sets up StrictMode for catching potential issues in development
     */
    private fun initializeStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
        
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )
    }
    
    /**
     * Preloads essential data in the background
     */
    private fun preloadData() {
        executor.execute {
            // Check if initial data needs to be seeded
            val wordCount = database.wordDao().getWordCount()
            
            if (wordCount == 0) {
                // Seed initial data
                seedSampleData()
            }
        }
    }
    
    /**
     * Seeds some sample data for first-time users
     */
    private fun seedSampleData() {
        // Basic greetings category
        val greetingsWords = listOf(
            com.example.englishhindi.data.model.Word(
                englishWord = "Hello",
                hindiWord = "नमस्ते",
                englishPronunciation = "huh-loh",
                hindiPronunciation = "nuh-muh-stay",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Hello, how are you?",
                exampleSentenceHindi = "नमस्ते, आप कैसे हैं?"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Good morning",
                hindiWord = "सुप्रभात",
                englishPronunciation = "good mor-ning",
                hindiPronunciation = "soo-pruh-bhaat",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Good morning, did you sleep well?",
                exampleSentenceHindi = "सुप्रभात, क्या आपने अच्छी नींद ली?"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Thank you",
                hindiWord = "धन्यवाद",
                englishPronunciation = "thank-yoo",
                hindiPronunciation = "dhun-yuh-vaad",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Thank you for your help.",
                exampleSentenceHindi = "आपकी मदद के लिए धन्यवाद।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Goodbye",
                hindiWord = "अलविदा",
                englishPronunciation = "good-bye",
                hindiPronunciation = "ul-vee-daa",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Goodbye, see you tomorrow.",
                exampleSentenceHindi = "अलविदा, कल मिलते हैं।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Good night",
                hindiWord = "शुभ रात्रि",
                englishPronunciation = "good night",
                hindiPronunciation = "shubh raa-tree",
                category = "Greetings",
                difficulty = 1,
                exampleSentenceEnglish = "Good night, sleep well.",
                exampleSentenceHindi = "शुभ रात्रि, अच्छी नींद लें।"
            )
        )
        
        // Basic food category
        val foodWords = listOf(
            com.example.englishhindi.data.model.Word(
                englishWord = "Water",
                hindiWord = "पानी",
                englishPronunciation = "waa-ter",
                hindiPronunciation = "paa-nee",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "I would like a glass of water.",
                exampleSentenceHindi = "मुझे एक गिलास पानी चाहिए।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Bread",
                hindiWord = "रोटी",
                englishPronunciation = "bred",
                hindiPronunciation = "ro-tee",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "I eat bread for breakfast.",
                exampleSentenceHindi = "मैं नाश्ते में रोटी खाता हूँ।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Rice",
                hindiWord = "चावल",
                englishPronunciation = "rice",
                hindiPronunciation = "chaa-val",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "Rice is a staple food in many countries.",
                exampleSentenceHindi = "चावल कई देशों का मुख्य भोजन है।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Tea",
                hindiWord = "चाय",
                englishPronunciation = "tee",
                hindiPronunciation = "chaay",
                category = "Food & Drinks",
                difficulty = 1,
                exampleSentenceEnglish = "Would you like some tea?",
                exampleSentenceHindi = "क्या आप कुछ चाय लेंगे?"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Fruit",
                hindiWord = "फल",
                englishPronunciation = "froot",
                hindiPronunciation = "phal",
                category = "Food & Drinks",
                difficulty = 2,
                exampleSentenceEnglish = "I eat fruit every day.",
                exampleSentenceHindi = "मैं हर दिन फल खाता हूँ।"
            )
        )
        
        // Basic numbers
        val numberWords = listOf(
            com.example.englishhindi.data.model.Word(
                englishWord = "One",
                hindiWord = "एक",
                englishPronunciation = "wun",
                hindiPronunciation = "ek",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I have one book.",
                exampleSentenceHindi = "मेरे पास एक किताब है।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Two",
                hindiWord = "दो",
                englishPronunciation = "too",
                hindiPronunciation = "do",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I have two hands.",
                exampleSentenceHindi = "मेरे पास दो हाथ हैं।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Three",
                hindiWord = "तीन",
                englishPronunciation = "three",
                hindiPronunciation = "teen",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I need three tickets.",
                exampleSentenceHindi = "मुझे तीन टिकट चाहिए।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Four",
                hindiWord = "चार",
                englishPronunciation = "for",
                hindiPronunciation = "chaar",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "The table has four legs.",
                exampleSentenceHindi = "मेज के चार पैर हैं।"
            ),
            com.example.englishhindi.data.model.Word(
                englishWord = "Five",
                hindiWord = "पांच",
                englishPronunciation = "five",
                hindiPronunciation = "paanch",
                category = "Numbers",
                difficulty = 1,
                exampleSentenceEnglish = "I have five fingers on each hand.",
                exampleSentenceHindi = "मेरे हर हाथ में पांच उंगलियां हैं।"
            )
        )
        
        // Insert all words
        val allWords = greetingsWords + foodWords + numberWords
        val wordDao = database.wordDao()
        
        for (word in allWords) {
            wordDao.insert(word)
        }
    }
    
    override fun onTerminate() {
        super.onTerminate()
        executor.shutdown()
    }
    
    companion object {
        private const val TAG = "EnglishHindiApp"
    }
}
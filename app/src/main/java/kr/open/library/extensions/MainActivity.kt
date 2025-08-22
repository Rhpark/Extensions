package kr.open.library.extensions

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.open.library.easy_extensions.null_safety.ifNotNull
import kr.open.library.easy_extensions.null_safety.ifNull
import kr.open.library.easy_extensions.resource.getColorCompat
import kr.open.library.easy_extensions.resource.getColorSafe
import kr.open.library.easy_extensions.resource.getDrawableCompat
import kr.open.library.easy_extensions.resource.getStringFormatted
import kr.open.library.easy_extensions.resource.getStringSafe

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun demonstrateCollectionExtensions() {
        // ✅ Safe list access - use Kotlin stdlib
        val productList = listOf("iPhone", "Samsung", "Pixel")
        val firstProduct = productList.getOrNull(0) // Using stdlib function
        val nonExistentProduct = productList.getOrNull(10) // Using stdlib function

        // ✅ Conditional filtering - our custom extension
        val showOnlyFavorites = true
        val allProducts = listOf("iPhone", "Samsung", "Pixel", "OnePlus")
        val favorites = setOf("iPhone", "Pixel")


        // ✅ Map operations - use Kotlin stdlib
        val userSettings = mapOf("theme" to "dark", "notifications" to "enabled")
        val theme = userSettings["theme"].ifNotNull {
            Toast.makeText(this, "Theme: $it", Toast.LENGTH_SHORT).show()
        } ?: "light"
        val language = userSettings["language"] ?: "en"

    }

    private fun demonstrateNullSafetyExtensions() {
        // ✅ Cleaner null handling
        val userProfile: UserProfile? = getCurrentUser()

        userProfile.ifNotNull { profile ->
            updateUI(profile.name, profile.email)
        }.ifNull {
            showLoginScreen()
        }
    }

    private fun demonstrateResourceExtensions() {
        // ✅ Cleaner resource access
        val primaryColor = this.baseContext.getColorCompat(android.R.color.holo_blue_dark)
        val icon = getDrawableCompat(android.R.drawable.ic_menu_info_details)

        // ✅ Safe resource access
        val safeColor = getColorSafe(android.R.color.holo_red_dark, Color.RED)
        val safeText = getStringSafe(android.R.string.ok)

        // ✅ Formatted strings
        val welcomeText = getStringFormatted(
            android.R.string.ok, // Using existing string resource
            "User Name"
        )
    }

    // Mock data classes and methods for demo
    data class UserProfile(val name: String, val email: String, val nickname: String?)
    data class ErrorResponse(val message: String)

    private fun getCurrentUser(): UserProfile? = null // Mock implementation
    private fun getApiResponse(): Any = UserProfile("John", "john@example.com", "Johnny")
    private fun updateUI(name: String, email: String) {}
    private fun showLoginScreen() {}
    private fun showUserProfile(profile: UserProfile) {}
    private fun showError(message: String) {}
    private fun showWelcome(name: String) {}
    private fun showLoginPrompt() {}
}
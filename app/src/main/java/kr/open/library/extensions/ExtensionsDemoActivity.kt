package kr.open.library.extensions

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.open.library.easy_extensions.collection.*
import kr.open.library.easy_extensions.null_safety.*
import kr.open.library.easy_extensions.resource.*

/**
 * Demo activity showcasing the new Extensions functionality
 */
class ExtensionsDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Demonstrate new extensions
        demonstrateCollectionExtensions()
        demonstrateNullSafetyExtensions()
        demonstrateResourceExtensions()
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
        
        val displayProducts = allProducts
            .filterIf(showOnlyFavorites) { it in favorites }  // Our custom extension
            .ifNotEmpty { products ->  // Our custom extension with chaining
                Toast.makeText(this, "Found ${products.size} products", Toast.LENGTH_SHORT).show()
            }
            .ifEmpty {  // Our custom extension with chaining
                Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show()
            }
            
        // ✅ Map operations - use Kotlin stdlib
        val userSettings = mapOf("theme" to "dark", "notifications" to "enabled")
        val theme = userSettings["theme"].ifNotNull { 
            Toast.makeText(this, "Theme: $it", Toast.LENGTH_SHORT).show()
        } ?: "light"
        val language = userSettings["language"] ?: "en"
        
        // ✅ Our custom Map extension
        userSettings.ifNotEmpty { settings ->
            // Our custom extension for chaining
            Toast.makeText(this, "Loaded ${settings.size} settings", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun demonstrateNullSafetyExtensions() {
        // ✅ Cleaner null handling
        val userProfile: UserProfile? = getCurrentUser()
        
        userProfile
            .ifNotNull { profile ->
                updateUI(profile.name, profile.email)
            }
            .ifNull { 
                showLoginScreen()
            }
            
        // ✅ Safe casting
        val response: Any = getApiResponse()
        response.safeCast<UserProfile>()?.let { profile ->
            showUserProfile(profile)
        }
        response.safeCast<ErrorResponse>()?.let { error ->
            showError(error.message)
        }
        
        // ✅ First non-null value
        val displayName = firstNotNull(
            userProfile?.nickname,
            userProfile?.name, 
            "Anonymous User"
        )
        
        // ✅ Conditional execution with fallback
        userProfile.ifNotNullOrElse(
            notNullAction = { profile -> showWelcome(profile.name) },
            nullAction = { showLoginPrompt() }
        )
    }
    
    private fun demonstrateResourceExtensions() {
        // ✅ Cleaner resource access
        val primaryColor = getColorCompat(android.R.color.holo_blue_dark)
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
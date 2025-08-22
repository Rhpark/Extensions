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
import kr.open.library.easy_extensions.view.toastShowShort
import kr.open.library.easy_extensions.view.toastShowLong
import kr.open.library.easy_extensions.string.*
import kr.open.library.easy_extensions.time.*
import kr.open.library.easy_extensions.context.*
import kr.open.library.easy_extensions.conditional.*

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
        
        // Extensions 라이브러리 예제 실행
        // Run Extensions library examples
        demonstrateAllExtensions()
    }
    
    private fun demonstrateAllExtensions() {
        // 각 카테고리별 예제 실행
        // Run examples for each category
        demonstrateToastExtensions()
        demonstrateStringExtensions()
        demonstrateTimeExtensions()
        demonstrateContextExtensions()
        demonstrateConditionalExtensions()
        demonstrateCollectionExtensions()
        demonstrateNullSafetyExtensions()
        demonstrateResourceExtensions()
    }
    
    private fun demonstrateToastExtensions() {
        // ✅ Toast Extensions - 간편한 토스트 메시지 표시
        // Toast Extensions - Easy toast message display
        toastShowShort("안녕하세요! Hello from Toast Extension!")
        
        // 긴 토스트 메시지
        // Long toast message
        toastShowLong("이것은 긴 토스트 메시지입니다.\nThis is a long toast message.")
    }
    
    private fun demonstrateStringExtensions() {
        // ✅ String Validation Extensions - 문자열 검증
        // String Validation Extensions
        val email = "user@example.com"
        val phone = "+82-10-1234-5678"  
        val website = "https://www.google.com"
        val numberString = "12345"
        val alphaNumString = "abc123"
        
        // 이메일, 전화번호, URL 유효성 검증
        // Email, phone number, URL validation
        if (email.isEmailValid()) {
            toastShowShort("유효한 이메일: $email")
        }
        
        if (phone.isPhoneNumberValid()) {
            toastShowShort("유효한 전화번호: $phone")
        }
        
        if (website.isUrlValid()) {
            toastShowShort("유효한 URL: $website")
        }
        
        // 숫자 및 문자숫자 검증
        // Numeric and alphanumeric validation
        if (numberString.isNumeric()) {
            toastShowShort("숫자 문자열: $numberString")
        }
        
        if (alphaNumString.isAlphaNumeric()) {
            toastShowShort("문자숫자 문자열: $alphaNumString")
        }
        
        // HTML 태그 제거 및 공백 제거
        // HTML tag removal and whitespace removal
        val htmlString = "<p>Hello <b>World</b></p>"
        val cleanHtml = htmlString.stripHtmlTags()
        
        val spacedString = "  Hello   World  "
        val noWhitespace = spacedString.removeWhitespace()
        
        toastShowShort("HTML 제거: $cleanHtml")
        toastShowShort("공백 제거: $noWhitespace")
    }
    
    private fun demonstrateTimeExtensions() {
        // ✅ Time Measurement Extensions - 실행 시간 측정
        // Time Measurement Extensions
        val executionTime = measureTimeMillis {
            // 가짜 작업 시뮬레이션
            // Simulate some work
            Thread.sleep(100)
        }
        
        toastShowShort("작업 실행 시간: ${executionTime}ms")
        
        // 결과와 함께 시간 측정
        // Measure time with result
        val (result, time) = measureTimeMillis<String> {
            "작업 완료!" // Some work that returns a result
        }
        
        toastShowShort("결과: $result, 시간: ${time}ms")
    }
    
    private fun demonstrateContextExtensions() {
        // ✅ Context Extensions - 컨텍스트 관련 유틸리티
        // Context Extensions - Context-related utilities
        
        // URL 열기
        // Open URL
        val urlOpened = openUrl("https://www.google.com")
        if (urlOpened) {
            toastShowShort("브라우저에서 Google 열림")
        }
        
        // 이메일 보내기
        // Send email
        val emailSent = sendEmail(
            email = "example@test.com",
            subject = "테스트 이메일",
            body = "안녕하세요! 이것은 테스트 이메일입니다."
        )
        if (emailSent) {
            toastShowShort("이메일 앱 열림")
        }
        
        // 텍스트 공유
        // Share text
        val shared = shareText(
            text = "Extensions 라이브러리를 확인해보세요!",
            subject = "라이브러리 추천"
        )
        if (shared) {
            toastShowShort("공유 창 열림")
        }
        
        // 앱 설치 확인
        // Check if app is installed
        val isChromInstalled = isAppInstalled("com.android.chrome")
        toastShowShort("Chrome 설치됨: $isChromInstalled")
        
        // Toast 확장 사용
        // Use toast extension
        showToast("Context Extension에서 Toast 표시!", Toast.LENGTH_SHORT)
    }
    
    private fun demonstrateConditionalExtensions() {
        // ✅ Conditional Extensions - 조건부 실행
        // Conditional Extensions - Conditional execution
        
        val score = 85
        val temperature = 25.5f
        val isLoggedIn = true
        
        // 숫자 비교 조건
        // Numeric comparison conditions
        score.ifGreaterThan(80) {
            toastShowShort("우수한 점수입니다! 점수: $score")
        }
        
        temperature.ifGreaterThanOrEqual(25.0f) {
            toastShowShort("따뜻한 날씨입니다! 온도: ${temperature}°C")
        }
        
        // Boolean 조건
        // Boolean conditions
        isLoggedIn.ifTrue {
            toastShowShort("사용자가 로그인되었습니다.")
        }
        
        val count = 5
        count.ifEquals(5, 
            positiveWork = { toastShowShort("정확히 5개입니다!") },
            negativeWork = { toastShowShort("5개가 아닙니다.") }
        )
        
        // 다중 조건 체크
        // Multiple condition check
        val level = 10
        level.ifGreaterThan(5) {
            level.ifGreaterThan(15, 
                positiveWork = { "고급 레벨" },
                negativeWork = { "중급 레벨" }
            )?.let { levelType ->
                toastShowShort("레벨 타입: $levelType")
            }
        }
    }

    private fun demonstrateCollectionExtensions() {
        // ✅ Collection Extensions - 컬렉션 확장
        // Collection Extensions - Collection utilities
        val productList = listOf("iPhone", "Samsung", "Pixel")
        val firstProduct = productList.getOrNull(0) // Using stdlib function
        val nonExistentProduct = productList.getOrNull(10) // Using stdlib function

        toastShowShort("첫 번째 제품: $firstProduct")
        toastShowShort("존재하지 않는 제품: $nonExistentProduct")

        // ✅ 조건부 필터링 - Conditional filtering
        val showOnlyFavorites = true
        val allProducts = listOf("iPhone", "Samsung", "Pixel", "OnePlus")
        val favorites = setOf("iPhone", "Pixel")
        
        val filteredProducts = if (showOnlyFavorites) {
            allProducts.filter { it in favorites }
        } else {
            allProducts
        }
        toastShowShort("필터된 제품: ${filteredProducts.joinToString()}")

        // ✅ Map 연산 - Map operations
        val userSettings = mapOf("theme" to "dark", "notifications" to "enabled")
        val theme = userSettings["theme"].ifNotNull {
            toastShowShort("테마 설정: $it")
            it
        } ?: "light"
        val language = userSettings["language"] ?: "ko"
        
        toastShowShort("최종 테마: $theme, 언어: $language")
    }

    private fun demonstrateNullSafetyExtensions() {
        // ✅ Null Safety Extensions - 안전한 null 처리
        // Null Safety Extensions - Safe null handling
        val userProfile: UserProfile? = getCurrentUser()

        userProfile.ifNotNull { profile ->
            toastShowShort("사용자 로그인: ${profile.name}")
            updateUI(profile.name, profile.email)
        }.ifNull {
            toastShowShort("사용자가 로그인하지 않음")
            showLoginScreen()
        }
        
        // API 응답 처리 예제
        // API response handling example
        val apiResponse = getApiResponse()
        apiResponse.ifNotNull { response ->
            when (response) {
                is UserProfile -> {
                    toastShowShort("API 성공: ${response.name}")
                    showUserProfile(response)
                }
                is ErrorResponse -> {
                    toastShowShort("API 오류: ${response.message}")
                    showError(response.message)
                }
                else -> {
                    toastShowShort("알 수 없는 응답")
                }
            }
        }.ifNull {
            toastShowShort("API 응답이 null입니다")
        }
    }

    private fun demonstrateResourceExtensions() {
        // ✅ Resource Extensions - 리소스 접근 확장
        // Resource Extensions - Resource access utilities
        
        // 색상 리소스 접근
        // Color resource access
        val primaryColor = this.baseContext.getColorCompat(android.R.color.holo_blue_dark)
        val icon = getDrawableCompat(android.R.drawable.ic_menu_info_details)

        toastShowShort("기본 색상 로드됨: ${Integer.toHexString(primaryColor)}")
        
        // 안전한 리소스 접근
        // Safe resource access
        val safeColor = getColorSafe(android.R.color.holo_red_dark, Color.RED)
        val safeText = getStringSafe(android.R.string.ok)

        toastShowShort("안전한 색상: ${Integer.toHexString(safeColor)}")
        toastShowShort("안전한 텍스트: $safeText")

        // 포맷된 문자열
        // Formatted strings
        val welcomeText = getStringFormatted(
            android.R.string.ok, // Using existing string resource
            "User Name"
        )
        toastShowShort("포맷된 문자열: $welcomeText")
        
        // 추가 리소스 테스트
        // Additional resource tests
        val systemColor = getColorSafe(android.R.color.system_accent1_100, Color.BLUE)
        toastShowShort("시스템 색상: ${Integer.toHexString(systemColor)}")
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
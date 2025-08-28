// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
}

// 모든 하위 프로젝트에 detekt와 ktlint 적용
// Apply detekt and ktlint to all subprojects
subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    // Detekt 설정 - Detekt configuration
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        config.setFrom(rootProject.file("detekt.yml"))
        buildUponDefaultConfig = true
        autoCorrect = true
        reports {
            html.required.set(true)
            xml.required.set(true)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(false)
        }
    }

    // KtLint 설정 - KtLint configuration
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.0.1")
        debug.set(false)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(false)
        enableExperimentalRules.set(false)
        filter {
            exclude("**/generated/**")
            exclude("**/build/**")
        }
    }
}

// 전역 태스크 정의 - Global tasks definition
tasks.register("detektAll") {
    description = "Run detekt on all modules"
    group = "verification"
    dependsOn(subprojects.map { "${it.path}:detekt" })
}

tasks.register("ktlintCheckAll") {
    description = "Run ktlint check on all modules"
    group = "verification" 
    dependsOn(subprojects.map { "${it.path}:ktlintCheck" })
}

tasks.register("ktlintFormatAll") {
    description = "Run ktlint format on all modules"
    group = "formatting"
    dependsOn(subprojects.map { "${it.path}:ktlintFormat" })
}

tasks.register("codeQualityCheck") {
    description = "Run all code quality checks (detekt + ktlint)"
    group = "verification"
    dependsOn("detektAll", "ktlintCheckAll")
}
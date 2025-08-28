package kr.open.library.easy_extensions.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.IntegerRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

/********
 * View *
 ********/
public fun View.setVisible() {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
}

public fun View.setGone() {
    if (this.visibility != View.GONE) this.visibility = View.GONE
}

public fun View.setInvisible() {
    if (this.visibility != View.INVISIBLE) this.visibility = View.INVISIBLE
}

/**
 * Sets a debounced click listener on this view to prevent rapid consecutive clicks
 * Uses View's tag system to store timing information, preventing memory leaks
 *
 * @param debounceTime The minimum time interval between clicks in milliseconds (default: 600ms)
 * @param action The action to execute when a valid click occurs
 *
 * Example:
 * ```
 * button.setOnDebouncedClickListener(1000L) { view ->
 *     // This will only execute once per second maximum
 *     navigateToNextScreen()
 * }
 * ```
 */
public fun View.setOnDebouncedClickListener(
    debounceTime: Long = 600L,
    action: (View) -> Unit,
) {
    setOnClickListener { view ->
        val currentTime = System.currentTimeMillis()
        val lastClickTime = (view.getTag(ViewIds.LAST_CLICK_TIME) as? Long) ?: 0L

        if (currentTime - lastClickTime >= debounceTime) {
            view.setTag(ViewIds.LAST_CLICK_TIME, currentTime)
            action(view)
        }
    }
}

/*************
 * ViewGroup *
 *************/
public fun ViewGroup.forEachChild(action: (View) -> Unit) {
    for (i in 0 until childCount) {
        action(getChildAt(i))
    }
}

@SuppressLint("ResourceType")
public fun ViewGroup.getLayoutInflater(
    @IntegerRes xmlRes: Int,
    attachToRoot: Boolean,
): View = LayoutInflater.from(this.context).inflate(xmlRes, this, attachToRoot)

/*********************
 * Animation Extensions *
 *********************/

/**
 * Fades in the view with animation
 *
 * @param duration Animation duration in milliseconds (default: 300ms)
 * @param onComplete Optional callback when animation completes
 *
 * Example:
 * ```
 * imageView.fadeIn(500L) {
 *     // Animation completed
 * }
 * ```
 */
public fun View.fadeIn(
    duration: Long = 300L,
    onComplete: (() -> Unit)? = null,
) {
    if (alpha == 1f && isVisible) {
        onComplete?.invoke()
        return
    }

    alpha = 0f
    isVisible = true
    animate()
        .alpha(1f)
        .setDuration(duration)
        .setListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onComplete?.invoke()
                }
            },
        )
        .start()
}

/**
 * Fades out the view with animation
 *
 * @param duration Animation duration in milliseconds (default: 300ms)
 * @param hideOnComplete Whether to set visibility to GONE after animation (default: true)
 * @param onComplete Optional callback when animation completes
 *
 * Example:
 * ```
 * progressBar.fadeOut(200L, hideOnComplete = true) {
 *     // View is now hidden
 * }
 * ```
 */
public fun View.fadeOut(
    duration: Long = 300L,
    hideOnComplete: Boolean = true,
    onComplete: (() -> Unit)? = null,
) {
    if (alpha == 0f || !isVisible) {
        onComplete?.invoke()
        return
    }

    animate()
        .alpha(0f)
        .setDuration(duration)
        .setListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (hideOnComplete) {
                        visibility = View.GONE
                    }
                    onComplete?.invoke()
                }
            },
        )
        .start()
}

/**
 * Toggles visibility with fade animation
 *
 * @param duration Animation duration in milliseconds (default: 300ms)
 * @param onComplete Optional callback when animation completes
 *
 * Example:
 * ```
 * menuView.fadeToggle(400L) {
 *     // Toggle animation completed
 * }
 * ```
 */
public fun View.fadeToggle(
    duration: Long = 300L,
    onComplete: (() -> Unit)? = null,
) {
    if (isVisible && alpha > 0f) {
        fadeOut(duration, true, onComplete)
    } else {
        fadeIn(duration, onComplete)
    }
}

/**********************
 * Layout Extensions *
 **********************/

/**
 * Sets all margin values at once
 *
 * @param left Left margin in pixels
 * @param top Top margin in pixels
 * @param right Right margin in pixels
 * @param bottom Bottom margin in pixels
 *
 * Example:
 * ```
 * view.setMargins(16, 8, 16, 8)
 * ```
 */
public fun View.setMargins(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(left, top, right, bottom)
        layoutParams = params
    }
}

/**
 * Sets uniform margin for all sides
 *
 * @param margin Margin value in pixels for all sides
 *
 * Example:
 * ```
 * view.setMargin(16)
 * ```
 */
public fun View.setMargin(margin: Int) {
    setMargins(margin, margin, margin, margin)
}

/**
 * Sets all padding values at once
 *
 * @param left Left padding in pixels
 * @param top Top padding in pixels
 * @param right Right padding in pixels
 * @param bottom Bottom padding in pixels
 *
 * Example:
 * ```
 * view.setPaddings(12, 8, 12, 8)
 * ```
 */
public fun View.setPaddings(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
) {
    setPadding(left, top, right, bottom)
}

/**
 * Sets uniform padding for all sides
 *
 * @param padding Padding value in pixels for all sides
 *
 * Example:
 * ```
 * view.setPadding(12)
 * ```
 */
public fun View.setPadding(padding: Int) {
    setPadding(padding, padding, padding, padding)
}

/***********************
 * Measurement Extensions *
 ***********************/

/**
 * Executes a block when the view has been laid out and measured
 * Useful for getting actual view dimensions
 *
 * @param action Block to execute when view is laid out
 *
 * Example:
 * ```
 * customView.doOnLayout {
 *     val width = it.width
 *     val height = it.height
 *     // Use actual dimensions
 * }
 * ```
 */
public inline fun View.doOnLayout(crossinline action: (view: View) -> Unit) {
    if (isLaidOut && !isLayoutRequested) {
        action(this)
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    action(this@doOnLayout)
                }
            },
        )
    }
}

/**
 * Gets the view's location on screen as a Pair
 *
 * @return Pair of (x, y) coordinates on screen
 *
 * Example:
 * ```
 * val (x, y) = button.getLocationOnScreen()
 * ```
 */
public fun View.getLocationOnScreen(): Pair<Int, Int> {
    val location = IntArray(2)
    getLocationOnScreen(location)
    return Pair(location[0], location[1])
}

/**************************
 * Window Insets Extensions *
 **************************/

/**
 * Applies window insets as padding to the view
 * Useful for handling system bars and keyboard
 *
 * @param left Whether to apply left inset as left padding (default: true)
 * @param top Whether to apply top inset as top padding (default: true)
 * @param right Whether to apply right inset as right padding (default: true)
 * @param bottom Whether to apply bottom inset as bottom padding (default: true)
 *
 * Example:
 * ```
 * rootView.applyWindowInsetsAsPadding(bottom = true, top = false)
 * ```
 */
public fun View.applyWindowInsetsAsPadding(
    left: Boolean = true,
    top: Boolean = true,
    right: Boolean = true,
    bottom: Boolean = true,
) {
    val initialPadding = Pair(Pair(paddingLeft, paddingTop), Pair(paddingRight, paddingBottom))

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

        view.setPadding(
            if (left) initialPadding.first.first + systemBars.left else initialPadding.first.first,
            if (top) initialPadding.first.second + systemBars.top else initialPadding.first.second,
            if (right) initialPadding.second.first + systemBars.right else initialPadding.second.first,
            if (bottom) initialPadding.second.second + systemBars.bottom else initialPadding.second.second,
        )

        insets
    }
}

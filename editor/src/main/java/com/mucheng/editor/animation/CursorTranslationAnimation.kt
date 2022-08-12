/*
 * CN:
 * 作者：SuMuCheng
 * 我的 QQ: 3578557729
 * Github 主页：https://github.com/CaiMuCheng
 * 项目主页: https://github.com/CaiMuCheng/MuCodeEditor
 *
 * 你可以免费使用、商用以下代码，也可以基于以下代码做出修改，但是必须在你的项目中标注出处
 * 例如：在你 APP 的设置中添加 “关于编辑器” 一栏，其中标注作者以及此编辑器的 Github 主页
 *
 * 此代码使用 MPL 2.0 开源许可证，你必须标注作者信息
 * 若你要修改文件，请勿删除此注释
 * 若你违反以上条例我们有权向您提起诉讼!
 *
 * EN:
 * Author: SuMuCheng
 * My QQ-Number: 3578557729
 * Github Homepage: https://github.com/CaiMuCheng
 * Project Homepage: https://github.com/CaiMuCheng/MuCodeEditor
 *
 * You can use the following code for free, commercial use, or make modifications based on the following code, but you must mark the source in your project.
 * For example: add an "About Editor" column in your app's settings, which identifies the author and the Github home page of this editor.
 *
 * This code uses the MPL 2.0 open source license, you must mark the author information.
 * Do not delete this comment if you want to modify the file.
 * If you violate the above regulations we have the right to sue you!
 */

package com.mucheng.editor.animation

import android.animation.ValueAnimator
import com.mucheng.editor.base.animation.CursorAnimation
import com.mucheng.editor.view.MuCodeEditor

@Suppress("LeakingThis", "unused")
open class CursorTranslationAnimation(protected val editor: MuCodeEditor) : CursorAnimation,
    ValueAnimator.AnimatorUpdateListener {

    private val animatorX = ValueAnimator()

    private val animatorY = ValueAnimator()

    private var startX = 0f

    private var startY = 0f

    private var duration: Long = 120

    private var lastAnimatedTime: Long = 0

    init {
        animatorX.addUpdateListener(this)
    }

    override fun markAnimationBegin(line: Int, row: Int) {
        val layout = editor.layout
        val painters = editor.styleManager.painters
        startX = layout.measureLineRow(line, 0, row)
        startY = (painters.getLineHeight() * line).toFloat()
    }

    override fun markAnimationFinish(line: Int, row: Int) {
        if (!editor.functionManager.isCursorAnimationEnabled) {
            return
        }
        if (isRunning()) {
            startX = animatedX()
            startY = animatedY()
            cancel()
        }
        val layout = editor.layout
        val painters = editor.styleManager.painters
        val endX = layout.measureLineRow(line, 0, row)
        val endY = (painters.getLineHeight() * line).toFloat()

        animatorX.setFloatValues(startX, endX)
        animatorY.setFloatValues(startY, endY)

        animatorX.duration = duration
        animatorY.duration = duration
        // 若多次进行动画说明需要瞬间执行
        if (System.currentTimeMillis() - lastAnimatedTime < 100) {
            animatorX.duration = 0
            animatorY.duration = 0
        }
    }

    override fun start() {
        if (!editor.functionManager.isCursorAnimationEnabled) {
            return
        }

        animatorX.start()
        animatorY.start()
        lastAnimatedTime = System.currentTimeMillis()
    }

    override fun cancel() {
        animatorX.cancel()
        animatorY.cancel()
    }

    override fun setDuration(duration: Long) {
        this.duration = duration
    }

    override fun getDuration(): Long {
        return duration
    }

    override fun isRunning(): Boolean {
        return animatorX.isRunning || animatorY.isRunning
    }

    override fun animatedX(): Float {
        return animatorX.animatedValue as Float
    }

    override fun animatedY(): Float {
        return animatorY.animatedValue as Float
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        editor.postInvalidateOnAnimation()
    }


}
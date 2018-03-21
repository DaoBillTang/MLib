package com.daotangbill.exlib.ui.suppertext

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import com.daotangbill.exlib.commons.utils.dip2px
import com.daotangbill.exlib.commons.utils.sp2px
import com.daotangbill.exlib.exlib.R

/**
 * 打造万能的布局满足市面常见的样式
 * sLeftTextString	string	左边文字字符串
 * sLeftTopTextString	string	左上文字字符串
 * sLeftBottomTextString	string	左下文字字符串
 * sCenterTextString	string	中间文字字符串
 * sCenterTopTextString	string	中上文字字符串
 * sCenterBottomTextString	string	中下文字字符串
 * sRightTextString	string	右边文字字符串
 * sRightTopTextString	string	右上文字字符串
 * sRightBottomTextString	string	右下文字字符串
 * sLeftTextColor	color	左边文字颜色	默认0xFF373737
 * sLeftTopTextColor	color	左上文字颜色	默认0xFF373737
 * sLeftBottomTextColor	color	左下文字颜色	默认0xFF373737
 * sCenterTextColor	color	中间文字颜色	默认0xFF373737
 * sCenterTopTextColor	color	中上文字颜色	默认0xFF373737
 * sCenterBottomTextColor	color	中下文字颜色	默认0xFF373737
 * sRightTextColor	color	左边文字颜色	默认0xFF373737
 * sRightTopTextColor	color	右上文字颜色	默认0xFF373737
 * sRightBottomTextColor	color	右下文字颜色	默认0xFF373737
 * sLeftTextSize	dimension	左边字体大小	默认15sp
 * sLeftTopTextSize	dimension	左上字体大小	默认15sp
 * sLeftBottomTextSize	dimension	左下字体大小	默认15sp
 * sCenterTextSize	dimension	中间字体大小	默认15sp
 * sCenterTopTextSize	dimension	中上字体大小	默认15sp
 * sCenterBottomTextSize	dimension	中下字体大小	默认15sp
 * sRightTextSize	dimension	右边字体大小	默认15sp
 * sRightTopTextSize	dimension	右上字体大小	默认15sp
 * sRightBottomTextSize	dimension	右下字体大小	默认15sp
 * sLeftLines	integer	左边文字显示行数	默认1
 * sLeftTopLines	integer	左上文字显示行数	默认1
 * sLeftBottomLines	integer	左下文字显示行数	默认1
 * sCenterLines	integer	中间文字显示行数	默认1
 * sCenterTopLines	integer	中上文字显示行数	默认1
 * sCenterBottomLines	integer	中下文字显示行数	默认1
 * sRightLines	integer	右边文字显示行数	默认1
 * sRightTopLines	integer	右上文字显示行数	默认1
 * sRightBottomLines	integer	右下文字显示行数	默认1
 * sLeftMaxEms	integer	左边文字显示个数	默认15
 * sLeftTopMaxEms	integer	左上文字显示个数	默认15
 * sLeftBottomMaxEms	integer	左下文字显示个数	默认15
 * sCenterMaxEms	integer	中间文字显示个数	默认15
 * sCenterTopMaxEms	integer	中上文字显示个数	默认15
 * sCenterBottomMaxEms	integer	中下文字显示个数	默认15
 * sRightMaxEms	integer	右边文字显示个数	默认15
 * sRightTopMaxEms	integer	右上文字显示个数	默认15
 * sRightBottomMaxEms	integer	右下文字显示个数	默认15
 * sLeftViewGravity	enum	左边文字对齐方式
 * left_center(左对齐)
 * center(居中)
 * right_center(右对齐)	默认center
 * sCenterViewGravity	enum	中间文字对齐方式
 * left_center(左对齐)
 * center(居中)
 * right_center(右对齐)	默认center
 * sRightViewGravity	enum	右边文字对齐方式
 * left_center(左对齐)
 * center(居中)
 * right_center(右对齐)	默认center
 * sLeftTvDrawableLeft	reference	左边TextView左侧的drawable
 * sLeftTvDrawableRight	reference	左边TextView右侧的drawable
 * sCenterTvDrawableLeft	reference	中间TextView左侧的drawable
 * sCenterTvDrawableRight	reference	中间TextView右侧的drawable
 * sRightTvDrawableLeft	reference	右边TextView左侧的drawable
 * sRightTvDrawableRight	reference	右边TextView右侧的drawable
 * sLeftTvDrawableWidth	dimension	左边TextView的drawable的宽度
 * sLeftTvDrawableHeight	dimension	左边TextView的drawable的高度
 * sCenterTvDrawableWidth	dimension	中间TextView的drawable的宽度
 * sCenterTvDrawableHeight	dimension	中间TextView的drawable的高度
 * sRightTvDrawableWidth	dimension	右边TextView的drawable的宽度
 * sRightTvDrawableHeight	dimension	右边TextView的drawable的高度
 * sTextViewDrawablePadding	dimension	TextView的drawable对应的Padding	默认10dp
 * sLeftViewWidth	dimension	左边textView的宽度 为了中间文字左对齐的时候使用
 * sTopDividerLineMarginLR	dimension	上边分割线的MarginLeft和MarginRight	默认0dp
 * sTopDividerLineMarginLeft	dimension	上边分割线的MarginLeft	默认0dp
 * sTopDividerLineMarginRight	dimension	上边分割线的MarginRight	默认0dp
 * sBottomDividerLineMarginLR	dimension	下边分割线的MarginLeft和MarginRigh	默认0dp
 * sBottomDividerLineMarginLeft	dimension	下边分割线的MarginLeft	默认0dp
 * sBottomDividerLineMarginRight	dimension	下边分割线的MarginRight	默认0dp
 * sDividerLineColor	color	分割线的颜色	默认0xFFE8E8E8
 * sDividerLineHeight	dimension	分割线的高度	默认0.5dp
 * sDividerLineType	enum	分割线显示方式
 * none(不显示分割线)
 * top(显示上边的分割线)
 * bottom(显示下边的分割线)
 * both(显示上下两条分割线)	默认bottom
 * sLeftViewMarginLeft	dimension	左边view的MarginLeft	默认10dp
 * sLeftViewMarginRight	dimension	左边view的MarginRight	默认10dp
 * sCenterViewMarginLeft	dimension	中间view的MarginLeft	默认10dp
 * sCenterViewMarginRight	dimension	中间view的MarginRight	默认10dp
 * sRightViewMarginLeft	dimension	右边view的MarginLeft	默认10dp
 * sRightViewMarginRight	dimension	右边view的MarginRight	默认10dp
 * sLeftTextIsBold	boolean	左边文字是否加粗	默认false
 * sLeftTopTextIsBold	boolean	左上文字是否加粗	默认false
 * sLeftBottomTextIsBold	boolean	左下文字是否加粗	默认false
 * sCenterTextIsBold	boolean	中间文字是否加粗	默认false
 * sCenterTopTextIsBold	boolean	中上文字是否加粗	默认false
 * sCenterBottomTextIsBold	boolean	中下文字是否加粗	默认false
 * sRightTextIsBold	boolean	右边文字是否加粗	默认false
 * sRightTopTextIsBold	boolean	右上文字是否加粗	默认false
 * sRightBottomTextIsBold	boolean	右下文字是否加粗	默认false
 * sLeftIconRes	reference	左边图片资源 可以用来显示网络图片或者本地
 * sRightIconRes	reference	右边图片资源 可以用来显示网络图片或者本地
 * sLeftIconWidth	dimension	左边图片资源的宽度 用于固定图片大小的时候使用
 * sLeftIconHeight	dimension	左边图片资源的高度 用于固定图片大小的时候使用
 * sRightIconWidth	dimension	右边图片资源的宽度 用于固定图片大小的时候使用
 * sRightIconHeight	dimension	右边图片资源的高度 用于固定图片大小的时候使用
 * sLeftIconMarginLeft	dimension	左边图片资源的MarginLeft	默认10dp
 * sRightIconMarginRight	dimension	右边图片资源的MarginLeft	默认10dp
 * sCenterSpaceHeight	dimension	上中下三行文字的间距	默认5dp
 * sRightCheckBoxRes	reference	右边CheckBox的资源
 * sRightCheckBoxMarginRight	dimension	右边CheckBox的MarginRight	默认10dp
 * sIsChecked	boolean	右边CheckBox是否选中	默认 false
 * sUseRipple	boolean	是否开启点击出现水波效果	默认 true
 * sBackgroundDrawableRes	reference	SuperTextView的背景资源
 * sRightViewType	enum	右边显示的特殊View
 * checkbox
 * switchBtn	默认都不显示
 * sRightSwitchMarginRight	dimension	右边SwitchBtn的MarginRight	默认10dp
 * sSwitchIsChecked	boolean	右边SwitchBtn是否选中	默认 false
 * sTextOff	string	TextOff	默认""
 * sTextOn	string	TextOn	默认""
 * sSwitchMinWidth	dimension	SwitchMinWidth	系统默认
 * sSwitchPadding	dimension	SwitchPadding	系统默认
 * sThumbTextPadding	dimension	ThumbTextPadding	系统默认
 * sThumbResource	reference	右边SwitchBtn自定义选中资源	系统默认
 * sTrackResource	reference	右边SwitchBtn自定义未选中资源	系统默认
 * sUseShape	boolean	是否使用shape设置圆角及触摸反馈
 * 设为true之后才能使用一下属性	默认false
 * sShapeSolidColor	color	填充色	默认false
 * sShapeSelectorPressedColor	color	按下时候的颜色	默认0xffffffff
 * sShapeSelectorNormalColor	color	正常显示的颜色	默认0xffffffff
 * sShapeCornersRadius	dimension	四个角的圆角半径	默认0dp
 * sShapeCornersTopLeftRadius	dimension	左上角的圆角半径	默认0dp
 * sShapeCornersTopRightRadius	dimension	右上角的圆角半径	默认0dp
 * sShapeCornersBottomLeftRadius	dimension	左下角的圆角半径	默认0dp
 * sShapeCornersBottomRightRadius	dimension	右下角的圆角半径	默认0dp
 * sShapeStrokeWidth	dimension	边框宽度	默认0dp
 * sShapeStrokeDashWidth	dimension	虚线宽度	默认0dp
 * sShapeStrokeDashGap	dimension	虚线间隙宽度	默认0dp
 * sShapeStrokeColor	color	边框颜色	默认0dp
 * sLeftTextBackground	reference	左边textView的背景
 * sCenterTextBackground	reference	中间textView的背景
 * sRightTextBackground	reference	右边textView的背景
 */
class SuperTextView @JvmOverloads constructor(private val mContext: Context,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0)
    : RelativeLayout(mContext, attrs, defStyleAttr) {

    private var leftView: BaseTextView? = null
    private var centerView: BaseTextView? = null
    private var rightView: BaseTextView? = null
    private var leftBaseViewParams: RelativeLayout.LayoutParams? = null
    private var centerBaseViewParams: RelativeLayout.LayoutParams? = null
    private var rightBaseViewParams: RelativeLayout.LayoutParams? = null

    private var leftIconIV: ImageView? = null
    private var rightIconIV: ImageView? = null
    private var leftImgParams: RelativeLayout.LayoutParams? = null
    private var rightImgParams: RelativeLayout.LayoutParams? = null
    private var leftIconWidth: Int = 0//左边图标的宽
    private var leftIconHeight: Int = 0//左边图标的高

    private var rightIconWidth: Int = 0//右边图标的宽
    private var rightIconHeight: Int = 0//右边图标的高

    private var leftIconMarginLeft: Int = 0//左边图标的左边距
    private var rightIconMarginRight: Int = 0//右边图标的右边距

    private var leftIconRes: Drawable? = null//左边图标资源
    private var rightIconRes: Drawable? = null//右边图标资源

    private val defaultColor = -0xc8c8c9//文字默认颜色
    private var defaultSize = 15//默认字体大小
    private val defaultMaxEms = 15

    private var mLeftTextString: String? = null
    private var mLeftTopTextString: String? = null
    private var mLeftBottomTextString: String? = null

    private var mRightTextString: String? = null
    private var mRightTopTextString: String? = null
    private var mRightBottomTextString: String? = null

    private var mCenterTextString: String? = null
    private var mCenterTopTextString: String? = null
    private var mCenterBottomTextString: String? = null

    private var mLeftTextColor: Int = 0
    private var mLeftTopTextColor: Int = 0
    private var mLeftBottomTextColor: Int = 0

    private var mCenterTextColor: Int = 0
    private var mCenterTopTextColor: Int = 0
    private var mCenterBottomTextColor: Int = 0

    private var mRightTextColor: Int = 0
    private var mRightTopTextColor: Int = 0
    private var mRightBottomTextColor: Int = 0

    private var mLeftTextSize: Int = 0
    private var mLeftTopTextSize: Int = 0
    private var mLeftBottomTextSize: Int = 0

    private var mRightTextSize: Int = 0
    private var mRightTopTextSize: Int = 0
    private var mRightBottomTextSize: Int = 0

    private var mCenterTextSize: Int = 0
    private var mCenterTopTextSize: Int = 0
    private var mCenterBottomTextSize: Int = 0

    private var mLeftTopLines: Int = 0
    private var mLeftLines: Int = 0
    private var mLeftBottomLines: Int = 0

    private var mCenterTopLines: Int = 0
    private var mCenterLines: Int = 0
    private var mCenterBottomLines: Int = 0

    private var mRightTopLines: Int = 0
    private var mRightLines: Int = 0
    private var mRightBottomLines: Int = 0

    private var mLeftTopMaxEms: Int = 0
    private var mLeftMaxEms: Int = 0
    private var mLeftBottomMaxEms: Int = 0

    private var mCenterTopMaxEms: Int = 0
    private var mCenterMaxEms: Int = 0
    private var mCenterBottomMaxEms: Int = 0

    private var mRightTopMaxEms: Int = 0
    private var mRightMaxEms: Int = 0
    private var mRightBottomMaxEms: Int = 0

    private var mLeftTopTextBold: Boolean = false
    private var mLeftTextBold: Boolean = false
    private var mLeftBottomTextBold: Boolean = false

    private var mCenterTopTextBold: Boolean = false
    private var mCenterTextBold: Boolean = false
    private var mCenterBottomTextBold: Boolean = false

    private var mRightTopTextBold: Boolean = false
    private var mRightTextBold: Boolean = false
    private var mRightBottomTextBold: Boolean = false

    private var mLeftTextBackground: Drawable? = null
    private var mCenterTextBackground: Drawable? = null
    private var mRightTextBackground: Drawable? = null

    private var mLeftTvDrawableLeft: Drawable? = null
    private var mLeftTvDrawableRight: Drawable? = null

    private var mCenterTvDrawableLeft: Drawable? = null
    private var mCenterTvDrawableRight: Drawable? = null

    private var mRightTvDrawableLeft: Drawable? = null
    private var mRightTvDrawableRight: Drawable? = null

    private var mLeftTvDrawableWidth: Int = 0
    private var mLeftTvDrawableHeight: Int = 0

    private var mCenterTvDrawableWidth: Int = 0
    private var mCenterTvDrawableHeight: Int = 0

    private var mRightTvDrawableWidth: Int = 0
    private var mRightTvDrawableHeight: Int = 0

    private var mTextViewDrawablePadding: Int = 0

    private var mLeftGravity: Int = 0
    private var mCenterGravity: Int = 0
    private var mRightGravity: Int = 0

    private var mLeftViewWidth: Int = 0

    private var topDividerLineView: View? = null
    private var bottomDividerLineView: View? = null

    private var topDividerLineParams: RelativeLayout.LayoutParams? = null
    private var bottomDividerLineParams: RelativeLayout.LayoutParams? = null
    private var mTopDividerLineMarginLR: Int = 0
    private var mTopDividerLineMarginLeft: Int = 0
    private var mTopDividerLineMarginRight: Int = 0

    private var mBottomDividerLineMarginLR: Int = 0
    private var mBottomDividerLineMarginLeft: Int = 0
    private var mBottomDividerLineMarginRight: Int = 0

    private var mDividerLineType: Int = 0
    private var mDividerLineColor: Int = 0
    private var mDividerLineHeight: Int = 0

    private val mDefaultDividerLineColor = -0x171718//分割线默认颜色

    private var default_Margin = 10

    private var mLeftViewMarginLeft: Int = 0
    private var mLeftViewMarginRight: Int = 0

    private var mCenterViewMarginLeft: Int = 0
    private var mCenterViewMarginRight: Int = 0

    private var mRightViewMarginLeft: Int = 0
    private var mRightViewMarginRight: Int = 0


    private var useRipple: Boolean = false
    private var mBackground_drawable: Drawable? = null

    private var superTextViewClickListener: OnSuperTextViewClickListener? = null

    private var leftTopTvClickListener: OnLeftTopTvClickListener? = null
    private var leftTvClickListener: OnLeftTvClickListener? = null
    private var leftBottomTvClickListener: OnLeftBottomTvClickListener? = null

    private var centerTopTvClickListener: OnCenterTopTvClickListener? = null
    private var centerTvClickListener: OnCenterTvClickListener? = null
    private var centerBottomTvClickListener: OnCenterBottomTvClickListener? = null

    private var rightTopTvClickListener: OnRightTopTvClickListener? = null
    private var rightTvClickListener: OnRightTvClickListener? = null
    private var rightBottomTvClickListener: OnRightBottomTvClickListener? = null

    private var switchCheckedChangeListener: OnSwitchCheckedChangeListener? = null
    private var checkBoxCheckedChangeListener: OnCheckBoxCheckedChangeListener? = null

    private var leftImageViewClickListener: OnLeftImageViewClickListener? = null
    private var rightImageViewClickListener: OnRightImageViewClickListener? = null

    private var rightCheckBox: CheckBox? = null//右边checkbox
    private var rightCheckBoxParams: RelativeLayout.LayoutParams? = null//右边checkbox
    private var rightCheckBoxBg: Drawable? = null//checkBox的背景
    private var rightCheckBoxMarginRight: Int = 0//右边checkBox的右边距
    private var isChecked: Boolean = false//是否默认选中

    private var centerSpaceHeight: Int = 0//中间空间的高度

    private var mSwitch: Switch? = null
    private var mSwitchParams: RelativeLayout.LayoutParams? = null//右边switch
    private var rightSwitchMarginRight: Int = 0
    private var switchIsChecked = true

    private var mTextOff: String? = null
    private var mTextOn: String? = null

    private var mSwitchMinWidth: Int = 0
    private var mSwitchPadding: Int = 0

    private var mThumbTextPadding: Int = 0

    private var mThumbResource: Drawable? = null
    private var mTrackResource: Drawable? = null

    /////////////////////一下是shape相关属性
    private val defaultShapeColor = -0x1

    private var selectorPressedColor: Int = 0
    private var selectorNormalColor: Int = 0

    private var solidColor: Int = 0

    override fun getSolidColor(): Int {
        super.getSolidColor()
        return solidColor
    }

    private var cornersRadius: Float = 0.toFloat()
    private var cornersTopLeftRadius: Float = 0.toFloat()
    private var cornersTopRightRadius: Float = 0.toFloat()
    private var cornersBottomLeftRadius: Float = 0.toFloat()
    private var cornersBottomRightRadius: Float = 0.toFloat()

    private var strokeWidth: Int = 0
    private var strokeColor: Int = 0

    private var strokeDashWidth: Float = 0.toFloat()
    private var strokeDashGap: Float = 0.toFloat()

    private var useShape: Boolean = false

    private var gradientDrawable: GradientDrawable? = null

    companion object {

        private val gravity_Left_Center = 0
        private val gravity_Center = 1
        private val gravity_Right_Center = 2

        private val default_Gravity = 1

        /**
         * 分割线的类型
         */
        private val NONE = 0
        private val TOP = 1
        private val BOTTOM = 2
        private val BOTH = 3
        private val default_Divider = BOTTOM

        private val TYPE_CHECKBOX = 0
        private val TYPE_SWITCH = 1

        private var mRightViewType: Int = 0
    }

    /**
     * 获取左上字符串
     *
     * @return 返回字符串
     */
    val leftTopString: String
        get() = if (leftView != null) leftView!!.topTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取左中字符串
     *
     * @return 返回字符串
     */
    val leftString: String
        get() = if (leftView != null) leftView!!.centerTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取左下字符串
     *
     * @return 返回字符串
     */
    val leftBottomString: String
        get() = if (leftView != null) leftView!!.bottomTextView.text.toString().trim { it <= ' ' } else ""

    ////////////////////////////////////////////

    /**
     * 获取中上字符串
     *
     * @return 返回字符串
     */
    val centerTopString: String
        get() = if (centerView != null) centerView!!.topTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取中间字符串
     *
     * @return 返回字符串
     */

    val centerString: String
        get() = if (centerView != null) centerView!!.centerTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取中下字符串
     *
     * @return 返回字符串
     */
    val centerBottomString: String
        get() = if (centerView != null) centerView!!.bottomTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取右上字符串
     *
     * @return 返回字符串
     */
    val rightTopString: String
        get() = if (rightView != null) rightView!!.topTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取右中字符串
     *
     * @return 返回字符串
     */
    val rightString: String
        get() = if (rightView != null) rightView!!.centerTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取右下字符串
     *
     * @return 返回字符串
     */
    val rightBottomString: String
        get() = if (rightView != null) rightView!!.bottomTextView.text.toString().trim { it <= ' ' } else ""

    /**
     * 获取checkbox状态
     *
     * @return 返回选择框当前选中状态
     */
    val cbisChecked: Boolean
        get() {
            var isChecked = false
            if (rightCheckBox != null) {
                isChecked = rightCheckBox!!.isChecked
            }
            return isChecked
        }

    /**
     * 获取左上的TextView
     *
     * @return textView
     */
    val leftTopTextView: TextView?
        get() {
            var textView: TextView? = null
            if (leftView != null) {
                textView = leftView!!.topTextView
            }
            return textView
        }

    /**
     * 获取左中的TextView
     *
     * @return textView
     */
    val leftTextView: TextView?
        get() {
            var textView: TextView? = null
            if (leftView != null) {
                textView = leftView!!.centerTextView
            }
            return textView
        }

    /**
     * 获取左下的TextView
     *
     * @return textView
     */
    val leftBottomTextView: TextView?
        get() {
            var textView: TextView? = null
            if (leftView != null) {
                textView = leftView!!.bottomTextView
            }
            return textView
        }

    /**
     * 获取中上的TextView
     *
     * @return textView
     */
    val centerTopTextView: TextView?
        get() {
            var textView: TextView? = null
            if (centerView != null) {
                textView = centerView!!.topTextView
            }
            return textView
        }

    /**
     * 获取中中的TextView
     *
     * @return textView
     */
    val centerTextView: TextView?
        get() {
            var textView: TextView? = null
            if (centerView != null) {
                textView = centerView!!.centerTextView
            }
            return textView
        }

    /**
     * 获取中下的TextView
     *
     * @return textView
     */
    val centerBottomTextView: TextView?
        get() {
            var textView: TextView? = null
            if (centerView != null) {
                textView = centerView!!.bottomTextView
            }
            return textView
        }

    /**
     * 获取右上的TextView
     *
     * @return textView
     */
    val rightTopTextView: TextView?
        get() {
            var textView: TextView? = null
            if (rightView != null) {
                textView = rightView!!.topTextView
            }
            return textView
        }

    /**
     * 获取右中的TextView
     *
     * @return textView
     */
    val rightTextView: TextView?
        get() {
            var textView: TextView? = null
            if (rightView != null) {
                textView = rightView!!.centerTextView
            }
            return textView
        }

    /**
     * 获取右下的TextView
     *
     * @return textView
     */
    val rightBottomTextView: TextView?
        get() {
            var textView: TextView? = null
            if (rightView != null) {
                textView = rightView!!.bottomTextView
            }
            return textView
        }


    // TODO: 2017/7/10 一下是shape相关属性方法

    /**
     * 获取设置之后的Selector
     *
     * @return stateListDrawable
     */
    //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
    //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
    val selector: StateListDrawable
        get() {
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled), getDrawable(android.R.attr.state_pressed))
            stateListDrawable.addState(intArrayOf(), getDrawable(android.R.attr.state_enabled))

            return stateListDrawable
        }

    init {
        defaultSize = sp2px(defaultSize.toFloat())
        default_Margin = dip2px(default_Margin.toFloat())

        getAttr(attrs)
        initView()
    }

    private fun getAttr(attrs: AttributeSet?) {
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.SuperTextView)

        /////////////////////////////////////////////////
        mLeftTextString = typedArray.getString(R.styleable.SuperTextView_sLeftTextString)
        mLeftTopTextString = typedArray.getString(R.styleable.SuperTextView_sLeftTopTextString)
        mLeftBottomTextString = typedArray.getString(R.styleable.SuperTextView_sLeftBottomTextString)

        mCenterTextString = typedArray.getString(R.styleable.SuperTextView_sCenterTextString)
        mCenterTopTextString = typedArray.getString(R.styleable.SuperTextView_sCenterTopTextString)
        mCenterBottomTextString = typedArray.getString(R.styleable.SuperTextView_sCenterBottomTextString)

        mRightTextString = typedArray.getString(R.styleable.SuperTextView_sRightTextString)
        mRightTopTextString = typedArray.getString(R.styleable.SuperTextView_sRightTopTextString)
        mRightBottomTextString = typedArray.getString(R.styleable.SuperTextView_sRightBottomTextString)

        //////////////////////////////////////////////////

        mLeftTextColor = typedArray.getColor(R.styleable.SuperTextView_sLeftTextColor, defaultColor)
        mLeftTopTextColor = typedArray.getColor(R.styleable.SuperTextView_sLeftTopTextColor, defaultColor)
        mLeftBottomTextColor = typedArray.getColor(R.styleable.SuperTextView_sLeftBottomTextColor, defaultColor)

        mCenterTextColor = typedArray.getColor(R.styleable.SuperTextView_sCenterTextColor, defaultColor)
        mCenterTopTextColor = typedArray.getColor(R.styleable.SuperTextView_sCenterTopTextColor, defaultColor)
        mCenterBottomTextColor = typedArray.getColor(R.styleable.SuperTextView_sCenterBottomTextColor, defaultColor)

        mRightTextColor = typedArray.getColor(R.styleable.SuperTextView_sRightTextColor, defaultColor)
        mRightTopTextColor = typedArray.getColor(R.styleable.SuperTextView_sRightTopTextColor, defaultColor)
        mRightBottomTextColor = typedArray.getColor(R.styleable.SuperTextView_sRightBottomTextColor, defaultColor)

        //////////////////////////////////////////////////


        mLeftTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftTextSize, defaultSize)
        mLeftTopTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftTopTextSize, defaultSize)
        mLeftBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftBottomTextSize, defaultSize)

        mCenterTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterTextSize, defaultSize)
        mCenterTopTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterTopTextSize, defaultSize)
        mCenterBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterBottomTextSize, defaultSize)

        mRightTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightTextSize, defaultSize)
        mRightTopTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightTopTextSize, defaultSize)
        mRightBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightBottomTextSize, defaultSize)

        //////////////////////////////////////////////////
        mLeftTopLines = typedArray.getInt(R.styleable.SuperTextView_sLeftTopLines, 1)
        mLeftLines = typedArray.getInt(R.styleable.SuperTextView_sLeftLines, 1)
        mLeftBottomLines = typedArray.getInt(R.styleable.SuperTextView_sLeftBottomLines, 1)

        mCenterTopLines = typedArray.getInt(R.styleable.SuperTextView_sCenterTopLines, 1)
        mCenterLines = typedArray.getInt(R.styleable.SuperTextView_sCenterLines, 1)
        mCenterBottomLines = typedArray.getInt(R.styleable.SuperTextView_sCenterBottomLines, 1)

        mRightTopLines = typedArray.getInt(R.styleable.SuperTextView_sRightTopLines, 1)
        mRightLines = typedArray.getInt(R.styleable.SuperTextView_sRightLines, 1)
        mRightBottomLines = typedArray.getInt(R.styleable.SuperTextView_sRightBottomLines, 1)

        //////////////////////////////////////////////////

        mLeftTopMaxEms = typedArray.getInt(R.styleable.SuperTextView_sLeftTopMaxEms, defaultMaxEms)
        mLeftMaxEms = typedArray.getInt(R.styleable.SuperTextView_sLeftMaxEms, defaultMaxEms)
        mLeftBottomMaxEms = typedArray.getInt(R.styleable.SuperTextView_sLeftBottomMaxEms, defaultMaxEms)

        mCenterTopMaxEms = typedArray.getInt(R.styleable.SuperTextView_sCenterTopMaxEms, defaultMaxEms)
        mCenterMaxEms = typedArray.getInt(R.styleable.SuperTextView_sCenterMaxEms, defaultMaxEms)
        mCenterBottomMaxEms = typedArray.getInt(R.styleable.SuperTextView_sCenterBottomMaxEms, defaultMaxEms)

        mRightTopMaxEms = typedArray.getInt(R.styleable.SuperTextView_sRightTopMaxEms, defaultMaxEms)
        mRightMaxEms = typedArray.getInt(R.styleable.SuperTextView_sRightMaxEms, defaultMaxEms)
        mRightBottomMaxEms = typedArray.getInt(R.styleable.SuperTextView_sRightBottomMaxEms, defaultMaxEms)

        ////////////////////////////////////////////////

        mLeftGravity = typedArray.getInt(R.styleable.SuperTextView_sLeftViewGravity, default_Gravity)
        mCenterGravity = typedArray.getInt(R.styleable.SuperTextView_sCenterViewGravity, default_Gravity)
        mRightGravity = typedArray.getInt(R.styleable.SuperTextView_sRightViewGravity, default_Gravity)

        ////////////////////////////////////////////////

        mLeftTvDrawableLeft = typedArray.getDrawable(R.styleable.SuperTextView_sLeftTvDrawableLeft)
        mLeftTvDrawableRight = typedArray.getDrawable(R.styleable.SuperTextView_sLeftTvDrawableRight)
        mCenterTvDrawableLeft = typedArray.getDrawable(R.styleable.SuperTextView_sCenterTvDrawableLeft)
        mCenterTvDrawableRight = typedArray.getDrawable(R.styleable.SuperTextView_sCenterTvDrawableRight)
        mRightTvDrawableLeft = typedArray.getDrawable(R.styleable.SuperTextView_sRightTvDrawableLeft)
        mRightTvDrawableRight = typedArray.getDrawable(R.styleable.SuperTextView_sRightTvDrawableRight)

        mTextViewDrawablePadding = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sTextViewDrawablePadding, default_Margin)
        ////////////////////////////////////////////////

        mLeftTvDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftTvDrawableWidth, -1)
        mLeftTvDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftTvDrawableHeight, -1)

        mCenterTvDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterTvDrawableWidth, -1)
        mCenterTvDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterTvDrawableHeight, -1)

        mRightTvDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightTvDrawableWidth, -1)
        mRightTvDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightTvDrawableHeight, -1)

        mLeftViewWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftViewWidth, 0)
        ///////////////////////////////////////////////
        mTopDividerLineMarginLR = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sTopDividerLineMarginLR, 0)
        mTopDividerLineMarginLeft = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sTopDividerLineMarginLeft, 0)
        mTopDividerLineMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sTopDividerLineMarginRight, 0)

        mBottomDividerLineMarginLR = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sBottomDividerLineMarginLR, 0)
        mBottomDividerLineMarginLeft = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sBottomDividerLineMarginLeft, 0)
        mBottomDividerLineMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sBottomDividerLineMarginRight, 0)
        ///////////////////////////////////////////////
        mDividerLineType = typedArray.getInt(R.styleable.SuperTextView_sDividerLineType, default_Divider)
        mDividerLineColor = typedArray.getColor(R.styleable.SuperTextView_sDividerLineColor, mDefaultDividerLineColor)

        mDividerLineHeight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sDividerLineHeight, dip2px(0.5f))
        ////////////////////////////////////////////////
        mLeftViewMarginLeft = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftViewMarginLeft, default_Margin)
        mLeftViewMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftViewMarginRight, default_Margin)
        mCenterViewMarginLeft = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterViewMarginLeft, 0)
        mCenterViewMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterViewMarginRight, 0)
        mRightViewMarginLeft = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightViewMarginLeft, default_Margin)
        mRightViewMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightViewMarginRight, default_Margin)
        ///////////////////////////////////////////////
        leftIconWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftIconWidth, 0)
        leftIconHeight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftIconHeight, 0)

        rightIconWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightIconWidth, 0)
        rightIconHeight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightIconHeight, 0)

        leftIconMarginLeft = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sLeftIconMarginLeft, default_Margin)
        rightIconMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightIconMarginRight, default_Margin)

        leftIconRes = typedArray.getDrawable(R.styleable.SuperTextView_sLeftIconRes)
        rightIconRes = typedArray.getDrawable(R.styleable.SuperTextView_sRightIconRes)
        //////////////////////////////////////////////
        mLeftTopTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sLeftTopTextIsBold, false)
        mLeftTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sLeftTextIsBold, false)
        mLeftBottomTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sLeftBottomTextIsBold, false)

        mCenterTopTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sCenterTopTextIsBold, false)
        mCenterTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sCenterTextIsBold, false)
        mCenterBottomTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sCenterBottomTextIsBold, false)

        mRightTopTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sRightTopTextIsBold, false)
        mRightTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sRightTextIsBold, false)
        mRightBottomTextBold = typedArray.getBoolean(R.styleable.SuperTextView_sRightBottomTextIsBold, false)

        mLeftTextBackground = typedArray.getDrawable(R.styleable.SuperTextView_sLeftTextBackground)
        mCenterTextBackground = typedArray.getDrawable(R.styleable.SuperTextView_sCenterTextBackground)
        mRightTextBackground = typedArray.getDrawable(R.styleable.SuperTextView_sRightTextBackground)

        //////////////////////////////////////////////
        useRipple = typedArray.getBoolean(R.styleable.SuperTextView_sUseRipple, true)
        mBackground_drawable = typedArray.getDrawable(R.styleable.SuperTextView_sBackgroundDrawableRes)
        ///////////////////////////////////////////////
        mRightViewType = typedArray.getInt(R.styleable.SuperTextView_sRightViewType, -1)
        ////////////////////////////////////////////////
        isChecked = typedArray.getBoolean(R.styleable.SuperTextView_sIsChecked, false)
        rightCheckBoxMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightCheckBoxMarginRight, default_Margin)
        rightCheckBoxBg = typedArray.getDrawable(R.styleable.SuperTextView_sRightCheckBoxRes)
        //////////////////////////////////////////////////
        rightSwitchMarginRight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sRightSwitchMarginRight, default_Margin)
        switchIsChecked = typedArray.getBoolean(R.styleable.SuperTextView_sSwitchIsChecked, false)
        mTextOff = typedArray.getString(R.styleable.SuperTextView_sTextOff)
        mTextOn = typedArray.getString(R.styleable.SuperTextView_sTextOn)

        mSwitchMinWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sSwitchMinWidth, 0)
        mSwitchPadding = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sSwitchPadding, 0)
        mThumbTextPadding = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sThumbTextPadding, 0)

        mThumbResource = typedArray.getDrawable(R.styleable.SuperTextView_sThumbResource)
        mTrackResource = typedArray.getDrawable(R.styleable.SuperTextView_sTrackResource)

        centerSpaceHeight = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sCenterSpaceHeight, dip2px(5f))
        ////////////////////////////////////////////////////
        selectorPressedColor = typedArray.getColor(R.styleable.SuperTextView_sShapeSelectorPressedColor, defaultShapeColor)
        selectorNormalColor = typedArray.getColor(R.styleable.SuperTextView_sShapeSelectorNormalColor, defaultShapeColor)

        solidColor = typedArray.getColor(R.styleable.SuperTextView_sShapeSolidColor, defaultShapeColor)

        cornersRadius = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeCornersRadius, 0).toFloat()
        cornersTopLeftRadius = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeCornersTopLeftRadius, 0).toFloat()
        cornersTopRightRadius = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeCornersTopRightRadius, 0).toFloat()
        cornersBottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeCornersBottomLeftRadius, 0).toFloat()
        cornersBottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeCornersBottomRightRadius, 0).toFloat()

        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeStrokeWidth, 0)
        strokeDashWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeStrokeDashWidth, 0).toFloat()
        strokeDashGap = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_sShapeStrokeDashGap, 0).toFloat()

        strokeColor = typedArray.getColor(R.styleable.SuperTextView_sShapeStrokeColor, defaultShapeColor)

        useShape = typedArray.getBoolean(R.styleable.SuperTextView_sUseShape, false)

        typedArray.recycle()
    }

    /**
     * 初始化Params
     *
     * @param params params
     * @return params
     */
    private fun getParams(params: RelativeLayout.LayoutParams?): RelativeLayout.LayoutParams {
        var params = params
        if (params == null) {
            params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        }
        return params
    }

    /**
     * 初始化View
     */
    private fun initView() {
        initSuperTextView()
        initLeftIcon()
        when (mRightViewType) {
            TYPE_CHECKBOX -> initRightCheckBox()
            TYPE_SWITCH -> initRightSwitch()
        }
        initRightIcon()
        initLeftTextView()
        initCenterTextView()
        initRightTextView()
        initDividerLineView()
    }

    private fun initSuperTextView() {
        if (useRipple) {
            this.setBackgroundResource(R.drawable.selector_white)
            this.isClickable = true
        }

        if (mBackground_drawable != null) {
            this.setBackgroundDrawable(mBackground_drawable)
        }

        if (useShape) {
            if (Build.VERSION.SDK_INT < 16) {
                setBackgroundDrawable(selector)
            } else {
                background = selector
            }
        }
    }

    /**
     * 初始化左边图标
     */
    private fun initLeftIcon() {
        if (leftIconIV == null) {
            leftIconIV = ImageView(mContext)
        }
        leftImgParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
        leftImgParams!!.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        leftImgParams!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        if (leftIconHeight != 0 && leftIconWidth != 0) {
            leftImgParams!!.width = leftIconWidth
            leftImgParams!!.height = leftIconHeight
        }
        leftIconIV!!.scaleType = ImageView.ScaleType.FIT_CENTER
        leftIconIV!!.id = R.id.sLeftImgId
        leftIconIV!!.layoutParams = leftImgParams
        if (leftIconRes != null) {
            leftImgParams!!.setMargins(leftIconMarginLeft, 0, 0, 0)
            leftIconIV!!.setImageDrawable(leftIconRes)
        }
        addView(leftIconIV)
    }

    /**
     * 初始化右边图标
     */
    private fun initRightIcon() {
        if (rightIconIV == null) {
            rightIconIV = ImageView(mContext)
        }
        rightImgParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        rightImgParams!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)

        when (mRightViewType) {
            TYPE_CHECKBOX -> rightImgParams!!.addRule(RelativeLayout.LEFT_OF, R.id.sRightCheckBoxId)
            TYPE_SWITCH -> rightImgParams!!.addRule(RelativeLayout.LEFT_OF, R.id.sRightSwitchId)
            else -> rightImgParams!!.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        }

        if (rightIconHeight != 0 && rightIconWidth != 0) {
            rightImgParams!!.width = rightIconWidth
            rightImgParams!!.height = rightIconHeight
        }

        rightIconIV!!.scaleType = ImageView.ScaleType.FIT_CENTER
        rightIconIV!!.id = R.id.sRightImgId
        rightIconIV!!.layoutParams = rightImgParams
        if (rightIconRes != null) {
            rightImgParams!!.setMargins(0, 0, rightIconMarginRight, 0)
            rightIconIV!!.setImageDrawable(rightIconRes)
        }
        addView(rightIconIV)
    }

    /**
     * 初始化LeftTextView
     */
    private fun initLeftTextView() {
        if (leftView == null) {
            leftView = initBaseView(R.id.sLeftViewId)
        }
        leftBaseViewParams = getParams(leftBaseViewParams)
        leftBaseViewParams!!.addRule(RelativeLayout.RIGHT_OF, R.id.sLeftImgId)
        leftBaseViewParams!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        if (mLeftViewWidth != 0) {
            leftBaseViewParams!!.width = mLeftViewWidth
        }
        leftBaseViewParams!!.setMargins(mLeftViewMarginLeft, 0, mLeftViewMarginRight, 0)

        leftView!!.layoutParams = leftBaseViewParams

        leftView!!.setCenterSpaceHeight(centerSpaceHeight)
        setDefaultColor(leftView, mLeftTopTextColor, mLeftTextColor, mLeftBottomTextColor)
        setDefaultSize(leftView, mLeftTopTextSize, mLeftTextSize, mLeftBottomTextSize)
        setDefaultLines(leftView, mLeftTopLines, mLeftLines, mLeftBottomLines)
        setDefaultMaxEms(leftView, mLeftTopMaxEms, mLeftMaxEms, mLeftBottomMaxEms)
        setDefaultTextIsBold(leftView, mLeftTopTextBold, mLeftTextBold, mLeftBottomTextBold)
        setDefaultGravity(leftView, mLeftGravity)
        setDefaultDrawable(leftView!!.centerTextView, mLeftTvDrawableLeft, mLeftTvDrawableRight, mTextViewDrawablePadding, mLeftTvDrawableWidth, mLeftTvDrawableHeight)
        setDefaultBackground(leftView!!.centerTextView, mLeftTextBackground)
        setDefaultString(leftView, mLeftTopTextString, mLeftTextString, mLeftBottomTextString)
        addView(leftView)
    }

    /**
     * 初始化CenterTextView
     */
    private fun initCenterTextView() {
        if (centerView == null) {
            centerView = initBaseView(R.id.sCenterViewId)
        }
        centerBaseViewParams = getParams(centerBaseViewParams)
        centerBaseViewParams!!.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        centerBaseViewParams!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)

        //默认情况下  中间的View整体剧中显示，设置左对齐或者右对齐的话使用下边属性
        if (mCenterGravity != gravity_Center) {
            centerBaseViewParams!!.addRule(RelativeLayout.RIGHT_OF, R.id.sLeftViewId)
            centerBaseViewParams!!.addRule(RelativeLayout.LEFT_OF, R.id.sRightViewId)
        }

        centerBaseViewParams!!.setMargins(mCenterViewMarginLeft, 0, mCenterViewMarginRight, 0)

        centerView!!.layoutParams = centerBaseViewParams
        centerView!!.setCenterSpaceHeight(centerSpaceHeight)

        setDefaultColor(centerView, mCenterTopTextColor, mCenterTextColor, mCenterBottomTextColor)
        setDefaultSize(centerView, mCenterTopTextSize, mCenterTextSize, mCenterBottomTextSize)
        setDefaultLines(centerView, mCenterTopLines, mCenterLines, mCenterBottomLines)
        setDefaultMaxEms(centerView, mCenterTopMaxEms, mCenterMaxEms, mCenterBottomMaxEms)
        setDefaultTextIsBold(centerView, mCenterTopTextBold, mCenterTextBold, mCenterBottomTextBold)
        setDefaultGravity(centerView, mCenterGravity)
        setDefaultDrawable(centerView!!.centerTextView, mCenterTvDrawableLeft, mCenterTvDrawableRight, mTextViewDrawablePadding, mCenterTvDrawableWidth, mCenterTvDrawableHeight)
        setDefaultBackground(centerView!!.centerTextView, mCenterTextBackground)
        setDefaultString(centerView, mCenterTopTextString, mCenterTextString, mCenterBottomTextString)

        addView(centerView)
    }

    /**
     * 初始化RightTextView
     */
    private fun initRightTextView() {
        if (rightView == null) {
            rightView = initBaseView(R.id.sRightViewId)
        }
        rightBaseViewParams = getParams(rightBaseViewParams)
        rightBaseViewParams!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)

        rightBaseViewParams!!.addRule(RelativeLayout.LEFT_OF, R.id.sRightImgId)
        rightBaseViewParams!!.setMargins(mRightViewMarginLeft, 0, mRightViewMarginRight, 0)

        rightView!!.layoutParams = rightBaseViewParams
        rightView!!.setCenterSpaceHeight(centerSpaceHeight)

        setDefaultColor(rightView, mRightTopTextColor, mRightTextColor, mRightBottomTextColor)
        setDefaultSize(rightView, mRightTopTextSize, mRightTextSize, mRightBottomTextSize)
        setDefaultLines(rightView, mRightTopLines, mRightLines, mRightBottomLines)
        setDefaultMaxEms(rightView, mRightTopMaxEms, mRightMaxEms, mRightBottomMaxEms)
        setDefaultTextIsBold(rightView, mRightTopTextBold, mRightTextBold, mRightBottomTextBold)
        setDefaultGravity(rightView, mRightGravity)
        setDefaultDrawable(rightView!!.centerTextView, mRightTvDrawableLeft, mRightTvDrawableRight, mTextViewDrawablePadding, mRightTvDrawableWidth, mRightTvDrawableHeight)
        setDefaultBackground(rightView!!.centerTextView, mRightTextBackground)
        setDefaultString(rightView, mRightTopTextString, mRightTextString, mRightBottomTextString)

        addView(rightView)
    }


    /**
     * 初始化RightCheckBox
     */
    private fun initRightCheckBox() {
        if (rightCheckBox == null) {
            rightCheckBox = CheckBox(mContext)
        }
        rightCheckBoxParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

        rightCheckBoxParams!!.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        rightCheckBoxParams!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        rightCheckBoxParams!!.setMargins(0, 0, rightCheckBoxMarginRight, 0)
        rightCheckBox!!.id = R.id.sRightCheckBoxId
        rightCheckBox!!.layoutParams = rightCheckBoxParams
        if (rightCheckBoxBg != null) {
            rightCheckBox!!.gravity = RelativeLayout.CENTER_IN_PARENT
            rightCheckBox!!.buttonDrawable = rightCheckBoxBg
        }
        rightCheckBox!!.isChecked = isChecked
        rightCheckBox!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (checkBoxCheckedChangeListener != null) {
                checkBoxCheckedChangeListener!!.onCheckedChanged(buttonView, isChecked)
            }
        }
        addView(rightCheckBox)
    }

    /**
     * 初始化RightSwitch
     */
    private fun initRightSwitch() {
        if (mSwitch == null) {
            mSwitch = Switch(mContext)
        }
        mSwitchParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

        mSwitchParams!!.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
        mSwitchParams!!.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        mSwitchParams!!.setMargins(0, 0, rightSwitchMarginRight, 0)
        mSwitch!!.id = R.id.sRightSwitchId
        mSwitch!!.layoutParams = mSwitchParams

        mSwitch!!.isChecked = switchIsChecked
        if (!TextUtils.isEmpty(mTextOff)) {
            mSwitch!!.textOff = mTextOff
        }
        if (!TextUtils.isEmpty(mTextOn)) {
            mSwitch!!.textOn = mTextOn
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mSwitchMinWidth != 0) {
                mSwitch!!.switchMinWidth = mSwitchMinWidth
            }
            if (mSwitchPadding != 0) {
                mSwitch!!.switchPadding = mSwitchPadding
            }
            if (mThumbResource != null) {
                mSwitch!!.thumbDrawable = mThumbResource
            }
            if (mThumbResource != null) {
                mSwitch!!.trackDrawable = mTrackResource
            }
            if (mThumbTextPadding != 0) {
                mSwitch!!.thumbTextPadding = mThumbTextPadding
            }

        }
        mSwitch!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (switchCheckedChangeListener != null) {
                switchCheckedChangeListener!!.onCheckedChanged(buttonView, isChecked)
            }
        }

        addView(mSwitch)
    }

    /////////////////////////////////////默认属性设置----begin/////////////////////////////////

    /**
     * 初始化BaseTextView
     *
     * @param id id
     * @return baseTextView
     */
    private fun initBaseView(id: Int): BaseTextView {
        val baseTextView = BaseTextView(mContext)
        baseTextView.id = id
        return baseTextView
    }

    /**
     * 设置默认值
     *
     * @param baseTextView     baseTextView
     * @param topTextString    topTextString
     * @param leftTextString   leftTextString
     * @param bottomTextString bottomTextString
     */
    private fun setDefaultString(baseTextView: BaseTextView?, topTextString: String?, leftTextString: String?, bottomTextString: String?) {
        if (baseTextView != null) {
            baseTextView.setTopTextString(topTextString!!)
            baseTextView.setCenterTextString(leftTextString!!)
            baseTextView.setBottomTextString(bottomTextString!!)
        }
    }

    /**
     * 设置默认
     *
     * @param baseTextView    baseTextView
     * @param topTextColor    topTextColor
     * @param textColor       textColor
     * @param bottomTextColor bottomTextColor
     */
    private fun setDefaultColor(baseTextView: BaseTextView?, topTextColor: Int, textColor: Int, bottomTextColor: Int) {
        if (baseTextView != null) {
            baseTextView.topTextView.setTextColor(topTextColor)
            baseTextView.centerTextView.setTextColor(textColor)
            baseTextView.bottomTextView.setTextColor(bottomTextColor)
        }
    }

    /**
     * 设置默认字体大小
     *
     * @param baseTextView   baseTextView
     * @param leftTextSize   leftTextSize
     * @param topTextSize    topTextSize
     * @param bottomTextSize bottomTextSize
     */
    private fun setDefaultSize(baseTextView: BaseTextView?, topTextSize: Int, leftTextSize: Int, bottomTextSize: Int) {
        if (baseTextView != null) {
            baseTextView.topTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, topTextSize.toFloat())
            baseTextView.centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize.toFloat())
            baseTextView.bottomTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, bottomTextSize.toFloat())
        }
    }

    /**
     * 设置默认maxEms
     *
     * @param baseTextView baseTextView
     * @param topMaxEms    topMaxEms
     * @param centerMaxEms centerMaxEms
     * @param bottomMaxEms bottomMaxEms
     */
    private fun setDefaultMaxEms(baseTextView: BaseTextView?, topMaxEms: Int, centerMaxEms: Int, bottomMaxEms: Int) {
        baseTextView?.setMaxEms(topMaxEms, centerMaxEms, bottomMaxEms)
    }

    /**
     * 设置默认lines
     *
     * @param baseTextView baseTextView
     * @param leftTopLines leftTopLines
     * @param leftLines    leftLines
     * @param bottomLines  bottomLines
     */
    private fun setDefaultLines(baseTextView: BaseTextView?, leftTopLines: Int, leftLines: Int, bottomLines: Int) {
        if (baseTextView != null) {
            baseTextView.topTextView.maxLines = leftTopLines
            baseTextView.centerTextView.maxLines = leftLines
            baseTextView.bottomTextView.maxLines = bottomLines
        }

    }

    /**
     * 设置文字对其方式
     *
     * @param baseTextView baseTextView
     * @param gravity      对其方式
     */
    private fun setDefaultGravity(baseTextView: BaseTextView?, gravity: Int) {
        if (baseTextView != null) {
            setGravity(baseTextView, gravity)
        }
    }

    /**
     * 文字对其方式
     *
     * @param baseTextView textView
     * @param gravity      对其方式
     */
    private fun setGravity(baseTextView: BaseTextView, gravity: Int) {
        when (gravity) {
            gravity_Left_Center -> baseTextView.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
            gravity_Center -> baseTextView.gravity = Gravity.CENTER
            gravity_Right_Center -> baseTextView.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        }
    }

    /**
     * 设置textView的drawable
     *
     * @param textView        对象
     * @param drawableLeft    左边图标
     * @param drawableRight   右边图标
     * @param drawablePadding 图标距离文字的间距
     */
    fun setDefaultDrawable(textView: TextView, drawableLeft: Drawable?, drawableRight: Drawable?, drawablePadding: Int, drawableWidth: Int, drawableHeight: Int) {
        if (drawableLeft != null || drawableRight != null) {
            textView.visibility = View.VISIBLE
        }
        //可以指定drawable的宽高
        if (drawableWidth != -1 && drawableHeight != -1) {
            drawableLeft?.setBounds(0, 0, drawableWidth, drawableHeight)
            drawableRight?.setBounds(0, 0, drawableWidth, drawableHeight)
            textView.setCompoundDrawables(drawableLeft, null, drawableRight, null)
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)
        }
        textView.compoundDrawablePadding = drawablePadding
    }

    /**
     * 设置textView的背景，用户传入drawable实现圆角之类的样式
     *
     * @param textView
     * @param background
     */
    private fun setDefaultBackground(textView: TextView, background: Drawable?) {
        if (background != null) {
            textView.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT < 16) {
                textView.setBackgroundDrawable(background)
            } else {
                textView.background = background
            }
        }
    }

    /**
     * 初始化分割线
     */
    private fun initDividerLineView() {
        if (!useShape) {
            when (mDividerLineType) {
                NONE -> {
                }
                TOP -> setTopDividerLineView()
                BOTTOM -> setBottomDividerLineView()
                BOTH -> {
                    setTopDividerLineView()
                    setBottomDividerLineView()
                }
            }
        }

    }

    /**
     * 设置上边的分割线
     */
    private fun setTopDividerLineView() {
        if (mTopDividerLineMarginLR != 0) {
            initTopDividerLineView(mTopDividerLineMarginLR, mTopDividerLineMarginLR)
        } else {
            initTopDividerLineView(mTopDividerLineMarginLeft, mTopDividerLineMarginRight)
        }
    }

    /**
     * 设置下边的分割线
     */
    private fun setBottomDividerLineView() {
        if (mBottomDividerLineMarginLR != 0) {
            initBottomDividerLineView(mBottomDividerLineMarginLR, mBottomDividerLineMarginLR)
        } else {
            initBottomDividerLineView(mBottomDividerLineMarginLeft, mBottomDividerLineMarginRight)
        }
    }


    /**
     * 初始化上边分割线view
     *
     * @param marginLeft  左间距
     * @param marginRight 右间距
     */
    private fun initTopDividerLineView(marginLeft: Int, marginRight: Int) {
        if (topDividerLineView == null) {
            if (topDividerLineParams == null) {
                topDividerLineParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mDividerLineHeight)
            }
            topDividerLineParams!!.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
            topDividerLineParams!!.setMargins(marginLeft, 0, marginRight, 0)
            topDividerLineView = View(mContext)
            topDividerLineView!!.layoutParams = topDividerLineParams
            topDividerLineView!!.setBackgroundColor(mDividerLineColor)
        }
        addView(topDividerLineView)
    }

    /**
     * 初始化底部分割线view
     *
     * @param marginLeft  左间距
     * @param marginRight 右间距
     */
    private fun initBottomDividerLineView(marginLeft: Int, marginRight: Int) {
        if (bottomDividerLineView == null) {
            if (bottomDividerLineParams == null) {
                bottomDividerLineParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mDividerLineHeight)
            }
            bottomDividerLineParams!!.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            bottomDividerLineParams!!.setMargins(marginLeft, 0, marginRight, 0)

            bottomDividerLineView = View(mContext)
            bottomDividerLineView!!.layoutParams = bottomDividerLineParams
            bottomDividerLineView!!.setBackgroundColor(mDividerLineColor)
        }
        addView(bottomDividerLineView)
    }


    /**
     * 左边点击事件
     *
     * @param baseTextView baseTextView
     */
    private fun setDefaultLeftViewClickListener(baseTextView: BaseTextView?) {
        if (baseTextView != null) {
            if (leftTopTvClickListener != null) {
                baseTextView.topTextView.setOnClickListener { leftTopTvClickListener!!.onClickListener() }
            }

            if (leftTvClickListener != null) {
                baseTextView.centerTextView.setOnClickListener { leftTvClickListener!!.onClickListener() }
            }
            if (leftBottomTvClickListener != null) {
                baseTextView.bottomTextView.setOnClickListener { leftBottomTvClickListener!!.onClickListener() }
            }
        }

    }

    /**
     * 中间点击事件
     *
     * @param baseTextView baseTextView
     */
    private fun setDefaultCenterViewClickListener(baseTextView: BaseTextView?) {
        if (baseTextView != null) {
            if (centerTopTvClickListener != null) {
                baseTextView.topTextView.setOnClickListener { centerTopTvClickListener!!.onClickListener() }
            }

            if (centerTvClickListener != null) {
                baseTextView.centerTextView.setOnClickListener { centerTvClickListener!!.onClickListener() }
            }
            if (centerBottomTvClickListener != null) {
                baseTextView.bottomTextView.setOnClickListener { centerBottomTvClickListener!!.onClickListener() }
            }
        }

    }


    /**
     * 右边点击事件
     *
     * @param baseTextView baseTextView
     */
    private fun setDefaultRightViewClickListener(baseTextView: BaseTextView?) {
        if (baseTextView != null) {
            if (rightTopTvClickListener != null) {
                baseTextView.topTextView.setOnClickListener { rightTopTvClickListener!!.onClickListener() }
            }

            if (rightTvClickListener != null) {
                baseTextView.centerTextView.setOnClickListener { rightTvClickListener!!.onClickListener() }
            }
            if (rightBottomTvClickListener != null) {
                baseTextView.bottomTextView.setOnClickListener { rightBottomTvClickListener!!.onClickListener() }
            }
        }

    }


    /**
     * 字体是否加粗
     *
     * @param baseTextView   baseTextView
     * @param topTextBold    上边字体加粗
     * @param centerTextBold 中间字体加粗
     * @param bottomTextBold 下边字体加粗
     */
    private fun setDefaultTextIsBold(baseTextView: BaseTextView?, topTextBold: Boolean, centerTextBold: Boolean, bottomTextBold: Boolean) {
        if (baseTextView != null) {
            baseTextView.topTextView.paint.isFakeBoldText = topTextBold
            baseTextView.centerTextView.paint.isFakeBoldText = centerTextBold
            baseTextView.bottomTextView.paint.isFakeBoldText = bottomTextBold
        }
    }


    /////////////////////////////////////默认属性设置----end/////////////////////////////////


    /////////////////////////////////////对外暴露的方法---begin/////////////////////////////////

    /**
     * 设置左上字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setLeftTopString(string: CharSequence): SuperTextView {
        if (leftView != null) {
            leftView!!.setTopTextString(string)
        }
        return this
    }

    /**
     * 设置左中字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setLeftString(string: CharSequence): SuperTextView {
        if (leftView != null) {
            leftView!!.setCenterTextString(string)
        }
        return this
    }

    /**
     * 设置左下字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setLeftBottomString(string: CharSequence): SuperTextView {
        if (leftView != null) {
            leftView!!.setBottomTextString(string)
        }
        return this
    }


    /**
     * 设置中上字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setCenterTopString(string: CharSequence): SuperTextView {
        if (centerView != null) {
            centerView!!.setTopTextString(string)
        }
        return this
    }

    /**
     * 设置中间字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setCenterString(string: CharSequence): SuperTextView {
        if (centerView != null) {
            centerView!!.setCenterTextString(string)
        }
        return this
    }

    /**
     * 设置中下字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setCenterBottomString(string: CharSequence): SuperTextView {
        if (centerView != null) {
            centerView!!.setBottomTextString(string)
        }
        return this
    }

    /**
     * 设置右上字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setRightTopString(string: CharSequence): SuperTextView {
        if (rightView != null) {
            rightView!!.setTopTextString(string)
        }
        return this
    }

    /**
     * 设置右中字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setRightString(string: CharSequence): SuperTextView {
        if (rightView != null) {
            rightView!!.setCenterTextString(string)
        }
        return this
    }

    /**
     * 设置右下字符串
     *
     * @param string 字符串
     * @return 方便链式调用
     */
    fun setRightBottomString(string: CharSequence): SuperTextView {
        if (rightView != null) {
            rightView!!.setBottomTextString(string)
        }
        return this
    }

    /**
     * 设置左上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setLeftTopTextColor(color: Int): SuperTextView {
        if (leftView != null) {
            leftView!!.topTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置左中文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setLeftTextColor(color: Int): SuperTextView {
        if (leftView != null) {
            leftView!!.centerTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置左下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setLeftBottomTextColor(color: Int): SuperTextView {
        if (leftView != null) {
            leftView!!.bottomTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置中上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setCenterTopTextColor(color: Int): SuperTextView {
        if (centerView != null) {
            centerView!!.topTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置中间文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setCenterTextColor(color: Int): SuperTextView {
        if (centerView != null) {
            centerView!!.centerTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置中下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setCenterBottomTextColor(color: Int): SuperTextView {
        if (centerView != null) {
            centerView!!.bottomTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置右上文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setRightTopTextColor(color: Int): SuperTextView {
        if (rightView != null) {
            rightView!!.topTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置右中文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setRightTextColor(color: Int): SuperTextView {
        if (rightView != null) {
            rightView!!.centerTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 设置右下文字颜色
     *
     * @param color 颜色值
     * @return SuperTextView
     */
    fun setRightBottomTextColor(color: Int): SuperTextView {
        if (rightView != null) {
            rightView!!.bottomTextView.setTextColor(color)
        }
        return this
    }

    /**
     * 获取左边ImageView
     *
     * @return ImageView
     */
    fun getLeftIconIV(): ImageView? {
        leftImgParams!!.setMargins(leftIconMarginLeft, 0, 0, 0)
        return leftIconIV
    }

    /**
     * 获取右边ImageView
     *
     * @return ImageView
     */
    fun getRightIconIV(): ImageView? {
        rightImgParams!!.setMargins(0, 0, rightIconMarginRight, 0)
        return rightIconIV
    }


    /**
     * @param checked 是否选中
     * @return 返回值
     */
    fun setCbChecked(checked: Boolean): SuperTextView {
        isChecked = checked
        if (rightCheckBox != null) {
            rightCheckBox!!.isChecked = checked
        }
        return this
    }

    /**
     * 设置checkbox的背景图
     *
     * @param drawable drawable对象
     * @return 返回对象
     */
    fun setCbBackground(drawable: Drawable): SuperTextView {
        rightCheckBoxBg = drawable
        if (rightCheckBox != null) {
            rightCheckBox!!.setBackgroundDrawable(drawable)
        }
        return this
    }

    /**
     * @param checked Switch是否选中
     * @return 返回值
     */
    fun setSwitchIsChecked(checked: Boolean): SuperTextView {
        switchIsChecked = checked
        if (mSwitch != null) {
            mSwitch!!.isChecked = checked
        }
        return this
    }

    /**
     * 获取switch状态
     *
     * @return 返回switch当前选中状态
     */
    fun getSwitchIsChecked(): Boolean {
        var isChecked = false
        if (mSwitch != null) {
            isChecked = mSwitch!!.isChecked
        }
        return isChecked
    }

    /**
     * 设置左边tv的左侧图片
     *
     * @param drawableLeft 左边图片资源
     */
    fun setLeftTvDrawableLeft(drawableLeft: Drawable): SuperTextView {
        setDefaultDrawable(leftView!!.centerTextView, drawableLeft, null, mTextViewDrawablePadding, mLeftTvDrawableWidth, mLeftTvDrawableHeight)
        return this
    }

    /**
     * 设置左边tv的右侧图片
     *
     * @param drawableRight 右边图片资源
     */
    fun setLeftTvDrawableRight(drawableRight: Drawable): SuperTextView {
        setDefaultDrawable(leftView!!.centerTextView, null, drawableRight, mTextViewDrawablePadding, mLeftTvDrawableWidth, mLeftTvDrawableHeight)
        return this
    }


    /**
     * 设置中间tv的左侧图片
     *
     * @param drawableLeft 左边图片资源
     */
    fun setCenterTvDrawableLeft(drawableLeft: Drawable): SuperTextView {
        setDefaultDrawable(centerView!!.centerTextView, drawableLeft, null, mTextViewDrawablePadding, mCenterTvDrawableWidth, mCenterTvDrawableHeight)
        return this
    }


    /**
     * 设置中间tv的右侧图片
     *
     * @param drawableRight 右边图片资源
     */
    fun setCenterTvDrawableRight(drawableRight: Drawable): SuperTextView {
        setDefaultDrawable(centerView!!.centerTextView, null, drawableRight, mTextViewDrawablePadding, mCenterTvDrawableWidth, mCenterTvDrawableHeight)
        return this
    }


    /**
     * 设置右边tv的左侧图片
     *
     * @param drawableLeft 左边图片资源
     */
    fun setRightTvDrawableLeft(drawableLeft: Drawable): SuperTextView {
        setDefaultDrawable(rightView!!.centerTextView, drawableLeft, null, mTextViewDrawablePadding, mRightTvDrawableWidth, mRightTvDrawableHeight)
        return this
    }

    /**
     * 设置右边tv的右侧图片
     *
     * @param drawableRight 右边图片资源
     */
    fun setRightTvDrawableRight(drawableRight: Drawable): SuperTextView {
        setDefaultDrawable(rightView!!.centerTextView, null, drawableRight, mTextViewDrawablePadding, mRightTvDrawableWidth, mRightTvDrawableHeight)
        return this
    }

    /**
     * 设置左边图标
     *
     * @param leftIcon 左边图标
     * @return 返回对象
     */
    fun setLeftIcon(leftIcon: Drawable): SuperTextView {
        if (leftIconIV != null) {
            leftImgParams!!.setMargins(leftIconMarginLeft, 0, 0, 0)
            leftIconIV!!.setImageDrawable(leftIcon)
        }
        return this
    }

    /**
     * 设置左边图标
     *
     * @param resId 左边图标资源id
     * @return 返回对象
     */
    fun setLeftIcon(resId: Int): SuperTextView {
        if (leftIconIV != null) {
            leftImgParams!!.setMargins(leftIconMarginLeft, 0, 0, 0)
            leftIconIV!!.setImageResource(resId)
        }
        return this
    }

    /**
     * 设置右边图标
     *
     * @param rightIcon 右边图标
     * @return 返回对象
     */
    fun setRightIcon(rightIcon: Drawable): SuperTextView {
        if (rightIconIV != null) {
            rightImgParams!!.setMargins(0, 0, rightIconMarginRight, 0)
            rightIconIV!!.setImageDrawable(rightIcon)
        }
        return this
    }

    /**
     * 设置右边图标资源Id
     *
     * @param resId 右边图标
     * @return 返回对象
     */
    fun setRightIcon(resId: Int): SuperTextView {
        if (rightIconIV != null) {
            rightImgParams!!.setMargins(0, 0, rightIconMarginRight, 0)
            rightIconIV!!.setImageResource(resId)
        }
        return this
    }

    /**
     * 设置背景
     *
     * @param drawable 背景资源
     * @return 对象
     */
    fun setSBackground(drawable: Drawable?): SuperTextView {
        if (drawable != null) {
            this.setBackgroundDrawable(drawable)
        }
        return this
    }

    /**
     * 设置左边textView文字对齐方式
     *
     * @param gravity 对齐方式
     * @return SuperTextView
     */
    fun setLeftTextGravity(gravity: Int): SuperTextView {
        setTextGravity(leftView, gravity)
        return this
    }

    /**
     * 设置中间textView文字对齐方式
     *
     * @param gravity 对齐方式
     * @return SuperTextView
     */
    fun setCenterTextGravity(gravity: Int): SuperTextView {
        setTextGravity(centerView, gravity)
        return this
    }

    /**
     * 设置右边textView文字对齐方式
     *
     * @param gravity 对齐方式
     * @return SuperTextView
     */
    fun setRightTextGravity(gravity: Int): SuperTextView {
        setTextGravity(rightView, gravity)
        return this
    }

    /**
     * 文字对齐方式
     *
     * @param baseTextView view
     * @param gravity      对齐方式
     */
    private fun setTextGravity(baseTextView: BaseTextView?, gravity: Int) {
        if (baseTextView != null) {
            baseTextView.centerTextView.gravity = gravity
        }
    }

    /**
     * 设置上边分割线显示状态
     *
     * @param visibility visibility
     * @return superTextView
     */
    fun setTopDividerLineVisibility(visibility: Int): SuperTextView {
        if (topDividerLineView == null) {
            setTopDividerLineView()
        }
        topDividerLineView!!.visibility = visibility
        return this
    }

    /**
     * 设置下边分割线显示状态
     *
     * @param visibility visibility
     * @return superTextView
     */
    fun setBottomDividerLineVisibility(visibility: Int): SuperTextView {
        if (bottomDividerLineView == null) {
            setBottomDividerLineView()
        }
        bottomDividerLineView!!.visibility = visibility
        return this
    }

    /////////////////////////////////////对外暴露的方法---end/////////////////////////////////


    /**
     * 点击事件
     *
     * @param onSuperTextViewClickListener ClickListener
     * @return SuperTextView
     */
    fun setOnSuperTextViewClickListener(onSuperTextViewClickListener: OnSuperTextViewClickListener): SuperTextView {
        this.superTextViewClickListener = onSuperTextViewClickListener
        if (superTextViewClickListener != null) {
            this.setOnClickListener { superTextViewClickListener!!.onClickListener(this@SuperTextView) }
        }
        return this
    }

    fun setLeftTopTvClickListener(leftTopTvClickListener: OnLeftTopTvClickListener): SuperTextView {
        this.leftTopTvClickListener = leftTopTvClickListener
        setDefaultLeftViewClickListener(leftView)
        return this
    }

    fun setLeftTvClickListener(leftTvClickListener: OnLeftTvClickListener): SuperTextView {
        this.leftTvClickListener = leftTvClickListener
        setDefaultLeftViewClickListener(leftView)
        return this
    }

    fun setLeftBottomTvClickListener(leftBottomTvClickListener: OnLeftBottomTvClickListener): SuperTextView {
        this.leftBottomTvClickListener = leftBottomTvClickListener
        setDefaultLeftViewClickListener(leftView)
        return this
    }

    fun setCenterTopTvClickListener(centerTopTvClickListener: OnCenterTopTvClickListener): SuperTextView {
        this.centerTopTvClickListener = centerTopTvClickListener
        setDefaultCenterViewClickListener(centerView)
        return this
    }

    fun setCenterTvClickListener(centerTvClickListener: OnCenterTvClickListener): SuperTextView {
        this.centerTvClickListener = centerTvClickListener
        setDefaultCenterViewClickListener(centerView)
        return this
    }

    fun setCenterBottomTvClickListener(centerBottomTvClickListener: OnCenterBottomTvClickListener): SuperTextView {
        this.centerBottomTvClickListener = centerBottomTvClickListener
        setDefaultCenterViewClickListener(centerView)
        return this
    }

    fun setRightTopTvClickListener(rightTopTvClickListener: OnRightTopTvClickListener): SuperTextView {
        this.rightTopTvClickListener = rightTopTvClickListener
        setDefaultRightViewClickListener(rightView)
        return this
    }

    fun setRightTvClickListener(rightTvClickListener: OnRightTvClickListener): SuperTextView {
        this.rightTvClickListener = rightTvClickListener
        setDefaultRightViewClickListener(rightView)
        return this
    }

    fun setRightBottomTvClickListener(rightBottomTvClickListener: OnRightBottomTvClickListener): SuperTextView {
        this.rightBottomTvClickListener = rightBottomTvClickListener
        setDefaultRightViewClickListener(rightView)
        return this
    }

    fun setLeftImageViewClickListener(listener: OnLeftImageViewClickListener): SuperTextView {
        this.leftImageViewClickListener = listener

        if (leftIconIV != null) {
            leftIconIV!!.setOnClickListener { leftImageViewClickListener!!.onClickListener(leftIconIV) }
        }
        return this
    }

    fun setRightImageViewClickListener(listener: OnRightImageViewClickListener): SuperTextView {
        this.rightImageViewClickListener = listener
        if (rightIconIV != null) {
            rightIconIV!!.setOnClickListener { rightImageViewClickListener!!.onClickListener(rightIconIV) }
        }
        return this
    }

    fun setSwitchCheckedChangeListener(switchCheckedChangeListener: OnSwitchCheckedChangeListener): SuperTextView {
        this.switchCheckedChangeListener = switchCheckedChangeListener
        return this
    }

    fun setCheckBoxCheckedChangeListener(checkBoxCheckedChangeListener: OnCheckBoxCheckedChangeListener): SuperTextView {
        this.checkBoxCheckedChangeListener = checkBoxCheckedChangeListener
        return this
    }

    ////////////////////////////////////////////////////////////////////////////////////
    interface OnSuperTextViewClickListener {
        fun onClickListener(superTextView: SuperTextView)
    }

    interface OnLeftTopTvClickListener {
        fun onClickListener()
    }

    interface OnLeftTvClickListener {
        fun onClickListener()
    }

    interface OnLeftBottomTvClickListener {
        fun onClickListener()
    }

    interface OnCenterTopTvClickListener {
        fun onClickListener()
    }

    interface OnCenterTvClickListener {
        fun onClickListener()
    }

    interface OnCenterBottomTvClickListener {
        fun onClickListener()
    }

    interface OnRightTopTvClickListener {
        fun onClickListener()
    }

    interface OnRightTvClickListener {
        fun onClickListener()
    }

    interface OnRightBottomTvClickListener {
        fun onClickListener()
    }

    interface OnLeftImageViewClickListener {
        fun onClickListener(imageView: ImageView?)
    }

    interface OnRightImageViewClickListener {
        fun onClickListener(imageView: ImageView?)
    }

    interface OnSwitchCheckedChangeListener {
        fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean)
    }

    interface OnCheckBoxCheckedChangeListener {
        fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean)
    }

    fun getDrawable(state: Int): GradientDrawable? {
        gradientDrawable = GradientDrawable()
        gradientDrawable!!.shape = GradientDrawable.RECTANGLE
        when (state) {
            android.R.attr.state_pressed -> gradientDrawable!!.setColor(selectorPressedColor)
            android.R.attr.state_enabled -> gradientDrawable!!.setColor(selectorNormalColor)
            else -> gradientDrawable!!.setColor(solidColor)
        }
        setBorder()
        setRadius()

        return gradientDrawable
    }


    /**
     * 设置边框  宽度  颜色  虚线  间隙
     */
    private fun setBorder() {
        gradientDrawable!!.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokeDashGap)
    }

    /**
     * 只有类型是矩形的时候设置圆角半径才有效
     */
    private fun setRadius() {
        if (cornersRadius != 0f) {
            gradientDrawable!!.cornerRadius = cornersRadius//设置圆角的半径
        } else {
            //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
            gradientDrawable!!.cornerRadii = floatArrayOf(cornersTopLeftRadius, cornersTopLeftRadius, cornersTopRightRadius, cornersTopRightRadius, cornersBottomRightRadius, cornersBottomRightRadius, cornersBottomLeftRadius, cornersBottomLeftRadius)
        }

    }

    /**
     * 设置按下的颜色
     *
     * @param color 颜色
     * @return 对象
     */
    fun setShapeSelectorPressedColor(color: Int): SuperTextView {
        this.selectorPressedColor = color
        return this
    }

    /**
     * 设置正常的颜色
     *
     * @param color 颜色
     * @return 对象
     */
    fun setShapeSelectorNormalColor(color: Int): SuperTextView {
        this.selectorNormalColor = color
        return this
    }


    /**
     * 设置边框颜色
     *
     * @param strokeColor 边框颜色
     * @return 对象
     */
    fun setShapeStrokeColor(strokeColor: Int): SuperTextView {
        this.strokeColor = strokeColor
        return this
    }

    /**
     * 设置边框虚线宽度
     *
     * @param strokeDashWidth 边框虚线宽度
     * @return 对象
     */
    fun setShapeSrokeDashWidth(strokeDashWidth: Float): SuperTextView {
        this.strokeDashWidth = dip2px(strokeDashWidth).toFloat()
        return this
    }

    /**
     * 设置边框虚线间隙
     *
     * @param strokeDashGap 边框虚线间隙值
     * @return 对象
     */
    fun setShapeStrokeDashGap(strokeDashGap: Float): SuperTextView {
        this.strokeDashGap = dip2px(strokeDashGap).toFloat()
        return this
    }

    /**
     * 设置填充的颜色
     *
     * @param color 颜色
     * @return 对象
     */
    fun setTheShapeSolidColor(color: Int): SuperTextView {
        this.solidColor = color
        return this
    }

    /**
     * 设置边框宽度
     *
     * @param strokeWidth 边框宽度值
     * @return 对象
     */
    fun setTheShapeStrokeWidth(strokeWidth: Int): SuperTextView {
        this.strokeWidth = dip2px(strokeWidth.toFloat())
        return this
    }

    /**
     * 设置圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    fun setShapeCornersRadius(radius: Float): SuperTextView {
        this.cornersRadius = dip2px(radius).toFloat()
        return this
    }

    /**
     * 设置左上圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    fun setShapeCornersTopLeftRadius(radius: Float): SuperTextView {
        this.cornersTopLeftRadius = dip2px(radius).toFloat()
        return this
    }

    /**
     * 设置右上圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    fun setShapeCornersTopRightRadius(radius: Float): SuperTextView {
        this.cornersTopRightRadius = dip2px(radius).toFloat()
        return this
    }

    /**
     * 设置左下圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    fun setShapeCornersBottomLeftRadius(radius: Float): SuperTextView {
        this.cornersBottomLeftRadius = dip2px(radius).toFloat()
        return this
    }

    /**
     * 设置右下圆角半径
     *
     * @param radius 半径
     * @return 对象
     */
    fun setShapeCornersBottomRightRadius(radius: Float): SuperTextView {
        this.cornersBottomRightRadius = dip2px(radius).toFloat()
        return this
    }

    /**
     * 所有与shape相关的属性设置之后调用此方法才生效
     *
     * @return 对象
     */
    fun useShape(): SuperTextView {
        if (Build.VERSION.SDK_INT < 16) {
            setBackgroundDrawable(selector)
        } else {
            background = selector
        }
        return this
    }
}
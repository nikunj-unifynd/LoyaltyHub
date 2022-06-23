package com.lib.mylibrary.ui.feature_spin_wheel.presentation

import android.animation.Animator
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.lib.mylibrary.R
import com.lib.mylibrary.core.customViews.customWheel.WheelItem
import com.lib.mylibrary.core.util.*
import com.lib.mylibrary.data.remote.dto.BuySpinWheelDto
import com.lib.mylibrary.data.remote.dto.GetSpinWheelDto
import com.lib.mylibrary.databinding.ActivitySpinTheWheelBinding
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class SpinTheWheelActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: SpinTheWheelViewModel by viewModels()
    private lateinit var binding: ActivitySpinTheWheelBinding
    var spinWheelIdReceived: String = ""
    var mediaPlayer: MediaPlayer? = null
    private var userPoints: String = ""
    private var userGems: String = ""
    private var wheelItemData: ArrayList<WheelItem> = ArrayList()
    private lateinit var spinWheelData: GetSpinWheelDto.Data.SpinWheels
    private lateinit var buySpinWheelData: BuySpinWheelDto.Data
    private lateinit var benefitsData: GetSpinWheelDto.Data.SpinWheels.BenefitsData
    private lateinit var myContext: Context
    private var index: Int = -1
    var isReward = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spin_the_wheel)

        initVariables()
        setUpListener()
        setUpViewModel()
        if (null != intent?.getStringExtra("spinWheelId")) {
            spinWheelIdReceived = intent.getStringExtra("spinWheelId")!!
            Timber.d("onCreate: {$spinWheelIdReceived}")
            getSpinWin()
        }
    }
    companion object {
        fun startActivity(context: Context, spinWheelId: String) {
            val intent = Intent(context, SpinTheWheelActivity::class.java)
            intent.putExtra("spinWheelId", spinWheelId)
            context.startActivity(intent)
        }
    }
    private fun getSpinWin() {
        if (spinWheelIdReceived.isNotBlank()) viewModel.getSpinWheel(spinWheelIdReceived)
    }

    private fun buySpinWin() {
        if (spinWheelIdReceived.isNotBlank()) viewModel.buySpinWheel(spinWheelIdReceived)
    }

    private fun initVariables() {
        myContext = this
        binding.lifecycleOwner = this
        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
        binding.colors = Colors
        binding.textLabels = TextLabels
        binding.localColors = SpinTheWheelColors
    }

    private fun setUpListener() {


//        binding.btnViewVoucher.setOnClickListener(this)
//        binding.cvViewVoucher.setOnClickListener(this)
//        binding.cvSpinWheel.setOnClickListener(this)
//        binding.tvSpinWheel.setColor()
//        binding.tvSpinWheel.setOnClickListener(this)
//        binding.cvSpinWheel.setOnLongClickListener(this)
//        binding.cvSpinWheel.setOnTouchListener(this)
//        binding.tvSpinWheel.setOnLongClickListener(this)
//        binding.tvSpinWheel.setOnTouchListener(this)

        binding.ivBack.setOnClickListener(this)
        binding.ivHelp.setOnClickListener(this)
        binding.btnSpinTheWheel.setOnClickListener(this)
        binding.ivProfilePastBooking.setOnClickListener(this)
        binding.llUnlockCoupon.setOnClickListener(this)
        binding.llUnlockGem.setOnClickListener(this)
        binding.llUnlockCouponOrRewards.setOnClickListener(this)

    }

    private fun setUpViewModel() {
        viewModel.uiStateFlow.onEach {
            when (it) {
                is UIState.Loading -> {
                    Timber.d("onCreate: loading")
                }
                is UIState.Success<*> -> {
                    when (it.data) {
                        is GetSpinWheelDto.Data -> {
                            Log.d(TAG, "bind: get {${it.data}}")
                            this.spinWheelData = it.data.spinWheels
                            userPoints = it.data.totalPoints
                            userGems = it.data.gems
                            initSpinView()
                        }
                        is BuySpinWheelDto.Data -> {
                            Log.d(TAG, "bind: buy  {${it.data}}")
                            buySpinWheelData = it.data
                            onBuySpinWheelResponse()
                        }
                    }
                }
                is UIState.Error -> {
//                    if (!it.message.isNullOrEmpty()){
//                        if (it.message.contains("token", true) ||
//                            it.message.contains("unauthorized", true) ||
//                            it.message.contains("details not found", true)) {
//                            CommonDialogUtils.showSingleButtonLogoutDialog(this, it.message, null)
//                        } else if(it.message == "NetworkError"){
//                            if (!binding.noNetworkUi.clEmptyStateNoInternet.isVisible){
//                                binding.noNetworkUi.clEmptyStateNoInternet.showView()
//                            }
//                        }
//                        else {
//                            CommonDialogUtils.showSingleButtonAlertDialog(myContext, it.message)
//                        }
//                    }
                }
            }
        }.observeInLifecycle(this)
    }
    private fun onBuySpinWheelResponse() {
        // update points and gems after spining
        if (spinWheelData.unlockType == 2) {
            val roundedPoints: Int = userPoints.split(".")[0].toInt()
            userPoints = (roundedPoints - spinWheelData.unlockValue).toString()
            binding.tvPhoenixPoints.text = getFormattedPoints(userPoints)
        } else if (spinWheelData.unlockType == 3) {
            val roundedGems: Int = userGems.split(".")[0].toInt()
            userGems = (roundedGems - spinWheelData.unlockValue).toString()
            binding.tvPhoenixGems.text = getFormattedPoints(userGems)
        }
        var tempIndex = -1
        for (i in spinWheelData.benefitsData) {
            tempIndex += 1
            if (i.benefitId == buySpinWheelData.benefitId) {
                benefitsData = i
                index = tempIndex
                break
            }
        }
        binding.luckyWheelView.startLuckyWheelWithTargetIndex(index)
        //show spinning
        binding.tvBanner.text = getString(R.string.spinning)
        binding.llButtons.alpha = 0.30f
        binding.llButtons.isClickable = false
        startMediaPlayer("spinning")
        animateCenterCircle(true)
        animateBanner(true)
        animateCircle(true)
        animateArrow(true)
        binding.luckyWheelView.setLuckyRoundItemSelectedListener {
            binding.luckyWheelView.hideView()
            animateCenterCircle(false)
            animateBanner(false)
            animateCircle(false)
            animateArrow(false)
            //revealSuccessView()
            showHideSuccessView(true)
            Handler().postDelayed({
                showSpinSuccess()
                //donot show success anim on message
                if(buySpinWheelData.benefitType!=6) {
                    showSuccessAnimation()
                }
            }, 300)
        }
    }
    private fun revealSuccessView() {
        val view = findViewById<View>(R.id.rlRevealView)
        val cx = view.width / 2
        val cy = view.height / 2
        val finalRadius =
            Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim: Animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0F, finalRadius)
        binding.ivWheelCCircle?.hideView()
        binding.tvWheelTitle?.hideView()
        binding.luckyWheelView?.hideView()
        view.showView()
        anim.start()
    }
    private fun showHideSuccessView(status : Boolean){
        if(status){
            binding.rlRevealView?.showView()
            binding.ivWheelCCircle?.hideView()
            binding.tvWheelTitle?.hideView()
            binding.luckyWheelView?.hideView()
        }
        else{
            binding.rlRevealView.hideView()
            binding.luckyWheelView?.showView()
            binding.luckyWheelView.alpha = 1f
            binding.ivWheelCCircle?.showView()
            binding.tvWheelTitle?.showView()
        }
    }

    private fun hideSuccessView() {
        val view = findViewById<View>(R.id.rlRevealView)
        val cx = view.width / 2
        val cy = view.height / 2
        val initialRadius =
            Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim: Animator =
            ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0F)
        view.hideView()
        binding.luckyWheelView?.showView()
        binding.ivWheelCCircle?.showView()
        binding.tvWheelTitle?.showView()
        anim.start()
    }
    private fun showSpinSuccess() {
        val benfitType = buySpinWheelData.benefitType
        binding.llButtons.isClickable=true
        binding.tvSpinTheWheel.text = getString(R.string.spin_again)
        binding.llButtons.alpha = 1.0f
        when (benfitType) {
            1, 2 -> {
                // Coupons & Reward
                binding.tvBanner.text = getString(R.string.hurray)
                binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                binding.rlSuccessPoints.hideView()
                binding.rlSuccessGem.hideView()
                binding.rlSuccessMessage.hideView()
                binding.rlSuccessXp.hideView()
                binding.rlSuccessCouponAndRewards.showView()
                Glide.with(this)
                    .load(buySpinWheelData.benefit.logo)
                    .into(binding.ivShopClaimed)
                binding.tvWonDetails.text = buySpinWheelData.benefit.name
                when(benfitType){
                    1->{
                        Glide.with(this)
                            .load(R.drawable.ic_coupon_coloured)
                            .into(binding.ivCouponOrReward)
                        binding.tvRewardOrCouponName.text = "You have won coupon"
                        binding.tvUnlockCouponOrRewards.text = getString(R.string.view_unlocked_coupons)
                        isReward = false
                    }
                    2->{
                        Glide.with(this)
                            .load(R.drawable.ic_gift_coloured)
                            .into(binding.ivCouponOrReward)
                        binding.tvRewardOrCouponName.text = "You have won reward"
                        binding.tvUnlockCouponOrRewards.text = getString(R.string.view_unlocked_rewards)
                        isReward = true
                    }
                }

            }
            3, 4 -> {
                // Points, GEMS & XP
                binding.tvBanner.text = getString(R.string.hurray)
                binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                binding.rlSuccessCouponAndRewards.hideView()
                binding.rlSuccessMessage.hideView()
                binding.rlSuccessXp.hideView()
                when (buySpinWheelData.benefitType) {
                    3 -> {
                        binding.rlSuccessPoints.showView()
                        binding.rlSuccessGem.hideView()
                        val updatedPoints=userPoints.toDouble().toInt()+ buySpinWheelData.amount
                        binding.tvPhoenixPoints.text = getFormattedPoints(updatedPoints.toString())
                    }
                    4 -> {
                        binding.rlSuccessGem.showView()
                        binding.rlSuccessPoints.hideView()
                        val updatedGems= userGems.toDouble().toInt()+ buySpinWheelData.amount
                        binding.tvPhoenixGems.text = getFormattedPoints(updatedGems.toString())

                    }
                }
            }
            5 -> {
                binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                binding.tvBanner.text = getString(R.string.hurray)
                binding.rlSuccessXp.showView()
                binding.rlSuccessCouponAndRewards.hideView()
                binding.rlSuccessPoints.hideView()
                binding.rlSuccessMessage.hideView()
                binding.rlSuccessGem.hideView()
                binding.tvSuccessGem.text = buySpinWheelData.amount.toString()

            }
            6 -> {
                // Msg
                binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                binding.tvBanner.text = getString(R.string.we_have_a_message)
                binding.rlSuccessCouponAndRewards.hideView()
                binding.rlSuccessPoints.hideView()
                binding.rlSuccessXp.hideView()
                binding.rlSuccessGem.hideView()
                binding.rlSuccessMessage.showView()
                binding.tvSuccessMessage.text = buySpinWheelData.amount.toString()
            }
        }
    }
    private fun showSuccessAnimation() {

        startMediaPlayer("success")
        binding.spinWheelSuccessAnimation.showView()
        binding.spinWheelSuccessAnimation.playAnimation()
        binding.spinWheelSuccessAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.spinWheelSuccessAnimation.hideView()
                stopMediaPlayer()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
    }


    fun startMediaPlayer(view: String) {
        if (view == "success") {
            mediaPlayer = MediaPlayer.create(this, R.raw.wheel_success_state_sound)
            mediaPlayer!!.start()
        } else if (view == "spinning") {
            mediaPlayer = MediaPlayer.create(this, R.raw.wheel_spinning_sound)
            mediaPlayer!!.start()
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.negative_state_sound)
            mediaPlayer!!.start()
        }
    }

    fun stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivHelp -> {
//                HelpButtomSheetDialogFragment(spinWheelData.termsAndConditions).show(supportFragmentManager, "")

            }
            R.id.ivBack -> {
                onBackPressed()
            }
            R.id.btnSpinTheWheel, R.id.tvSpinTheWheel -> {
                    if(binding.tvSpinTheWheel.text == "Spin Again") {

                        //spin again flow
                    Log.d(TAG, "onCreate: {spin again }")


                    getSpinWin()
                    showHideSuccessView(false)
                    binding.spinWheelSuccessAnimation.cancelAnimation()
                    binding.spinWheelSuccessAnimation.hideView()
                    stopMediaPlayer()
                    binding.btnSpinTheWheel.showView()
                }else{
                    //normal flow
                    Log.d(TAG, "onCreate: {spin }")

                    buySpinWin()
                }
            }

//            R.id.btnViewVoucher, R.id.cvViewVoucher -> {
//                when (buySpinWheelData.benefitType) {
//                    1 -> {
//                        OffersDetailsActivity.startActivity(myContext, buySpinWheelData.typeId, false,true,false,buySpinWheelData.benefit.transactionId.toInt(), false, page= "SpinWheel")
//                        onBackPressed()
//                    }
//                    2 -> {
//                        OffersDetailsActivity.startActivity(myContext, buySpinWheelData.typeId, true,true,false,buySpinWheelData.benefit.transactionId.toInt(), false, page= "SpinWheel")
//                        onBackPressed()
//                    }
//                    3 -> {
//                        WalletHistoryActivity.startActivity(this, true,true)
//                        onBackPressed()
//                    }
//                    4 -> {
//                        WalletHistoryActivity.startActivity(this, false,true)
//                        onBackPressed()
//                    }
//                }
         //   }
        }
    }
    fun initSpinView() {

        animateArrow(false)
        animateBanner(false)
        animateCircle(false)
        animateCenterCircle(false)
        binding.tvBanner.animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        wheelItemData.clear()
        binding.luckyWheelView.setData(wheelItemData)
        binding.luckyWheelView.isTouchEnabled = false

        binding.luckyWheelView.setBorderColor(resources.getColor(android.R.color.transparent))
        //set data in Wheel
        if (spinWheelData.benefitsData.size == 5) {

            var wheelData = spinWheelData.benefitsData
            for (i in 0 until wheelData.size) {
                val androidColors = resources.getIntArray(R.array.spin_background5)
                drawItemsOnWheel(wheelData[i], androidColors[i])
            }
        } else if (spinWheelData.benefitsData.size == 6) {

            var wheelData = spinWheelData.benefitsData
            for (i in 0 until wheelData.size) {
                val androidColors = resources.getIntArray(R.array.spin_background6)
                drawItemsOnWheel(wheelData[i], androidColors[i])
            }
        } else if (spinWheelData.benefitsData.size == 7) {
            var wheelData = spinWheelData.benefitsData
            for (i in 0 until wheelData.size) {
                val androidColors = resources.getIntArray(R.array.spin_background7)
                drawItemsOnWheel(wheelData[i], androidColors[i])
            }
        } else if (spinWheelData.benefitsData.size == 8) {
            var wheelData = spinWheelData.benefitsData
            for (i in 0 until wheelData.size) {
                val androidColors = resources.getIntArray(R.array.spin_background8)
                drawItemsOnWheel(wheelData[i], androidColors[i])
            }
        } else if (spinWheelData.benefitsData.size == 9) {
            var wheelData = spinWheelData.benefitsData
            for (i in 0 until wheelData.size) {
                val androidColors = resources.getIntArray(R.array.spin_background9)
                drawItemsOnWheel(wheelData[i], androidColors[i])
            }
        } else if (spinWheelData.benefitsData.size == 10) {
            var wheelData = spinWheelData.benefitsData
            for (i in 0 until wheelData.size) {
                val androidColors = resources.getIntArray(R.array.spin_background10)
                drawItemsOnWheel(wheelData[i], androidColors[i])
            }
        }
      updatePointsGems()
    }
    private fun drawItemsOnWheel(
        wheelData: GetSpinWheelDto.Data.SpinWheels.BenefitsData,
        androidColors: Int
    ) {
        when (wheelData.benefitType) {
            1 -> {
                var luckyItem = WheelItem()
                luckyItem.color = androidColors
                luckyItem.isSmallDevice = getScreenSize()
                luckyItem.iconTop = R.drawable.ic_coupon_on_wheel
                luckyItem.crossText = "x"
                val thread = Thread {
                    try {
                        val image = getBitmapFromURL(wheelData.logo)
                        luckyItem.iconBottom = image
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                thread.start()
                wheelItemData.add(luckyItem)
            }
            2 -> {
                var luckyItem = WheelItem()
                luckyItem.color = androidColors

                luckyItem.isSmallDevice = getScreenSize()
                luckyItem.iconTop = R.drawable.ic_reward_on_wheel
                luckyItem.crossText = "x"

                val thread = Thread {
                    try {
                        val image = getBitmapFromURL(wheelData.logo)
                        luckyItem.iconBottom = image
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                thread.start()
                wheelItemData.add(luckyItem)
            }
            3 -> {
                var luckyItem = WheelItem()
                luckyItem.color = androidColors
                luckyItem.icon = R.drawable.coin_on_wheel
                luckyItem.isSmallDevice = getScreenSize()
                luckyItem.topText =
                    getFormattedPoints(Math.round(wheelData.value.toString().toFloat()).toString())
                wheelItemData.add(luckyItem)

            }
            4 -> {
                var luckyItem = WheelItem()
                luckyItem.color = androidColors
                luckyItem.isSmallDevice = getScreenSize()
                luckyItem.icon = R.drawable.gem_on_wheel
                luckyItem.topText =
                    getFormattedPoints(Math.round(wheelData.value.toString().toFloat()).toString())
                wheelItemData.add(luckyItem)

            }
            5 -> {
                var luckyItem = WheelItem()
                luckyItem.color = androidColors
                luckyItem.icon = R.drawable.xp_on_wheel
                luckyItem.topText =
                    getFormattedPoints(Math.round(wheelData.value.toString().toFloat()).toString())
                wheelItemData.add(luckyItem)
            }
            6 -> {
                var luckyItem = WheelItem()
                luckyItem.color = androidColors
                luckyItem.iconCenter = R.drawable.message_spin_wheel

                luckyItem.isSmallDevice = getScreenSize()

                wheelItemData.add(luckyItem)
            }
        }
        Handler().postDelayed({
            binding.luckyWheelView.setData(wheelItemData)
            binding.luckyWheelView.setRound(2)
        }, 400)


    }

    private fun showInActiveWheelState(isActive: Boolean) {
        if (isActive) {
            binding.luckyWheelView.alpha = 0.30f

        } else {
            binding.luckyWheelView.alpha = 1f
        }
    }
    fun getScreenSize():Boolean
    {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        if(width<=720 || height<=1500)
            return true
        else
            return false
    }

    private fun animateBanner(isShow: Boolean) {
        if (isShow) {
            binding.ivWheelBanner.post {
                val background = ContextCompat.getDrawable(
                    this,
                    R.drawable.view_wheel_banner_seq
                ) as AnimationDrawable?
                binding.ivWheelBanner?.background = background
                binding.ivWheelBanner?.setImageDrawable(null)
//                ivWheelBanner?.rotation = 0f
                val animation =
                    binding.ivWheelBanner?.background as AnimationDrawable? ?: return@post
                if (!animation.isRunning) {
                    animation.start()
                }
            }
        } else {
            binding.ivWheelBanner?.background = null
//            ivWheelBanner?.rotation = -15f
            binding.ivWheelBanner?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_wheel_banner_lit_full
                )
            )
        }
    }

    private fun animateCircle(isShow: Boolean) {
        if (isShow) {
            binding.ivWheelCircle.post {
                val background = ContextCompat.getDrawable(
                    this,
                    R.drawable.view_wheel_stand_seq
                ) as AnimationDrawable?
                binding.ivWheelCircle?.background = background
                binding.ivWheelCircle?.setImageDrawable(null)
                val animation =
                    binding.ivWheelCircle?.background as AnimationDrawable? ?: return@post
                if (!animation.isRunning) {
                    animation.start()
                }
            }
        } else {
            binding.ivWheelCircle?.background = null
            binding.ivWheelCircle?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_wheel_stand_lit_full
                )
            )
        }
    }

    private fun animateCenterCircle(isShow: Boolean) {
        if (isShow) {
            binding.ivWheelCCircle.post {
                val background = ContextCompat.getDrawable(
                    this,
                    R.drawable.view_wheel_circle_center_seq
                ) as AnimationDrawable?
                binding.ivWheelCCircle?.background = background
                binding.ivWheelCCircle?.setImageDrawable(null)
                val animation =
                    binding.ivWheelCCircle?.background as AnimationDrawable? ?: return@post
                if (!animation.isRunning) {
                    animation.start()
                }
            }
        } else {
            binding.ivWheelCCircle?.background = null
            binding.ivWheelCCircle?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_wheel_circle_light_full
                )
            )
        }
    }

    private fun animateArrow(isShow: Boolean) {
        if (isShow) {
            binding.ivWheelArrow?.post {
                val background = ContextCompat.getDrawable(
                    this,
                    R.drawable.view_wheel_arrow_seq
                ) as AnimationDrawable?
                binding.ivWheelArrow?.background = background
                binding.ivWheelArrow?.setImageDrawable(null)
                val animation =
                    binding.ivWheelArrow?.background as AnimationDrawable? ?: return@post
                if (!animation.isRunning) {
                    animation.start()
                }
            }
        } else {
            val background = ContextCompat.getDrawable(
                this,
                R.drawable.ic_wheel_arrow_full_lit
            ) as Drawable
            binding.ivWheelArrow?.background = background
            binding.ivWheelArrow?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_wheel_arrow_no_lit
                )
            )
        }
    }

    private fun updatePointsGems() {
        binding.tvPhoenixPoints.text = getFormattedPoints(userPoints)
        binding.tvPhoenixGems.text = getFormattedPoints(userGems)
        binding.tvWheelTitle?.text = spinWheelData.name
        when (spinWheelData.unlockType) {
            2 -> {
                binding.tvSpinTheWheel.text = getString(R.string.pay_and_spin)
                val img: Drawable = getResources().getDrawable(R.drawable.ic_coin)
                binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
                binding.tvBanner.text =
                    " " + spinWheelData.unlockValue.toString() + " / Spin"
            }
            3 -> {
                binding.tvSpinTheWheel.text = getString(R.string.pay_and_spin)
                val img: Drawable = getResources().getDrawable(R.drawable.ic_gem)
                binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
                binding.tvBanner.text =
                    " " + spinWheelData.unlockValue.toString() + " / Spin"
            }
            else -> {
                binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                binding.tvBanner.text =getString(R.string.claim_your_free_spin)
                binding.tvSpinTheWheel.text = getString(R.string.spin_the_wheel)
            }
        }
        val roundedPoints: Int = userPoints.split(".")[0].toInt()
        val roundedGems: Int = userGems.split(".")[0].toInt()
        if (spinWheelData.canAttempt == 0) {
            //OUT OF SPIN
            showInActiveWheelState(true)
            binding.tvBanner.text = getString(R.string.out_of_spins)
            binding.tvBanner.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            binding.tvWheelTitle.hideView()
            binding.btnSpinTheWheel.hideView()
            binding.tvLock.showView()
            binding.tvDailyExhaustLimit.showView()
            animateCircle(false)
            animateArrow(false)

            binding.ivWheelCircle?.background = null
            binding.ivWheelCircle?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.gray_lit_stand
                )
            )
            binding.ivWheelCCircle?.background = null
            binding.ivWheelCCircle?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.gray_lit_center
                )
            )

        }
//        else if (roundedPoints < spinWheelData.unlockValue && spinWheelData.unlockType == 2) {
//            //OUT OF POINTS
//            showInActiveWheelState(true)
//            binding.cvPointsNeeded.showView()
//            binding.btnPointsNeeded.showView()
//            binding.ivLock.showView()
//            val img: Drawable = getResources().getDrawable(R.drawable.ic_gem_filled)
//            binding.unlockValue.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
//            binding.unlockValue.text = getFormattedPoints((spinWheelData.unlockValue - roundedPoints).toString())
//            binding.tvSpinWheel.setText("EARN MORE AT REWARDS")
//            changeButtonState(true)
//            binding.ivArrow.showView()
//        } else if (roundedGems < spinWheelData.unlockValue && spinWheelData.unlockType == 3) {
//            //out of gems
//            showInActiveWheelState(true)
//            binding.cvPointsNeeded.showView()
//            binding.btnPointsNeeded.showView()
//            binding.ivLock.showView()
//            val img: Drawable = getResources().getDrawable(R.drawable.ic_star_filled)
//            binding.unlockValue.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
//            binding.unlockValue.text = getFormattedPoints((spinWheelData.unlockValue - roundedGems).toString())
//            binding.tvSpinWheel.setText("EARN MORE AT REWARDS")
//            changeButtonState(true)
//            binding.ivArrow.showView()
//        }
    }

}
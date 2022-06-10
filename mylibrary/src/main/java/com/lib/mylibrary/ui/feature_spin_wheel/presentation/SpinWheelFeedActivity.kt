package com.lib.mylibrary.ui.feature_spin_wheel.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lib.mylibrary.R
import com.lib.mylibrary.core.util.*
import com.lib.mylibrary.data.remote.dto.SpinWheelFeedDto
import com.lib.mylibrary.databinding.ActivitySpinWheelFeedBinding
import kotlinx.coroutines.flow.onEach

class SpinWheelFeedActivity : AppCompatActivity(), View.OnClickListener ,
    SpinWheelFeedAdapter.Interaction {
    private lateinit var binding: ActivitySpinWheelFeedBinding
    private lateinit var myContext: Context
    private var currentPage = 1
    private var totalPages = 1
    private var isScrollDataLoading = false
    private lateinit var spinWheelFeedAdapter: SpinWheelFeedAdapter
    private val viewModel: SpinWheelFeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spin_wheel_feed)

        binding.lifecycleOwner = this
        binding.colors = Colors
        binding.textLabels = TextLabels
        binding.localColors = SpinWheelFeedColors

        initVariables()
        setUpListner()
        setupAdapters()
        registerScrollListener()
        resetListParams()
        setUpViewModel()

    }

    private fun registerScrollListener() {
        binding.rvSpinWheelFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvSpinWheelFeed.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE &&
                    !isScrollDataLoading &&
                    currentPage < totalPages
                ) {
                    currentPage++
                    isScrollDataLoading = true
                    getSpinWheelFeed()
                }
            }
        })
    }

    private fun setupAdapters() {
        spinWheelFeedAdapter = SpinWheelFeedAdapter(this)
        binding.rvSpinWheelFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvSpinWheelFeed.adapter = spinWheelFeedAdapter
    }
    override fun onClickSpinWheelFeedItem(position: Int, item: SpinWheelFeedDto.Data.SpinWheel) {

  //      SpinTheWheelActivity.startActivity(this, item.id.toString())

//        // Post cleverTap event
//        val map = java.util.HashMap<String, Any>()
//        map["Spin the Wheel ID"] =item.id.toString()
//        CleverTapUtils.postCleverTapEvent(this, "Select Spin the Wheel", map)
    }
    private fun resetListParams(){
        currentPage = 1
        totalPages = 1
        isScrollDataLoading = false
        spinWheelFeedAdapter.swapData(mutableListOf())
    }

    private fun initVariables(){
        myContext = this
        binding.lifecycleOwner = this
        binding.viewModel = this.viewModel
//        loadingDialog = LoadingDialog(myContext)

    }
    private fun setUpListner()
    {
        binding.ivBack.setOnClickListener(this)
//        binding.noNetworkUi.btnReload.setOnClickListener(this)
//        binding.noNetworkUi.cvGoToSettings.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBack->{
                onBackPressed()
            }
//            R.id.btnReload->{
//                resetListParams()
//                getSpinWheelFeed()
//            }
//            R.id.cvGoToSettings->{
//                redirectToNetworkSettings()
//            }
        }
    }
    override fun onResume() {
        super.onResume()
        getSpinWheelFeed()
    }

    private fun getSpinWheelFeed() {
        viewModel.getSpinWheelFeed(currentPage)
    }

    private fun setUpViewModel() {
        viewModel.uiStateFlow.onEach {
            when (it) {
                is UIState.Loading -> {
                    //    loadingDialog.show()
                }
                is UIState.Success<*> -> {
                    //    loadingDialog.cancel()
                    //    binding.noNetworkUi.clEmptyStateNoInternet.hideView()
                    when (it.data) {
                        is SpinWheelFeedDto.Data -> {
                            val response = it.data
                            onSpinWheelFeedReceived(response, response.totalPages)
                        }
                    }
                }
                is UIState.Error -> {
                    //    loadingDialog.cancel()
//                    try {
//                        val errorCode = it.message.toInt()
//                        when (errorCode) {
//                            401 -> {
//                                CommonDialogUtils.showSingleButtonLogoutDialog(this, "Session Expired!", null)
//                            }
//                            422 -> {
//                                showServerErrorDialog()
//                            }
//                        }
//                    }catch (e: java.lang.Exception){
//                        if(it.message == "NetworkError"){
//                            if (!binding.noNetworkUi.clEmptyStateNoInternet.isVisible){
//                                binding.noNetworkUi.clEmptyStateNoInternet.showView()
//                            }
//                        }
//                    }
//
//                }
                }
            }
        }.observeInLifecycle(this)
    }
    fun onSpinWheelFeedReceived(responseData: SpinWheelFeedDto.Data, totalPages: Int) {
        isScrollDataLoading = false
        this.totalPages = totalPages
        if(responseData.spinWheels.isNullOrEmpty()){
            binding.noDataFound.showView()
            binding.rvSpinWheelFeed.hideView()
        }else{
            binding.rvSpinWheelFeed.showView()
            binding.noDataFound.hideView()
            spinWheelFeedAdapter.swapData(responseData.spinWheels.toMutableList())
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SpinWheelFeedActivity::class.java)
            context.startActivity(intent)
        }

    }
}
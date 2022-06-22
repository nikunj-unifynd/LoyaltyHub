package com.lib.mylibrary.ui.feature_spin_wheel.presentation

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.lib.mylibrary.R
import com.lib.mylibrary.core.util.*
import com.lib.mylibrary.data.remote.dto.SpinWheelFeedDto
import com.lib.mylibrary.databinding.ListSpinWheelItemBinding

class SpinWheelFeedAdapter(private val interaction: Interaction? = null) :
    ListAdapter<SpinWheelFeedDto.Data.SpinWheel, SpinWheelFeedAdapter.ViewHolder>(SpinWheelsDC()) {
    val TAG = this::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListSpinWheelItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_spin_wheel_item,
            parent,
            false)
        return ViewHolder(binding, interaction)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position),position)

    fun swapData(data: MutableList<SpinWheelFeedDto.Data.SpinWheel>) {
        submitList(data)
    }

    inner class ViewHolder(
        val binding: ListSpinWheelItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SpinWheelFeedDto.Data.SpinWheel, position: Int) {
            binding.colors = Colors
            binding.textLabels = TextLabels
            binding.localColors = SpinWheelFeedColors
            binding.executePendingBindings()
            binding.tvTitle.text = item.name
            if(item.benefitsData.isNullOrEmpty()){
                binding.llCoupons.hideView()
                binding.llRewards.hideView()
                binding.llPointGems.showView()
                binding.tvPointsGemsFrom.text = "Try your luck at winning some points + stars"
            }else{
                val couponOfferingStoresList = item.benefitsData.filter {it.benefitType == 1}
                val rewardOfferingStoresList = item.benefitsData.filter {it.benefitType == 2}
                val pointsOfferingStoresList = item.benefitsData.filter {it.benefitType == 3}
                val gemsOfferingStoresList = item.benefitsData.filter {it.benefitType == 4}
                //val nonEmptyLogoStoresList = item.benefitsData.filter { !it.logo.isNullOrEmpty() }
                if(!couponOfferingStoresList.isNullOrEmpty()){
                    binding.llCoupons.showView()
                    binding.tvCouponsFrom.text = "Try your luck at winning some vouchers from"
                    when(couponOfferingStoresList.size){
                        1-> {
                            binding.llCouponStoresImages.showView()
                            binding.ivCoupStoreOne.showView()
                            binding.ivCoupStoreTwo.hideView()
                            binding.ivCoupStoreThree.hideView()
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[0].logo)
                                .into(binding.ivCoupStoreOne)
                        }

                        2-> {
                            binding.llCouponStoresImages.showView()
                            binding.ivCoupStoreOne.showView()
                            binding.ivCoupStoreTwo.showView()
                            binding.ivCoupStoreThree.hideView()
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[0].logo)
                                .into(binding.ivCoupStoreOne)
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[1].logo)
                                .into(binding.ivCoupStoreTwo)
                        }

                        3-> {
                            binding.llCouponStoresImages.showView()
                            binding.ivCoupStoreOne.showView()
                            binding.ivCoupStoreTwo.showView()
                            binding.ivCoupStoreThree.showView()
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[0].logo)
                                .into(binding.ivCoupStoreOne)
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[1].logo)
                                .into(binding.ivCoupStoreTwo)
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[2].logo)
                                .into(binding.ivCoupStoreThree)
                        }

                        else->{
                            binding.llCouponStoresImages.showView()
                            binding.ivCoupStoreOne.showView()
                            binding.ivCoupStoreTwo.showView()
                            binding.ivCoupStoreThree.showView()
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[0].logo)
                                .into(binding.ivCoupStoreOne)
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[1].logo)
                                .into(binding.ivCoupStoreTwo)
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[2].logo)
                                .into(binding.ivCoupStoreThree)
                        }

                    }
                }else{
                    binding.llCoupons.hideView()
                }

                if(!rewardOfferingStoresList.isNullOrEmpty()){
                    binding.llRewards.showView()
                    if(!binding.llCoupons.isVisible()){
                        binding.tvRewardsFrom.text = "Try your luck at winning some rewards from"
                    }else{
                        binding.tvRewardsFrom.text = " + rewards from"
                    }
                    when(rewardOfferingStoresList.size){
                        1-> {
                            binding.llRewardStoresImages.showView()
                            binding.ivRewaStoreOne.showView()
                            binding.ivRewaStoreTwo.hideView()
                            binding.ivRewaStoreThree.hideView()
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[0].logo)
                                .into(binding.ivRewaStoreOne)
                        }

                        2-> {
                            binding.llRewardStoresImages.showView()
                            binding.ivRewaStoreOne.showView()
                            binding.ivRewaStoreTwo.showView()
                            binding.ivRewaStoreThree.hideView()
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[0].logo)
                                .into(binding.ivRewaStoreOne)
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[1].logo)
                                .into(binding.ivRewaStoreTwo)
                        }

                        3-> {
                            binding.llRewardStoresImages.showView()
                            binding.ivRewaStoreOne.showView()
                            binding.ivRewaStoreTwo.showView()
                            binding.ivRewaStoreThree.showView()
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[0].logo)
                                .into(binding.ivRewaStoreOne)
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[1].logo)
                                .into(binding.ivRewaStoreTwo)
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[2].logo)
                                .into(binding.ivRewaStoreThree)
                        }

                        else ->{
                            binding.llRewardStoresImages.showView()
                            binding.ivRewaStoreOne.showView()
                            binding.ivRewaStoreTwo.showView()
                            binding.ivRewaStoreThree.showView()
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[0].logo)
                                .into(binding.ivRewaStoreOne)
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[1].logo)
                                .into(binding.ivRewaStoreTwo)
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[2].logo)
                                .into(binding.ivRewaStoreThree)
                        }

                    }
                }else{
                    binding.llRewards.hideView()
                }

                if(!pointsOfferingStoresList.isNullOrEmpty() && !gemsOfferingStoresList.isNullOrEmpty()){
                    binding.llPointGems.showView()
                    if(!binding.llCoupons.isVisible() && !binding.llRewards.isVisible()){
                        binding.tvPointsGemsFrom.text = "Try your luck at winning some points + stars"
                    }else if (binding.llRewards.isVisible() &&!binding.llCoupons.isVisible() ){
                        binding.llPointGems.showView()
                        binding.tvPointsGemsReward.hideView()
                        binding.tvPointsGemsFrom.text = " + points + stars"
                    }else if (binding.llRewards.isVisible()) {
                        binding.llPointGems.hideView()
                        binding.tvPointsGemsReward.showView()
                        binding.tvPointsGemsReward.text = " + points + stars"
                    }else{
                        binding.tvPointsGemsFrom.text = " + points + stars"
                    }
                }else if(!pointsOfferingStoresList.isNullOrEmpty() && gemsOfferingStoresList.isNullOrEmpty()){
                    binding.llPointGems.showView()
                    if(!binding.llCoupons.isVisible() && !binding.llRewards.isVisible()){
                        binding.tvPointsGemsFrom.text = "Try your luck at winning some points"
                    }else if (binding.llRewards.isVisible()){
                        binding.llPointGems.hideView()
                        binding.tvPointsGemsReward.showView()
                        binding.tvPointsGemsReward.text = " + points"
                    }else{
                        binding.tvPointsGemsFrom.text = " + points"
                    }
                }else if(!gemsOfferingStoresList.isNullOrEmpty() && pointsOfferingStoresList.isNullOrEmpty()){
                    binding.llPointGems.showView()
                    if(!binding.llCoupons.isVisible() && !binding.llRewards.isVisible()){
                        binding.tvPointsGemsFrom.text = "Try your luck at winning some stars"
                    }else if (binding.llRewards.isVisible()){
                        binding.llPointGems.hideView()
                        binding.tvPointsGemsReward.showView()
                        binding.tvPointsGemsReward.text = " + stars"
                    }else{
                        binding.tvPointsGemsFrom.text = " + stars"
                    }
                }else{
                    Log.d(TAG, "bind: pointsStoresList and gemsStoresList both empty")
                }

            }
            binding.root.setOnClickListener {
                interaction?.onClickSpinWheelFeedItem(position, item)
            }

        }
    }

    interface Interaction {
        fun onClickSpinWheelFeedItem(position: Int, item: SpinWheelFeedDto.Data.SpinWheel)
    }

    private class SpinWheelsDC : DiffUtil.ItemCallback<SpinWheelFeedDto.Data.SpinWheel>() {
        override fun areItemsTheSame(
            oldItem: SpinWheelFeedDto.Data.SpinWheel,
            newItem: SpinWheelFeedDto.Data.SpinWheel
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: SpinWheelFeedDto.Data.SpinWheel,
            newItem: SpinWheelFeedDto.Data.SpinWheel
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
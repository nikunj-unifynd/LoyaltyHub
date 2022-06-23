package com.lib.mylibrary.ui.feature_spin_wheel.presentation

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
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
                binding.llCoupons.visibility = View.GONE
                binding.llRewards.visibility = View.GONE
                binding.llPointGems.visibility = View.VISIBLE
                binding.tvPointsGemsFrom.text = "Try your luck at winning some points and gems"
            }else{
                val couponOfferingStoresList = item.benefitsData.filter {it.benefitType == 1L}
                val rewardOfferingStoresList = item.benefitsData.filter {it.benefitType == 2L}
                val pointsOfferingStoresList = item.benefitsData.filter {it.benefitType == 3L}
                val gemsOfferingStoresList = item.benefitsData.filter {it.benefitType == 4L}
                //val nonEmptyLogoStoresList = item.benefitsData.filter { !it.logo.isNullOrEmpty() }
                if(!couponOfferingStoresList.isNullOrEmpty()){
                    binding.llCoupons.visibility = View.VISIBLE
                    binding.tvCouponsFrom.text = "Try your luck at winning some coupons from"
                    when(couponOfferingStoresList.size){
                        1-> {
                            binding.llCouponStoresImages.visibility = View.VISIBLE
                            binding.ivCoupStoreOne.visibility = View.VISIBLE
                            binding.ivCoupStoreTwo.visibility = View.GONE
                            binding.ivCoupStoreThree.visibility = View.GONE
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[0].logo)
                                .into(binding.ivCoupStoreOne)
                        }

                        2-> {
                            binding.llCouponStoresImages.visibility = View.VISIBLE
                            binding.ivCoupStoreOne.visibility = View.VISIBLE
                            binding.ivCoupStoreTwo.visibility = View.VISIBLE
                            binding.ivCoupStoreThree.visibility = View.GONE
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[0].logo)
                                .into(binding.ivCoupStoreOne)
                            Glide.with(binding.root.context)
                                .load(couponOfferingStoresList[1].logo)
                                .into(binding.ivCoupStoreTwo)
                        }

                        3-> {
                            binding.llCouponStoresImages.visibility = View.VISIBLE
                            binding.ivCoupStoreOne.visibility = View.VISIBLE
                            binding.ivCoupStoreTwo.visibility = View.VISIBLE
                            binding.ivCoupStoreThree.visibility = View.VISIBLE
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
                            binding.llCouponStoresImages.visibility = View.VISIBLE
                            binding.ivCoupStoreOne.visibility = View.VISIBLE
                            binding.ivCoupStoreTwo.visibility = View.VISIBLE
                            binding.ivCoupStoreThree.visibility = View.VISIBLE
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
                    binding.llCoupons.visibility = View.GONE
                }

                if(!rewardOfferingStoresList.isNullOrEmpty()){
                    binding.llRewards.visibility = View.VISIBLE
                    if(!binding.llCoupons.isVisible()){
                        binding.tvRewardsFrom.text = "Try your luck at winning some rewards from"
                    }else{
                        binding.tvRewardsFrom.text = " + rewards from"
                    }
                    when(rewardOfferingStoresList.size){
                        1-> {
                            binding.llRewardStoresImages.visibility = View.VISIBLE
                            binding.ivRewaStoreOne.visibility = View.VISIBLE
                            binding.ivRewaStoreTwo.visibility = View.GONE
                            binding.ivRewaStoreThree.visibility = View.GONE
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[0].logo)
                                .into(binding.ivRewaStoreOne)
                        }

                        2-> {
                            binding.llRewardStoresImages.visibility = View.VISIBLE
                            binding.ivRewaStoreOne.visibility = View.VISIBLE
                            binding.ivRewaStoreTwo.visibility = View.VISIBLE
                            binding.ivRewaStoreThree.visibility = View.GONE
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[0].logo)
                                .into(binding.ivRewaStoreOne)
                            Glide.with(binding.root.context)
                                .load(rewardOfferingStoresList[1].logo)
                                .into(binding.ivRewaStoreTwo)
                        }

                        3-> {
                            binding.llRewardStoresImages.visibility = View.VISIBLE
                            binding.ivRewaStoreOne.visibility = View.VISIBLE
                            binding.ivRewaStoreTwo.visibility = View.VISIBLE
                            binding.ivRewaStoreThree.visibility = View.VISIBLE
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
                            binding.llRewardStoresImages.visibility = View.VISIBLE
                            binding.ivRewaStoreOne.visibility = View.VISIBLE
                            binding.ivRewaStoreTwo.visibility = View.VISIBLE
                            binding.ivRewaStoreThree.visibility = View.VISIBLE
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
                    binding.llRewards.visibility = View.GONE
                }

                if(!pointsOfferingStoresList.isNullOrEmpty() && !gemsOfferingStoresList.isNullOrEmpty()){
                    binding.llPointGems.visibility = View.VISIBLE
                    if(!binding.llCoupons.isVisible() && !binding.llRewards.isVisible()){
                        binding.tvPointsGemsFrom.text = "Try your luck at winning some points and gems"
                    }else{
                        binding.tvPointsGemsFrom.text = " + points and gems"
                    }
                }else if(!pointsOfferingStoresList.isNullOrEmpty() && gemsOfferingStoresList.isNullOrEmpty()){
                    binding.llPointGems.visibility = View.VISIBLE
                    if(!binding.llCoupons.isVisible() && !binding.llRewards.isVisible()){
                        binding.tvPointsGemsFrom.text = "Try your luck at winning some points"
                    }else{

                        binding.tvPointsGemsFrom.text = " + points"
                    }
                }else if(!gemsOfferingStoresList.isNullOrEmpty() && pointsOfferingStoresList.isNullOrEmpty()){
                    binding.llPointGems.visibility = View.VISIBLE
                    if(!binding.llCoupons.isVisible() && !binding.llRewards.isVisible()){
                        binding.tvPointsGemsFrom.text = "Try your luck at winning some gems"
                    }else{
                        binding.tvPointsGemsFrom.text = " + gems"
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
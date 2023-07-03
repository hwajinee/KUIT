package com.example.naverwebtoon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naverwebtoon.databinding.FragmentBannerBinding

class BannerVPAdapter(private val imgList: ArrayList<Int>)
    : RecyclerView.Adapter<BannerVPAdapter.BannerViewHolder>() {

    lateinit var binding : FragmentBannerBinding

    inner class BannerViewHolder(val binding: FragmentBannerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(img: Int){
            binding.fBannerImgIv.setImageResource(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerVPAdapter.BannerViewHolder {
        binding = FragmentBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: BannerVPAdapter.BannerViewHolder, position: Int) {
        holder.bind(imgList[position % imgList.size])
    }


}
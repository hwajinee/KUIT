package com.example.naverwebtoon.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naverwebtoon.databinding.FragmentRecommendFinishBinding

class RecommendFinishFragment : Fragment() {
    lateinit var binding: FragmentRecommendFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendFinishBinding.inflate(inflater, container, false)
        return binding.root
    }
}
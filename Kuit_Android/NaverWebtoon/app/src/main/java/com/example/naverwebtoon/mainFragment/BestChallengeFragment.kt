package com.example.naverwebtoon.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naverwebtoon.databinding.FragmentBestChallengeBinding

class BestChallengeFragment : Fragment() {
    lateinit var binding: FragmentBestChallengeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBestChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }
}
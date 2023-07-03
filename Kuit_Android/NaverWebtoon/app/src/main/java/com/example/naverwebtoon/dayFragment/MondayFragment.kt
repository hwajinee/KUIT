package com.example.naverwebtoon.dayFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naverwebtoon.R
import com.example.naverwebtoon.RecommendWebtoonAdapter
import com.example.naverwebtoon.WebtoonAdapter
import com.example.naverwebtoon.WebtoonDetailFragment
import com.example.naverwebtoon.data.WebtoonCover
import com.example.naverwebtoon.data.WebtoonDB
import com.example.naverwebtoon.databinding.FragmentDailyWebtoonBinding
import com.google.gson.Gson

class MondayFragment() : Fragment() {
    lateinit var binding : FragmentDailyWebtoonBinding

    var webtoonAdapter: WebtoonAdapter? = null
    var recWebtoonAdapter : RecommendWebtoonAdapter?= null
    var webtoonList = ArrayList<WebtoonCover>()
    var recommendWebtoonList =  ArrayList<WebtoonCover>()

    override fun onResume() {
        super.onResume()
        webtoonAdapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyWebtoonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDummyData()

        webtoonAdapter = WebtoonAdapter(webtoonList)
        binding.webtoonListRv.adapter = webtoonAdapter
        binding.webtoonListRv.layoutManager = GridLayoutManager(context,3)
        webtoonAdapter!!.setOnItemClickListener(object : WebtoonAdapter.OnItemClickListener {
            override fun onItemClick(webtoonInfo: WebtoonCover) {
                val webtoonBundle = Bundle()
                val webtoonDetailFragment = WebtoonDetailFragment()
                val infoJson = Gson().toJson(webtoonInfo)
                webtoonBundle.putString("webtoonInfo", infoJson)
                webtoonDetailFragment.arguments = webtoonBundle
                parentFragment!!.parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, webtoonDetailFragment).commit()
        }
    })

    recWebtoonAdapter = RecommendWebtoonAdapter(recommendWebtoonList)
    binding.webtoonRecommendRv.adapter = recWebtoonAdapter
    binding.webtoonRecommendRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    recWebtoonAdapter!!.setOnItemClickListener(object :
        RecommendWebtoonAdapter.OnItemClickListener {
        override fun onItemClick(webtoonInfo: WebtoonCover) {
            val webtoonBundle = Bundle()
            val webtoonDetailFragment = WebtoonDetailFragment()
            val infoJson = Gson().toJson(webtoonInfo)
            webtoonBundle.putString("webtoonInfo", infoJson)
            webtoonDetailFragment.arguments = webtoonBundle
            parentFragment!!.parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, webtoonDetailFragment).commit()
        }
    })
}

private fun initDummyData() {
    val wcDao = WebtoonDB.getInstance(requireContext())!!.WebtoonCoverDao()
    val webtoonCovers = wcDao.getWebtoonCovers(1)
    recommendWebtoonList.addAll(webtoonCovers)
    webtoonList.addAll(webtoonCovers)
    webtoonAdapter?.notifyDataSetChanged()
}

}
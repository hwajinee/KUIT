package com.example.naverwebtoon.mainFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naverwebtoon.FavoriteRVAdapter
import com.example.naverwebtoon.R
import com.example.naverwebtoon.WebtoonDetailFragment
import com.example.naverwebtoon.data.WebtoonDB
import com.example.naverwebtoon.data.getUserId
import com.example.naverwebtoon.databinding.FragmentMyBinding
import com.example.naverwebtoon.userData.UserDB
import com.google.gson.Gson

class MyFragment : Fragment() {
    lateinit var binding: FragmentMyBinding
    lateinit var userDB : UserDB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userDB = UserDB.getInstance(requireContext())!!
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)

        val datalist = userDB.UserDao().getFavorites(getUserId(requireContext()))
        val mAdapter = FavoriteRVAdapter(datalist)
        mAdapter.setOnItemClickListener(object : FavoriteRVAdapter.OnItemClickListener{
            override fun itemClickListener(webtoonId : Int) {
                val webtoonDetailFragment = WebtoonDetailFragment()
                val webtoonDB = WebtoonDB.getInstance(requireContext())!!
                val webtoonCoverInfo = Gson().toJson(webtoonDB.WebtoonCoverDao().getWebtoonCover(webtoonId))
                val webtoonBundle = Bundle()
                webtoonBundle.putString("webtoonInfo", webtoonCoverInfo)
                webtoonDetailFragment.arguments = webtoonBundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm,webtoonDetailFragment)
                    .commit()
            }

            override fun alarmChangeListener(webtoonId : Int, isAlarmed : Boolean) {
                userDB.UserDao().updateAlarmed(isAlarmed, getUserId(requireContext()), webtoonId)
            }
        })

        // 어댑터를 생성하기 위해서 datalist가 필요 -> userDB를 하나 선언해주고, onAttach() 단계에서 초기화.
        // 바로 위 문장으로, 유저 아이디에 맞는 찜 항목을 리스트로 받는다.
        binding.myFavoriteRv.apply{
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }


}
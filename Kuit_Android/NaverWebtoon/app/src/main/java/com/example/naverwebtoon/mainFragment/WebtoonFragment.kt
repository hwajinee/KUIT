package com.example.naverwebtoon.mainFragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.naverwebtoon.BannerVPAdapter
import com.example.naverwebtoon.R
import com.example.naverwebtoon.WebtoonVPAdapter
import com.example.naverwebtoon.databinding.FragmentWebtoonBinding
import com.google.android.material.tabs.TabLayoutMediator

class WebtoonFragment : Fragment() {
    lateinit var binding: FragmentWebtoonBinding

    private val categoryList = listOf<String>("신작","매일+","월","화","수","목","금","토","일", "완결")
    var bannerPosition = 0
    var pagerHandler = Handler(Looper.getMainLooper())
    val bannerSwiper = BannerSwiper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebtoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bannerList = ArrayList<Int>().apply {
            add(R.drawable.banner_about_death)
            add(R.drawable.banner_umis_cell)
            add(R.drawable.banner_aimer)
            add(R.drawable.banner_your_smile_is_trap)
            add(R.drawable.banner_alone_necromancer)
        }

        val bannerAdapter = BannerVPAdapter(bannerList)
        binding.webtoonBannerVp.adapter = bannerAdapter
        binding.webtoonBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.webtoonBannerTextTv.text =
            getString(R.string.webtoon_banner_text, 1, bannerList.size)


        bannerPosition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % bannerList.size)
        binding.webtoonBannerVp.setCurrentItem(bannerPosition, false)

        binding.webtoonBannerVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                bannerPosition = position
                binding.webtoonBannerTextTv.text =
                    getString(R.string.webtoon_banner_text,
                        (bannerPosition % bannerList.size) + 1,
                        bannerList.size)
            }
        })

        bannerSwiper.start()

        val webtoonAdapter = WebtoonVPAdapter(this)
        binding.webtoonListVp.adapter = webtoonAdapter
        //첫번째 인자는 TabLayout, 두번째 인자는 ViewPager - 둘을 연결. {} 내에는 어떻게 연결할지
        TabLayoutMediator(binding.webtoonCategoryTb, binding.webtoonListVp){ tab, pos ->
            tab.text = categoryList[pos]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerSwiper.interrupt()
    }

    inner class BannerSwiper : Thread(){
        override fun run() {
            try {
                while(true){
                    sleep(3000)
                    pagerHandler.post{
                        var position = binding.webtoonBannerVp.currentItem
                        if(position == 5) {
                            position = 0
                            binding.webtoonBannerVp.currentItem = position
                        } else {
                            position += 1
                            binding.webtoonBannerVp.currentItem = position
                        }
                    }
                }
            } catch (e : InterruptedException){
                Log.d("interruptException!", "Banner Swiper 종료")
                interrupt()
            }
        }
    }

}
package com.example.naverwebtoon

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.naverwebtoon.data.WebtoonCover
import com.example.naverwebtoon.data.getUserId
import com.example.naverwebtoon.databinding.FragmentWebtoonDetailBinding
import com.example.naverwebtoon.mainFragment.WebtoonFragment
import com.example.naverwebtoon.userData.UserDB
import com.example.naverwebtoon.userData.UserFavorite
import com.google.gson.Gson

class WebtoonDetailFragment :Fragment() {
    lateinit var binding : FragmentWebtoonDetailBinding
    var isInterest = false
    var isAlarmed = false
    lateinit var userDB : UserDB
    lateinit var webtoonInfo : WebtoonCover

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userDB = UserDB.getInstance(requireContext())!!
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebtoonDetailBinding.inflate(inflater, container, false)
        webtoonInfo = Gson().fromJson(requireArguments().getString("webtoonInfo"), WebtoonCover::class.java)
        initView(webtoonInfo)

        binding.btnBack.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, WebtoonFragment()).commitAllowingStateLoss()
        }
        binding.btnFavorite.setOnClickListener{
            isInterest =!isInterest
            updateInterest(isInterest)
        }

        binding.btnAlarm.setOnClickListener{
            isAlarmed =!isAlarmed
            updateAlarm(isAlarmed)
        }
        binding.ivSummaryDown.setOnClickListener {
            binding.tvSummary.maxLines = Int.MAX_VALUE
            binding.ivSummaryUp.visibility = View.VISIBLE
            binding.ivSummaryDown.visibility = View.GONE
        }
        binding.ivSummaryUp.setOnClickListener {
            binding.tvSummary.maxLines = 1
            binding.ivSummaryUp.visibility = View.GONE
            binding.ivSummaryDown.visibility = View.VISIBLE
        }
        binding.ivPreviewDown.setOnClickListener{
            setPreviewLayout(true)
        }
        binding.ivPreviewUp.setOnClickListener{
            setPreviewLayout(false)
         }
        binding.webtoonDetailItem1.setOnClickListener{
            val mIntent = Intent(requireContext(), WebtoonActivity::class.java)
            val dataJson = requireArguments().getString("webtoonInfo")
            mIntent.putExtra("webtoonData",dataJson)
            mIntent.putExtra("webtoonTitle", binding.tvItem1Title.text.toString())
            startActivity(mIntent)
        }

        //more 옵션메뉴 dialog
        val option_dialog = AlertDialog.Builder(requireContext())
            .setItems(arrayOf("첫화보기","공유하기","임시저장","소장하기")){dialog, which ->
                when(which){
                    0 -> {
                        val mIntent = Intent(requireContext(), WebtoonActivity::class.java)
                        startActivity(mIntent)
                    }
                    1 -> {
                        val shareDialog = ShareDialog()
                        shareDialog.show(parentFragmentManager, "shareDialog")
                    }
                    2 -> {Toast.makeText(requireContext(), which.toString() + "Selected!!", Toast.LENGTH_SHORT).show()}
                    3 -> {Toast.makeText(requireContext(), which.toString()+ "Selected!!", Toast.LENGTH_SHORT).show()}
                }
            }.create()
        binding.btnMoreVert.setOnClickListener {
            option_dialog.show()
        }

        return binding.root
    }

    fun updateInterest(isInterest : Boolean){
        if (isInterest){
            userDB.UserDao().insertFavorite(
                //insertFavorite은 insert할 UserFavorite 객체를 생성해서 넘겨주면 된다.
                UserFavorite(
                    getUserId(requireContext()),
                    webtoonInfo.id,
                    webtoonInfo.title,
                    webtoonInfo.author,
                    webtoonInfo.poster,
                    isAlarmed
                )
            ) // 찜을 눌렀을 때, 데이터를 UserFavorite 테이블에 저장.
            binding.ivInterestOn.visibility = View.VISIBLE
            binding.ivInterestOff.visibility = View.GONE
            if(isAlarmed){
                binding.ivAlarmOn.visibility = View.VISIBLE
                binding.ivAlarmOff.visibility = View.GONE
            }else {
                binding.ivAlarmOn.visibility = View.GONE
                binding.ivAlarmOff.visibility = View.VISIBLE
            }
        }else {
            userDB.UserDao().deleteFavoriteById(getUserId(requireContext()), webtoonInfo.id)
            // 이미 찜한 웹툰은 다시 찜을 눌렀을 때, UserFavorite 테이블에서 삭제.
            binding.ivInterestOn.visibility = View.GONE
            binding.ivInterestOff.visibility = View.VISIBLE
            binding.ivAlarmOn.visibility = View.GONE
            binding.ivAlarmOff.visibility = View.GONE
        }
    }

    fun updateAlarm(isAlarmed : Boolean){
        userDB.UserDao().updateAlarmed(isAlarmed, getUserId(requireContext()), webtoonInfo.id)
        if(isAlarmed){
            binding.ivAlarmOff.visibility = View.GONE
            binding.ivAlarmOn.visibility = View.VISIBLE
        }else{
            binding.ivAlarmOff.visibility = View.VISIBLE
            binding.ivAlarmOn.visibility = View.GONE

        }
    }
    fun setPreviewLayout(status : Boolean) {
        if (status) {
            binding.tvPreviewNum.text = ""
            binding.tvPreview.text = "미리보기 접기"
            binding.cvPreviewPoster1.visibility = View.GONE
            binding.cvPreviewPoster2.visibility = View.GONE
            binding.cvPreviewPoster3.visibility = View.GONE
            binding.ivPreviewUp.visibility = View.VISIBLE
            binding.ivPreviewDown.visibility = View.GONE
            binding.layoutPreview.visibility = View.VISIBLE
        } else {
            binding.tvPreviewNum.text = "5개"
            binding.tvPreview.text = "의 미리보기가 있습니다."
            binding.cvPreviewPoster1.visibility = View.VISIBLE
            binding.cvPreviewPoster2.visibility = View.VISIBLE
            binding.cvPreviewPoster3.visibility = View.VISIBLE
            binding.ivPreviewUp.visibility = View.GONE
            binding.ivPreviewDown.visibility = View.VISIBLE
            binding.layoutPreview.visibility = View.GONE
        }
    }

    fun initView(webtoonInfo: WebtoonCover) {
//        val title = requireArguments().getString("title")
//        val author = requireArguments().getString("author")
//        val poster = requireArguments().getInt("poster")
//        val banner = requireArguments().getInt("banner")
        val themeColor = requireContext().getColor(webtoonInfo.themeColor)
        val favoriteInfo = userDB.UserDao().isFavorite(getUserId(requireContext()), webtoonInfo.id)
        //isFavorite 함수는 유저 아이디와 웹툰 아이디를 인자로 보냈을 떼,
        // 유저가 찜한 웹툰이면 UserFavorite 객체를 반환하고, 아니면 null값을 반환한다.
        if (favoriteInfo == null){
            isInterest = false
        } else {
            isInterest = true
            isAlarmed = favoriteInfo.isAlarmed
        }
        // 관심&알람 상태UI 유저데이터에 맞게 초기화
        if(isInterest){
            binding.ivInterestOn.visibility = View.VISIBLE
            binding.ivInterestOff.visibility = View.GONE
            if (isAlarmed){
                binding.ivAlarmOn.visibility = View.VISIBLE
                binding.ivAlarmOff.visibility = View.GONE
            } else {
                binding.ivAlarmOn.visibility = View.GONE
                binding.ivAlarmOff.visibility = View.VISIBLE
            }
        } else {
            binding.ivInterestOn.visibility = View.GONE
            binding.ivInterestOff.visibility = View.VISIBLE
            binding.ivAlarmOn.visibility = View.GONE
            binding.ivAlarmOff.visibility = View.GONE
        }

        binding.tvTitle.text = webtoonInfo.title
        val author_text = webtoonInfo.author + ">"
        binding.tvAuthor.text = author_text
        binding.ivBannerTop.setImageResource(webtoonInfo.banner)
        binding.ivItem1Poster.setImageResource(webtoonInfo.poster)
        binding.ivItem2Poster.setImageResource(webtoonInfo.poster)
        binding.ivItem3Poster.setImageResource(webtoonInfo.poster)
        binding.ivItem4Poster.setImageResource(webtoonInfo.poster)
        binding.ivPrevItem1Poster.setImageResource(webtoonInfo.poster)
        binding.ivPrevItem2Poster.setImageResource(webtoonInfo.poster)
        binding.backgroundTop.setBackgroundColor(themeColor)
        binding.cvInterest.setCardBackgroundColor(themeColor)
        if (webtoonInfo.summary != null) {
            binding.tvSummary.text = webtoonInfo.summary
        }
    }
}

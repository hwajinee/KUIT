package com.example.naverwebtoon

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.naverwebtoon.data.WebtoonCover
import com.example.naverwebtoon.data.WebtoonDB
import com.example.naverwebtoon.data.saveUserId
import com.example.naverwebtoon.databinding.ActivityMainBinding
import com.example.naverwebtoon.mainFragment.*
import com.example.naverwebtoon.userData.User
import com.example.naverwebtoon.userData.UserDB
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var exit_millis :Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("Main_lifecycle", "onCreate()")

        val webtoonDB = WebtoonDB.getInstance(this)!!
        initDummyCovers(webtoonDB)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, WebtoonFragment())
            .commitAllowingStateLoss()

        val bnv_fvbyId = findViewById<BottomNavigationView>(R.id.main_bnv)
        bnv_fvbyId.setOnClickListener { }

        binding.mainBnv.setOnClickListener { }

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.webtoonFragment -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frm, WebtoonFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.recommendFinishFragment -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frm, RecommendFinishFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.bestChallengeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frm, BestChallengeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.myFragment -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frm, MyFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.moreFragment -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_frm, MoreFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        binding.mainBnv.itemIconTintList = null

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = supportFragmentManager.findFragmentById(R.id.main_frm)
                if (fragment is WebtoonFragment) {
                    killApp()
                } else {
                    binding.mainBnv.selectedItemId = R.id.webtoonFragment
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    fun killApp() {
        if(System.currentTimeMillis() - exit_millis > 2000){
            exit_millis=System.currentTimeMillis()
            Toast.makeText( this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }else{
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Main_lifecycle", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Main_lifecycle", "onResume()")
    }
    override fun onPause() {
        super.onPause()
        Log.d("Main_lifecycle", "onPause()")
    }
    override fun onStop() {
        super.onStop()
        Log.d("Main_lifecycle", "onStop()")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("Main_lifecycle", "onDestroy()")
    }

    fun initDummyCovers(webtoonDB: WebtoonDB){
        val userDao = UserDB.getInstance(this)!!.UserDao()
        userDao.insert(
            User(
                "hvvxzin",
                "화진"
            )
        )
        saveUserId(this,"hvvxzin")

        val wcDao = webtoonDB.WebtoonCoverDao()
        val covers = wcDao.getAllWebtoonCovers()
        if(covers.isNotEmpty()) return
        wcDao.insert(
            WebtoonCover(
                "택배기사",
                "이윤균",
                9.87f,
                0,
                "오염된 세상, 바깥은 위험하다 어쩌구 꿈과 희망이 담긴 어쩌구 뉴진스 하입보이요 아러라어로롤ㅇㅁㄹ;나덜;ㅁ이ㅏ럴라렇ㄹ;ㅎ 똥ㅁ냠꿍 믇ㄹ베ㅜ렐렉",
                false,
                R.color.theme_wasabi,
                R.drawable.poster_aimer,
                R.drawable.banner_nano_demon
            )
        )
        wcDao.insert(
            WebtoonCover(
                "인자강",
                "김경태/진성",
                9.84f,
                0,
                "너무나 거대한 재능은 소유자의 삶을 휘두른다 강력한신체와 언쩌구 그리고 난 엄청세고 난 진짜 강하고 내가 주인공이니까 내가짱짱 아러라어로롤ㅇㅁㄹ;나덜;ㅁ이ㅏ럴라렇ㄹ;ㅎ 똥ㅁ냠꿍 믇ㄹ베ㅜ렐렉",
                false,
                R.color.theme_skyblue,
                R.drawable.poster_your_smile_is_trap,
                R.drawable.banner_umis_cell
            )
        )
        wcDao.insert(
            WebtoonCover(
                "악마는 없다",
                "없는사람",
                9.67f,
                0,
                "인터넷 기사인 남자는 기괴한 고객집에 방문 후 밤새 악몽에 시달린다. 다음날 스토커에 괴롭힘을 당하는 전여자친구가 찾아 오거나말거나 어쩌구 그런데 제목이 악마는없다 인데 작가가 없는 사람이야 그럼 작가가 악마인건가? ㅋㅋ루삥뽕 훔ㅎㄴㅇㅎ히힛",
                true,
                R.color.theme_mustard,
                R.drawable.poster_want_to_be_you,
                R.drawable.banner_study_group
            )
        )
        wcDao.insert(
            WebtoonCover(
                "휴재일기",
                "자까",
                9.23f,
                0,
                "분명 휴재중인데 연재 중인 이 신박한 상황은 뭐지...? 자까 작가만 할 수 있는 신개념 휴재 연재물 와 진짜 신개념이다 진짜 신기하다 웃긴다 나도 분명 학교다니는데 휴학중인 신박한 상황같은거 되고싶다 휴학하고 하고싶은거 하는데 학교다니는거로 쳐줘서 졸업시켜주면 좋겠다",
                false,
                R.color.theme_skyblue,
                R.drawable.poster_ugly_hood,
                R.drawable.banner_about_death
            )
        )
        wcDao.insert(
            WebtoonCover(
                "똑 닮은 딸",
                "이담",
                9.84f,
                1,
                "우리 엄마가 살인마인 것 같다. 성적 우수, 품행 단정, 모범적인 자식인 길소명은 엄마가 요구하는 기준에 맞춰 완벽한 딸로 살아ㅗ앗ㅁㄸ낚ㄹㅇㄹ 와 근데 엄마가 살인마이면 진짜 무섭겠다 어떻게 엄마가 살인마지 진짜 무섭겠다 와",
                false,
                R.color.theme_skyblue,
                R.drawable.poster_your_smile_is_trap,
                R.drawable.banner_umis_cell
            )
        )
        wcDao.insert(
            WebtoonCover(
                "신의탑",
                "SIU",
                9.14f,
                1,
                "자신의 모든것이었던 소녀를 쫓아 탑에 들어온 소년 그리고 그 여정을 그린 엄청 오래된 웹툰 다 알잖아 설명안해도 아러라어로롤ㅇㅁㄹ;나덜;ㅁ이ㅏ럴라렇ㄹ;ㅎ 똥ㅁ냠꿍 믇ㄹ베ㅜ렐렉",
                false,
                R.color.theme_red,
                R.drawable.poster_love_revolution,
                R.drawable.banner_your_smile_is_trap
            )
        )
        wcDao.insert(
            WebtoonCover(
                "윈드브레이커",
                "조용석",
                9.45f,
                1,
                "혼자서 자전거를 즐겨타던 모범생 조자현, 원치않게 자전거 크루에 들어가서 엄청난 활약을 펼치는 수퍼먼치킨 웹툰짱짱 뉴진스 하입보이요 아러라어로롤ㅇㅁㄹ;나덜;ㅁ이ㅏ럴라렇ㄹ;ㅎ 똥ㅁ냠꿍 믇ㄹ베ㅜ렐렉",
                false,
                R.color.naver_green,
                R.drawable.poster_flower_lover_is_too_strong,
                R.drawable.banner_build_up
            )
        )
        wcDao.insert(
            WebtoonCover(
                "김부장",
                "박태준 만화회사/정종택",
                9.43f,
                2,
                "제발 안경 쓴 아저씨는 건들지 말자... \"오직 자신의 딸 민지를 위해 싸우는 부장아재\"아니 건들지 말라니까아ㅏㅏㅇㄹ캉아앍카캌아ㅓㄹ덜;ㅁ이ㅏ럴라렇ㄹ;ㅎ 똥ㅁ냠꿍 믇ㄹ베ㅜ렐렉",
                false,
                R.color.theme_mustard,
                R.drawable.poster_love_revolution,
                R.drawable.banner_alone_fighting_learning
            )
        )
        wcDao.insert(
            WebtoonCover(
                "사신소년",
                "류",
                9.91f,
                2,
                "수명을 대가로 죽은 자의 능력을 빌리는 소년! 화려한 액션에서 내가 이ㄱ세계에선 최고짱짱 능력자 하입보이요 아러라어로롤ㅇㅁㄹ;나덜;ㅁ이ㅏ럴라렇ㄹ;ㅎ 똥ㅁ냠꿍 믇ㄹ베ㅜ렐렉",
                false,
                R.color.theme_wasabi,
                R.drawable.poster_king_of_animals,
                R.drawable.banner_execution_boy
            )
        )
        wcDao.insert(
            WebtoonCover(
                "너의 미소가 함정",
                "앵고",
                9.96F,
                2,
                "너의미소가함정너의미소가함정너의미소가함정너의미소가함정 너무너무무서워함정이야 도망가잇아얼;마ㅣㄴ얼;ㅏㅁ덜;ㅏㅇ무서무서워 도망가아앙라ㅓㅇ;ㅏㄻ알달",
                false,
                R.color.theme_skyblue,
                R.drawable.poster_your_smile_is_trap,
                R.drawable.banner_your_smile_is_trap
            )
        )
        wcDao.insert(
            WebtoonCover(
                "에이머",
                "구동인",
                9.80F,
                2,
                "에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이머에이ㅓ메브ㅂ뤱ㄹ붸레렑루벨굴베룩벡룰구베뤵렐멍ㄹ",
                true,
                R.color.theme_wasabi,
                R.drawable.poster_aimer,
                R.drawable.banner_aimer
            )
        )
        wcDao.insert(
            WebtoonCover(
                "99강화 나무 몽둥이",
                "홍설 / 지페리",
                9.76F,
                2,
                "겁나쎈 나무몽둥이 진짜쎄 진짜 엄청 강해 이거 한방이면 모든 몬스터들 다 때려잡아 진짜 강해 나만의 나무몽둥이 너무 쎄 너무좋아 99강이야 1억천만금을 줘도 안팔아 이것만 있으면 내가 최강이야 진짜 내가 짱이야 진짜로",
                true,
                R.color.theme_red,
                R.drawable.poster_99plus_wooden_stick,
                R.drawable.banner_99plus_wooden_stick
            )
        )
        wcDao.insert(
            WebtoonCover(
                "유미의 세포들",
                "이동건",
                9.93F,
                2,
                "유미의 세포들 너무 귀여워 특히 그 뚱뚱한애 진짜 귀여워 키우고싶어 유미의 세포들 짱이야 진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워진짜귀여ㅑ워",
                false,
                R.color.theme_mustard,
                R.drawable.poster_umis_cell,
                R.drawable.banner_umis_cell
            )
        )
    }
}
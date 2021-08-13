package com.ksw.imagesplash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ksw.imagesplash.databinding.ActivityMainBinding
import com.ksw.imagesplash.retrofit.RetrofitManager
import com.ksw.imagesplash.util.Constants.TAG
import com.ksw.imagesplash.util.RESPONSE_STATE
import com.ksw.imagesplash.util.SEARCH_TYPE
import com.ksw.imagesplash.util.onMyTextChanged

class MainActivity : AppCompatActivity() {

    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
//    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.USER

    private lateinit var binding: ActivityMainBinding
//    private lateinit var binding2: LayoutButtonSearchBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        binding2 = LayoutButtonSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(TAG, "onCreate: ")

        // 라디오 그룹 가져오기

        binding.rgSearch.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn_photo -> {
                    Log.d(TAG, "사진검색버튼클릭")
                    binding.searchText.hint = "사진검색"
                    binding.searchText.startIconDrawable =
                        resources.getDrawable(R.drawable.ic_photo_library, resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                }
                R.id.btn_user -> {
                    Log.d(TAG, "사용자검색버튼클릭")
                    binding.searchText.hint = "사용자검색"
                    binding.searchText.startIconDrawable =
                        resources.getDrawable(R.drawable.ic_person, resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.USER
                }
            }
            Log.d(TAG, "onCreate")
        }

        // 텍스트가 변경이 되었을 때
        binding.searchEditText.onMyTextChanged {
            if (it.toString().count() > 0) {
                binding.frameSearchBtn.visibility = View.VISIBLE
                binding.searchText.helperText = " "
                // 글자 입력시 강제 스크롤뷰를 올린다.
                binding.mainScrollView.scrollTo(0, 200)
            } else {
                binding.frameSearchBtn.visibility = View.INVISIBLE
            }

            if (it.toString().count() == 12) {
                Toast.makeText(this, "검색어는 12자 까지 입력 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchButton.setOnClickListener {
            handleSearchButtonUi()

            // 검색 api 호출
            RetrofitManager.instance.searchPhotos(searchTerm = binding.searchEditText.toString(),
                completion = { responseState, responseBody ->

                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            Log.d(TAG, "onCreate: $responseBody")
                        }
                        RESPONSE_STATE.FAIL -> {
                            Toast.makeText(this, "api error", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "호출 실패: $responseBody")
                        }
                    }
                })
        }

    }

    private fun handleSearchButtonUi() {
        binding.btnProgress.visibility = View.INVISIBLE
        binding.searchButton.text = ""

        Handler().postDelayed({
            binding.btnProgress.visibility = View.INVISIBLE
            binding.searchButton.text = "검색"
        }, 1500)
    }

}
package com.learnwithpuneet.mvvmsample.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.learnwithpuneet.mvvmsample.R
import com.learnwithpuneet.mvvmsample.databinding.ActivityMainBinding
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment
import com.zegocloud.uikit.prebuilt.call.config.ZegoHangUpConfirmDialogInfo
import java.util.Random


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding.btnStart.setOnClickListener {
            val userName = activityMainBinding.etUserName.text.toString()
            if (userName.isNotEmpty()){
                proceed(userName)
            }
        }


    }

    private fun generateUserID(): String {
        val builder = StringBuilder()
        val random = Random()
        while (builder.length < 5) {
            val nextInt: Int = random.nextInt(10)
            if (builder.isEmpty() && nextInt == 0) {
                continue
            }
            builder.append(nextInt)
        }
        return builder.toString()
    }

    private fun proceed(enteredName: String) {
        val appId: Long =  384046180;
        val appSign = "8863826db8a976b343ed526433e9898851c28a860b9db86ed3d963563c87deb4";
        val userId = generateUserID()
        val userName = userId + enteredName
        val callId = "123456"

        val config = ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()

        config.hangUpConfirmDialogInfo = ZegoHangUpConfirmDialogInfo()
        config.hangUpConfirmDialogInfo.title = "Hangup confirm"
        config.hangUpConfirmDialogInfo.message = "Do you want to hangup this call?"
        config.hangUpConfirmDialogInfo.cancelButtonName = "Cancel"
        config.hangUpConfirmDialogInfo.confirmButtonName = "Confirm"


        val fragment = ZegoUIKitPrebuiltCallFragment.newInstance(
            appId, appSign, generateUserID(), userName, callId, config
        )
        activityMainBinding.btnStart.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commitNow()
    }
}
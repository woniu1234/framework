package com.common.framework

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.common.common.CommonExFragment
import com.common.frame.utils.ToastUtil
import com.common.framework.databinding.FragmentBlankBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : CommonExFragment<FragmentBlankBinding, MainViewModel>() {

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun getLoadSirView(): View? {
        return null
    }

    override fun initEvent() {
        ToastUtil.show(binding.tvContent.text.toString())
    }

    override fun onRetryBtnClick() {

    }
}
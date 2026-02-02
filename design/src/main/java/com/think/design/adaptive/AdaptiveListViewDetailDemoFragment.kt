package com.think.design.adaptive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.think.design.R
import com.think.design.adaptive.AdaptiveListViewDemoFragment.EmailData

class AdaptiveListViewDetailDemoFragment : Fragment() {
    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(
            R.layout.cat_adaptive_list_view_detail_fragment, viewGroup, false
        )
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        val emailId = this.emailId
        val emailTitle = view.findViewById<TextView>(R.id.email_title)
        emailTitle.append(" " + (emailId + 1))
        // Set transition name that matches the list item to be transitioned from for the shared element
        // transition.
        val container = view.findViewById<View>(R.id.list_view_detail_container)
        ViewCompat.setTransitionName(container, emailTitle.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        updateEmailSelected(false)
    }

    override fun onStart() {
        super.onStart()
        updateEmailSelected(true)
    }

    private fun updateEmailSelected(selected: Boolean) {
        val email = EmailData.getEmailById(this.emailId)
        email.isSelected = selected
    }

    private val emailId: Long
        get() {
            var emailId = 0L
            if (getArguments() != null) {
                emailId = getArguments()!!.getLong(
                    EMAIL_ID_KEY,
                    0L
                )
            }
            return emailId
        }

    companion object {
        const val TAG: String = "AdaptiveListViewDetailDemoFragment"
        private const val EMAIL_ID_KEY = "email_id_key"

        @JvmStatic
        fun newInstance(emailId: Long): AdaptiveListViewDetailDemoFragment {
            val fragment = AdaptiveListViewDetailDemoFragment()
            val bundle = Bundle()
            bundle.putLong(EMAIL_ID_KEY, emailId)
            fragment.setArguments(bundle)
            return fragment
        }
    }
}

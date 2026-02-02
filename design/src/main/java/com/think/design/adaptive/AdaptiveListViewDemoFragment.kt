package com.think.design.adaptive

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialContainerTransform
import com.think.design.R
import com.think.design.adaptive.AdaptiveListViewDemoFragment.EmailAdapter.EmailAdapterListener
import com.think.design.adaptive.AdaptiveListViewDemoFragment.EmailAdapter.EmailViewHolder
import com.think.design.adaptive.AdaptiveListViewDetailDemoFragment.Companion.newInstance
import java.util.Arrays
import java.util.Locale

class AdaptiveListViewDemoFragment : Fragment() {
    @IdRes
    private var detailViewContainerId = 0
    private var emailList: RecyclerView? = null
    private var currentSelectedEmailId = -1L

    private val emailAdapterListener: EmailAdapterListener = object : EmailAdapterListener {
        override fun onEmailClicked(view: View, emailId: Long) {
            val fragment =
                newInstance(emailId)

            if (currentSelectedEmailId != -1L) {
                // Highlight selected email when fragments are side by side.
                setEmailSelected(currentSelectedEmailId, false)
                currentSelectedEmailId = emailId
                setEmailSelected(emailId, true)
            }

            // Create a shared element transition when an email item is opened.
            val enterTransform =
                MaterialContainerTransform(requireContext(),  /* entering= */true)
            fragment.setSharedElementEnterTransition(enterTransform)

            // Don't add back to stack if fragments are both visible side by side.
            if (detailViewContainerId == R.id.list_view_detail_fragment_container) {
                getParentFragmentManager()
                    .beginTransaction()
                    .addSharedElement(view, ViewCompat.getTransitionName(view)!!)
                    .replace(
                        detailViewContainerId,
                        fragment,
                        AdaptiveListViewDetailDemoFragment.TAG
                    )
                    .commit()
            } else {
                getParentFragmentManager()
                    .beginTransaction()
                    .addSharedElement(view, ViewCompat.getTransitionName(view)!!)
                    .replace(
                        detailViewContainerId,
                        fragment,
                        AdaptiveListViewDetailDemoFragment.TAG
                    )
                    .addToBackStack(AdaptiveListViewDetailDemoFragment.TAG)
                    .commit()
            }
        }
    }

    /**
     * Sets the id of the container that should hold the detail view fragment.
     *
     * @param detailViewContainerId the detail view fragment container id
     */
    fun setDetailViewContainerId(@IdRes detailViewContainerId: Int) {
        this.detailViewContainerId = detailViewContainerId
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.cat_adaptive_list_view_fragment, viewGroup, false)
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        // Set up email list's recycler view.
        emailList = view.findViewById<RecyclerView?>(R.id.email_list)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        emailList!!.setLayoutManager(layoutManager)
        val adapter = EmailAdapter(emailAdapterListener)
        adapter.setHasStableIds(true)
        emailList!!.setAdapter(adapter)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            currentSelectedEmailId = 0L
        }
    }

    /** Marks an email item as selected.  */
    private fun setEmailSelected(emailId: Long, selected: Boolean) {
        if (emailList == null) {
            return
        }
        val emailViewHolder = emailList!!.findViewHolderForItemId(emailId) as EmailViewHolder?
        if (emailViewHolder != null) {
            (emailViewHolder.container as MaterialCardView).setChecked(selected)
        }
        val email: EmailData.Email = EmailData.getEmailById(emailId)
        email.isSelected = selected
    }

    /** A RecyclerView adapter for the email list.  */
    internal class EmailAdapter(private val listener: EmailAdapterListener) :
        RecyclerView.Adapter<EmailViewHolder?>() {
        /** Listener for the email adapter.  */
        interface EmailAdapterListener {
            /**
             * Listens to when an email is clicked.
             *
             * @param view the email clicked.
             * @param emailId the email id.
             */
            fun onEmailClicked(view: View, emailId: Long)
        }

        /** Provides a reference to the views for each data item.  */
        internal class EmailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val container: View
            val senderTitle: TextView
            val emailTitle: TextView
            val emailPreview: TextView

            init {
                container = view.findViewById<View>(R.id.list_view_item_container)
                senderTitle = view.findViewById<TextView>(R.id.sender_title)
                emailTitle = view.findViewById<TextView>(R.id.email_title)
                emailPreview = view.findViewById<TextView>(R.id.email_preview)
            }
        }

        private fun updateEmailSelected(holder: EmailViewHolder) {
            // If on landscape orientation, update email to be selected or unselected.
            if (holder.container.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE
            ) {
                val email: EmailData.Email = EmailData.getEmailById(holder.getItemId())
                (holder.container as MaterialCardView).setChecked(email.isSelected)
            }
        }

        override fun getItemId(position: Int): Long {
            return EmailData.emailData.get(position).emailId
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
            val view =
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cat_adaptive_list_view_fragment_item, parent, false)
            return EmailViewHolder(view)
        }

        override fun onViewAttachedToWindow(holder: EmailViewHolder) {
            super.onViewAttachedToWindow(holder)
            updateEmailSelected(holder)
        }

        override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
            val resources = holder.senderTitle.getResources()
            val emailId = getItemId(holder.getBindingAdapterPosition())
            holder.container.setOnClickListener(
                object : View.OnClickListener {
                    override fun onClick(view: View) {
                        listener.onEmailClicked(view, emailId)
                    }
                })
            holder.senderTitle.setText(resources.getString(R.string.cat_list_view_sender_title))
            holder.emailTitle.setText(resources.getString(R.string.cat_list_view_email_title))
            holder.emailTitle.append(" " + (emailId + 1))
            holder.emailPreview.setText(resources.getString(R.string.cat_list_view_email_text))
            ViewCompat.setTransitionName(holder.container, holder.emailTitle.toString())
            updateEmailSelected(holder)
        }

        override fun getItemCount(): Int {
            return 10
        }
    }

    /** A simple email data class.  */
    internal object EmailData {
        val emailData: MutableList<Email> = Arrays.asList<Email>(
            Email(0L, true),
            Email(1L, false),
            Email(2L, false),
            Email(3L, false),
            Email(4L, false),
            Email(5L, false),
            Email(6L, false),
            Email(7L, false),
            Email(8L, false),
            Email(9L, false)
        )

        fun getEmailById(emailId: Long): Email {
            for (email in emailData) {
                if (email.emailId == emailId) {
                    return email
                }
            }
            throw IllegalArgumentException(
                String.format(Locale.US, "Email %d does not exist.", emailId)
            )
        }

        /** Returns whether the email is selected.  */
        /** Class that represents an email.  */
        internal class Email(
            /** Returns the email id.  */
            val emailId: Long,
            /** Sets the email to be selected or not.  */
            var isSelected: Boolean
        )
    }
}

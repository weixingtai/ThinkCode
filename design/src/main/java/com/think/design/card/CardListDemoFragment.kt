package com.think.design.card

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate
import com.google.android.material.card.MaterialCardView
import com.think.design.R
import com.think.design.feature.DemoFragment
import java.util.Locale

class CardListDemoFragment : DemoFragment() {
    override fun getDemoTitleResId(): Int {
        return R.string.cat_card_list
    }

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view =
            layoutInflater.inflate(
                R.layout.cat_card_list_fragment, viewGroup,  /* attachToRoot= */false
            )

        val recyclerView = view.findViewById<RecyclerView>(R.id.cat_card_list_recycler_view)

        val cardAdapter = CardAdapter(generateCardNumbers())
        val itemTouchHelper =
            ItemTouchHelper(CardItemTouchHelperCallback(cardAdapter))
        // Provide an ItemTouchHelper to the Adapter; can't use constructor due to circular dependency.
        cardAdapter.setItemTouchHelper(itemTouchHelper)
        recyclerView.setLayoutManager(LinearLayoutManager(getActivity()))
        recyclerView.setAdapter(cardAdapter)
        recyclerView
            .setAccessibilityDelegateCompat(object :
                RecyclerViewAccessibilityDelegate(recyclerView) {
                override fun getItemDelegate(): AccessibilityDelegateCompat {
                    return object : ItemDelegate(this) {
                        override fun onInitializeAccessibilityNodeInfo(
                            host: View,
                            info: AccessibilityNodeInfoCompat
                        ) {
                            super.onInitializeAccessibilityNodeInfo(host, info)
                            val position = recyclerView.getChildLayoutPosition(host)
                            if (position != 0) {
                                info.addAction(
                                    AccessibilityActionCompat(
                                        R.id.move_card_up_action,
                                        host.getResources()
                                            .getString(R.string.cat_card_action_move_up)
                                    )
                                )
                            }
                            if (position != (CARD_COUNT - 1)) {
                                info.addAction(
                                    AccessibilityActionCompat(
                                        R.id.move_card_down_action,
                                        host.getResources()
                                            .getString(R.string.cat_card_action_move_down)
                                    )
                                )
                            }
                        }

                        override fun performAccessibilityAction(
                            host: View,
                            action: Int,
                            args: Bundle?
                        ): Boolean {
                            val fromPosition = recyclerView.getChildLayoutPosition(host)
                            if (action == R.id.move_card_down_action) {
                                cardAdapter.swapCards(fromPosition, fromPosition + 1)
                                return true
                            } else if (action == R.id.move_card_up_action) {
                                cardAdapter.swapCards(fromPosition, fromPosition - 1)
                                return true
                            }

                            return super.performAccessibilityAction(host, action, args)
                        }
                    }
                }
            })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view
    }

    private class CardAdapter(private val cardNumbers: IntArray) :
        RecyclerView.Adapter<RecyclerView.ViewHolder?>(), OnKeyboardDragListener {
        private var draggedViewHolder: RecyclerView.ViewHolder? = null

        private var itemTouchHelper: ItemTouchHelper? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.getContext())
            val view =
                inflater.inflate(R.layout.cat_card_list_item, parent,  /* attachToRoot= */false)
            return CardViewHolder(view, this)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (viewHolder as CardViewHolder).bind(cardNumbers[position])
        }

        override fun getItemCount(): Int {
            return cardNumbers.size
        }

        fun setItemTouchHelper(itemTouchHelper: ItemTouchHelper) {
            this.itemTouchHelper = itemTouchHelper
        }

        fun swapCards(fromPosition: Int, toPosition: Int) {
            if (fromPosition < 0 || fromPosition >= cardNumbers.size || toPosition < 0 || toPosition >= cardNumbers.size) {
                return
            }

            val fromNumber = cardNumbers[fromPosition]
            cardNumbers[fromPosition] = cardNumbers[toPosition]
            cardNumbers[toPosition] = fromNumber
            notifyItemMoved(fromPosition, toPosition)
        }

        fun cancelDrag() {
            if (draggedViewHolder != null) {
                (draggedViewHolder!!.itemView as MaterialCardView).setDragged(false)
                draggedViewHolder = null
            }
        }

        override fun onDragStarted(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper!!.startDrag(viewHolder)
        }

        override fun onKeyboardDragToggle(viewHolder: RecyclerView.ViewHolder) {
            val isCurrentlyDragged = draggedViewHolder === viewHolder
            cancelDrag()
            if (!isCurrentlyDragged) {
                draggedViewHolder = viewHolder
                (viewHolder.itemView as MaterialCardView).setDragged(true)
            }
        }

        override fun onKeyboardMoved(keyCode: Int): Boolean {
            if (draggedViewHolder == null) {
                return false
            }

            val fromPosition = draggedViewHolder!!.getBindingAdapterPosition()
            if (fromPosition == RecyclerView.NO_POSITION) {
                return false
            }

            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_UP -> {
                    swapCards(fromPosition, fromPosition - 1)
                    return true
                }

                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    swapCards(fromPosition, fromPosition + 1)
                    return true
                }

                else -> return false
            }
        }

        private class CardViewHolder(itemView: View, listener: OnKeyboardDragListener) :
            RecyclerView.ViewHolder(itemView) {
            private val titleView: TextView

            init {
                val cardView = itemView as MaterialCardView
                cardView.setFocusable(true)
                cardView.setOnKeyListener(
                    View.OnKeyListener { v: View?, keyCode: Int, event: KeyEvent? ->
                        if (event!!.getAction() != KeyEvent.ACTION_DOWN) {
                            return@OnKeyListener false
                        }
                        when (keyCode) {
                            KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_DPAD_CENTER -> {
                                listener.onKeyboardDragToggle(this)
                                return@OnKeyListener true
                            }

                            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_DOWN -> return@OnKeyListener listener.onKeyboardMoved(
                                keyCode
                            )

                            else -> return@OnKeyListener false
                        }
                    })

                val dragHandleView =
                    itemView.findViewById<View>(R.id.cat_card_list_item_drag_handle)
                dragHandleView.setOnTouchListener(
                    OnTouchListener { v: View?, event: MotionEvent? ->
                        when (event!!.getAction()) {
                            MotionEvent.ACTION_DOWN -> {
                                listener.onDragStarted(this)
                                return@OnTouchListener true
                            }

                            MotionEvent.ACTION_UP -> v!!.performClick()
                            else -> {}
                        }
                        false
                    })

                titleView = itemView.findViewById<TextView>(R.id.cat_card_list_item_title)
            }

            fun bind(cardNumber: Int) {
                titleView.setText(String.format(Locale.getDefault(), "Card #%02d", cardNumber))
            }
        }
    }

    private class CardItemTouchHelperCallback(private val cardAdapter: CardAdapter) :
        ItemTouchHelper.Callback() {
        private var dragCardView: MaterialCardView? = null

        override fun getMovementFlags(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(DRAG_FLAGS, SWIPE_FLAGS)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.getBindingAdapterPosition()
            val toPosition = target.getBindingAdapterPosition()
            cardAdapter.swapCards(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)

            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
                cardAdapter.cancelDrag()
                dragCardView = viewHolder.itemView as MaterialCardView
                dragCardView!!.setDragged(true)
            } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && dragCardView != null) {
                dragCardView!!.setDragged(false)
                dragCardView = null
            }
        }

        companion object {
            private val DRAG_FLAGS = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            private const val SWIPE_FLAGS = 0
        }
    }

    private interface OnKeyboardDragListener {
        fun onDragStarted(viewHolder: RecyclerView.ViewHolder)

        fun onKeyboardDragToggle(viewHolder: RecyclerView.ViewHolder)

        fun onKeyboardMoved(keyCode: Int): Boolean
    }

    companion object {
        private const val CARD_COUNT = 30

        private fun generateCardNumbers(): IntArray {
            val cardNumbers = IntArray(CARD_COUNT)
            for (i in 0..<CARD_COUNT) {
                cardNumbers[i] = i + 1
            }
            return cardNumbers
        }
    }
}

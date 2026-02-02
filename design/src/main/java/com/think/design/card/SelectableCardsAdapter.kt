package com.think.design.card

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityViewCommand
import androidx.core.view.accessibility.AccessibilityViewCommand.CommandArguments
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.think.design.R

internal class SelectableCardsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var items: MutableList<Item>

    private var selectionTracker: SelectionTracker<Long?>? = null

    init {
        this.items = ArrayList<Item>()
    }

    fun setItems(items: MutableList<Item>) {
        this.items = items
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    fun setSelectionTracker(selectionTracker: SelectionTracker<Long?>?) {
        this.selectionTracker = selectionTracker
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.getContext())
        val view = inflater.inflate(R.layout.cat_card_item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = items.get(position)
        (viewHolder as ItemViewHolder).bind(item, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val details: Details
        private val materialCardView: MaterialCardView
        private val titleView: TextView
        private val subtitleView: TextView

        init {
            materialCardView = itemView.findViewById<MaterialCardView>(R.id.item_card)
            titleView = itemView.findViewById<TextView>(R.id.cat_card_title)
            subtitleView = itemView.findViewById<TextView>(R.id.cat_card_subtitle)
            details = Details()
        }

        fun bind(item: Item, position: Int) {
            details.position = position.toLong()
            titleView.setText(item.title)
            subtitleView.setText(item.subtitle)
            if (selectionTracker != null) {
                bindSelectedState()
                materialCardView.setOnKeyListener(
                    View.OnKeyListener { v: View?, keyCode: Int, event: KeyEvent? ->
                        if (event!!.getAction() == KeyEvent.ACTION_DOWN
                            && (keyCode == KeyEvent.KEYCODE_ENTER
                                    || keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
                        ) {
                            val selectionKey = details.getSelectionKey()
                            if (selectionKey != null) {
                                if (selectionTracker!!.isSelected(selectionKey)) {
                                    selectionTracker!!.deselect(selectionKey)
                                } else {
                                    selectionTracker!!.select(selectionKey)
                                }
                                return@OnKeyListener true
                            }
                        }
                        false
                    })
            } else {
                materialCardView.setOnKeyListener(null)
            }
        }

        private fun bindSelectedState() {
            val selectionKey = details.getSelectionKey()
            materialCardView.setChecked(selectionTracker!!.isSelected(selectionKey))
            if (selectionKey != null) {
                addAccessibilityActions(selectionKey)
            }
        }

        private fun addAccessibilityActions(selectionKey: Long) {
            ViewCompat.addAccessibilityAction(
                materialCardView,
                materialCardView.getContext().getString(R.string.cat_card_action_select),
                AccessibilityViewCommand { view: View?, arguments: CommandArguments? ->
                    selectionTracker!!.select(selectionKey)
                    true
                })
            ViewCompat.addAccessibilityAction(
                materialCardView,
                materialCardView.getContext().getString(R.string.cat_card_action_deselect),
                AccessibilityViewCommand { view: View?, arguments: CommandArguments? ->
                    selectionTracker!!.deselect(selectionKey)
                    true
                })
        }

        val itemDetails: ItemDetails<Long?>
            get() = details
    }

    internal class DetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long?>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long?>? {
            val view = recyclerView.findChildViewUnder(e.getX(), e.getY())
            if (view != null) {
                val viewHolder = recyclerView.getChildViewHolder(view)
                if (viewHolder is ItemViewHolder) {
                    return viewHolder.itemDetails
                }
            }
            return null
        }
    }

    internal class KeyProvider(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder?>?) :
        ItemKeyProvider<Long?>(
            SCOPE_MAPPED
        ) {
        override fun getKey(position: Int): Long? {
            return position.toLong()
        }

        override fun getPosition(key: Long): Int {
            val value = key
            return value.toInt()
        }
    }

    internal class Item(val title: String?, val subtitle: String?)

    internal class Details : ItemDetails<Long?>() {
        var position: Long = 0

        override fun getPosition(): Int {
            return position.toInt()
        }

        override fun getSelectionKey(): Long? {
            return position
        }

        override fun inSelectionHotspot(e: MotionEvent): Boolean {
            return false
        }

        override fun inDragRegion(e: MotionEvent): Boolean {
            return true
        }
    }
}

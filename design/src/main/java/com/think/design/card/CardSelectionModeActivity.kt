package com.think.design.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.think.design.R
import com.think.design.card.SelectableCardsAdapter.DetailsLookup
import com.think.design.feature.DemoActivity

class CardSelectionModeActivity : DemoActivity(), ActionMode.Callback {
    private var actionMode: ActionMode? = null
    private var adapter: SelectableCardsAdapter? = null
    private var selectionTracker: SelectionTracker<Long?>? = null

    override fun onCreateDemoView(
        layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?
    ): View {
        val view = layoutInflater.inflate(R.layout.cat_card_selection_activity, viewGroup, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        setUpRecyclerView(recyclerView)

        return view
    }

    protected fun setUpRecyclerView(recyclerView: RecyclerView) {
        adapter = SelectableCardsAdapter()
        adapter!!.setItems(generateItems())
        recyclerView.setAdapter(adapter)

        selectionTracker =
            SelectionTracker.Builder<Long?>(
                "card_selection",
                recyclerView,
                SelectableCardsAdapter.KeyProvider(adapter),
                DetailsLookup(recyclerView),
                StorageStrategy.createLongStorage()
            )
                .withSelectionPredicate(SelectionPredicates.createSelectAnything<Long?>())
                .build()

        adapter!!.setSelectionTracker(selectionTracker)
        selectionTracker!!.addObserver(
            object : SelectionTracker.SelectionObserver<Long?>() {
                override fun onSelectionChanged() {
                    if (selectionTracker!!.getSelection().size() > 0) {
                        if (actionMode == null) {
                            actionMode = startSupportActionMode(this@CardSelectionModeActivity)
                        }
                        actionMode!!.setTitle(selectionTracker!!.getSelection().size().toString())
                    } else if (actionMode != null) {
                        actionMode!!.finish()
                    }
                }
            })
        recyclerView.setLayoutManager(LinearLayoutManager(this))
    }

    private fun generateItems(): MutableList<SelectableCardsAdapter.Item> {
        val titlePrefix = getString(R.string.cat_card_selectable_item_title)
        val items: MutableList<SelectableCardsAdapter.Item> =
            ArrayList<SelectableCardsAdapter.Item>()
        for (i in 0..<ITEM_COUNT) {
            items.add(
                SelectableCardsAdapter.Item(
                    titlePrefix + " " + (i + 1),
                    getString(R.string.cat_card_selectable_content)
                )
            )
        }

        return items
    }

    override fun getDemoTitleResId(): Int {
        return R.string.cat_card_selection_mode
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menuItem: MenuItem?): Boolean {
        return false
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        selectionTracker!!.clearSelection()
        this.actionMode = null
    }

    companion object {
        private const val ITEM_COUNT = 20
    }
}

package com.zj.notes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.zj.model.NoteInfo
import com.zj.model.NotesDatabase
import com.zj.notes.databinding.ActivityMainBinding

class MainActivity : FragmentActivity(), NotesListAdapter.NotesItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesListAdapter: NotesListAdapter
    private val viewModel by viewModels<MainViewModel>()
    private var num = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(
            this,
            TwoPaneOnBackPressedCallback(binding.slidingPaneLayout)
        )
        viewModel.notesList.observe(this) {
            notesListAdapter = NotesListAdapter(it)
            binding.listPane.adapter = notesListAdapter
            notesListAdapter.setOnClickItemListener(this)
        }
        binding.slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        binding.imageView.setOnClickListener {
            viewModel.insertNoteInfo(
                NoteInfo(
                    title = "${num++} Title",
                    data = "${num++} Title Data"
                )
            )
        }
    }

    /**
     * A method on the Fragment that owns the SlidingPaneLayout,
     * called by the adapter when an item is selected.
     */
    private fun openDetails(id: Int) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.detail_container,
                EditNotesFragment.newInstance(id)
            )
            // If we're already open and the detail pane is visible,
            // crossFade between the fragments.
            if (binding.slidingPaneLayout.isOpen) {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            }
        }
        binding.slidingPaneLayout.open()
    }

    override fun onItemClick(id: Int) {
        openDetails(id)
    }

}
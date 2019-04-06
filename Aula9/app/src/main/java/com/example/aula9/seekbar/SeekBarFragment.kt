package com.example.aula9.seekbar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.aula9.R
import com.example.aula9.SeekBarViewModel
import kotlinx.android.synthetic.main.fragment_seek_bar.*

/**
 * A simple [Fragment] subclass.
 *
 */
class SeekBarFragment : Fragment() {

    private val viewModel by lazy {
        activity?.let {
            getViewModel<SeekBarViewModel>()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seek_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeSeekbar()
    }

    private fun subscribeSeekbar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel?.seekBarValue?.value = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        viewModel?.seekBarValue?.observe(this, Observer {
            seekBar.progress = it
        })
    }
}

inline fun <reified T : ViewModel?> Fragment.getViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}
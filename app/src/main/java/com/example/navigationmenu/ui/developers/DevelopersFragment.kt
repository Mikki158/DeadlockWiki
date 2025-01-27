package com.example.navigationmenu.ui.developers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.navigationmenu.databinding.FragmentDevelopersBinding

class DevelopersFragment : Fragment() {

    private var _binding: FragmentDevelopersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val aboutViewModel =
            ViewModelProvider(this).get(DevelopersViewModel::class.java)

        _binding = FragmentDevelopersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDevelopers
        aboutViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.apisamplecode.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.apisamplecode.databinding.FragmentTopBinding

class TopFragment : Fragment() {
    private var _binding: FragmentTopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            val keyword = binding.searchBox.text.toString()
            if (keyword.isEmpty()) errorToast() else navigateToSearchResult(keyword)
        }

        binding.searchBox.setOnEditorActionListener { v, keyCode, _ ->
            // エンターキーでも検索できるように
            if (keyCode == 0) {
                val keyword = v.text.toString()
                if (keyword.isEmpty()) errorToast() else navigateToSearchResult(keyword)

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun errorToast() {
        Toast.makeText(requireContext(),"キーワードが入力されていません", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToSearchResult(keyword: String) {
        val action = TopFragmentDirections.actionTopFragmentToProductListFragment(keyword)
        findNavController().navigate(action)
        binding.searchBox.text?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
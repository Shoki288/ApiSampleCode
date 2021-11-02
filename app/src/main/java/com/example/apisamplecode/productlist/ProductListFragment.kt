package com.example.apisamplecode.productlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apisamplecode.*
import com.example.apisamplecode.databinding.FragmentProductListBinding
import com.example.apisamplecode.productlist.viewmodels.Article
import com.example.apisamplecode.productlist.viewmodels.ArticleListRepository
import com.example.apisamplecode.vo.Status
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ProductListFragment : Fragment(), ArticleListItemAdapter.ItemListener {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val args: ProductListFragmentArgs by navArgs()

    private val viewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory(ArticleListRepository())
    }

    private var start: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("hogehoge", args.searchWord)
        viewModel.searchRequest(args.searchWord)
        start = System.currentTimeMillis()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            when(it.state) {
                Status.SUCCESS -> setAdapter(it.data)
                Status.ERROR -> EmptyAdapter("エラー")
                else -> { /*  nop  */ }
            }
        }
    }

    private fun setAdapter(articles: List<Article>?) {
        binding.articleList.let {
            it.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            it.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = GroupAdapter<GroupieViewHolder>().apply {
                if (articles.isNullOrEmpty()) {
                    EmptyAdapter("検索結果にヒットしませんでした")
                } else {
                    val list = mutableListOf<Group>()
                    articles.forEach { item -> list.add(ArticleListItemAdapter(item, this@ProductListFragment)) }
//                    (1 until articles.size).map { item ->
//                        list.add(ArticleListItemAdapter(articles[item], this@ProductListFragment))
//                    }
                    addAll(list)
                    Log.d("hogehoge", (System.currentTimeMillis() - start).toString())
                }
            }
            it.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("hogehoge", "list onDestroy")
    }

    override fun onClickItem(pos: Int) {
        findNavController().navigate(
            ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(pos)
        )
    }
}
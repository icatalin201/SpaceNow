package space.pal.sig.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import space.pal.sig.R
import space.pal.sig.databinding.FragmentNewsBinding
import space.pal.sig.model.entity.News
import space.pal.sig.view.BaseFragment

/**
 * SpaceNow
 * Created by Catalin on 12/21/2020
 **/
class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by inject()
    private val adapter = NewsAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news,
                container, false)
        viewModel.getNewsList()
                .observe(viewLifecycleOwner) { showNewsList(it) }
        binding.newsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRecycler.adapter = adapter
        return binding.root
    }

    private fun showNewsList(newsList: MutableList<News>) {
        adapter.submit(newsList)
    }

}
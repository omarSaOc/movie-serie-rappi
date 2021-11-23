package com.oaso.movie_series_rappi.ui.popular

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.FragmentPopularBinding
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.ui.common.startActivity
import com.oaso.movie_series_rappi.ui.common.transformToNav
import com.oaso.movie_series_rappi.ui.popular_detail.DetailPopularMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class PopularFragment : Fragment() {
    private val viewModel: PopularViewModel by viewModels()
    private var _binding: FragmentPopularBinding? = null
    private lateinit var adapter: PopularMovieAdapter
    private var filterMovies = ArrayList<PopularMovie>()
    private var movies = ArrayList<PopularMovie>()
    private var sharedPref: SharedPreferences? = null
    private var currentPage: Int = 2

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.CURRENT_PAGE_POPULAR),
            Context.MODE_PRIVATE
        )
        currentPage = sharedPref?.getInt(getString(R.string.CURRENT_PAGE_POPULAR), 2) ?: 2

        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter = PopularMovieAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter
        binding.recycler.itemAnimator

        binding.swipe.setOnRefreshListener {
            viewModel.loadMorePopularMovies(currentPage + 1)
            with(sharedPref?.edit()) {
                this?.putInt(getString(R.string.CURRENT_PAGE_POPULAR), currentPage + 1)
                this?.apply()
            }
        }

        binding.searchPopularMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMovies = movies.filter { popularMovie ->
                    popularMovie.title.lowercase().contains(newText.toString().lowercase())
                } as ArrayList<PopularMovie>
                adapter.popularMovies = filterMovies
                return true
            }
        })

        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.navigation.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                context?.startActivity<DetailPopularMovieActivity> {
                    putExtra(DetailPopularMovieActivity.MOVIE, it.transformToNav())
                }
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(model: PopularViewModel.UiModel) {
        binding.progress.visibility =
            if (model is PopularViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is PopularViewModel.UiModel.Content -> {
                binding.progress.visibility = View.GONE
                movies = model.popularMovies as ArrayList<PopularMovie>
                adapter.popularMovies = movies
            }
            is PopularViewModel.UiModel.LoadMoreMovies -> {
                binding.swipe.isRefreshing = false
                binding.progress.visibility = View.GONE
                movies = model.popularMovies as ArrayList<PopularMovie>
                adapter.popularMovies = movies
            }
            else -> {}
        }
    }
}
package com.oaso.movie_series_rappi.ui.top_rated

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.R
import com.oaso.movie_series_rappi.databinding.FragmentTopRatedBinding
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie
import com.oaso.movie_series_rappi.ui.common.startActivity
import com.oaso.movie_series_rappi.ui.common.transformToNav
import com.oaso.movie_series_rappi.ui.top_rated_detail.DetailTopRatedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment() {

    private val viewModel: TopRatedViewModel by viewModels()
    private var _binding: FragmentTopRatedBinding? = null
    private lateinit var adapter: RatedMovieAdapter
    private var movies = ArrayList<RatedMovie>()
    private var filterMovies = ArrayList<RatedMovie>()
    private var sharedPref: SharedPreferences? = null
    private var currentPage: Int = 2
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sharedPref = activity?.getSharedPreferences(
            getString(R.string.CURRENT_PAGE_RATED),
            Context.MODE_PRIVATE
        )
        currentPage = sharedPref?.getInt(getString(R.string.CURRENT_PAGE_RATED), 2) ?: 2

        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter = RatedMovieAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter

        binding.swipe.setOnRefreshListener {
            viewModel.loadMoreRatedMovies(currentPage + 1)
            with(sharedPref?.edit()) {
                this?.putInt(getString(R.string.CURRENT_PAGE_RATED), currentPage + 1)
                this?.apply()
            }
        }

        binding.searchTopRatedMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMovies = movies.filter { ratedMovie ->
                    ratedMovie.title.lowercase().contains(newText.toString().lowercase())
                } as ArrayList<RatedMovie>
                adapter.ratedMovies = filterMovies
                return true
            }

        })

        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.navigation.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                context?.startActivity<DetailTopRatedActivity> {
                    putExtra(DetailTopRatedActivity.MOVIE, it.transformToNav())
                }
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(model: TopRatedViewModel.UiModel) {
        binding.progress.visibility =
            if (model is TopRatedViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is TopRatedViewModel.UiModel.Content -> {
                binding.progress.visibility = View.GONE
                movies = model.ratedMovies as ArrayList<RatedMovie>
                adapter.ratedMovies = movies
            }
            is TopRatedViewModel.UiModel.LoadMoreMovies -> {
                binding.swipe.isRefreshing = false
                binding.progress.visibility = View.GONE
                movies = model.ratedMovies as ArrayList<RatedMovie>
                adapter.ratedMovies = movies
            }
            else -> {}
        }
    }
}
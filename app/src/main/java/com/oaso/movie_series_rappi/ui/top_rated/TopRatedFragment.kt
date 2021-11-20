package com.oaso.movie_series_rappi.ui.top_rated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.oaso.movie_series_rappi.databinding.FragmentTopRatedBinding
import com.oaso.movie_series_rappi.ui.common.startActivity
import com.oaso.movie_series_rappi.ui.popular_detail.DetailPopularMovieActivity
import com.oaso.movie_series_rappi.ui.top_rated_detail.DetailTopRatedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment() {

    private val viewModel: TopRatedViewModel by viewModels()
    private var _binding: FragmentTopRatedBinding? = null
    private lateinit var adapter : RatedMovieAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter = RatedMovieAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter

        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.navigation.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                context?.startActivity<DetailTopRatedActivity> {
                    putExtra(DetailTopRatedActivity.MOVIE, it)
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
        binding.progress.visibility = if (model is TopRatedViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when (model) {
            is TopRatedViewModel.UiModel.Content -> {
                binding.progress.visibility = View.GONE
                adapter.ratedMovies = model.ratedMovies
            }
        }
    }
}
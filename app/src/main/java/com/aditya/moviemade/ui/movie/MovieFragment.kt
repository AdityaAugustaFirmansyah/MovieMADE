package com.aditya.moviemade.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.core.domain.model.Movie
import com.aditya.core.utils.SortUtils
import com.aditya.core.vo.Resource
import com.aditya.core.vo.Status
import com.aditya.moviemade.R
import com.aditya.moviemade.databinding.FragmentMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getData(SortUtils.DEFAULT).observe(viewLifecycleOwner,observer)

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_ascending){
            viewModel.getData(SortUtils.ASCENDING).observe(viewLifecycleOwner,observer)
        }else if (item.itemId == R.id.menu_descending){
            viewModel.getData(SortUtils.DESCENDING).observe(viewLifecycleOwner,observer)
        }
        return true
    }

    companion object{
        fun showError(binding: FragmentMovieBinding,visibility:Int,message:String){
            binding.emptyMovie.visibility = visibility
            binding.tvError.visibility = visibility
            binding.tvError.text = message
        }
    }

    private val observer = Observer<Resource<List<Movie>>> {
        if (it.status== Status.LOADING){

            binding.progressBar.visibility = View.VISIBLE
            showError(binding,View.GONE,"")
            binding.recyclerView.visibility = View.GONE

        }else if (it.status == Status.SUCCESS){

            binding.progressBar.visibility = View.GONE

            binding.recyclerView.apply {
                it.data?.let { it1 ->
                    this.adapter = MovieAdapter(it1)
                }
            }

            if (it.message?.isNotEmpty() == true) {
                binding.emptyMovie.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                showError(binding,View.VISIBLE,getString(R.string.empty_list))
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                showError(binding,View.GONE,"")
            }

        }else if (it.status == Status.FAILURE){

            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE

            it.message?.let { it1 -> showError(binding,View.VISIBLE, it1) }
        }
    }
}
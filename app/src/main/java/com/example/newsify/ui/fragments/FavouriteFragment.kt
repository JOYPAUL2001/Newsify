package com.example.newsify.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsify.R
import com.example.newsify.adapters.NewsAdapters
import com.example.newsify.databinding.FragmentFavouriteBinding
import com.example.newsify.ui.NewsActivity
import com.example.newsify.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapters: NewsAdapters
    lateinit var binding: FragmentFavouriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteBinding.bind(view)



        newsViewModel = (activity as NewsActivity).newsViewModel
        setUpFavouritesRecycler()

        newsAdapters.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_favouriteFragment_to_articleFragment,bundle)
        }

        val itemTouchHelperCallBack = object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapters.differ.currentList[position]
                newsViewModel.deleteArticles(article)
                Snackbar.make(view,"Removed from favourites!",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        newsViewModel.addToFavourites(article)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.recyclerFavourites)
        }

        newsViewModel.getFavouriteNews().observe(viewLifecycleOwner, Observer {article ->
            newsAdapters.differ.submitList(article)
        })

    }

    private fun setUpFavouritesRecycler(){
        newsAdapters = NewsAdapters()
        binding.recyclerFavourites.apply {
            adapter = newsAdapters
            layoutManager = LinearLayoutManager(activity)
        }
    }

}
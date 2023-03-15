package com.example.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.fakeentity.FavDomainEntity
import com.example.weather.R
import com.example.weather.adapters.FavAdapter
import com.example.weather.databinding.FragmentFavBinding
import com.example.weather.databinding.FragmentTestBinding
import com.example.weather.helpers.Consts
import com.example.weather.helpers.Navigator
import com.example.weather.viewmodels.FavViewModel
import com.example.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavFragment : Fragment(),FavInterface {
    lateinit var binding: FragmentFavBinding
    private val viewModel:FavViewModel by viewModels()
    lateinit var  favAdapter: FavAdapter

    private val viewModel2:WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Navigator.hasDetails=""
        viewModel.getFavList()
        lifecycleScope.launch {
            viewModel.favList.collect{
                favAdapter= FavAdapter(requireContext(),this@FavFragment)
                favAdapter.submitList(it)
                binding.favRecyclerView.apply {
                    adapter=favAdapter
                    layoutManager= LinearLayoutManager(context).apply {
                        orientation= RecyclerView.VERTICAL
                    }
                }
            }
        }
        Navigator.hasEntered=""
        if(Navigator.myArrival==Consts.FROM_MAP_TO_FAV){
            Navigator.myArrival=""
                viewModel2.getWeather(FavFragmentArgs.fromBundle(requireArguments()).flat.toDouble(),
                    FavFragmentArgs.fromBundle(requireArguments()).flon.toDouble(),"en",Consts.API_KEY)
                lifecycleScope.launch{
                    viewModel2.weather.collect{
                        if(it?.name!=null){
                            viewModel.addFavTODB(FavDomainEntity(it?.name!!,FavFragmentArgs.fromBundle(requireArguments()).flat.toDouble()
                            ,FavFragmentArgs.fromBundle(requireArguments()).flon.toDouble()))
                            Toast.makeText(requireContext(),"added",Toast.LENGTH_LONG).show()
                            viewModel.getFavList()
                            viewModel.favList.collect{
                                favAdapter.submitList(it)
                            }
                        }
                    }
                }

        }

        binding.addFavFloatingActionButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                Navigator.moveFromFavToMap()
                val action=FavFragmentDirections.actionFavFragmentToMapFragment()
                this@FavFragment.findNavController().navigate(action)
            }

        })
    }

    override fun delete(fav: FavDomainEntity) {

        lifecycleScope.launch {
            viewModel.delFavFromDB(fav)
            viewModel.getFavList()
            viewModel.favList.collect{
                favAdapter.submitList(it)
            }
        }
    }

    override fun goToDetails(fav: FavDomainEntity) {
        if(Navigator.hasDetails=="on"){
        val action = FavFragmentDirections.actionFavFragmentToFavDetailsFragment(fav.lat.toFloat(),
           fav.lon.toFloat())
        this.findNavController().navigate(action)}
    }
}
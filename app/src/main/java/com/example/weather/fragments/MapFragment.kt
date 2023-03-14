package com.example.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.helpers.Consts
import com.example.weather.helpers.Navigator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


class MapFragment() : Fragment(),OnMapReadyCallback {
    private lateinit var googleMap:GoogleMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)



    }
    fun init(view: View){
        mapView=view.findViewById(R.id.mapView)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap=map
        googleMap.setOnMapLongClickListener(object: GoogleMap.OnMapLongClickListener{
            override fun onMapLongClick(p0: LatLng) {
                if(Navigator.dir==Consts.DIRECTION_SET){
                Navigator.dir=""
                Navigator.fromMapToTest()
                val action= MapFragmentDirections.actionMapFragmentToTestFragment(p0.latitude.toFloat(),p0.longitude.toFloat())
                action.arguments.putFloat("lat",p0.latitude.toFloat())
                action.arguments.putFloat("lon",p0.longitude.toFloat())
                this@MapFragment.findNavController().navigate(action)
            }else{
                    Navigator.dir=""
                    Navigator.fromMapToFav()
                    val action= MapFragmentDirections.actionMapFragmentToFavFragment(p0.latitude.toFloat(),p0.longitude.toFloat())
                    action.arguments.putFloat("flat",p0.latitude.toFloat())
                    action.arguments.putFloat("flon",p0.longitude.toFloat())
                    this@MapFragment.findNavController().navigate(action)
            }}
        })
    }


}
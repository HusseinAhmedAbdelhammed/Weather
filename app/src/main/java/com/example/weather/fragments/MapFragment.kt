package com.example.weather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng


class MapFragment : Fragment(),OnMapReadyCallback {
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
        mapView.setOnClickListener(object : OnMapClickListener, View.OnClickListener {
            override fun onMapClick(lat: LatLng) {
                //Navigate Here
            }

            override fun onClick(p0: View?) {

            }
        })

    }
    fun init(view: View){
        mapView=view.findViewById(R.id.mapView)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap=map
    }


}
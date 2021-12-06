package com.example.myproject.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings

import android.util.Log
import android.widget.Toast

import androidx.core.app.ActivityCompat

import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException
import java.security.Permission

class MapLocation : AppCompatActivity(), OnMapReadyCallback ,GoogleMap.OnMarkerClickListener{
   private lateinit var currentLocation: Location

 private lateinit var mMap : GoogleMap
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var  mFusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    // 1
    private lateinit var locationCallback: LocationCallback
    // 2
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        // 3
        private const val REQUEST_CHECK_SETTINGS = 2
     //   private const val PLACE_PICKER_REQUEST = 3

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(com.example.myproject.R.layout.activity_map_location)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(com.example.myproject.R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
   //     fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()


    }

    override fun onMapReady(googleMap: GoogleMap) {

//       val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
 //       val markerOptions = MarkerOptions().position(latLng).title("I am here!")
 //       googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
//        googleMap.addMarker(MarkerOptions()
//               .position(latLng).title("I am here!")
//        )

 //      val raj = LatLng(26.9430, 75.8410)
//       googleMap.addMarker(
//            MarkerOptions()
 //               .position(raj)
 //               .title("Marker in Rajasthan")
//        )
  //      googleMap.moveCamera(CameraUpdateFactory.newLatLng(raj))
        mMap = googleMap
        getLastLocation()
  //      map = googleMap
 //       map.uiSettings.isZoomControlsEnabled = true
 //       map.setOnMarkerClickListener(this)

   //     setUpMap()

    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(currentLatLng))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16F))
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    // Get current location, if shifted
    // from previous location
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    // If current location could not be located, use last location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
             var currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    // function to check if GPS is on
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // Request permissions if not granted before
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CHECK_SETTINGS
        )
    }

    // What must happen when permission is granted
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }
    /*   private fun setUpMap() {
           if (ActivityCompat.checkSelfPermission(
                   this,
                   Manifest.permission.ACCESS_FINE_LOCATION
               ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                   this,
                   Manifest.permission.ACCESS_COARSE_LOCATION
               ) != PackageManager.PERMISSION_GRANTED
           ) {
               // TODO: Consider calling
               //    ActivityCompat#requestPermissions
               // here to request the missing permissions, and then overriding
               //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
               //                                          int[] grantResults)
               // to handle the case where the user grants the permission. See the documentation
               // for ActivityCompat#requestPermissions for more details.
               return
           }
           map.isMyLocationEnabled = true

           fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
               // Got last known location. In some rare situations this can be null.
               if (location != null) {
                   lastLocation = location
                   val currentLatLng = LatLng(location.latitude, location.longitude)
                   placeMarkerOnMap(currentLatLng)
                   map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
               }
           }
       }
       private fun placeMarkerOnMap(location: LatLng) {
           val markerOptions = MarkerOptions().position(location)
           val titleStr = getAddress(location)  // add these two lines
           markerOptions.title(titleStr)
          map.addMarker(markerOptions)
       }

    //   private fun placeMarkerOnMap(currentLatLng: LatLng) {
     //      val markerOptions = MarkerOptions().position(currentLatLng)
           // 2
    //       map.addMarker(markerOptions)

    //  }

       override fun onMarkerClick(p0: Marker): Boolean = false
       private fun getAddress(latLng: LatLng): String {
           // 1
           val geocoder = Geocoder(this)
           val addresses: List<Address>?
           val address: Address?
           var addressText = ""

           try {
               // 2
               addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
               // 3
               if (null != addresses && addresses.isNotEmpty()) {
                   address = addresses[0]
                   for (i in 0 until address.maxAddressLineIndex) {
                       addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                   }
               }
           } catch (e: IOException) {
               Log.e("MapsActivity", e.localizedMessage)
           }

           return addressText
       }
       private fun startLocationUpdates() {
           //1
           if (ActivityCompat.checkSelfPermission(this,
                   Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions(this,
                   arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                   LOCATION_PERMISSION_REQUEST_CODE)
               return
           }
           //2
           fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)

       }
       private fun createLocationRequest() {
           // 1
           locationRequest = LocationRequest()
           // 2
           locationRequest.interval = 10000
           // 3
           locationRequest.fastestInterval = 5000
           locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

           val builder = LocationSettingsRequest.Builder()
               .addLocationRequest(locationRequest)

           // 4
           val client = LocationServices.getSettingsClient(this)
           val task = client.checkLocationSettings(builder.build())

           // 5
           task.addOnSuccessListener {
               locationUpdateState = true
               startLocationUpdates()
           }
           task.addOnFailureListener { e ->
               // 6
               if (e is ResolvableApiException) {
                   // Location settings are not satisfied, but this can be fixed
                   // by showing the user a dialog.
                   try {
                       // Show the dialog by calling startResolutionForResult(),
                       // and check the result in onActivityResult().
                       e.startResolutionForResult(this,
                           REQUEST_CHECK_SETTINGS)
                   } catch (sendEx: IntentSender.SendIntentException) {
                       // Ignore the error.
                   }
               }
           }
       }

       // 1
       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
           super.onActivityResult(requestCode, resultCode, data)
           if (requestCode == REQUEST_CHECK_SETTINGS) {
               if (resultCode == Activity.RESULT_OK) {
                   locationUpdateState = true
                   startLocationUpdates()

               }
           }
   //        if (requestCode == PLACE_PICKER_REQUEST) {
    //           if (resultCode == RESULT_OK) {
   //                val place = PlacePicker.getPlace(this, data)
    //               var addressText = place.name.toString()
   //                addressText += "\n" + place.address.toString()

    //               placeMarkerOnMap(place.latLng)
   //            }
     //      }

       }

       // 2
       override fun onPause() {
           super.onPause()
           fusedLocationClient.removeLocationUpdates(locationCallback)
       }

       // 3
       public override fun onResume() {
           super.onResume()
           if (!locationUpdateState) {
               startLocationUpdates()
           }
       }*/


}

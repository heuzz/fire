package com.example.fire_manager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.security.MessageDigest

class MainActivity : AppCompatActivity(),MapView.POIItemEventListener {
    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        try {
            val packageInfo = packageManager.getPackageInfo(
                packageName, PackageManager.GET_SIGNING_CERTIFICATES
            )
            val signingInfo = packageInfo.signingInfo.apkContentsSigners

            for (signature in signingInfo) {
                val messageDigest = MessageDigest.getInstance("SHA")
                messageDigest.update(signature.toByteArray())
                val keyHash = String(Base64.encode(messageDigest.digest(), 0))
                Log.d("KeyHash", keyHash)
            }

        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }

        //kakaomap
        val mapView = MapView(this)
        val map_view = findViewById<View>(R.id.mapView) as RelativeLayout
        val mapViewContainer = map_view
        mapViewContainer.addView(mapView)
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(33.46194, 126.90892), true)
        mapView.setZoomLevel(6, true)
        mapView.setCalloutBalloonAdapter(CustomCalloutBalloonAdapter(layoutInflater))
        mapView.setPOIItemEventListener(this)
        // kakao map marker
        val marker1 = MapPOIItem()
        marker1.itemName = "소화전 1번"
        marker1.tag = 0
        marker1.mapPoint = MapPoint.mapPointWithGeoCoord(33.46194, 126.90892)
        marker1.markerType = MapPOIItem.MarkerType.BluePin
        marker1.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker1)

        val marker2 = MapPOIItem()
        marker2.itemName = "소화전 2번"
        marker2.tag = 0
        marker2.mapPoint = MapPoint.mapPointWithGeoCoord(33.45869965890663, 126.94254288170762)
        marker2.markerType = MapPOIItem.MarkerType.BluePin
        marker2.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker2)
        Log.d("slect_test","맵은 잘 될거고?")
    }

    override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
       val intent = Intent(this,InformationActivity::class.java)
        if (poiItem != null) {
            intent.putExtra("name","${poiItem.itemName}")
        }
        startActivity(intent)
        finish()

    }

    override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {

    }

}
    class CustomCalloutBalloonAdapter(inflater: LayoutInflater) : CalloutBalloonAdapter{

        val mCalloutBalloon: View =inflater.inflate(R.layout.custom_callout_balloon,null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.text_name)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            name.text = poiItem?.itemName
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
            return mCalloutBalloon
    }
}



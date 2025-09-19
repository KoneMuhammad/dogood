package com.kone.fullbodymuscle.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
/**
//on button click location ask
//check before button click
//if granted could all be in one function
// within composable function launch sideeffect becuase its asynchronous
// persisit location data -

@Composable
fun GetUserPermission() {
    val context = LocalContext.current
    PermissionButton() {}

    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {
            //fetch location -> pass in location to function
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->

                }
        }
        else -> {
            //permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

            val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val fusedLocationClient: FusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->

                    }
            } else {
                //function name with default value which should be cloudy

            }

        }

        permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

    }


@Composable
fun PermissionButton(onClick: () -> Unit) {
    Button(onClick = onClick) { }
}
        */
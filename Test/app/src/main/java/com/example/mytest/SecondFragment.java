package com.example.mytest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements OnMapReadyCallback, PermissionsListener
{
    private static SecondFragment secondFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (secondFragment == null)
        {
            secondFragment = this;
        }
    }

    private MapView mapView;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private ImageButton getLocation;
    private static String lat;
    private static String lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        mapView = view.findViewById(R.id.mapView);
        getLocation = view.findViewById(R.id.imageButton);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    public void setPosition(String x, String y)
    {
        lat = x;
        lng = y;
    }

    public static SecondFragment getInstance()
    {
        if (secondFragment == null)
        {
            secondFragment = new SecondFragment();
        }
        return secondFragment;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain)
    {
        Toast.makeText(getContext(), "R.string.user_location_permission_explanation", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted)
    {
        if (granted)
        {
            mapboxMap.getStyle(new Style.OnStyleLoaded()
            {
                @Override
                public void onStyleLoaded(@NonNull Style style)
                {
                    enableLocationComponent(style);
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "R.string.user_location_permission_not_granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap)
    {
        SecondFragment.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded()
        {
            @Override
            public void onStyleLoaded(@NonNull Style style)
            {
                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener()
                {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Create bookmark");
                        builder.setMessage("Lat: " + point.getLatitude() + "\n" + "Long: " + point.getLongitude());
                        // Set up the input
                        final EditText input = new EditText(getActivity());
                        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);
                        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                BookmarkManager.getInstance().insertNewLocation(input.getText().toString(), Double.toString(point.getLatitude()), Double.toString(point.getLongitude()));
                                Toast.makeText(getActivity(), "Your bookmark added", Toast.LENGTH_LONG).show();
                                mapboxMap.addMarker(new MarkerOptions().position(point).title(input.getText().toString()));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                            }
                        });
                        builder.create().show();
                        return false;
                    }
                });

                ArrayList<Location> bookmarks = BookmarkManager.getInstance().getAllLocations();

                for (int i = 0; i < bookmarks.size(); i++)
                {
                    mapboxMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(bookmarks.get(i).getX()), Double.parseDouble(bookmarks.get(i).getY()))).title(bookmarks.get(i).getLocationName()));
                }

                System.out.println("******");
                System.out.println(lat);
                System.out.println(lng);

                getLocation.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        enableLocationComponent(style);
                    }
                });

                if (lat == null)
                {
                    enableLocationComponent(style);
                }
                else
                {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).zoom(10).build();
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000);
                }
            }
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle)
    {
        if (PermissionsManager.areLocationPermissionsGranted(getContext()))
        {
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(getContext()).pulseEnabled(true).build();

            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(getContext(), loadedMapStyle).locationComponentOptions(customLocationComponentOptions).build());

            locationComponent.setLocationComponentEnabled(true);

            locationComponent.setCameraMode(CameraMode.TRACKING);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(locationComponent.getLastKnownLocation())).zoom(10).build();
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000);

            locationComponent.setRenderMode(RenderMode.COMPASS);
        }
        else
        {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
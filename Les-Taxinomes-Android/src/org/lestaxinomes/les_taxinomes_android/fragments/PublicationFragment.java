package org.lestaxinomes.les_taxinomes_android.fragments;

import java.util.ArrayList;
import java.util.List;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.entities.GIS;
import org.lestaxinomes.les_taxinomes_android.entities.Licence;
import org.lestaxinomes.les_taxinomes_android.entities.Media;
import org.lestaxinomes.les_taxinomes_android.model.CreateMediaModel;
import org.lestaxinomes.les_taxinomes_android.utils.GISUtils;
import org.lestaxinomes.les_taxinomes_android.utils.LoginUtils;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableCreateMediaView;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableLicenceSingletonView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PublicationFragment extends BaseFragment {

	private ImageView imageView;
	private MapView mapView;

	MapController myMapController;
	MyLocationOverlay location = null;
	Double currentLat;
	Double currentLon;
	LocationManager lm;
	LocationListener ll;
	boolean mapAlreadyLoaded = false;

	private class Myll implements LocationListener {

		public void onLocationChanged(Location location) {
			currentLat = location.getLatitude();
			currentLon = location.getLongitude();
			if (!mapAlreadyLoaded) {
				run();
				mapAlreadyLoaded = true;
			}
		}

		public void run() {
			location.runOnFirstFix(new Runnable() {
				public void run() {
					Toast.makeText(getActivity(),
							"votre localisation est "+currentLat.toString()+"-"+currentLon.toString(), Toast.LENGTH_LONG)
							.show();
					GeoPoint mediaLocalisation = new GeoPoint(currentLat,
							currentLon);
					GIS mediaGIS = new GIS();
					mediaGIS.setLatitude(currentLat);
					mediaGIS.setLongitude(currentLon);

					mapView.getController().animateTo(location.getMyLocation());

					List<Media> mediaList = new ArrayList<Media>();
					Media mockMedia = new Media();
					mockMedia.setGis(mediaGIS);
					mediaList.add(mockMedia);
					GISUtils.setMediaListOnMapView(mediaList, mapView);

					mapView.invalidate();

				}
			});
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.publication_fragment, container,
				false);

		String loadedImageUri = null;
		Intent intent = getActivity().getIntent();
		if (intent.getExtras() != null) {
			if (intent.getExtras().get("loadedImageUri") != null) {
				loadedImageUri = intent.getExtras().get("loadedImageUri")
						.toString();
			}

		}

		imageView = (ImageView) view.findViewById(R.id.result);

		Bitmap bitmap = BitmapFactory.decodeFile(loadedImageUri);
		TextView uri = (TextView) view.findViewById(R.id.publicationDocUri);
		uri.setText(loadedImageUri);

		imageView.setImageBitmap(bitmap);

		// load licences selectlist

		Spinner spinner = (Spinner) view.findViewById(R.id.licenceSpinner);
		final List<Licence> list = UpdatableLicenceSingletonView.getInstance()
				.getLicences();

		ArrayAdapter<Licence> dataAdapter = new ArrayAdapter<Licence>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				Activity currentAct = (Activity) arg1.getContext();
				TextView licenceId = (TextView) currentAct
						.findViewById(R.id.publicationLicenceId);
				licenceId.setText(list.get(pos).getId().toString());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button pb = (Button) view.findViewById(R.id.publishbutton);
		pb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				EditText title = (EditText) v.getRootView().findViewById(
						R.id.publicationTitle);
				EditText description = (EditText) v.getRootView().findViewById(
						R.id.publicationDescription);
				TextView uri = (TextView) v.getRootView().findViewById(
						R.id.publicationDocUri);
				TextView pleaseWait = (TextView) v.getRootView().findViewById(
						R.id.publicationPleaseWait);
				pleaseWait.setVisibility(View.VISIBLE);

				TextView licenceId = (TextView) v.getRootView().findViewById(
						R.id.publicationLicenceId);

				Media mediaToBe = new Media();
				mediaToBe.setTitre(title.getText().toString());
				mediaToBe.setDescription(description.getText().toString());
				mediaToBe.setLicenceId(Integer.parseInt(licenceId.getText()
						.toString()));

				TextView lat = (TextView) v.getRootView().findViewById(
						R.id.publicationLat);

				TextView lon = (TextView) v.getRootView().findViewById(
						R.id.publicationLon);

				if (lat.getText().toString() != ""
						&& lon.getText().toString() != "") {

					GIS mediaGIS = new GIS();
					mediaGIS.setLatitude(Double.valueOf(lat.getText()
							.toString()));
					mediaGIS.setLongitude(Double.valueOf(lon.getText()
							.toString()));
					mediaToBe.setGis(mediaGIS);
				}

				CreateMediaModel cmm = new CreateMediaModel();

				UpdatableCreateMediaView ucmv = new UpdatableCreateMediaView(v,
						cmm);
				cmm.addView(ucmv);

				cmm.setLocalMediaUri(uri.getText().toString());
				cmm.setMedia(mediaToBe);

				cmm.setLogin(LoginUtils.getLogin(getActivity()));
				cmm.setPassword(LoginUtils.getPassword(getActivity()));

				cmm.createMedia();

			}
		});

		Button llb = (Button) view.findViewById(R.id.loadLocationbutton);
		llb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(),
						"chargement de votre localisation", Toast.LENGTH_LONG)
						.show();

				// TODO store the lat and lon into something accessible in the
				// publishbutton onclick

				mapView = (MapView) v.getRootView().findViewById(
						R.id.publicationMapView);
				mapView.getController().setZoom(10);

				// by default if no gps : Brest
				Double latitude = 48.3928;
				Double longitude = -4.445702;
				mapView.getController().setCenter(
						new GeoPoint(latitude, longitude));

				// by default if gps enabled: media localisation is the current
				// location
				// of the user
				lm = (LocationManager) getActivity().getSystemService(
						Context.LOCATION_SERVICE);
				ll = new Myll();
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
						0, ll);
				location = new MyLocationOverlay(getActivity()
						.getApplicationContext(), mapView);
				// ajouter la loc
				mapView.getOverlays().add(location);
				// afficher
				location.enableMyLocation();
			}

		});

		return view;
	}
}

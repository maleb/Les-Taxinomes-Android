package org.lestaxinomes.les_taxinomes_android.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lestaxinomes.les_taxinomes_android.R;
import org.lestaxinomes.les_taxinomes_android.activities.MapActivity;
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
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.Settings;
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

public class PublicationFormFragment extends BaseFragment {

	private ImageView imageView;
	private MapView mapView;

	MyLocationOverlay locationOverlay = null;

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
		Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;

		Bitmap bitmap = BitmapFactory.decodeFile(loadedImageUri, options);
		TextView uri = (TextView) view.findViewById(R.id.publicationDocUri);
		uri.setText(loadedImageUri);

		ExifInterface exif;
		try {
			exif = new ExifInterface(loadedImageUri);
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			int rotate = 0;
			if (orientation == 3) {
				rotate = 180;
			} else if (orientation == 6) {
				rotate = 90;
			} else if (orientation == 8) {
				rotate = 270;
			}

			Matrix matrix = new Matrix();
			// matrix.postScale(2, 2); //reduce the size by 2 for having a
			// lighter bitmap to display
			matrix.postRotate(rotate);

			if (bitmap != null) {

				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
				// TextView pleaseWait = (TextView)
				// v.getRootView().findViewById(
				// R.id.publicationPleaseWait);
				// pleaseWait.setVisibility(View.VISIBLE);

				// Toast.makeText(
				// v.getContext(),
				// v.getContext().getResources()
				// .getString(R.string.pleaseWait),
				// Toast.LENGTH_LONG).show();

				ProgressDialog progressDialog = ProgressDialog.show(
						v.getContext(), "Envoi en cours", "Veuillez patienter");

				TextView licenceId = (TextView) v.getRootView().findViewById(
						R.id.publicationLicenceId);

				MapView mapView = (MapView) v.getRootView().findViewById(
						R.id.publicationMapView);

				Media mediaToBe = new Media();
				mediaToBe.setTitre(title.getText().toString());
				mediaToBe.setDescription(description.getText().toString());
				mediaToBe.setLicenceId(Integer.parseInt(licenceId.getText()
						.toString()));

				if (mapView.getVisibility() == View.VISIBLE
						&& mapView.getMapCenter() != null) {

					GIS mediaGIS = new GIS();
					mediaGIS.setLatitude(locationOverlay.getMyLocation()
							.getLatitudeE6() * 1E-6);
					mediaGIS.setLongitude(locationOverlay.getMyLocation()
							.getLongitudeE6() * 1E-6);
					mediaToBe.setGis(mediaGIS);
				}

				CreateMediaModel cmm = new CreateMediaModel();

				UpdatableCreateMediaView ucmv = new UpdatableCreateMediaView(v,
						cmm, progressDialog);
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

				mapView = (MapView) v.getRootView().findViewById(
						R.id.publicationMapView);

				if (mapView.getVisibility() != View.VISIBLE) {
					// mapView invisible -> load location and display mapview
					Toast.makeText(getActivity(),
							getResources().getString(R.string.loadingLocation),
							Toast.LENGTH_LONG).show();

					mapView.getController().setZoom(10);
					mapView.setVisibility(View.VISIBLE);
					mapView.setBuiltInZoomControls(true);

					((Button) v).setText(getResources().getString(
							R.string.hideloadLocation));

					// get the current location and center the map to it
					centerToCurrentLocation();
				} else {
					// mapview visible -> hide it
					mapView.setVisibility(View.GONE);
					((Button) v).setText(getResources().getString(
							R.string.loadLocation));

				}

			}

		});

		return view;
	}

	private void askForGPSIfNeeded() {

		// This verification should be done during onStart() because the system
		// calls
		// this method when the user returns to the activity, which ensures the
		// desired
		// location provider is enabled each time the activity resumes from the
		// stopped state.
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!gpsEnabled) {
			// Build an alert dialog here that requests that the user enable
			// the location services, then when the user clicks the "OK" button,
			// call enableLocationSettings()
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
					getActivity());
			myAlertDialog.setTitle("Activation du GPS");
			myAlertDialog
					.setMessage("Pour améliorer votre positionnement, veuillez activer votre GPS");
			myAlertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// do something when the OK button is clicked
							enableLocationSettings();
						}
					});
			myAlertDialog.setNegativeButton("Annuler",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// do something when the Cancel button is clicked
						}
					});
			myAlertDialog.show();

		}
	}

	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(
				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(settingsIntent);
	}

	private void centerToCurrentLocation() {

		askForGPSIfNeeded();
		locationOverlay = new MyLocationOverlay(getActivity(), mapView);

		// ajouter la loc
		mapView.getOverlays().add(locationOverlay);
		// afficher
		locationOverlay.enableMyLocation();

		locationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						locationOverlay.getMyLocation());

			}
		});
	}
}

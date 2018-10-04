using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;

public class tracking : MonoBehaviour, ITrackableEventHandler {

	public GameObject showObject;
	//private bool isRendered = false;
	private TrackableBehaviour mTrackableBehaviour;
	//private GameObject Rb112;


	// Use this for initialization
	void Start()
	{
		showObject.SetActive(false);

		mTrackableBehaviour.GetComponent<TrackableBehaviour>();
		if(mTrackableBehaviour)
		{
			mTrackableBehaviour.RegisterTrackableEventHandler(this);
		}
		//OnTrackableStateChanged();
	}


	public void OnTrackableStateChanged(TrackableBehaviour.Status previousStatus, TrackableBehaviour.Status newStatus)
	{
		if (newStatus == TrackableBehaviour.Status.DETECTED || newStatus == TrackableBehaviour.Status.TRACKED || newStatus == TrackableBehaviour.Status.EXTENDED_TRACKED)
		{
			// Hide object when target is found
			showObject.SetActive(false);
		}
		else
		{
			// Show object when target is lost
			showObject.SetActive(true);
		}
	}

	void ITrackableEventHandler.OnTrackableStateChanged(TrackableBehaviour.Status previousStatus, TrackableBehaviour.Status newStatus)
	{
		throw new System.NotImplementedException();
	}
}

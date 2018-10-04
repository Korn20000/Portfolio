using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;

public class Autofocus : MonoBehaviour {

	private bool mVuforiaStarted = false;
	private float touchTime;

	void Start () 
	{
		VuforiaARController vuforia = VuforiaARController.Instance;

		if (vuforia != null)
			vuforia.RegisterVuforiaStartedCallback (StartAfterVuforia);
	}

	public void StartAfterVuforia ()
	{
		mVuforiaStarted = true;
		SetAutofocus ();
	}

	void OnApplicationPause(bool pause)
	{
		if (!pause)
		{
			// App resumed
			if (mVuforiaStarted)
			{
				SetAutofocus ();
			}
		}
	}

	private void SetAutofocus()
	{
		if (CameraDevice.Instance.SetFocusMode(CameraDevice.FocusMode.FOCUS_MODE_CONTINUOUSAUTO))
		{
			Debug.Log("Autofocus set");
		}
		else
		{
			Debug.Log("this device doesn't support auto focus");
		}
	}

	void Update()
	{
		foreach (Touch touch in Input.touches)
		{
			if (touch.phase == TouchPhase.Began)
			{
				touchTime = Time.time;
			}
			if (touch.phase == TouchPhase.Ended || touch.phase == TouchPhase.Canceled)
			{
				if (Time.time - touchTime <= 0.5)
				{
					//do stuff as a tap​
				}
				else
				{
					// this is a long press or drag​
					CameraDevice.Instance.SetFocusMode(CameraDevice.FocusMode.FOCUS_MODE_TRIGGERAUTO);
					Debug.Log("focus");
				}
			}
		}
	}
}

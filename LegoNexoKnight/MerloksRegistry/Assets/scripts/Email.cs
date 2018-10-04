using UnityEngine;
using System.Collections;
using System;
using System.Net;
using System.Net.Mail;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using UnityEngine.UI;
using Vuforia;
using System.Linq;

public class Email : MonoBehaviour 
{
	DatabaseAdapter DBAdapter;
	string[] trackableIDs = new string[5];
	string[] emails = new string[5];

	// Use this for initialization
	void Start () 
	{
		
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void LoadActiveTrackableIDs () 
	{
		// Get the Vuforia StateManager
		StateManager sm = TrackerManager.Instance.GetStateManager ();

		// Query the StateManager to retrieve the list of
		// currently 'active' trackables 
		//(i.e. the ones currently being tracked by Vuforia)
		IEnumerable activeTrackables = sm.GetActiveTrackableBehaviours ();

		TrackableBehaviour[] activeTrackablesArray = activeTrackables.Cast<TrackableBehaviour> ().ToArray ();

		// Iterate through the list of active trackables
		Debug.Log ("List of trackables currently active (tracked): ");

		for (int i = 0; i < activeTrackablesArray.Length; i++) {
			trackableIDs [i] = activeTrackablesArray [i].name;
			Debug.Log ("Loaded TrackableID:" + trackableIDs [i]);
		}
	}

	Int32 getRealID(String trackableName) /// our names are shield1 and shield2 but in real life the shields would have definite numbers. 
											//Therefore this method is implemented to accomode arguments in the databaseAdapter.
	{
		if (trackableName.Equals ("shield1")) {
			Debug.Log ("Returning Real ID: 1");
			return 1;
		} else if (trackableName.Equals ("shield2")) {
			Debug.Log ("Returning Real ID: 2");
			return 2;
		}
		else
			return 0;
	}

	void getMails()
	{
		//LoadActiveTrackableIDs (); // DO NOT FORGET TO OUTCOMMENT
		DBAdapter = new DatabaseAdapter();
		DBAdapter.ConnectToDB ();
		for(int i=0; i<trackableIDs.Length; i++)
		{
			if (trackableIDs [i] != null) {
				Debug.Log ("Expect to load real ID OF: " + trackableIDs [i]);
				emails [i] = DBAdapter.ReadMailByID (getRealID (trackableIDs [i]));
				Debug.Log ("Loaded mail: " + emails [i]);
			} else {
				Debug.Log ("Nothing scanned or no more shields on display - getMails - getRealID");
				break;
			}

		}
		DBAdapter.DisconnectFromDB ();
	}

	string getMailsInString()
	{
		string email = String.Empty;

		for (int i = 0; i < emails.Length; i++) {
			if(emails[i] != null){
			email += emails [i];
			if(i != emails.Length-1)
				email += ", ";
			} else {
				Debug.Log ("Mail String bulding end");
				break;
			}
		}
		Debug.Log ("EmailString: " + email);
		return email;
	}

	public void EmailFriend ()
	{
		LoadActiveTrackableIDs ();
		getMails ();
		//subject of the mail
		string subject = MyEscapeURL("Say Hello to Friend");
		//body of the mail which consists of Device Model and its Operating System
		string body = MyEscapeURL("Please Enter your message here\n\n\n\n" + "Regards");
		//Open the Default Mail App
		Application.OpenURL ("mailto:" + getMailsInString() + "?subject=" + subject + "&body=" + body);

	}  

	public void FriendReq()
	{
		LoadActiveTrackableIDs ();
		getMails ();
			//subject of the mail
			string subject = MyEscapeURL("Add");
			//body of the mail which consists of Device Model and its Operating System
			string body = MyEscapeURL("Hi! I would Like to add you as my friend\n\n\n\n" + "Regards " /*ID of scaned"*/);
			//Open the Default Mail App 
		Application.OpenURL ("mailto:" + getMailsInString() + "?subject=" + subject + "&body=" + body);

	}  
		


	string MyEscapeURL (string url) 
	{
		return WWW.EscapeURL(url).Replace("+","%20");


	}


}


using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class ShowButtons : MonoBehaviour 
{

	public GameObject jump;
	public GameObject wave;
	public GameObject rage;



	// Use this for initialization
	void Start () 
	{
		jump = GameObject.FindWithTag ("touchJmp");
		wave = GameObject.FindWithTag ("Wave");
		rage = GameObject.FindWithTag ("Rage");

		jump.SetActive (false);
		wave.SetActive (false);
		rage.SetActive (false);



	}

	// Update is called once per frame
	void Update () {

	}

	public void ShowButs(/*bool clicked*/)
	{
		
		Touch showAll = Input.GetTouch (0);
	
		if (/*clicked == true*/ Input.GetTouch (0).Equals (showAll) && !jump.activeInHierarchy && !wave.activeInHierarchy && !rage.activeInHierarchy) 
		{

			Debug.Log ("show buts called");
			//emo.gameObject.SetActive (clicked);
			jump.SetActive (true);
			wave.SetActive (true);
			rage.SetActive (true);


		}
		else 
		{
			//emo.gameObject.SetActive (clicked);
			jump.SetActive (false);
			wave.SetActive (false);
			rage.SetActive (false);
		}

	}

	public void HideButs()
	{
		Touch HideAll = Input.GetTouch (0);

		if (Input.GetTouch (0).Equals (HideAll) && jump.activeInHierarchy && wave.activeInHierarchy && rage.activeInHierarchy)
		{
			Debug.Log ("hide buts called");
			jump.SetActive (false);
			wave.SetActive (false);
			rage.SetActive (false);
		} 
		else
		{
			jump.SetActive (true);
			wave.SetActive (true);
			rage.SetActive (true);
		}
	}
}

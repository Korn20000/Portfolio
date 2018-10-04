using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class PauseScript : MonoBehaviour
{

	GameObject PauseMenu;
	//bool paused;
	bool muted;
	[SerializeField]
	private GameObject mutePic;
	public Sprite mutedPic, unmutedPic;



	// Use this for initialization
	void Start () {
		//paused = false;
		PauseMenu = GameObject.Find ("PauseMenu");
	}
	
	// Update is called once per frame
	void Update ()
	{
		/*if (Input.GetKeyDown (KeyCode.Escape)) {
			paused = !paused;
		}
		if (paused) {
			PauseMenu.SetActive (true);
			Time.timeScale = 0;
		} else if (!paused) {
			PauseMenu.SetActive (false);
			Time.timeScale = 1;
		}*/
		if (muted) {
			AudioListener.volume = 0;
			mutePic.GetComponent<Image>().sprite = mutedPic;
		} else if (!muted) {
			AudioListener.volume = 1;
			mutePic.GetComponent<Image>().sprite = unmutedPic;
	
		}
	}
	/*public void Resume()
	{
		paused = false;
	}*/

	public void MainMenu()
	{
		Application.LoadLevel (0);
	}

	public void Mute ()
	{
		muted = !muted;
	}



}

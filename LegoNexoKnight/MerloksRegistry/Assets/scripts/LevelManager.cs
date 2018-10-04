using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using UnityEngine.Audio;


public class LevelManager : MonoBehaviour
{
	public Transform MainMenu, optionsMenu,quitMenu;
	public Slider slider;
	public AudioSource mainMusic;


	void Start()
	{
		mainMusic = GetComponent<AudioSource> ();
		slider = GetComponent<Slider> ();
		//PlayerPrefs.SetFloat ("CurVol", 1);
	}


	void Awake()
	{
		if (slider) 
		{
			AudioListener.volume = PlayerPrefs.GetFloat ("CurVol");
			slider.value = AudioListener.volume;
		}
	}


		

	public void LoadLevel(string name)
	{
		PlayerPrefs.SetFloat ("CurVol", AudioListener.volume);
		Application.LoadLevel (name);
	}

	public void ExitGame()
	{
		PlayerPrefs.SetFloat ("CurVol", AudioListener.volume);
			Application.Quit ();

	}

	public void OptionsMenu(bool clicked)
	{
		if (clicked == true)
		{
			optionsMenu.gameObject.SetActive (clicked);
			MainMenu.gameObject.SetActive (false);
		}
		else
		{
			optionsMenu.gameObject.SetActive (clicked);
			MainMenu.gameObject.SetActive (true);

		}
	}

	public void QuitMenu(bool clicked)
	{
		if (clicked == true)
		{
			quitMenu.gameObject.SetActive (clicked);
			MainMenu.gameObject.SetActive (false);
		}
		else
		{
			quitMenu.gameObject.SetActive (clicked);
			MainMenu.gameObject.SetActive (true);

		}
	}

	public void VolumeControl(float volumeControl)
	{
		AudioListener.volume = volumeControl;
		//PlayerPrefs.SetFloat ("CurVol", AudioListener.volume);
	}
}
		

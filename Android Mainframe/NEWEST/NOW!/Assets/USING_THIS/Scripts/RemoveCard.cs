using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RemoveCard : MonoBehaviour {
	
	public GameObject card_type;

	public void removeCard()
	{
		card_type.SetActive (false);
	}
}

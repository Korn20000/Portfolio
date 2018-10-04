using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ButtonPressScript : MonoBehaviour
{

	private int zig_card;
	private GameObject zig_;
	private int zag_card;
	private GameObject zag_;
	private int veer_card;
	private GameObject veer_;
	private int skew_card;
	private GameObject skew_;
	private int lock_card;
	private GameObject lock_;
	private int replicate_card;
	private GameObject replicate_;
	private int block_card;
	private GameObject block_;
	private GameObject outOfCards;

	void Start ()
	{
//		canvas = GameObject.Find ("Canvas");
		zig_card = 5;
		zig_ = GameObject.Find ("Zig_Card");
		zig_.SetActive (false);
		zag_card = 5;
		zag_ = GameObject.Find ("Zag_Card");
		zag_.SetActive (false);
		veer_card = 5;
		veer_ = GameObject.Find ("Veer_Card");
		veer_.SetActive (false);
		skew_card = 5;
		skew_ = GameObject.Find ("Skew_Card");
		skew_.SetActive (false);
		lock_card = 5;
		lock_ = GameObject.Find ("Lock_Card");
		lock_.SetActive (false);
		replicate_card = 5;
		replicate_ = GameObject.Find ("Replicate_Card");
		replicate_.SetActive (false);
		block_card = 5;
		block_ = GameObject.Find ("Block_Card");
		block_.SetActive (false);

		outOfCards = GameObject.Find ("OutOfCards");
		outOfCards.SetActive (false);
	}

	public void RandomCard ()
	{
		if(!zig_.active && !zag_.active && !veer_.active && !skew_.active && !lock_.active && !replicate_.active && !block_.active)
		{
			if (zig_card != 0 || zag_card != 0 || veer_card != 0 || skew_card != 0 || lock_card != 0 || replicate_card != 0 || block_card != 0) {
				switch (Random.Range (1, 8)) {
				case 1:
					if (zig_card == 0) {
						RandomCard ();
						break;
					} else {
						zig_card--;
						addCardToCanvas (1);
					}
					break;
				case 2:
					if (zag_card == 0) {
						RandomCard ();
						break;
					} else {
						zag_card--;
						addCardToCanvas (2);
					}
					break;
				case 3:
					if (veer_card == 0) {
						RandomCard ();
						break;
					} else {
						veer_card--;
						addCardToCanvas (3);
					}
					break;
				case 4:
					if (skew_card == 0) {
						RandomCard ();
						break;
					} else {
						skew_card--;
						addCardToCanvas (4);
					}
					break;
				case 5:
					if (lock_card == 0) {
						RandomCard ();
						break;
					} else {
						lock_card--;
						addCardToCanvas (5);
					}
					break;
				case 6:
					if (replicate_card == 0) {
						RandomCard ();
						break;
					} else {
						replicate_card--;
						addCardToCanvas (6);
					}
					break;
				case 7:
					if (block_card == 0) {
						RandomCard ();
						break;
					} else {
						block_card--;
						addCardToCanvas (7);
					}
					break;
				default: 
					break;
				}
			} else {
				outOfCards.SetActive (true);
			}
		}
	}

	private void addCardToCanvas (int card_id)
	{
		switch (card_id) {
		case 1:
			zig_.SetActive(true);
			break;
		case 2:
			zag_.SetActive (true);
			break;
		case 3:
			veer_.SetActive (true);
			break;
		case 4:
			skew_.SetActive (true);
			break;
		case 5: 
			lock_.SetActive (true);
			break;
		case 6:
			replicate_.SetActive (true);
			break;
		case 7:
			block_.SetActive (true);
			break;
		default:
			break;
		}
	}
}

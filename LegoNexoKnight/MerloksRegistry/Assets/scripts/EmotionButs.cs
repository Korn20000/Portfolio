using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class EmotionButs: MonoBehaviour 
{
	//public GameObject anim;
	public GameObject animAaron;
	public GameObject animRobin;
	//public Animator jmp;
	//public Animator rage;
	//public Animator wave;
	public Animator aaronAnimator;
	public Animator robinAnimator;



	void Start() 
	{

		//anim = GameObject.FindGameObjectWithTag ("Chan");
		animAaron = GameObject.FindGameObjectWithTag ("Aaron");
		animRobin = GameObject.FindGameObjectWithTag ("Robin");
		//jmp = anim.GetComponent<Animator> ();
		//rage = anim.GetComponent<Animator> ();
		//wave = anim.GetComponent<Animator> ();
		aaronAnimator = animAaron.GetComponent<Animator>();
		robinAnimator = animRobin.GetComponent<Animator>();
	
	}

	//public void jumpClick(Touch touch)
	//{
	//	Touch makeMeJump = Input.GetTouch (0);

	//	if (touch.Equals(makeMeJump)) 
	//	{
	//		jmp.Play ("JUMP01", -1, 0f);
	//	}
	//}

	public void Jump()
	{
		Touch makeMeJump = Input.GetTouch (0);

		if (Input.GetTouch (0).Equals (makeMeJump)) 
		{
			//jmp.Play ("SLIDE00", -1, 0f);
			aaronAnimator.Play ("FightStance", -1, 0f);
			robinAnimator.Play ("FightStance", -1, 0f);
		} 
		//else 
	//	{
	//		jmp.Play("WAIT00", -1, 0f);
	//	}

	}

	public void Rage()
	{
		Touch makeMeRage = Input.GetTouch (0);

		if (Input.GetTouch (0).Equals (makeMeRage)) 
		{
			//rage.Play ("LOSE00", -1, 0f);
			aaronAnimator.Play ("Rage", -1, 0f);
			robinAnimator.Play ("Rage", -1, 0f);
		} 
	}

	public void Wave()
	{
		Touch makeMeWave = Input.GetTouch (0);

		if (Input.GetTouch (0).Equals (makeMeWave)) 
		{
			//wave.Play ("WIN00", -1, 0f);
			aaronAnimator.Play ("Wave", -1, 0f);
			robinAnimator.Play ("Wave", -1, 0f);
		} 
	}

	//public void NoJump()
	//{
	//	Touch stopJump = Input.GetTouch (0);

	//	if (Input.GetTouch(0).Equals(stopJump))
	//	{
	//		jmp.Play ("WAIT00", -1, 0);
	//	}

	//}

}

/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.SagasScripts;

/**
 *
 * @author Emperorc
 */

public class SagaOfTheSpectralDancer extends SagasSuperClass
{
	public static String qn1 = "96_SagaOfTheSpectralDancer";
	public static int qnu = 96;
	public static String qna = "Saga of the Spectral Dancer";
	
	public SagaOfTheSpectralDancer()
	{
		super(qnu, qn1, qna);
		NPC = new int[] { 31582, 31623, 31284, 31284, 31611, 31646, 31649, 31653, 31654, 31655, 31656, 31284 };
		Items = new int[] { 7080, 7527, 7081, 7511, 7294, 7325, 7356, 7387, 7418, 7449, 7092, 0 };
		Mob = new int[] { 27272, 27245, 27264 };
		qn = qn1;
		classid = new int[] { 107 };
		prevclass = new int[] { 0x22 };
		X = new int[] { 164650, 47429, 47391 };
		Y = new int[] { -74121, -56923, -56929 };
		Z = new int[] { -2871, -2383, -2370 };
		Text = new String[] {
				"PLAYERNAME! Pursued to here! However, I jumped out of the Banshouren boundaries! You look at the giant as the sign of power!",
				"... Oh ... good! So it was ... let's begin!",
				"I do not have the patience ..! I have been a giant force ...! Cough chatter ah ah ah!",
				"Paying homage to those who disrupt the orderly will be PLAYERNAME's death!",
				"Now, my soul freed from the shackles of the millennium, Halixia, to the back side I come ...",
				"Why do you interfere others' battles?",
				"This is a waste of time.. Say goodbye...!",
				"...That is the enemy",
				"...Goodness! PLAYERNAME you are still looking?",
				"PLAYERNAME ... Not just to whom the victory. Only personnel involved in the fighting are eligible to share in the victory.",
				"Your sword is not an ornament. Don't you think, PLAYERNAME?",
				"Goodness! I no longer sense a battle there now.",
				"let...",
				"Only engaged in the battle to bar their choice. Perhaps you should regret.",
				"The human nation was foolish to try and fight a giant's strength.",
				"Must...Retreat... Too...Strong.",
				"PLAYERNAME. Defeat...by...retaining...and...Mo...Hacker",
				"....! Fight...Defeat...It...Fight...Defeat...It..." };
		registerNPCs();
	}
}

# Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from quests.SagasSuperclass import Quest as JQuest

qn = "68_SagaOfTheSoulHound"
qnu = 68
qna = "Saga of the Soul Hound"

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     # first initialize the quest.  The superclass defines variables, instantiates States, etc
     JQuest.__init__(self,id,name,descr)
     # Next, override necessary variables:
     self.NPC = [32138,31272,31269,31317,32235,31646,31648,31652,31654,31655,31657,32241]
     self.Items = [7080,9802,7081,9741,9723,9726,9729,9732,9735,9738,9719,0]
     self.Mob = [27327,27329,27328]
     self.qn = qn
     self.classid = [132,133]
     self.prevclass = [0x80,0x81]
     self.X = [161719,46087,46066]
     self.Y = [-92823,-36372,-36396]
     self.Z = [-1893,-1685,-1685]
     self.Text = ["PLAYERNAME! Pursued to here! However, I jumped out of the Banshouren boundaries! You look at the giant as the sign of power!",
                  "... Oh ... good! So it was ... let's begin!","I do not have the patience ..! I have been a giant force ...! Cough chatter ah ah ah!",
                  "Paying homage to those who disrupt the orderly will be PLAYERNAME's death!","Now, my soul freed from the shackles of the millennium, Halixia, to the back side I come ...",
                  "Why do you interfere others' battles?","This is a waste of time.. Say goodbye...!","...That is the enemy",
                  "...Goodness! PLAYERNAME you are still looking?","PLAYERNAME ... Not just to whom the victory. Only personnel involved in the fighting are eligible to share in the victory.",
                  "Your sword is not an ornament. Don't you think, PLAYERNAME?","Goodness! I no longer sense a battle there now.","let...","Only engaged in the battle to bar their choice. Perhaps you should regret.",
                  "The human nation was foolish to try and fight a giant's strength.","Must...Retreat... Too...Strong.","PLAYERNAME. Defeat...by...retaining...and...Mo...Hacker","....! Fight...Defeat...It...Fight...Defeat...It..."]
     # finally, register all events to be triggered appropriately, using the overriden values.
     JQuest.registerNPCs(self)

#The following functions are needed for this quest because this single quest actually handles two different classes : Male and Female Kamael Soul Breakers.
#Which is unique to this quest, because all other Saga quests (3rd class change) only apply to one class

 def getClassId(self,player) :
     if player.getAppearance().getSex() : return self.classid[1] #if female kamael
     else : return self.classid[0]                               #else, it's male
     
 def getPrevClass(self,player) :
     if player.getAppearance().getSex() : return self.prevclass[1] #if female kamael
     else : return self.prevclass[0]                               #else, it's male

QUEST       = Quest(qnu,qn,qna)
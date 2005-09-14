print "importing seven signs data: 2004_mammerch"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

# Enchant Armor A
    if event == "1":
        if st.getQuestItemsCount(5575)>=480000 and st.getQuestItemsCount(5965)>=480:
            st.takeItems(5575,480000)
            st.takeItems(5965,480)
            st.giveItems(730,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Enchant Armor B
    if event == "2":
        if st.getQuestItemsCount(5575)>=160000 and st.getQuestItemsCount(5965)>=160:
            st.takeItems(5575,160000)
            st.takeItems(5965,160)
            st.giveItems(948,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Enchant Armor C
    if event == "3":
        if st.getQuestItemsCount(5575)>=30000 and st.getQuestItemsCount(5965)>=30:
            st.takeItems(5575,30000)
            st.takeItems(5965,30)
            st.giveItems(952,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Enchant Armor D
    if event == "4":
        if st.getQuestItemsCount(5575)>=12000 and st.getQuestItemsCount(5965)>=12:
            st.takeItems(5575,12000)
            st.takeItems(5965,12)
            st.giveItems(956,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Enchant Weapon A
    if event == "5":
        if st.getQuestItemsCount(5575)>=3600000 and st.getQuestItemsCount(5965)>=3600:
            st.takeItems(5575,3600000)
            st.takeItems(5965,3600)
            st.giveItems(729,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Enchant Weapon B
    if event == "6":
        if st.getQuestItemsCount(5575)>=1000000 and st.getQuestItemsCount(5965)>=1000:
            st.takeItems(5575,1000000)
            st.takeItems(5965,1000)
            st.giveItems(947,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Enchant Weapon C
    if event == "7":
        if st.getQuestItemsCount(5575)>=220000 and st.getQuestItemsCount(5965)>=220:
            st.takeItems(5575,220000)
            st.takeItems(5965,220)
            st.giveItems(951,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Enchant Weapon D
    if event == "8":
        if st.getQuestItemsCount(5575)>=100000 and st.getQuestItemsCount(5965)>=100:
            st.takeItems(5575,100000)
            st.takeItems(5965,100)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Gemstone A x 1
    if event == "9":
        if st.getQuestItemsCount(5575)>=30000:
            st.takeItems(5575,30000)
            st.giveItems(2133,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Gemstone A x 10
    if event == "10":
        if st.getQuestItemsCount(5575)>=300000:
            st.takeItems(5575,300000)
            st.giveItems(2133,10)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Gemstone A x 100
    if event == "11":
        if st.getQuestItemsCount(5575)>=3000000:
            st.takeItems(5575,3000000)
            st.giveItems(2133,100)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Crystal A Grade x 1
    if event == "12":
        if st.getQuestItemsCount(5575)>=21000:
            st.takeItems(5575,21000)
            st.giveItems(1461,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Crystal A Grade x 10
    if event == "13":
        if st.getQuestItemsCount(5575)>=210000:
            st.takeItems(5575,210000)
            st.giveItems(1461,10)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Crystal A Grade x 100
    if event == "14":
        if st.getQuestItemsCount(5575)>=2100000:
            st.takeItems(5575,2100000)
            st.giveItems(1461,100)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Blessed Scroll of Escape
    if event == "15":
        if st.getQuestItemsCount(5575)>=150000:
            st.takeItems(5575,150000)
            st.giveItems(1538,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Strenght
    if event == "16":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4469,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Strenght
    if event == "17":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4470,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Constitution
    if event == "18":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4471,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Constitution
    if event == "19":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4472,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Dexterity
    if event == "20":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4473,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Dexterity
    if event == "21":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4474,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Inteligence
    if event == "22":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4475,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Inteligence
    if event == "23":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4476,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Mental Strenght
    if event == "24":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4477,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Mental Strenght
    if event == "25":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4478,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Wit
    if event == "26":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4479,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of Wit
    if event == "27":
        if st.getQuestItemsCount(5575)>=42000:
            st.takeItems(5575,42000)
            st.giveItems(4480,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "28":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4553,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "29":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4554,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "30":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4555,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "31":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4556,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "32":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4557,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "33":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4558,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "34":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4559,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "35":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4560,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "36":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4561,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "37":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4562,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "38":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4563,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "39":
        if st.getQuestItemsCount(5575)>=60000:
            st.takeItems(5575,60000)
            st.giveItems(4564,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "40":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4589,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "41":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4590,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "42":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4591,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "43":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4592,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "44":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4593,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "45":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4594,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "46":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4595,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "47":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4596,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "48":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4597,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "49":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4598,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "50":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4599,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "51":
        if st.getQuestItemsCount(5575)>=72000:
            st.takeItems(5575,72000)
            st.giveItems(4600,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "52":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4601,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "53":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4602,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "54":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4603,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "55":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4604,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "56":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4605,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "57":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4606,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "58":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4607,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "59":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4608,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "60":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4609,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "61":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4610,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "62":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4611,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "63":
        if st.getQuestItemsCount(5575)>=108000:
            st.takeItems(5575,108000)
            st.giveItems(4612,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "64":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4613,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Strenght
    if event == "65":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4614,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "66":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4615,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Constitution
    if event == "67":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4616,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "68":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4617,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Dexterity
    if event == "69":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4618,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "70":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4619,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Inteligence
    if event == "71":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4620,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "72":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4621,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Mental Strenght
    if event == "73":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4622,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "74":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4623,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

# Dye of High Degree of Wit
    if event == "75":
        if st.getQuestItemsCount(5575)>=174000:
            st.takeItems(5575,174000)
            st.giveItems(4624,1)
            htmltext = "Item has been succesfully purchased."
	else:
             htmltext = "You do not have enough ancient adena."

    if event == "0":
      htmltext = "Cancel."
    
    if htmltext != event:
      st.setState(COMPLETED)
      st.exitQuest(1)

    return htmltext

 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say with you</body></html>"
   st.setState(STARTED)
   if npcId == 8113 :
      htmltext = "1.htm"
   return htmltext

QUEST       = Quest(2004,"2004_mammerch","Seven_signs")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(8113)

STARTED.addTalkId(8113)
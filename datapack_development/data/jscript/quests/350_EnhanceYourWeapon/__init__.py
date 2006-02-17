# Weapon SA Quest Written By MickyLee
# rewritten by Questdevs Team
print "importing quests: 350: Enhance Your Weapon"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

NPC=[7115,7856,7194];

RED_SOUL_CRYSTAL0_ID = 4629
RED_SOUL_CRYSTAL1_ID = 4630
RED_SOUL_CRYSTAL2_ID = 4631
RED_SOUL_CRYSTAL3_ID = 4632
RED_SOUL_CRYSTAL4_ID = 4633
RED_SOUL_CRYSTAL5_ID = 4634
RED_SOUL_CRYSTAL6_ID = 4635
RED_SOUL_CRYSTAL7_ID = 4636
RED_SOUL_CRYSTAL8_ID = 4637
RED_SOUL_CRYSTAL9_ID = 4638
RED_SOUL_CRYSTAL10_ID = 4639
RED_SOUL_CRYSTALX_ID = 4662
GREEN_SOUL_CRYSTAL0_ID = 4640
GREEN_SOUL_CRYSTAL1_ID = 4641
GREEN_SOUL_CRYSTAL2_ID = 4642
GREEN_SOUL_CRYSTAL3_ID = 4643
GREEN_SOUL_CRYSTAL4_ID = 4644
GREEN_SOUL_CRYSTAL5_ID = 4645
GREEN_SOUL_CRYSTAL6_ID = 4646
GREEN_SOUL_CRYSTAL7_ID = 4647
GREEN_SOUL_CRYSTAL8_ID = 4648
GREEN_SOUL_CRYSTAL9_ID = 4649
GREEN_SOUL_CRYSTAL10_ID = 4650
GREEN_SOUL_CRYSTALX_ID = 4663
BLUE_SOUL_CRYSTAL0_ID = 4651
BLUE_SOUL_CRYSTAL1_ID = 4652
BLUE_SOUL_CRYSTAL2_ID = 4653
BLUE_SOUL_CRYSTAL3_ID = 4654
BLUE_SOUL_CRYSTAL4_ID = 4655
BLUE_SOUL_CRYSTAL5_ID = 4656
BLUE_SOUL_CRYSTAL6_ID = 4657
BLUE_SOUL_CRYSTAL7_ID = 4658
BLUE_SOUL_CRYSTAL8_ID = 4659
BLUE_SOUL_CRYSTAL9_ID = 4660
BLUE_SOUL_CRYSTAL10_ID = 4661
BLUE_SOUL_CRYSTALX_ID = 4664


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event ["7115-04.htm","7856-04.htm","7194-04.htm"] :
        st.set("cond","1")
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    elif event ["7115-09.htm","7856-09.htm","7194-09.htm"] :
        st.giveItems(RED_SOUL_CRYSTAL0_ID,1)
    elif event ["7115-10.htm","7856-10.htm","7194-10.htm"] :
        st.giveItems(GREEN_SOUL_CRYSTAL0_ID,1)
    elif event in ["7115-11.htm","7856-11.htm","7194-11.htm"] :
        st.giveItems(BLUE_SOUL_CRYSTAL0_ID,1)
    elif event == "exit" :
        st.set("cond","0")
        st.setState(COMPLETED)
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId in NPC and int(st.get("cond")) == 0:   
        htmltext = npcId+"-01.htm"
   elif npcId in NPC and int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL4_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL5_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL6_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL7_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL8_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL9_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL10_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL4_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL5_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL6_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL7_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL8_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL9_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL10_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL4_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL5_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL6_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL7_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL8_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL9_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL10_ID) != 0 :
        htmltext = npcId+"-03.htm"
   elif npcId in NPC and int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) == 0 and st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) == 0 and st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) == 0 :
        htmltext = npcId+"-21.htm"
   return htmltext

 

QUEST       = Quest(350,"350_EnhanceYourWeapon","Enhance Your Weapon")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7115,7856,7194)

STARTING.addTalkId(7115,7856,7194)

STARTED.addTalkId(7115,7856,7194)


STARTED.addQuestDrop(4629,RED_SOUL_CRYSTAL0_ID,1)
STARTED.addQuestDrop(4640,GREEN_SOUL_CRYSTAL0_ID,1)
STARTED.addQuestDrop(4651,BLUE_SOUL_CRYSTAL0_ID,1)

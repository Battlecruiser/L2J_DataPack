# Maked by Mr. Have fun! Version 0.2
print "importing quests: 104: Spirit Of Mirror"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GALLINS_OAK_WAND_ID = 748
WAND_SPIRITBOUND1_ID = 1135
WAND_SPIRITBOUND2_ID = 1136
WAND_SPIRITBOUND3_ID = 1137
WAND_OF_ADEPT_ID = 747

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(GALLINS_OAK_WAND_ID,1)
      st.giveItems(GALLINS_OAK_WAND_ID,1)
      st.giveItems(GALLINS_OAK_WAND_ID,1)
      htmltext = "7017-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7017 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 0 :
          htmltext = "7017-00.htm"
        elif st.getPlayer().getLevel() >= 10 :
          htmltext = "7017-02.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7017-06.htm"
      else:
        htmltext = "7017-06.htm"
   elif npcId == 7017 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7017 and int(st.get("cond")) and st.getQuestItemsCount(GALLINS_OAK_WAND_ID)>=1 and (st.getQuestItemsCount(WAND_SPIRITBOUND1_ID)==1 and st.getQuestItemsCount(WAND_SPIRITBOUND2_ID)==1 and st.getQuestItemsCount(WAND_SPIRITBOUND3_ID)==1)==0 :
      htmltext = "7017-04.htm"
   elif npcId == 7017 and int(st.get("cond")) and st.getQuestItemsCount(WAND_SPIRITBOUND1_ID)==1 and st.getQuestItemsCount(WAND_SPIRITBOUND2_ID)==1 and st.getQuestItemsCount(WAND_SPIRITBOUND3_ID)==1 and int(st.get("onlyone"))==0 :
      if int(st.get("id")) != 104 :
        st.set("id","104")
        st.takeItems(WAND_SPIRITBOUND1_ID,1)
        st.takeItems(WAND_SPIRITBOUND2_ID,1)
        st.takeItems(WAND_SPIRITBOUND3_ID,1)
        st.giveItems(WAND_OF_ADEPT_ID,1)
        htmltext = "7017-05.htm"
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        st.set("onlyone","1")
   elif npcId == 7045 and int(st.get("cond")) :
      htmltext = "7045-01.htm"
   elif npcId == 7043 and int(st.get("cond")) :
      htmltext = "7043-01.htm"
   elif npcId == 7041 and int(st.get("cond")) :
      htmltext = "7041-01.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5003 :
      st.set("id","0") if int(st.get("cond")) == 1 and 
      st.getQuestItemsCount(GALLINS_OAK_WAND_ID) > 0 and 
      st.getQuestItemsCount(WAND_SPIRITBOUND1_ID) == 0 :
      	
      	st.takeItems(GALLINS_OAK_WAND_ID,1) 
      	st.giveItems(WAND_SPIRITBOUND1_ID,1) 
      	st.playSound("ItemSound?.quest_middle") 
      	st.set("cond","2") 
      	
   elif npcId == 5004 :
      st.set("id","0") if int(st.get("cond")) == 2 and 
      st.getQuestItemsCount(GALLINS_OAK_WAND_ID) > 0 and 
      st.getQuestItemsCount(WAND_SPIRITBOUND2_ID) == 0 : 

		st.takeItems(GALLINS_OAK_WAND_ID,1) 
		st.giveItems(WAND_SPIRITBOUND2_ID,1) 
		st.playSound("ItemSound?.quest_middle") 
		st.set("cond","3") 

   elif npcId == 5005 :
      st.set("id","0") if int(st.get("cond")) == 3 and 
      st.getQuestItemsCount(GALLINS_OAK_WAND_ID) > 0 and 
      st.getQuestItemsCount(WAND_SPIRITBOUND3_ID) == 0 : 

		st.takeItems(GALLINS_OAK_WAND_ID,1) 
		st.giveItems(WAND_SPIRITBOUND3_ID,1) 
		st.playSound("ItemSound?.quest_middle") 
		st.set("cond","4") 

   return

QUEST       = Quest(104,"104_SpiritOfMirror","Spirit Of Mirror")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7017)

STARTED.addTalkId(7017)
STARTED.addTalkId(7041)
STARTED.addTalkId(7043)
STARTED.addTalkId(7045)

STARTED.addKillId(5003)
STARTED.addKillId(5004)
STARTED.addKillId(5005)

STARTED.addQuestDrop(5003,WAND_SPIRITBOUND1_ID,1)
STARTED.addQuestDrop(5004,WAND_SPIRITBOUND2_ID,1)
STARTED.addQuestDrop(5005,WAND_SPIRITBOUND3_ID,1)
STARTED.addQuestDrop(7017,GALLINS_OAK_WAND_ID,1)
STARTED.addQuestDrop(7017,GALLINS_OAK_WAND_ID,1)
STARTED.addQuestDrop(7017,GALLINS_OAK_WAND_ID,1)

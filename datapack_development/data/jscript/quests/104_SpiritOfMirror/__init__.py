# Made by Mr. Have fun! - Version 0.4 by kmarty
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GALLINS_OAK_WAND_ID = 748
WAND_SPIRITBOUND1_ID = 1135
WAND_SPIRITBOUND2_ID = 1136
WAND_SPIRITBOUND3_ID = 1137
WAND_OF_ADEPT_ID = 747

DROPLIST = {
5003: (WAND_SPIRITBOUND1_ID),
5004: (WAND_SPIRITBOUND2_ID),
5005: (WAND_SPIRITBOUND3_ID)
}

# Helper function - If player have all quest items returns 1, otherwise 0
def HaveAllQuestItems (st) :
  for mobId in DROPLIST.keys() :
    if st.getQuestItemsCount(DROPLIST[mobId]) == 0 :
      return 0
  return 1

# Main Quest code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7017-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(GALLINS_OAK_WAND_ID,1)
      st.giveItems(GALLINS_OAK_WAND_ID,1)
      st.giveItems(GALLINS_OAK_WAND_ID,1)
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
   if npcId == 7017 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
     if st.getPlayer().getRace().ordinal() != 0 :
        htmltext = "7017-00.htm"
     elif st.getPlayer().getLevel() >= 10 :
        htmltext = "7017-02.htm"
        return htmltext
     else:
        htmltext = "7017-06.htm"
        st.exitQuest(1)
   elif npcId == 7017 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7017 and int(st.get("cond")) and st.getQuestItemsCount(GALLINS_OAK_WAND_ID)>=1 and not HaveAllQuestItems(st) :
      htmltext = "7017-04.htm"
   elif npcId == 7017 and int(st.get("cond"))==3 and HaveAllQuestItems(st) :
      for mobId in DROPLIST.keys() :
        st.takeItems(DROPLIST[mobId],-1)
      st.giveItems(WAND_OF_ADEPT_ID,1)
      htmltext = "7017-05.htm"
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   elif npcId == 7045 and int(st.get("cond")) :
      htmltext = "7045-01.htm"
      st.set("cond","2")
   elif npcId == 7043 and int(st.get("cond")) :
      htmltext = "7043-01.htm"
      st.set("cond","2")
   elif npcId == 7041 and int(st.get("cond")) :
      htmltext = "7041-01.htm"
      st.set("cond","2")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if int(st.get("cond")) >= 1 and st.getItemEquipped(7) == GALLINS_OAK_WAND_ID and not st.getQuestItemsCount(DROPLIST[npcId]) : # (7) means weapon slot
     st.takeItems(GALLINS_OAK_WAND_ID,1)
     st.giveItems(DROPLIST[npcId],1)
     if HaveAllQuestItems(st) :
       st.set("cond","3")
       st.playSound("ItemSound.quest_middle")
     else :
       st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(104,"104_SpiritOfMirror","Spirit Of Mirror")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7017)

CREATED.addTalkId(7017)

STARTED.addTalkId(7017)
STARTED.addTalkId(7041)
STARTED.addTalkId(7043)
STARTED.addTalkId(7045)

for mobId in DROPLIST.keys():
  STARTED.addKillId(mobId)
  STARTED.addQuestDrop(mobId,DROPLIST[mobId],1)

STARTED.addQuestDrop(7017,GALLINS_OAK_WAND_ID,1)
STARTED.addQuestDrop(7017,GALLINS_OAK_WAND_ID,1)
STARTED.addQuestDrop(7017,GALLINS_OAK_WAND_ID,1)

print "importing quests: 104: Spirit Of Mirror"

# Maked by Mr. Have fun! Version 0.2
print "importing quests: 403: Path To Rogue"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BEZIQUES_LETTER_ID = 1180
SPATOIS_BONES_ID = 1183
HORSESHOE_OF_LIGHT_ID = 1184
WANTED_BILL_ID = 1185
STOLEN_JEWELRY_ID = 1186
STOLEN_TOMES_ID = 1187
STOLEN_RING_ID = 1188
STOLEN_NECKLACE_ID = 1189
BEZIQUES_RECOMMENDATION_ID = 1190
NETIS_BOW_ID = 1181
NETIS_DAGGER_ID = 1182

DROP_CHANCE = { 35:2, 42:3, 45:2, 51:2, 54:8, 60:8 }

STOLEN_ITEM = {
0: (STOLEN_JEWELRY_ID),
1: (STOLEN_TOMES_ID),
2: (STOLEN_RING_ID),
3: (STOLEN_NECKLACE_ID)
}

# Helper function - If player have all stolen items returns 1, otherwise 0
def HaveAllStolenItems (st) :
  for i in STOLEN_ITEM.keys() :
    if st.getQuestItemsCount(STOLEN_ITEM[i]) == 0 :
      return 0
  return 1

# Main Quest code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7379_2" :
          if st.getPlayer().getClassId().getId() == 0x00 :
            if st.getPlayer().getLevel() >= 19 :
              if st.getQuestItemsCount(BEZIQUES_RECOMMENDATION_ID)>0 :
                htmltext = "7379-04.htm"
              else:
                htmltext = "7379-05.htm"
                return htmltext
            else :
              htmltext = "7379-03.htm"
          else:
            if st.getPlayer().getClassId().getId() == 0x07 :
              htmltext = "7379-02a.htm"
            else:
              htmltext = "7379-02.htm"
    elif event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(BEZIQUES_LETTER_ID,1)
        htmltext = "7379-06.htm"
    elif event == "7425_1" :
          st.takeItems(BEZIQUES_LETTER_ID,1)
          if st.getQuestItemsCount(NETIS_BOW_ID) == 0 :
            st.giveItems(NETIS_BOW_ID,1)
          if st.getQuestItemsCount(NETIS_DAGGER_ID) == 0 :
            st.giveItems(NETIS_DAGGER_ID,1)
          st.set("cond","2")
          htmltext = "7425-05.htm"
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
   if npcId == 7379 and int(st.get("cond"))==0 :
     htmltext = "7379-01.htm"
   elif npcId == 7379 and int(st.get("cond")) :
        if st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID) == 0 and HaveAllStolenItems(st) :
          htmltext = "7379-09.htm"
          st.giveItems(BEZIQUES_RECOMMENDATION_ID,1)
          st.takeItems(NETIS_BOW_ID,1)
          st.takeItems(NETIS_DAGGER_ID,1)
          st.takeItems(WANTED_BILL_ID,1)
          for i in STOLEN_ITEM.keys() :
            st.takeItems(STOLEN_ITEM[i],-1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        elif st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID) == 0 and st.getQuestItemsCount(BEZIQUES_LETTER_ID)>0 :
          htmltext = "7379-07.htm"
        elif st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID)>0 :
          htmltext = "7379-08.htm"
          st.takeItems(HORSESHOE_OF_LIGHT_ID,1)
          st.giveItems(WANTED_BILL_ID,1)
          st.set("cond","5")
        elif st.getQuestItemsCount(NETIS_BOW_ID) and st.getQuestItemsCount(NETIS_DAGGER_ID) and st.getQuestItemsCount(WANTED_BILL_ID) == 0 :
          htmltext = "7379-10.htm"
        elif st.getQuestItemsCount(WANTED_BILL_ID) :
          htmltext = "7379-11.htm"
   elif npcId == 7425 and int(st.get("cond")) and st.getQuestItemsCount(BEZIQUES_LETTER_ID)>0 :
        htmltext = "7425-01.htm"
   elif npcId == 7425 and int(st.get("cond")) and st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID)==0 and st.getQuestItemsCount(BEZIQUES_LETTER_ID)==0 :
        if st.getQuestItemsCount(SPATOIS_BONES_ID)<10 :
          htmltext = "7425-06.htm"
        elif st.getQuestItemsCount(WANTED_BILL_ID) :
          htmltext = "7425-08.htm"
        elif st.getQuestItemsCount(SPATOIS_BONES_ID) >= 10 :
          htmltext = "7425-07.htm"
          st.takeItems(SPATOIS_BONES_ID,st.getQuestItemsCount(SPATOIS_BONES_ID))
          st.giveItems(HORSESHOE_OF_LIGHT_ID,1)
          st.set("cond","4")
   elif npcId == 7425 and int(st.get("cond")) and st.getQuestItemsCount(HORSESHOE_OF_LIGHT_ID)>0 :
        htmltext = "7425-08.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if st.getItemEquipped(7) == NETIS_BOW_ID or st.getItemEquipped(7) == NETIS_DAGGER_ID :
     if npcId in (35, 42, 45, 51, 54, 60) :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(SPATOIS_BONES_ID)<10 and st.getRandom(10)<DROP_CHANCE[npcId] :
            st.giveItems(SPATOIS_BONES_ID,1)
            if st.getQuestItemsCount(SPATOIS_BONES_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
              st.set("cond","3")
            else:
              st.playSound("ItemSound.quest_itemget")
     elif npcId == 5038 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(WANTED_BILL_ID)>0 :
            n = st.getRandom(4)
            if st.getQuestItemsCount(STOLEN_ITEM[n]) == 0 :
                st.giveItems(STOLEN_ITEM[n],1)
                if not HaveAllStolenItems(st) :
                  st.playSound("ItemSound.quest_itemget")
                else:
                  st.playSound("ItemSound.quest_middle")
                  st.set("cond","6")
   return

QUEST       = Quest(403,"403_PathToRogue","Path To Rogue")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7379)

STARTING.addTalkId(7379)

STARTED.addTalkId(7379)
STARTED.addTalkId(7425)

STARTED.addKillId(5038)

for StolenItemId in STOLEN_ITEM.keys():
  STARTED.addQuestDrop(5038,STOLEN_ITEM[StolenItemId],1)

for mobId in (35,42,45,51,54,60) :
  STARTED.addKillId(mobId)
  STARTED.addQuestDrop(mobId,SPATOIS_BONES_ID,1)

STARTED.addQuestDrop(7425,NETIS_BOW_ID,1)
STARTED.addQuestDrop(7425,NETIS_DAGGER_ID,1)
STARTED.addQuestDrop(7379,WANTED_BILL_ID,1)
STARTED.addQuestDrop(7425,HORSESHOE_OF_LIGHT_ID,1)
STARTED.addQuestDrop(7379,BEZIQUES_LETTER_ID,1)

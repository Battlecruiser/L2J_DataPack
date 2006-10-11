# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


#item definition
RECOMMENDATION_OF_BALANKI_ID = 2864
RECOMMENDATION_OF_FILAUR_ID = 2865
RECOMMENDATION_OF_ARIN_ID = 2866
MARK_OF_MAESTRO_ID = 2867
LETTER_OF_SOLDER_DETACHMENT_ID = 2868
PAINT_OF_KAMURU_ID = 2869
NECKLACE_OF_KAMURU_ID = 2870
PAINT_OF_TELEPORT_DEVICE_ID = 2871
TELEPORT_DEVICE_ID = 2872
ARCHITECTURE_OF_KRUMA_ID = 2873
REPORT_OF_KRUMA_ID = 2874
INGREDIENTS_OF_ANTIDOTE_ID = 2875
WEIRD_BEES_NEEDLE_ID = 2876
MARSH_SPIDERS_WEB_ID = 2877
BLOOD_OF_LEECH_ID = 2878
BROKEN_TELEPORT_DEVICE_ID = 2916

#This Handels all Mob Drop Data.  npcId:[condition,maxcount,item]
DROPLIST={
20225:[13,10,BLOOD_OF_LEECH_ID],
20229:[13,10,WEIRD_BEES_NEEDLE_ID],
20233:[13,10,MARSH_SPIDERS_WEB_ID],
27133:[4,1,NECKLACE_OF_KAMURU_ID]
}

#if you have all three recommendation it sets a end cond
def recommendationCount(st):
  count=0
  for device in [RECOMMENDATION_OF_ARIN_ID,RECOMMENDATION_OF_FILAUR_ID,RECOMMENDATION_OF_BALANKI_ID]:
    count+=st.getQuestItemsCount(device)
  if count == 3:
    st.set("cond","17")


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
          htmltext = "30531-04.htm"
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
          st.set("cond","1")
    elif event == "30533_1" :
          htmltext = "30533-02.htm"
          st.set("cond","2")
    elif event == "30671_1" :
          htmltext = "30671-02.htm"
          st.giveItems(PAINT_OF_KAMURU_ID,1)
          st.set("cond","3")
    elif event == "30556_1" :
          htmltext = "30556-02.htm"
    elif event == "30556_2" :
          htmltext = "30556-03.htm"
    elif event == "30556_3" :
          htmltext = "30556-05.htm"
          st.takeItems(PAINT_OF_TELEPORT_DEVICE_ID,1)
          st.getPlayer().teleToLocation(140352,-194133,-2028);
          st.giveItems(BROKEN_TELEPORT_DEVICE_ID,1)
          st.set("cond","9")
    elif event == "30556_4" :
          htmltext = "30556-04.htm"
    elif event == "30673_1" :
          htmltext = "30673-04.htm"
          st.giveItems(REPORT_OF_KRUMA_ID,1)
          st.takeItems(WEIRD_BEES_NEEDLE_ID,st.getQuestItemsCount(WEIRD_BEES_NEEDLE_ID))
          st.takeItems(MARSH_SPIDERS_WEB_ID,st.getQuestItemsCount(MARSH_SPIDERS_WEB_ID))
          st.takeItems(BLOOD_OF_LEECH_ID,st.getQuestItemsCount(BLOOD_OF_LEECH_ID))
          st.takeItems(INGREDIENTS_OF_ANTIDOTE_ID,st.getQuestItemsCount(INGREDIENTS_OF_ANTIDOTE_ID))
          st.set("cond","15")
    return htmltext


 def onTalk (self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30531:
     if int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if st.getPlayer().getClassId().getId() == 0x38 and st.getPlayer().getLevel() >38 :
          htmltext = "30531-03.htm"
        elif st.getPlayer().getClassId().getId() == 0x38 :
          htmltext = "30531-01.htm"
          st.exitQuest(1)
        else:
          htmltext = "30531-02.htm"
          st.exitQuest(1)
     elif int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
       htmltext = "<html><head><body>This quest has already been completed.</body></html>"
     elif int(st.get("cond"))>0 and int(st.get("cond"))<17 :
       htmltext = "30531-05.htm"
     elif int(st.get("cond"))==17 :
       st.addExpAndSp(154499,37500)
       htmltext = "30531-06.htm"
       st.giveItems(MARK_OF_MAESTRO_ID,1)
       st.takeItems(RECOMMENDATION_OF_BALANKI_ID,1)
       st.takeItems(RECOMMENDATION_OF_FILAUR_ID,1)
       st.takeItems(RECOMMENDATION_OF_ARIN_ID,1)
       st.set("cond","0")
       st.setState(COMPLETED)
       st.playSound("ItemSound.quest_finish")
       st.set("onlyone","1")
   elif npcId == 30533:
     if (int(st.get("cond"))==1 or int(st.get("cond"))==11 or int(st.get("cond"))==16) and st.getQuestItemsCount(RECOMMENDATION_OF_BALANKI_ID)==0:
       htmltext = "30533-01.htm"
     elif int(st.get("cond"))==2:
       htmltext = "30533-03.htm"
     elif int(st.get("cond"))==6 :
       htmltext = "30533-04.htm"
       st.giveItems(RECOMMENDATION_OF_BALANKI_ID,1)
       st.takeItems(LETTER_OF_SOLDER_DETACHMENT_ID,1)
       st.set("cond","7")
       recommendationCount(st)
     elif int(st.get("cond"))==7 or int(st.get("cond"))==17 :
       htmltext = "30533-05.htm"
   elif npcId == 30671:
     if int(st.get("cond"))==2 :
       htmltext = "30671-01.htm"
     elif int(st.get("cond"))==3:
       htmltext = "30671-03.htm"
     elif int(st.get("cond"))==5 :
       htmltext = "30671-04.htm"
       st.giveItems(LETTER_OF_SOLDER_DETACHMENT_ID,1)
       st.takeItems(NECKLACE_OF_KAMURU_ID,1)
       st.takeItems(PAINT_OF_KAMURU_ID,1)
       st.set("cond","6")
     elif int(st.get("cond"))==6 :
       htmltext = "30671-05.htm"
   elif npcId == 30672 and int(st.get("cond"))==3 :
       htmltext = "30672-01.htm"
   elif npcId == 30675 and int(st.get("cond"))==3:
       st.set("cond","4")
       htmltext="30675-01.htm"
   elif npcId == 30536:
     if (int(st.get("cond"))==1 or int(st.get("cond"))==7 or int(st.get("cond"))==16) and st.getQuestItemsCount(RECOMMENDATION_OF_ARIN_ID)==0:
       htmltext = "30536-01.htm"
       st.giveItems(PAINT_OF_TELEPORT_DEVICE_ID,1)
       st.set("cond","8")
     elif int(st.get("cond"))==8 :
       htmltext = "30536-02.htm"
     elif int(st.get("cond"))==10:
       htmltext = "30536-03.htm"
       st.giveItems(RECOMMENDATION_OF_ARIN_ID,1)
       st.takeItems(TELEPORT_DEVICE_ID,5)
       st.set("cond","11")
       recommendationCount(st)
     elif int(st.get("cond"))==11 or int(st.get("cond"))==17:
       htmltext = "30536-04.htm"
   elif npcId==30556:
     if int(st.get("cond"))==8:
       htmltext = "30556-01.htm"
     elif int(st.get("cond"))==9:
       htmltext = "30556-06.htm"
       st.giveItems(TELEPORT_DEVICE_ID,5)
       st.takeItems(BROKEN_TELEPORT_DEVICE_ID,1)
       st.set("cond","10")
     elif int(st.get("cond"))==10 :
       htmltext = "30556-07.htm"
   elif npcId==30535:  
     if (int(st.get("cond"))==1 or int(st.get("cond"))==7 or int(st.get("cond"))==11) and st.getQuestItemsCount(RECOMMENDATION_OF_FILAUR_ID)==0 :
       htmltext = "30535-01.htm"
       st.giveItems(ARCHITECTURE_OF_KRUMA_ID,1)
       st.set("cond","12")
     elif int(st.get("cond"))==12 :
       htmltext = "30535-02.htm"
     elif int(st.get("cond"))==15 :
       htmltext = "30535-03.htm"
       st.giveItems(RECOMMENDATION_OF_FILAUR_ID,1)
       st.takeItems(REPORT_OF_KRUMA_ID,1)
       st.set("cond","16")
       recommendationCount(st)
     elif int(st.get("cond"))>15:
       htmltext = "30535-04.htm"
   elif npcId == 30673:
     if int(st.get("cond"))==12 :
       htmltext = "30673-01.htm"
       st.giveItems(INGREDIENTS_OF_ANTIDOTE_ID,1)
       st.takeItems(ARCHITECTURE_OF_KRUMA_ID,1)
       st.set("cond","13")
     elif int(st.get("cond"))==14 :
       htmltext = "30673-03.htm"
     elif int(st.get("cond"))==15:
       htmltext = "30673-05.htm"
     elif int(st.get("cond"))==13 :
       htmltext = "30673-02.htm"
   elif npcId==30532 and int(st.get("cond")) :
      htmltext = "30532-01.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   condition,maxcount,item=DROPLIST[npcId]
   count=st.getQuestItemsCount(item)
   if int(st.get("cond")) == condition and count < maxcount :
        st.giveItems(item,1)
        if count == maxcount-1 :
          st.playSound("Itemsound.quest_middle")
          itemcount=0
          for id in [WEIRD_BEES_NEEDLE_ID,MARSH_SPIDERS_WEB_ID,BLOOD_OF_LEECH_ID]:
           itemcount+=st.getQuestItemsCount(id)
          if npcId==27133 or itemcount>29:          
            st.set("cond",str(int(st.get("cond"))+1))
        else:
          st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(231,"231_TestOfMaestro","Test Of Maestro")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30531)

STARTING.addTalkId(30531)
COMPLETED.addTalkId(30531)

for npcId in [30531,30532,30533,30535,30536,30556,30671,30672,30673,30675]:
  STARTED.addTalkId(npcId)

for mobId in [20225,20229,20233,27133]:
  STARTED.addKillId(mobId)

for item in range(2864,2867)+range(2868,2879)+[2916]:
  STARTED.addQuestDrop(30531,item,1)

print "importing quests: 231: Test Of Maestro"
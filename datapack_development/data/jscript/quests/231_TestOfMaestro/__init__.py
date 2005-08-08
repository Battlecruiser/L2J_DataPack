# Maked by Mr. Have fun! Version 0.2
print "importing quests: 231: Test Of Maestro"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

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

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmlfile = "7531-04.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.set("cond","1")
      st.set("cond",str(0))
    elif event == "7533_1" :
          htmltext = "7533-02.htm"
          st.set("cond","2")
    elif event == "7671_1" :
          htmltext = "7671-02.htm"
          st.giveItems(PAINT_OF_KAMURU_ID,1)
    elif event == "7556_1" :
          htmltext = "7556-02.htm"
    elif event == "7556_2" :
          htmltext = "7556-03.htm"
    elif event == "7556_3" :
          htmltext = "7556-05.htm"
          st.giveItems(BROKEN_TELEPORT_DEVICE_ID,1)
          st.takeItems(PAINT_OF_TELEPORT_DEVICE_ID,1)
          st.spawnNpc(150,140402,-194133,-1950)
          st.spawnNpc(150,140352,-194183,-1950)
          st.spawnNpc(150,140352,-194183,-1950)
    elif event == "7556_4" :
          htmltext = "7556-04.htm"
    elif event == "7673_1" :
          htmltext = "7673-04.htm"
          st.giveItems(REPORT_OF_KRUMA_ID,1)
          st.takeItems(WEIRD_BEES_NEEDLE_ID,st.getQuestItemsCount(WEIRD_BEES_NEEDLE_ID))
          st.takeItems(MARSH_SPIDERS_WEB_ID,st.getQuestItemsCount(MARSH_SPIDERS_WEB_ID))
          st.takeItems(BLOOD_OF_LEECH_ID,st.getQuestItemsCount(BLOOD_OF_LEECH_ID))
          st.takeItems(INGREDIENTS_OF_ANTIDOTE_ID,st.getQuestItemsCount(INGREDIENTS_OF_ANTIDOTE_ID))
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7531 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getClassId().getId() == 0x38 and st.getPlayer().getLevel() >= 39 :
          htmltext = "7531-03.htm"
          st.set("cond","1")
          return htmltext
        elif st.getPlayer().getClassId().getId() == 0x38 :
          htmltext = "7531-01.htm"
        else:
          htmltext = "7531-02.htm"
      else:
        htmltext = "7531-02.htm"
   elif npcId == 7531 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7531 and int(st.get("cond"))==1 and GetMemoState>=1 and ((st.getQuestItemsCount(RECOMMENDATION_OF_BALANKI_ID)+st.getQuestItemsCount(RECOMMENDATION_OF_FILAUR_ID)+st.getQuestItemsCount(RECOMMENDATION_OF_ARIN_ID))<3) :
      htmltext = "7531-05.htm"
   elif npcId == 7531 and int(st.get("cond"))==1 and ((st.getQuestItemsCount(RECOMMENDATION_OF_BALANKI_ID)+st.getQuestItemsCount(RECOMMENDATION_OF_FILAUR_ID)+st.getQuestItemsCount(RECOMMENDATION_OF_ARIN_ID))==3) :
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.getPlayer().addExpAndSp(46000,0)
        st.getPlayer().addExpAndSp(0,5900)
      htmltext = "7531-06.htm"
      st.giveItems(MARK_OF_MAESTRO_ID,1)
      st.takeItems(RECOMMENDATION_OF_BALANKI_ID,1)
      st.takeItems(RECOMMENDATION_OF_FILAUR_ID,1)
      st.takeItems(RECOMMENDATION_OF_ARIN_ID,1)
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   elif npcId == 7533 and int(st.get("cond"))==1 and GetMemoState==1 and st.getQuestItemsCount(RECOMMENDATION_OF_BALANKI_ID)==0 :
      htmltext = "7533-01.htm"
   elif npcId == 7533 and int(st.get("cond"))==1 and GetMemoState==2 and st.getQuestItemsCount(LETTER_OF_SOLDER_DETACHMENT_ID)==0 :
      htmltext = "7533-03.htm"
   elif npcId == 7533 and int(st.get("cond"))==1 and GetMemoState==2 and st.getQuestItemsCount(LETTER_OF_SOLDER_DETACHMENT_ID) :
      htmltext = "7533-04.htm"
      st.giveItems(RECOMMENDATION_OF_BALANKI_ID,1)
      st.takeItems(LETTER_OF_SOLDER_DETACHMENT_ID,1)
      st.set("cond","1")
   elif npcId == 7533 and int(st.get("cond"))==1 and st.getQuestItemsCount(RECOMMENDATION_OF_BALANKI_ID) :
      htmltext = "7533-05.htm"
   elif npcId == 7671 and int(st.get("cond"))==1 and GetMemoState==2 and st.getQuestItemsCount(PAINT_OF_KAMURU_ID)==0 and st.getQuestItemsCount(NECKLACE_OF_KAMURU_ID)==0 and st.getQuestItemsCount(LETTER_OF_SOLDER_DETACHMENT_ID)==0 :
      htmltext = "7671-01.htm"
   elif npcId == 7671 and int(st.get("cond"))==1 and st.getQuestItemsCount(PAINT_OF_KAMURU_ID) and st.getQuestItemsCount(NECKLACE_OF_KAMURU_ID)==0 :
      htmltext = "7671-03.htm"
   elif npcId == 7671 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_KAMURU_ID) :
      htmltext = "7671-04.htm"
      st.giveItems(LETTER_OF_SOLDER_DETACHMENT_ID,1)
      st.takeItems(NECKLACE_OF_KAMURU_ID,1)
      st.takeItems(PAINT_OF_KAMURU_ID,1)
   elif npcId == 7671 and int(st.get("cond"))==1 and st.getQuestItemsCount(LETTER_OF_SOLDER_DETACHMENT_ID) :
      htmltext = "7671-05.htm"
   elif npcId == 7672 and int(st.get("cond"))==1 and st.getQuestItemsCount(PAINT_OF_KAMURU_ID) :
      htmltext = "7672-01.htm"
   elif npcId == 7536 and int(st.get("cond"))==1 and GetMemoState==1 and st.getQuestItemsCount(RECOMMENDATION_OF_ARIN_ID)==0 :
      htmltext = "7536-01.htm"
      st.giveItems(PAINT_OF_TELEPORT_DEVICE_ID,1)
      st.set("cond","3")
   elif npcId == 7536 and int(st.get("cond"))==1 and GetMemoState==3 and st.getQuestItemsCount(PAINT_OF_TELEPORT_DEVICE_ID) and st.getQuestItemsCount(TELEPORT_DEVICE_ID)==0 :
      htmltext = "7536-02.htm"
   elif npcId == 7536 and int(st.get("cond"))==1 and GetMemoState==3 and st.getQuestItemsCount(TELEPORT_DEVICE_ID)==5 :
      htmltext = "7536-03.htm"
      st.giveItems(RECOMMENDATION_OF_ARIN_ID,1)
      st.takeItems(TELEPORT_DEVICE_ID,5)
      st.set("cond","1")
   elif npcId == 7536 and int(st.get("cond"))==1 and st.getQuestItemsCount(RECOMMENDATION_OF_ARIN_ID) :
      htmltext = "7536-04.htm"
   elif npcId == 7556 and int(st.get("cond"))==1 and GetMemoState==3 and st.getQuestItemsCount(PAINT_OF_TELEPORT_DEVICE_ID) :
      htmltext = "7556-01.htm"
   elif npcId == 7556 and int(st.get("cond"))==1 and GetMemoState==3 and st.getQuestItemsCount(BROKEN_TELEPORT_DEVICE_ID) :
      htmltext = "7556-06.htm"
      st.giveItems(TELEPORT_DEVICE_ID,5)
      st.takeItems(BROKEN_TELEPORT_DEVICE_ID,1)
   elif npcId == 7556 and int(st.get("cond"))==1 and GetMemoState==3 and st.getQuestItemsCount(TELEPORT_DEVICE_ID)==5 :
      htmltext = "7556-07.htm"
   elif npcId == 7535 and int(st.get("cond"))==1 and GetMemoState==1 and st.getQuestItemsCount(RECOMMENDATION_OF_FILAUR_ID)==0 :
      htmltext = "7535-01.htm"
      st.giveItems(ARCHITECTURE_OF_KRUMA_ID,1)
      st.set("cond","4")
   elif npcId == 7535 and int(st.get("cond"))==1 and GetMemoState==4 and st.getQuestItemsCount(ARCHITECTURE_OF_KRUMA_ID) and st.getQuestItemsCount(REPORT_OF_KRUMA_ID)==0 :
      htmltext = "7535-02.htm"
   elif npcId == 7535 and int(st.get("cond"))==1 and GetMemoState==4 and st.getQuestItemsCount(ARCHITECTURE_OF_KRUMA_ID)==0 and st.getQuestItemsCount(REPORT_OF_KRUMA_ID) :
      htmltext = "7535-03.htm"
      st.giveItems(RECOMMENDATION_OF_FILAUR_ID,1)
      st.takeItems(REPORT_OF_KRUMA_ID,1)
      st.set("cond","1")
   elif npcId == 7535 and int(st.get("cond"))==1 and st.getQuestItemsCount(RECOMMENDATION_OF_FILAUR_ID) :
      htmltext = "7535-04.htm"
   elif npcId == 7673 and int(st.get("cond"))==1 and GetMemoState==4 and st.getQuestItemsCount(INGREDIENTS_OF_ANTIDOTE_ID)==0 and st.getQuestItemsCount(REPORT_OF_KRUMA_ID)==0 :
      htmltext = "7673-01.htm"
      st.giveItems(INGREDIENTS_OF_ANTIDOTE_ID,1)
      st.takeItems(ARCHITECTURE_OF_KRUMA_ID,1)
   elif npcId == 7673 and int(st.get("cond"))==1 and GetMemoState==4 and st.getQuestItemsCount(INGREDIENTS_OF_ANTIDOTE_ID) and ((st.getQuestItemsCount(WEIRD_BEES_NEEDLE_ID)+st.getQuestItemsCount(MARSH_SPIDERS_WEB_ID)+st.getQuestItemsCount(BLOOD_OF_LEECH_ID))<30) and st.getQuestItemsCount(REPORT_OF_KRUMA_ID)==0 :
      htmltext = "7673-02.htm"
   elif npcId == 7673 and int(st.get("cond"))==1 and GetMemoState==4 and st.getQuestItemsCount(INGREDIENTS_OF_ANTIDOTE_ID) and ((st.getQuestItemsCount(WEIRD_BEES_NEEDLE_ID)+st.getQuestItemsCount(MARSH_SPIDERS_WEB_ID)+st.getQuestItemsCount(BLOOD_OF_LEECH_ID))==30) and st.getQuestItemsCount(REPORT_OF_KRUMA_ID)==0 :
      htmltext = "7673-03.htm"
   elif npcId == 7673 and int(st.get("cond"))==1 and st.getQuestItemsCount(REPORT_OF_KRUMA_ID) :
      htmltext = "7673-05.htm"
   elif npcId == 7532 and int(st.get("cond"))==1 :
      htmltext = "7532-01.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 225 :
      if int(st.get("cond")) and int(st.get("cond")) == 4 and st.getQuestItemsCount(BLOOD_OF_LEECH_ID) < 10 :
        if st.getQuestItemsCount(BLOOD_OF_LEECH_ID) == 9 :
          st.giveItems(BLOOD_OF_LEECH_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(BLOOD_OF_LEECH_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 229 :
      if int(st.get("cond")) and int(st.get("cond")) == 4 and st.getQuestItemsCount(WEIRD_BEES_NEEDLE_ID) < 10 :
        if st.getQuestItemsCount(WEIRD_BEES_NEEDLE_ID) == 9 :
          st.giveItems(WEIRD_BEES_NEEDLE_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(WEIRD_BEES_NEEDLE_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 233 :
      if int(st.get("cond")) and int(st.get("cond")) == 4 and st.getQuestItemsCount(MARSH_SPIDERS_WEB_ID) < 10 :
        if st.getQuestItemsCount(MARSH_SPIDERS_WEB_ID) == 9 :
          st.giveItems(MARSH_SPIDERS_WEB_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(MARSH_SPIDERS_WEB_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 5133 :
      if int(st.get("cond")) and int(st.get("cond")) == 2 and st.getQuestItemsCount(NECKLACE_OF_KAMURU_ID) == 0 and st.getQuestItemsCount(PAINT_OF_KAMURU_ID) :
        st.giveItems(NECKLACE_OF_KAMURU_ID,1)
        st.playSound("Itemsound.quest_middle")
   return

QUEST       = Quest(231,"231_TestOfMaestro","Test Of Maestro")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7531)

STARTED.addTalkId(7531)
STARTED.addTalkId(7532)
STARTED.addTalkId(7533)
STARTED.addTalkId(7535)
STARTED.addTalkId(7536)
STARTED.addTalkId(7556)
STARTED.addTalkId(7671)
STARTED.addTalkId(7672)
STARTED.addTalkId(7673)

STARTED.addKillId(225)
STARTED.addKillId(229)
STARTED.addKillId(233)
STARTED.addKillId(5133)

STARTED.addQuestDrop(7533,RECOMMENDATION_OF_BALANKI_ID,1)
STARTED.addQuestDrop(7535,RECOMMENDATION_OF_FILAUR_ID,1)
STARTED.addQuestDrop(7536,RECOMMENDATION_OF_ARIN_ID,1)
STARTED.addQuestDrop(7671,LETTER_OF_SOLDER_DETACHMENT_ID,1)
STARTED.addQuestDrop(5133,NECKLACE_OF_KAMURU_ID,1)
STARTED.addQuestDrop(7671,PAINT_OF_KAMURU_ID,1)
STARTED.addQuestDrop(7536,PAINT_OF_TELEPORT_DEVICE_ID,1)
STARTED.addQuestDrop(7556,BROKEN_TELEPORT_DEVICE_ID,1)
STARTED.addQuestDrop(7556,TELEPORT_DEVICE_ID,1)
STARTED.addQuestDrop(7536,PAINT_OF_TELEPORT_DEVICE_ID,1)
STARTED.addQuestDrop(7556,BROKEN_TELEPORT_DEVICE_ID,1)
STARTED.addQuestDrop(7673,REPORT_OF_KRUMA_ID,1)
STARTED.addQuestDrop(7535,ARCHITECTURE_OF_KRUMA_ID,1)
STARTED.addQuestDrop(229,WEIRD_BEES_NEEDLE_ID,1)
STARTED.addQuestDrop(233,MARSH_SPIDERS_WEB_ID,1)
STARTED.addQuestDrop(225,BLOOD_OF_LEECH_ID,1)
STARTED.addQuestDrop(7673,INGREDIENTS_OF_ANTIDOTE_ID,1)
